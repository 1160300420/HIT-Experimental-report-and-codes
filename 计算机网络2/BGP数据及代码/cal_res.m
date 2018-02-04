x=[];
y=[];
fid=fopen('C:\Users\lovebear96\Desktop\res.txt');
tline=fgetl(fid)
while ischar(tline)
    for i=1:length(tline)
        if(tline(i)==' ')
            break;
        end       
    end
    x=[x,str2double(tline(1:i-1))];
    y=[y,str2double(tline(i+1:length(tline)))];
    tline = fgetl(fid);
end
fclose(fid);
xx=ceil(log10(x));
cnt=[0 0 0 0 0];
for i=1:length(xx)
    if(xx(i)==0)
       cnt(1)=cnt(1)+1; 
    end
    if(xx(i)==1)
       cnt(2)=cnt(2)+1; 
    end
    if(xx(i)==2)
       cnt(3)=cnt(3)+1; 
    end
    if(xx(i)==3)
       cnt(4)=cnt(4)+1; 
    end
    if(xx(i)==4)
       cnt(5)=cnt(5)+1; 
    end
end
yy=[];
yy(1)=mean(y(1:1));
yy(2)=mean(y(2:10));
yy(3)=mean(y(11:95));
yy(4)=mean(y(96:218));
yy(5)=mean(y(219:238));
xx=[1,2,3,4];
yy=yy(2:5);
plot(xx,yy);
title('Degree #Prefix(ceiling(log)-mean())');
xlabel('Degree');
ylabel('#Prefix');
% scatter(xx,yy);
% title('Degree #Prefix(ceiling(log)-mean())');
% xlabel('Degree');
% ylabel('#Prefix');