%RS cal
function y=calRS(img,M)
[len,wid]=size(img);
y=zeros(1,4);  %Rm,Sm,R_m,S_m
for i=1:8:len
    for j=1:8:wid
        tmp=img(i:i+7,j:j+7);
        cor0=spaceCor(tmp);
        cor1=spaceCor(f1(tmp,M));  %非负反转
        cor_1=spaceCor(f_1(tmp,M));  %非正反转
        
        if(cor1>cor0)
            y(1)=y(1)+1;
        elseif(cor1<cor0)
            y(2)=y(2)+1;
        end
        if(cor_1>cor0)
            y(3)=y(3)+1;
        elseif(cor_1<cor0)
            y(4)=y(4)+1;
        end
    end
end
y=y/(len*wid/(64));