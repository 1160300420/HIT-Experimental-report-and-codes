package lab6;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class simpilfy {
	private String order;//传入的命令
	private boolean isRight;//传入的命令是否正确
	
	public simpilfy(){
		isRight = true;
		mn = new String();
		order = new String();
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public boolean isRight() {
		return isRight;
	}

	public void setRight(boolean isRight) {
		this.isRight = isRight;
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
	
	public static int simplify(String str,String mn,StringBuilder complete){
		
		
		
		String cmd1,mnstr;
		String[] list,partList,tmp,finalList;
		int flag,res,finalres;
		int flag2 = 0;
		StringBuilder mntmp,strtmp,result,finalresult;
		
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
}
