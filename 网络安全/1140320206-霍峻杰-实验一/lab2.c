#include <stdio.h>

void main(int argc, char **argv){
    char buf[1];
    strcpy(buf,argv[1]);
    printf("%s\n", buf);
    printf(buf);
    printf("\n");
}
