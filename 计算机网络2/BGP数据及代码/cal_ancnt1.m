x=[];
y=[];
fid=fopen('C:\Users\lovebear96\Desktop\ancnt1.txt');
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
scatter(log10(x),log(y));
title('Degree Frequency(log-log)');
xlabel('Degree');
ylabel('Frequency');