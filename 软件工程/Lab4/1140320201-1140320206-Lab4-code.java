
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class multinomial {
	
	public static void main(String[] args){
		StringBuilder complete=new StringBuilder();

		
		InputStream is = null;
		StringBuilder sb=null;

		int ch,flag;
		char c='\0',prec='\0';
		String str,mn="\0",cmd2;



		try{
			is=System.in;
			sb=new StringBuilder();
			while(true){
				c='\0';
				prec='\0';
				if((ch=is.read())!='\n'){
					c=(char)ch;					
					sb.append(c);
				}else{
					str=sb.toString();
					
					sb.delete(0, sb.length());
					
					if(str.startsWith("!simplify")){        //simplify
						if(!str.startsWith("!simplify ")){
							System.out.println("Invalid Simplify!");
							continue;
						}
						long startTime=System.nanoTime();   //获取开始时间  
						int flag2 = simplify(str,mn,complete);
						if(flag2>0){
							continue;
						}
						long endTime=System.nanoTime(); //获取结束时间  
						System.out.println("化简程序运行时间： "
								+(endTime-startTime)+"ns");   
					}else if(str.startsWith("!d/d")){        //d/d*
						cmd2=str.substring(4,str.length()-1);						
						
						if(mn=="\0"){
							System.out.println("Please input the multinomial first!");
							continue;
						}
						String[] array = sp(mn);
						long startTime=System.nanoTime();   //获取开始时间  
						String mdnt ="";
						for(String a:array){
								String mdn = dao1(dao(a,cmd2),cmd2);
								if(mdn != null){
									
									mdnt=mdnt+mdn+"+";
								}
							
						}
						
						mdnt = mdnt.substring(0, mdnt.length()-1);
						//System.out.println(mdnt);
						merge(mdnt,complete);
						System.out.println("the result is: " +complete);
						complete.delete(0, complete.length());
						long endTime=System.nanoTime(); //获取结束时间  
						System.out.println("求导程序运行时间： "+(endTime-startTime)+"ns");   
						flag=mn.indexOf(cmd2);
						if(-1==flag){
							System.out.println("There is no such par in the multinomial");
							continue;
						}			
					}else{
						String mn1=str.substring(0,str.length()-1);
						mn = general1(general(mn1));
						cal(mn,complete);
						mn = complete.toString();
						complete.delete(0, complete.length());
						flag=0;
						for(int i=0;i<mn.length();i++){
							c=mn.charAt(i);
							if(!Character.isDigit(c) && !Character.isLetter(c) && (c!='*' && c !='+'&&c!='\r'&&c!='\n' &&c!='-')){
								System.out.println("Invalid Multinomial");
								flag++;
								break;
							}else if(Character.isDigit(c)||((prec=='\0'||prec=='*'||prec=='+'||prec=='-'||Character.isLetter(prec)) && Character.isLetter(c))
									||((Character.isLetter(prec) || Character.isDigit(prec)) && (c=='*'||c=='-'||c=='+'||c=='\r'))||(c=='\r'||c=='\n')){
								;
							}
							else if(prec =='+' && c =='-'){
								
							}
							else{
								System.out.println("Invalid Multinomial");		
								flag++;
								break;
							}
							prec=c;
						}
						if(0==flag){
							System.out.println("Valid Multinomial!\nyour mm is: "+mn);						
						}
					}
					
				}
			}
		}
		catch(IOException e){
			System.out.println(e.toString());
		}
		finally{
			try{if(is!=null)
				is.close();
			}catch(IOException e){
				System.out.println(e.toString());
			}
		}
	}
	
	
	
	
	
	///HERE
	
	
	
	
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
	
	public static int simplify(String str,String mn,StringBuilder complete){
		
		
		
		String cmd1,mnstr;
		String[] list,partList,tmp,finalList;
		int flag,res,finalres;
		int flag2 = 0;
		StringBuilder mntmp,strtmp,result,finalresult;
		    //simplify
						
						if(mn=="\0"){
							System.out.println("Please input the multinomial first!");
							flag2++;
							return flag2;
						}
						//fix here
						
						if(str.equals("!simplify\r")|| str.equals("!simplify \r")){
							merge(mn,complete);
							System.out.print("The simplify result is: "+ complete);
							complete.delete(0, complete.length());
							
							flag2++;
							return flag2;
						}
						cmd1=str.substring(10,str.length()-1);
						list=cmd1.split(" ");
												
						flag=0;
						StringBuilder st=new StringBuilder();
					    for(int i=0;i<list.length;i++){
					    	if(!list[i].matches("^[a-zA-Z]+={1}[0-9]+")){
					    		System.out.println("Invalid Simplify");
					    		flag2++;
					    		return flag2;
					    	}
					        Pattern pattern = Pattern.compile("(.*?)=");
					        Matcher matcher = pattern.matcher(list[i]);					         
					        while (matcher.find()) {
					        	st.append(matcher.group());
					        }
					        st.delete(st.length()-1,st.length());
					        if(mn.contains(st)){
					        	flag++;
					        }
					        st.delete(0, st.length());
					    }
					    if(flag!=list.length){
					    	System.out.println("There is no such par in the multinomial");
					    	flag2++;
					    	return flag2;
					    }
							
						for(int i=0;i<list.length;i++){
							if(!(list[i].matches("^[a-zA-Z]+={1}-?[0-9]+\r?\n?$"))){
								System.out.println("Invalid simplify forms");
								continue;
							}
						}
						
						System.out.println("Valid Simplify!");
						//cal
						mntmp=new StringBuilder();
						mnstr=mn;
						int loc=0;
						for(String a : list){
							loc=a.indexOf('=');
							mnstr=mnstr.replace(a.substring(0, loc), a.substring(loc+1));
													
						}
						mntmp.append(mnstr);
						//System.out.println(mntmp);
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
						System.out.println("The simplify result is: "+complete);
						complete.delete(0, complete.length());
		
		return flag2;
	}
	
	
	
	
	/**
	 * 规范化表达式
	 * @param mn
	 * @return
	 */
	public static String general(String mn){
		String mn1="";
		String mn2 = "";
		char[] mn3;
		

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
	
	//规范化表达式
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
	
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 求导函数
	 */
	public static String splite(String str){
		String regEx = "[^0-9]";//匹配指定范围内的数字

        //Pattern是一个正则表达式经编译后的表现模式
        Pattern p = Pattern.compile(regEx);

        // 一个Matcher对象是一个状态机器，它依据Pattern对象做为匹配模式对字符串展开匹配检查。
        Matcher m = p.matcher(str);

        //将输入的字符串中非数字部分用空格取代并存入一个字符串
        String string = m.replaceAll(" ").trim();

        //以空格为分割符在讲数字存入一个字符串数组中
        String[] strArr = string.split(" ");

        //遍历数组转换数据类型输出
        return strArr[0];
    }
		
	public static String[] sp(String str){
		String[] array;
		array=str.split("\\+");
		return array;
	}
	//计算表达式中未知数的个数
	public static int countStr(String str1, String str2) {   
        int counter=0;  
        if (str1.indexOf(str2) == -1) {    
            return 0;  
        }   
            while(str1.indexOf(str2)!=-1){  
                counter++;  
                str1=str1.substring(str1.indexOf(str2)+str2.length());  
            }  
            return counter;    
    }  
//求导函数1	
	public static String dao(String str,String str2){
		int s = countStr(str,str2);
		String str1 = "";


		String regex="[0-9]+?";
		Pattern p=Pattern.compile(regex);
		Matcher m=p.matcher(str);
		if(s ==0){
			return null;
		}
		else{
			if(m.find() == false)
				str1 = str1+(String)(s +"")+'*';
			else{
				String ss = splite(str);
				int i =str.indexOf(ss);
				str1=str.substring(0, i)+str.substring(i+ss.length());
				str1 = str.substring(0, i)+(String)(Integer.parseInt(ss)*s+"")+str.substring(i+ss.length());
			
			}
		}
		
		return str1;
		
	}
	
	
	//求导函数2
	public static String dao1(String str,String str2){
		if(str == null){
			return null;
		}
		else{
			String str4="";
			int a = str.indexOf("*"+str2);
			char[] ch1= str.toCharArray();
			String str3= '*'+str2;
			char[] ch2= str3.toCharArray();
			for(int i = 0;i<ch1.length;i++){
				if(i<a || i>=a+ch2.length){
					str4+=ch1[i];
				}
				else{
					continue;
				}
			}
			return str4;
		}
		
		
		
	}
	//进行表达式化简（系数整合）
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
	

}





