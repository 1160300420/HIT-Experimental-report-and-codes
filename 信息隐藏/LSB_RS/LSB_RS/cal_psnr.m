function psnr=cal_psnr(img1,img2)
if(size(img1)~=size(img2))
    error('different size!');
end
mse=sum(sum((img2-img1).^2));
if (mse==0)
    error('the same images');
else
    psnr=10*log10((255^2)/mse);
end