img1=imread('C:\Users\lovebear96\Desktop\LSB_RS\eight.bmp');
img2=imread('C:\Users\lovebear96\Desktop\LSB_RS\eight_LSB.bmp');
M=randsrc(8,8,[0 1]);

%าþะดยส
rate=(m-1)/(len*wid);
fprintf('the steg rate: %f\n',rate);

%PSNR
psnr=cal_psnr(img1,img2);
fprintf('the psnr: %f\n',psnr);

%pre LSB
res1=calRS(img1,M);
disp('pre_LSB:');
fprintf('Rm: %f  Sm: %f  R_m: %f  S_m: %f\n',res1(1),res1(2),res1(3),res1(4));

%post LSB
disp('post_LSB:')
res2=calRS(img2,M); %Rm,Sm,R_m,S_m
fprintf('Rm: %f  Sm: %f  R_m: %f  S_m: %f\n',res2(1),res2(2),res2(3),res2(4));
