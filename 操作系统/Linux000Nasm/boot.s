bootseg equ 0x07c0  
sysseg equ 0x1000  
syslen equ 17  
start:  
    jmp bootseg:go  
go:  
    mov ax,cs  
    mov ds,ax  
    mov ss,ax  
    mov sp,0x0400  
  
load_syetem:  
    mov dx,0x0000  
    mov cx,0x0002  
    mov ax,sysseg  
    mov es,ax  
    xor bx,bx  
    mov ax,0x200+syslen  
    int 0x13  
    jnc ok_load  
die:  
    jmp die  
  
ok_load:  
    cli  
    mov ax,sysseg  
    mov ds,ax  
    xor ax,ax  
    mov es,ax  
    mov cx,0x2000  
    sub si,si  
    sub di,di  
    rep movsb  
  
    mov ax,bootseg  
    mov ds,ax  
    lidt [idt_48]  
    lgdt [gdt_48]  
    mov ax,0x0001  
    lmsw ax  
    jmp 8:0  
  
gdt:  
    dw 0,0,0,0  
    dw 0x07ff  
    dw 0x0000  
    dw 0x9a00  
    dw 0x00c0  
      
    dw 0x07ff  
    dw 0x0000  
    dw 0x9200  
    dw 0x00c0  
  
idt_48:  
    dw 0,0,0  
gdt_48:  
    dw 0x7ff  
    dw 0x7c00+gdt,0  
    times 510-($-$$) db 0  
    dw 0xaa55  
