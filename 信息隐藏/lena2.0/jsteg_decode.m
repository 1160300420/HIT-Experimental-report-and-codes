fid=fopen('C:\Users\lovebear96\Desktop\lena2.0\encode.dat','rb');
enco=fread(fid,'ubit1');  %get the huffman code
enco=enco(1:length(enco)-6,1);
deco = huffmandeco(enco,dic);   %decode huffman
deco=reshape(deco,1,length(deco));
rev_a=[];
zigzag = [ 0, 1, 8, 16, 9, 2, 3, 10, ...
      17, 24, 32, 25, 18, 11, 4, 5, ...
      12, 19, 26, 33, 40, 48, 41, 34, ...
      27, 20, 13, 6, 7, 14, 21, 28, ...
      35, 42, 49, 56, 57, 50, 43, 36, ...
      29, 22, 15, 23, 30, 37, 44, 51, ...
      58, 59, 52, 45, 38, 31, 39, 46, ...
      53, 60, 61, 54, 47, 55, 62, 63];
zigzag = zigzag + 1;
rev_zigzag=zeros(1,64);   %init
for i=1:64
    rev_zigzag(1,zigzag(1,i))=i;      %reverse zigzag  
end

for i=1:64:length(deco)     
    rev_aa=deco(1,i:i+63);
    rev_a=[rev_a;rev_aa(rev_zigzag)];    %recover the zigzag
end
k=1;
rev_ld2=int8(zeros(len,wid));
flag2=0;
for i=1:8:len
    for j=1:8:wid
        tmp=reshape(rev_a(k,:),8,8);
        rev_ld2(i:i+7,j:j+7)=tmp;       %the pre light dct
        k=k+1;
        if(k>length(deco)/64)
            flag2=1;
            break;
        end
    end
    if(flag2==1)
       break;
    end
end

%decode the ld
m=1;flag=0;
c=[];slen=18*7;
for i=1:len
    for j=1:wid
        if(rev_ld2(i,j)~=0)
           c = [c,char(bitget(rev_ld2(i,j),1)+'0')];
           m=m+1;
           if(m>slen)
               flag=1;
               break;
           end
        end
    end
    if(flag==1)
        break;
    end
end
clen=int8(length(c)/7);
c=reshape(c,clen,7);
ppp=char(bin2dec(c));
ppp=ppp';  %get the string

%reverse light
rev_ld=double(ld).*color;

%idct
for i=1:8:len
    for j=1:8:wid
        rev_img(i:i+7,j:j+7)=idct2(rev_ld(i:i+7,j:j+7))./255;
    end
end
imwrite(rev_img,'C:\Users\lovebear96\Desktop\lena2.0\666eight.bmp');
