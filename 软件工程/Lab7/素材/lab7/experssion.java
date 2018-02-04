package lab6;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class experssion {
	//表达式规范化  处理空格 以及缺少*
	private String mn;//控制类传入的表达式；
	private boolean isRight;//判断表达式是否正确；
	
	public boolean isRight() {
		return isRight;
	}


	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}


	public String getMn() {
		return mn;
	}


	public void setMn(String mn) {
		this.mn = mn;
	}


	public static String general(String mn){
		String mn1="";
		String mn2 = "";
		char[] mn3;
		
		char c='\0';
		for(int i = 0;i<mn.length();i++){
			if (mn.charAt(i) ==' '){
				continue;
			}else
			{
				mn1+=mn.charAt(i);
			}
		}
		mn3 = mn1.toCharArray();
		for(int i = 0;i<mn3.length;i++){
			if(i ==0){
				mn2+=mn3[i];
				continue;
			}
			
			if(Character.isLetter(mn3[i]) && Character.isDigit(mn3[i-1])){
				mn2+='*';
				mn2+=mn3[i];
			}
			else{
				mn2+=mn3[i];
			}
			
		}
		
		return mn2;
		
	}
	
	
	//表达式规范化处理 ^
	public static String general1(String mn){
		String str1 = "";
		char[] str2;
		str2 = mn.toCharArray();
		for(int i =0;i<str2.length;i++){
			if(i ==0 ){
				if(str2[i] =='-'){
					str1+='+';
					str1+='-';
				}
				else str1+=str2[i];
					continue;
			}else{
				if(str2[i] =='-'){
					str1+='+';
					str1+='-';
					continue;
				}
				else if(str2[i-1] =='^' && Character.isDigit(str2[i])){
					int flag =1;
					int a = calint(mn,i-1);
					
					for(int j = 1;j<a;j++){
						int r = i-2;
						str1+='*';
						while(r>=0 && Character.isLetter(str2[r])){
							r-=1;
						}
						while(r<=i-2 && r+1>=0){
							if(r ==-1 || str2[r] =='*' || str2[r] =='+' || str2[r] =='-'){
								r++;
								continue;
							}
							str1+=str2[r];
							r++;
						}
					}
					if(i+flag <str2.length){
						if(Character.isDigit(str2[i+flag])){
							flag ++;
						}
						
					}
					i+=flag-1;
				
				}
				
				else{
					
					if(str2[i] =='^'){
						continue;
					}
					else{
						str1+=str2[i];
					}
				}
			}
		}
		
		
		
		
		return str1;
		
	}
	//进行表达式的拆分，分理出其中的数字
	public static int calint(String str,int s){
		int a = str.indexOf('^',s);
		String str3="";
		char[]str2 = str.toCharArray();
		for(int i=a;i<str.length();i++){
			if(Character.isLetter(str2[i]) || str2[i] =='+'|| str2[i] == '-'){
				break;
			}
			if(Character.isDigit(str2[i])){
				
				str3+=str2[i];
			}
		}
	
		return Integer.parseInt(str3);
	}
	//表达式整合
	public static void cal(String mn,StringBuilder complete){
		String mnstr;
		String[] partList,tmp,finalList;
		int res,finalres;
		StringBuilder mntmp,strtmp,result,finalresult;
		mntmp=new StringBuilder();
		mnstr=mn;
		
		mntmp.append(mnstr);
		strtmp=new StringBuilder();
		result=new StringBuilder();
		partList=mntmp.toString().split("\\+");
		for(String a:partList){
			tmp=a.split("\\*");
			res=1;
			for(String b:tmp){
				if(b.matches("^[a-zA-Z]+$")){
					strtmp.append("*"+b);
				}else{
					res*=Integer.parseInt(b);
				}
			}
			result.append(res);
			result.append(strtmp+"+");
			strtmp.delete(0, strtmp.length());
		}
		finalList=result.toString().split("\\+");
		finalres=0;
		finalresult=new StringBuilder();
		for(String a:finalList){
			if(a.matches("^[0-9]+$")){
				finalres+=Integer.parseInt(a);
			}else{
				strtmp.append(a+"+");
			}
		}
		if(!strtmp.toString().isEmpty())
			finalresult.append(strtmp);
		if(finalres!=0)
			finalresult.append(finalres+"+");
		finalresult.delete(finalresult.length()-1,finalresult.length());
		
		merge(finalresult.toString(),complete);
		//System.out.println("The simplify result is: "+complete);
	}
	public static void merge(String mn, StringBuilder complete){ 
		String[] list,part,tmp;
		String cpl;
		list=mn.split("\\+");
		Map<String,Integer> maptotal=new HashMap<String,Integer>();
		Map<String,Integer> mappart=new HashMap<String,Integer>();
		for(String a:list){
			part=a.split("\\*");
			for(String b:part){
				if(b.matches("^[a-zA-Z]+$")){
					if(mappart.containsKey(b)){
						mappart.put(b, mappart.get(b)+1);
					}else{
						mappart.put(b,1);
					}	
				}
			}
			
			for(String b:part){
				if(b.matches("^-?[0-9]+$")){	
					if(maptotal.containsKey(mappart.toString())){							
						maptotal.put(mappart.toString(), maptotal.get(mappart.toString())+Integer.parseInt(b));
					}else{
						maptotal.put(mappart.toString(), Integer.parseInt(b));
					}				
					break;
				}
			}
			mappart.clear();

		}
		
		for(Entry<String,Integer> entry : maptotal.entrySet()){
			complete.append(entry.getValue()+"*");
			if(entry.getKey()=="{}"){
				complete.delete(complete.length()-1, complete.length());
				complete.append('+');
				continue;
			}
			part=entry.getKey().substring(1,entry.getKey().length()-1).split(", ");
			
			for(String a:part){
				tmp=a.split("=");
				
				for(int i=0;i<Integer.parseInt(tmp[1]);i++){
					complete.append(tmp[0]+"*");
				}				
			}
			complete.delete(complete.length()-1, complete.length());
			complete.append("+");
		
		}
		complete.delete(complete.length()-1, complete.length());
		cpl=complete.toString();
		cpl=cpl.replace("+-", "-");
		complete.delete(0, complete.length());
		complete.append(cpl);
	}
	//进行表达式正误判断
	public static String expression(String mn){
		char c = '\0';
		char prec = '\0';
		int flag = 0;
		for(int i=0;i<mn.length();i++){
			c=mn.charAt(i);
			if(!Character.isDigit(c) && !Character.isLetter(c) && (c!='*' && c !='+'&&c!='\r'&&c!='\n' &&c!='-')){
				
				flag++;
				break;
			}else if(Character.isDigit(c)||((prec=='\0'||prec=='*'||prec=='+'||prec=='-'||Character.isLetter(prec)) && Character.isLetter(c))||(c=='\r'||c=='\n')
					||((Character.isLetter(prec) || Character.isDigit(prec)) && (c=='*'||c=='-'||c=='+'||c=='\r'))){
				;
			}
			else if(prec =='+' && c =='-'){
				;
			}
			else{
						
				flag++;
				break;
			}
			prec=c;
		}
		if(0==flag){
			
			return mn;
		}else{
			return null;
		}
		
		
		
	}

}
