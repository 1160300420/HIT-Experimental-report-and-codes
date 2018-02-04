img=imread('C:\Users\lovebear96\Desktop\LSB_RS\eight.bmp');
fid=fopen('C:\Users\lovebear96\Desktop\LSB_RS\msg.txt','r');
msg=fread(fid,'ubit1');
[len,wid]=size(img);
m=1;
for i=1:len
    for j=1:wid
        img(i,j)=bitset(img(i,j),1,msg(m,1));
        m=m+1;
        if(m>length(msg))
            break;
        end
    end
    if(m>length(msg))
            break;
    end
end
imwrite(img,'C:\Users\lovebear96\Desktop\LSB_RS\eight_LSB.bmp');
fclose(fid);