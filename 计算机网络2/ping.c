#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <signal.h>
#include <arpa/inet.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/time.h>
#include <unistd.h>
#include <netinet/in.h>
#include <netdb.h>
#include <netinet/ip.h>
#include <netinet/ip_icmp.h>
#include <setjmp.h>
#include <errno.h>

#define PACKET_SIZE 4096
#define MAX_WAIT_TIME 5
#define MAX_NO_PACKETS 4

char sendpacket[PACKET_SIZE];
char recvpacket[PACKET_SIZE];
int sockfd,datalen=56;    //附带数据大小
int nsend=0,nreceived=0;   //发送，接收序列号
struct sockaddr_in dest_addr,from;    
pid_t pid;       //进程id
struct timeval tvrecv;   //获取当前时间

void statistics(int signo)
{
    printf("\n---------------------------PING statistics-----------------------\n");
    printf("%d pockets transmitted, %d received, %%%d lost\n",nsend,nreceived,(nsend-nreceived)*100/nsend);
    close(sockfd);
    exit(1);
}
//计算rrt
void tv_sub(struct timeval *recv,struct timeval *send)
{
    if((recv->tv_usec-=send->tv_usec)<0){
        --recv->tv_sec;
        recv->tv_usec+=1000000;
    }
    recv->tv_sec-=send->tv_sec;
}

unsigned short chk_sum(unsigned short *addr,int len)
{
    int n=len;
    int sum=0;
    unsigned short *w=addr;
    unsigned short answer=0;
    while(n>1)
    {
        sum+=*w++;
        n-=2;
    }
    if(n==1){
        sum+=*(unsigned char *)w;
    }
    sum=(sum>>16)+(sum&0xffff);
    sum+=(sum>>16);
    answer=~sum;
    return answer;
}




int pack(int pack_no)
{
    int i,packsize;
    struct icmp *icmp;
    struct timeval *tval;
    icmp=(struct icmp*)sendpacket;
    icmp->icmp_type=ICMP_ECHO;  //ICMP
    icmp->icmp_code=0;     //code为0
    icmp->icmp_cksum=0;      //初始校验和
    icmp->icmp_seq=pack_no;     //序列号，每次+1
    icmp->icmp_id=pid;      //标识设置为本进程id
    packsize=8+datalen;      //数据开始处存入当前时间
    tval=(struct timeval *)icmp->icmp_data;
    gettimeofday(tval,NULL);
    icmp->icmp_cksum=chk_sum((unsigned short *)icmp,packsize); //校验和   
    return packsize;
}

int unpack(char *buf,int len)
{
    int i,iphdrlen;
    struct ip *ip;
    struct icmp *icmp;
    struct timeval *tvsend;
    double rtt;
    ip=(struct ip *)buf;     //ip头开始
    iphdrlen=ip->ip_hl<<2;    //ip头长度
    icmp=(struct icmp *)(buf+iphdrlen);    //ICMP头开始
    len-=iphdrlen;
    if(len<8){          //残缺的包，丢弃
        printf("ICMP packets\'s length is less than 8 bytes\n");
        return -1;
    }
    if((icmp->icmp_type==ICMP_ECHOREPLY)&&(icmp->icmp_id==pid))  //判断是不是我们请求的回应包
    {
        tvsend=(struct timeval *)icmp->icmp_data;   //获取ICMP包开头的时间
        tv_sub(&tvrecv,tvsend);        
        rtt=tvrecv.tv_sec*1000+tvrecv.tv_usec/1000;   //计算ttr，都转化为ms
        printf("%d byte from %s: icmp_seq=%u ttl=%d rtt=%.3f ms\n",len,inet_ntoa(from.sin_addr),icmp->icmp_seq,ip->ip_ttl,rtt);
    }else{
        return -1;
    }
}

void recv_packet()
{
    int n,fromlen;
    signal(SIGALRM,statistics);   //建立SIGALARM信号处理函数
    fromlen=sizeof(from);
    alarm(MAX_WAIT_TIME);  //用来设置信号SIGALRM在经过参数seconds指定的秒数后传送给目前的进程
    if((n=recvfrom(sockfd,recvpacket,sizeof(recvpacket),0,(struct sockaddr *)&from,&fromlen))<0)
    {
        if(errno==EINTR)
        {
            printf("time out.\n");
        }else{
            printf("receive error.\n");
        }
    }else{
        gettimeofday(&tvrecv,NULL);
        if(unpack(recvpacket,n)==-1){
            printf("error when unpack packet\n");
        }
    }
    nreceived++;
}

void send_packet()
{
    int packetsize;
    while(nsend<MAX_NO_PACKETS)
    {
        nsend++;
        packetsize=pack(nsend);
        if(sendto(sockfd,sendpacket,packetsize,0,(struct sockaddr *)&dest_addr,sizeof(dest_addr))<0)
        {
            printf("sendto error\n");
            continue;
        }
        recv_packet();
    }
}



int main(int argc,char *argv[])
{
    struct hostent *host;
    struct protoent *protocol;
    unsigned long inaddr;
    int waittime=MAX_WAIT_TIME;   //定义最大等待时间
    int size=50*1024;
    if(argc<2)
    {
        printf("usage:%s hostname/IP address\n",argv[0]);
        exit(1);
    }
    if((protocol=getprotobyname("icmp"))==NULL){
        printf("error when get icmp protocol");
        exit(1);
    }
    if((sockfd=socket(AF_INET,SOCK_RAW,protocol->p_proto))<0){  //创建ICMP原始套接字
        printf("error when create socket\n");
        exit(1);
    }
    setuid(getuid());
    setsockopt(sockfd,SOL_SOCKET,SO_RCVBUF,&size,sizeof(size));
    memset(&dest_addr,0,sizeof dest_addr);
    if((inaddr=inet_addr(argv[1]))==INADDR_NONE)
    {
        if((host=gethostbyname(argv[1]))==NULL){
            printf("unknown host\n");
            exit(1);
        }
        memcpy((char *)&dest_addr.sin_addr,host->h_addr,host->h_length);
    }
    else{
        dest_addr.sin_addr.s_addr=inet_addr(argv[1]);  //赋值目的地址
    }
    pid=getpid();   //获取当前进程id
    printf("ping %s(%s): %d bytes data in ICMP packets.\n",argv[1],inet_ntoa(dest_addr.sin_addr),datalen);
    send_packet();		//发送包
    statistics(SIGALRM);
    return 0;
}
