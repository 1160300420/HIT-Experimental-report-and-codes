img=imread('C:\Users\lovebear96\Desktop\LSB_RS\eight.bmp');
M=randsrc(8,8,[0 1]);
rate =[];
rm=[];
sm=[];
rm_1=[];
sm_1=[];
[len,wid] = size(img);


for i = 1 :11
	for k = 1 : 25
        for j = 1 : len
            if i == 1 continue; end;
            img((i - 2) * 25 + k ,j) = bitset(img(i-1,j), 1, floor(rand()*2));
        end
	end
    m = (i-1) * 25 * len ;
    %าþะดยส
    rate(i) = (m-1)/(len*wid);

    %post LSB
    disp('post_LSB:')
    res2=calRS(img,M); %Rm,Sm,R_m,S_m
    fprintf('Rm: %f  Sm: %f  R_m: %f  S_m: %f\n',res2(1),res2(2),res2(3),res2(4));
    rm(i) = res2(1);
    sm(i) = res2(2);
    rm_1(i) = res2(3);
    sm_1(i) = res2(4);    
end
plot(rate,rm,'r',rate,sm,'b',rate,rm_1,'g',rate,sm_1,'y');
