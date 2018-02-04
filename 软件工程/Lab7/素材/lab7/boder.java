package lab6;

import java.io.IOException;
import java.io.InputStream;

public class boder {
	public static void main(String[] args){
		boder();
	}
	public static void boder(){
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
					control.con(str);
					//System.out.print(str);
					//System.out.print(str.length());
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
	public static void print(String text){
		System.out.println(text);
	}

}
