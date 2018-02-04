LATCH equ 11930  
SCRN_SEL equ 0x18  
TSS0_SEL equ 0x20  
LDT0_SEL equ 0x28  
TSS1_SEL equ 0x30  
LDT1_SEL equ 0x38  
TSS2_SEL equ 0x40  
LDT2_SEL equ 0x48
bits 32;设置处理器模式位模式为32位  
startup_32:  
    mov eax,0x10  
    mov ds,ax  
    lss esp,[init_stack]  
  
    call setup_idt  
    call setup_gdt  
    mov eax,dword 0x10  
    mov ds,ax  
    mov es,ax  
    mov fs,ax  
    mov gs,ax  
    lss esp,[init_stack]  
  
    mov al,0x36  
    mov edx,0x43  
    out dx,al  
    mov eax,LATCH  
    mov edx,0x40  
    out dx,al  
    mov al,ah  
    out dx,al  
  
    mov eax,0x00080000  
    mov ax,timer_interrupt  
    mov dx,0x8e00  
    mov ecx,0x08  
    lea esi,[idt+ecx*8]  
    mov [esi],eax  
    mov [esi+4],edx  
    mov ax,system_interrupt  
    mov dx,0xef00  
    mov ecx,0x80  
    lea esi,[idt+ecx*8]  
    mov [esi],eax  
    mov [esi+4],edx  
  
    pushf  
    and dword[esp],0xffffbfff  
    popf  
    mov eax,TSS0_SEL  
    ltr ax  
    mov eax,LDT0_SEL  
    lldt ax  
    mov dword[current],0  
    sti  
    push long 0x17  
    push long init_stack  
    pushf  
    push long 0x0f  
    push long task0  
    iret  
  
setup_gdt:  
    lgdt [lgdt_opcode]  
    ret  
setup_idt:  
    mov edx,ignore_int-$$;lea edx,[ignore_int]  
    mov eax,0x00080000  
    mov ax,dx  
    mov dx,0x8e00  
    mov edx,ignore_int-$$;lea edi,[idt]  
    mov ecx,256  
rp_idt:  
    mov [edi],eax  
    mov [edi+4],edx  
    add edi,8  
    dec ecx  
    jne rp_idt  
    lidt [lidt_opcode]  
    ret  
  
write_char:  
    push gs  
    push ebx  
    mov ebx,SCRN_SEL  
    mov gs,bx  
    mov bx,[scr_loc]  
    shl ebx,1  
    mov [gs:ebx],al  
    shr ebx,1  
    inc ebx  
    cmp ebx,2000  
    jb .l1  
    mov ebx,0  
.l1:  
    mov [scr_loc],ebx  
    pop ebx  
    pop gs  
    ret  
  
align 4  
ignore_int:  
    push ds  
    push eax  
    mov eax,0x10  
    mov ds,ax  
    mov eax,67  
    call write_char  
    pop eax  
    pop ds  
    iret  
  
align 4  
timer_interrupt:  
    push ds  
    push eax  
    mov eax,0x10  
    mov ds,ax  
    mov al,0x20  
    out 0x20,al  
    mov eax,1  
    cmp [current],eax  
    je .l1  
	mov eax,2
	cmp [current],eax
	je .l3 
	mov dword[current],1 
    jmp TSS1_SEL:1  
    jmp .l2  
.l1:  
    mov dword[current],2  
    jmp TSS2_SEL:2 
	jmp .l2
.l3:
	mov dword[current],0  
    jmp TSS0_SEL:0 
.l2:  
    pop eax  
    pop ds  
    iret  
  
align 4  
system_interrupt:  
    push ds  
    push edx  
    push ecx  
    push ebx  
    push eax  
    mov edx,0x10  
    mov ds,dx  
    call write_char  
    pop eax  
    pop ebx  
    pop ecx  
    pop edx  
    pop ds  
    iret  
  
current: dd 0  
scr_loc: dd 0  
  
align 4  
lidt_opcode:  
    dw 256*8-1  
    dd idt  
lgdt_opcode:  
    dw (end_gdt-gdt)-1  
    dd gdt  
  
align 8  
idt:  
    times 256*8 db 0  
gdt:  
    dq 0x0000000000000000  
    dq 0x00c09a00000007ff  
    dq 0x00c09200000007ff  
    dq 0x00c0920b80000002  
    dw 0x68,tss0,0xe900,0x0  
    dw 0x40,ldt0,0xe200,0x0  
    dw 0x68,tss1,0xe900,0x0  
    dw 0x40,ldt1,0xe200,0x0  
	dw 0x68,tss2,0xe900,0x0  
    dw 0x40,ldt2,0xe200,0x0
end_gdt:  
    times 128*4 db 0  
init_stack:  
    dd init_stack  
    dw 0x10  
  
align 8  
ldt0:  
    dq 0x0000000000000000  
    dq 0x00c0fa00000003ff  
    dq 0x00c0f200000003ff  
tss0:  
    dd 0  
    dd krn_stk0,0x10  
    dd 0,0,0,0,0  
    dd 0,0,0,0,0  
    dd 0,0,0,0,0  
    dd 0,0,0,0,0,0  
    dd LDT0_SEL,0x8000000  
  
    times 128*4 db 0  
  
krn_stk0:  
  
align 8  
ldt1:  
    dq 0x0000000000000000  
    dq 0x00c0fa00000003ff  
    dq 0x00c0f200000003ff  
  
tss1:  
    dd 0  
    dd krn_stk1,0x10  
    dd 0,0,0,0,0  
    dd task1,0x200  
    dd 0,0,0,0  
    dd usr_stk1,0,0,0  
    dd 0x17,0x0f,0x17,0x17,0x17,0x17  
    dd LDT1_SEL,0x8000000  
    times 128*4 db 0  
krn_stk1:  

align 8  
ldt2:  
    dq 0x0000000000000000  
    dq 0x00c0fa00000003ff  
    dq 0x00c0f200000003ff  
  
tss2:  
    dd 0  
    dd krn_stk2,0x10  
    dd 0,0,0,0,0  
    dd task2,0x200  
    dd 0,0,0,0  
    dd usr_stk2,0,0,0  
    dd 0x17,0x0f,0x17,0x17,0x17,0x17  
    dd LDT2_SEL,0x8000000  
    times 128*4 db 0  
krn_stk2:  

task0:  
    mov eax,0x17  
    mov ds,ax  
    mov al,65  
    int 0x80  
    mov ecx,0xfff  
.l1:  
    loop .l1  
    jmp task0  
  
task1:  
    mov al,66  
    int 0x80  
    mov ecx,0xfff  
.l2:  
    loop .l2  
    jmp task1 
	
times 128*4 db 0
usr_stk1:
task2:  
    mov al,67 
    int 0x80  
    mov ecx,0xfff  
.l3:  
    loop .l3 
    jmp task2 	
  
times 128*4 db 0  
usr_stk2:  