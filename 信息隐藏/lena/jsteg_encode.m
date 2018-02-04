str='River Flows In You';   %hidden info
strb=dec2bin(int8(str)); %change to bin
strb=reshape(strb,1,length(str)*7);    %change to one row,convinient  
img = imread('C:\Users\lovebear96\Desktop\lena\eight.bmp');
[len,wid] = size(img);   %get the length and width
len = ceil(len/8)*8;  %add 0
wid = ceil(wid/8)*8;
img(len,wid) = 0;
d=[];

%dct , 8*8 
for i=1:8:len
    for j=1:8:wid
        d(i:i+7,j:j+7)=dct2(img(i:i+7,j:j+7));
    end
end

%light
lighttable=...     %light meter
    [16 11 10 16 24 40 51 61 ; 
    12 12 14 19 26 58 60 55 ; 
    14 13 16 24 40 57 69 56 ; 
    14 17 22 29 51 87 80 62 ; 
    18 22 37 56 68 109 103 77;
    24 35 55 64 81 104 113 92; 
    49 64 78 87 103 121 120 101; 
    72 92 95 98 112 100 103 99];
colortable = ...      %chroma meter
    [17 18 24 47 99 99 99 99 ; 
    18 21 26 66 99 99 99 99 ; 
    24 26 56 99 99 99 99 99 ; 
    47 66 99 99 99 99 99 99 ; 
    99 99 99 99 99 99 99 99 ; 
    99 99 99 99 99 99 99 99 ; 
    99 99 99 99 99 99 99 99 ; 
    99 99 99 99 99 99 99 99];
color = repmat(lighttable,len/8,wid/8);  
ld = int8(d./color);   %light the matrix

%encode,hide info into it
m=1;flag=0;
for i=1:len
    for j=1:wid
        if(ld(i,j)~=1 || ld(i,j)~=-1 || ld(i,j)~=0)
           ld(i,j)=bitset(ld(i,j),1,strb(1,m)-'0');
           m=m+1;
           if(m>length(strb))
               flag=1;
               break;
           end
        end
    end
    if(flag==1)
        break;
    end
end

%zigzag
zigzag = [ 0, 1, 8, 16, 9, 2, 3, 10, ...   %zigzag table
      17, 24, 32, 25, 18, 11, 4, 5, ...
      12, 19, 26, 33, 40, 48, 41, 34, ...
      27, 20, 13, 6, 7, 14, 21, 28, ...
      35, 42, 49, 56, 57, 50, 43, 36, ...
      29, 22, 15, 23, 30, 37, 44, 51, ...
      58, 59, 52, 45, 38, 31, 39, 46, ...
      53, 60, 61, 54, 47, 55, 62, 63];
zigzag = zigzag + 1;  %index
b=[];
data=[];
for i=1:8:len
    for j=1:8:wid
       a = ld(i:i+7,j:j+7); 
       aa = reshape(a,1,64);  %8*8->1*64 to zigzag
       b = [b,aa(zigzag)]; % get data      
    end
end

bkey=unique(b);    %get the unique value
bp=zeros(1,length(bkey));
for i=1:length(bkey)
    bp(1,i)=length(find(b(:)==bkey(i)))/length(b);  %get each value's weight
end

dic=huffmandict(bkey,bp);   %generate the dic 
enco = huffmanenco(b(:),dic);    %huffman encode
fid=fopen('C:\Users\lovebear96\Desktop\lena\encode.dat','wb');
fwrite(fid,enco);
fclose(fid);


