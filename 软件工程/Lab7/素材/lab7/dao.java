package lab6;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class dao {
	/**
	 * 
	 * 
	 * 
	 * 
	 * 求导函数
	 */
	private String mn;//控制类传入的表达式；
	public String getCmd2() {
		return cmd2;
	}


	public void setCmd2(String cmd2) {
		this.cmd2 = cmd2;
	}


	public int getFlag() {
		return flag;
	}


	public void setFlag(int flag) {
		this.flag = flag;
	}


	public StringBuilder getComplete() {
		return complete;
	}


	public void setComplete(StringBuilder complete) {
		this.complete = complete;
	}


	private String cmd2;//要求导的未知数
	private int flag;//判断该未知数是否在表达式里
	private StringBuilder complete=new StringBuilder();
	public String getMn() {
		return mn;
	}


	public void setMn(String mn) {
		this.mn = mn;
	}
	public dao(){
		mn = new String();
		cmd2 = new String();
	}
	public String qiudao(String str,String mn){
		cmd2=str.substring(4,str.length()-1);						
		flag=mn.indexOf(cmd2);
	
		if(-1==flag){
			System.out.println("There is no such par in the multinomial");
		}		
		if(mn=="\0"){
			boder.print("Please input the multinomial first!");
		}
		
		String[] array = sp(mn);
		String mdnt ="";
		for(String a:array){
				String mdn = dao.dao1(dao.dao(a,cmd2),cmd2);
				if(mdn != null){
					
					mdnt=mdnt+mdn+"+";
				}
			
		}
		mdnt = mdnt.substring(0, mdnt.length()-1);
		experssion.merge(mdnt,complete);
		boder.print("求导结果："+mdnt);
		return mdnt;
	}

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
	//表达式按空格拆分	
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
		int s1 =0;
		char[] chars= str.toCharArray();
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

}
