%x->8*8 M random 0,1(8*8)
function y=f_1(x,M)
y=x;
for i=1:8
    for j=1:8
        if(M(i,j)~=0)
            if(mod(x(i,j),2)==0)
                y(i,j)=x(i,j)-1;  %1<->2
            else
                y(i,j)=x(i,j)+1;  
            end
        end
    end
end