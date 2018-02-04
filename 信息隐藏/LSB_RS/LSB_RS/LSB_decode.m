img=imread('C:\Users\lovebear96\Desktop\LSB_RS\eight_LSB.bmp');
[len,wid]=size(img);
m=1;
msg=[];
clen=len*wid/8;
for i=1:len
    for j=1:wid
        msg=[msg,char(bitget(img(i,j),1)+'0')];
        m=m+1;
        if(m>clen*8)
            break;
        end
    end
    if(m>clen*8)
            break;
    end
end
msg=reshape(msg,8,clen);
msg=msg';
for k=1:clen
    msg(k,1:8)=msg(k,end:-1:1);
end
msg_str=char(bin2dec(msg));
msg_str=msg_str';
loc=strfind(msg_str,'#');
msg_str=msg_str(1,1:loc(1)-1);
fid_r=fopen('C:\Users\lovebear96\Desktop\LSB_RS\msg_r.txt','w');
fwrite(fid_r,msg_str);
fclose(fid_r);