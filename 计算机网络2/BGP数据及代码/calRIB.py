# -*- coding: utf-8 -*-
f=open('C:\\Users\\lovebear96\\Desktop\\data.txt','r')
f1=open('C:\\Users\\lovebear96\\Desktop\\apcnt.txt','w')
f2=open('C:\\Users\\lovebear96\\Desktop\\ancnt.txt','w')
f3=open('C:\\Users\\lovebear96\\Desktop\\prefixcnt.txt','w')
f4=open('C:\\Users\\lovebear96\\Desktop\\res.txt','w')
lines = f.readlines()
'''
aslist=set()  #as count
ippre=set()   #prefix count

asprefix={}   #each as's prefix, as: ippreset
apcnt={}	  #ipprefix cnt， as: ip ipprefix count
asneighbor={} #each as's neighbor, as: neighborset
ancnt={}      #neighbor cnt , as: neighbor count
prefixcnt={}  #prefixcnt's count
neighborcnt={}#neighborcnt's count

for line in lines:         #read for lines
	tmp = line.split("|")   #split with '|'
	astmp=tmp[6].split(" ")  #aspath(a lot of as)
	
	#count(as)
	for i in range(0,len(astmp)):
		aslist.add(astmp[i])
	#count(ipprefix)
	ippre.add(tmp[5])
	
	#each as's prefix
	if astmp[-1] in asprefix:
		asprefix[astmp[-1]].add(tmp[5])
	else:
		asprefix[astmp[-1]]=set()
		asprefix[astmp[-1]].add(tmp[5])
	
	#each as's neighbor
	for i in range(0,len(astmp)):
		if astmp[i] in asneighbor:
			if(i==0 and len(astmp)>1):     #first as's neighbor tends to be only one
				asneighbor[astmp[i]].add(astmp[i+1])
			elif(i==len(astmp)-1):
				asneighbor[astmp[i]].add(astmp[i-1])  #so does the last one
			else:
				asneighbor[astmp[i]].add(astmp[i-1])  #the middle of as has two neighbors
				asneighbor[astmp[i]].add(astmp[i+1])
		else:
			asneighbor[astmp[i]]=set()
			if(i==0 and len(astmp)>1):
				asneighbor[astmp[i]].add(astmp[i+1])
			elif(i==len(astmp)-1):
				asneighbor[astmp[i]].add(astmp[i-1])
			else:
				asneighbor[astmp[i]].add(astmp[i-1])
				asneighbor[astmp[i]].add(astmp[i+1])

print len(aslist)
print len(ippre)
	

#cal for apcnt
for key in asprefix:       
	apcnt[key]=len(asprefix[key])

#cal for ancnt
for key in asneighbor:
	ancnt[key]=len(asneighbor[key])
	

#cal for neighborcnt's count
for m in ancnt:
	if(ancnt[m] in neighborcnt):
		neighborcnt[ancnt[m]]=neighborcnt[ancnt[m]]+1
	else:
		neighborcnt[ancnt[m]]=1
		

#each as's neighborcnt and prefixcnt 
res={}
for m in ancnt:
	for n in apcnt:
		if(m==n):
			res[ancnt[m]]=apcnt[n]
			break
			
#cal for prefixcnt's count
for m in apcnt:
	if(apcnt[m] in prefixcnt):
		prefixcnt[apcnt[m]]=prefixcnt[apcnt[m]]+1
	else:
		prefixcnt[apcnt[m]]=1

#data into files to use matlab to plot
for l in apcnt:
	f1.write(l)
	f1.write(' ')
	f1.write(str(apcnt[l]))
	f1.write('\n')

for l in neighborcnt:
	f2.write(str(l))
	f2.write(' ')
	f2.write(str(neighborcnt[l]))
	f2.write('\n')

for l in prefixcnt:
	f3.write(str(l))
	f3.write(' ')
	f3.write(str(prefixcnt[l]))
	f3.write('\n')

for l in res:
	f4.write(str(l))
	f4.write(' ')
	f4.write(str(res[l]))
	f4.write('\n')
f1.close()
f2.close()
f3.close()
f4.close()
'''

