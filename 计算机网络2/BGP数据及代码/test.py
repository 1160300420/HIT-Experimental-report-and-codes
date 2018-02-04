f=open('C:\\Users\\lovebear96\\Desktop\\ancnt.txt','r')
f1=open('C:\\Users\\lovebear96\\Desktop\\ancnt1.txt','w')
lines=f.readlines()
for line in lines:
	num=line.split(' ')
	if(line[0]=='{' or int(num[0])>50000):
		continue
	else:
		f1.write(line)