:Compile
@ECHO 1. Compile boot.s and head.s
@IF NOT EXIST boot.s ECHO boot.s does not exist
@IF NOT EXIST head.s ECHO head.s does not exist
@ECHO/
@nasm -f bin boot.s -o boot.bin
@nasm -f bin head.s -o head.bin

:Link
@ECHO 2. Combine boot.bin and head.bin into one bin file
@ECHO/
@DEL Image.bin
@REM The length of the created file must be 512-bits initially
@fsutil file createnew Image.bin 512 1>nul
@cat head.bin >>Image.bin
@hbin boot.bin Image.bin

:Successfully
@ECHO/
@ECHO Successfully !

:Author
@ECHO/
@ECHO/
@ECHO/
@ECHO Written by Dr. GuoJun LIU
@ECHO Last Updated on 2014-10-25
@ECHO/
@ECHO/

@PAUSE

