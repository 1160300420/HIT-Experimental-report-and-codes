package lab6;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class dao {
	/**
	 * 
	 * 
	 * 
	 * 
	 * �󵼺���
	 */
	private String mn;//�����ഫ��ı��ʽ��
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


	private String cmd2;//Ҫ�󵼵�δ֪��
	private int flag;//�жϸ�δ֪���Ƿ��ڱ��ʽ��
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
		boder.print("�󵼽����"+mdnt);
		return mdnt;
	}

	public static String splite(String str){
		String regEx = "[^0-9]";//ƥ��ָ����Χ�ڵ�����

        //Pattern��һ��������ʽ�������ı���ģʽ
        Pattern p = Pattern.compile(regEx);

        // һ��Matcher������һ��״̬������������Pattern������Ϊƥ��ģʽ���ַ���չ��ƥ���顣
        Matcher m = p.matcher(str);

        //��������ַ����з����ֲ����ÿո�ȡ��������һ���ַ���
        String string = m.replaceAll(" ").trim();

        //�Կո�Ϊ�ָ���ڽ����ִ���һ���ַ���������
        String[] strArr = string.split(" ");

        //��������ת�������������
        return strArr[0];
    }
	//���ʽ���ո���	
	public static String[] sp(String str){
		String[] array;
		array=str.split("\\+");
		return array;
	}
	//������ʽ��δ֪���ĸ���
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
//�󵼺���1	
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
	
	
	//�󵼺���2
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
