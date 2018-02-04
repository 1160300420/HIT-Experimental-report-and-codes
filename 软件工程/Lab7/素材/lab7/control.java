package lab6;

import java.io.InputStream;

public class control {
	static StringBuilder complete=new StringBuilder();
	StringBuilder sb=null;
	StringBuilder mntmp = null,mymn=null;
	int ch;
	static int flag;
	int res;
	private static String mn;
	String mnstr;
	String cmd1;
	static String cmd2;
	StringBuilder strtmp=null,result=null,finalresult;
	public static void con(String str){
		
		if(str.startsWith("!simplify")){        //simplify
			if(!str.startsWith("!simplify ")){
				boder.print("Invalid Simplify!");
			}
			simpilfy.simplify(str,mn,complete);
		
			
		}else if(str.startsWith("!d/d")){        //d/d*
			dao d = new dao();
			d.qiudao(str,mn);
				
		}else{//表达式处理
			String mn1=str.substring(0,str.length()-1);
			mn = experssion.general1(experssion.general(mn1));
			if(experssion.expression(mn) == null){
				boder.print("表达式错误");
			}else{
				experssion.cal(mn,complete);
				mn = complete.toString();
				boder.print("表达式为："+mn);
			}
			
			complete.delete(0, complete.length());
		}
	}
	//把表达式按空格拆分
	public static String[] sp(String str){
		String[] array;
		array=str.split("\\+");
		return array;
	}
}
