#include <stdio.h>

void test(char *name){
    char buf[1]="";
    strcpy(buf,name);
}

void main(){
    char name[100]="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    test(name);
}
