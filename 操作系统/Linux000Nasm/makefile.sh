rm *.bin
nasm -f bin boot.s -o boot.bin
nasm -f bin head.s -o head.bin
cat boot.bin head.bin >> Image.bin
