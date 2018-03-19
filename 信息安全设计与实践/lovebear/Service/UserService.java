package com.lovebear.Service;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.lovebear.Dao.UserDao;
import com.lovebear.Util.CalDes;
import com.lovebear.Util.CalMD5;
import com.lovebear.Util.CalSHA256;
import com.lovebear.entity.Result;
import com.lovebear.entity.User;
import com.lovebear.entity.UserId;

public class UserService {
	private static int uid;
	private UserDao userDao;
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public UserService(){
		UserService.uid=0;
	}


	public Result<Boolean> register(User u) {
		HttpSession session=ServletActionContext.getRequest().getSession();
		Result<Boolean> result = new Result<Boolean>();
		try {
			//cal uid
			if(u.getId().getUid()==null){
				uid++;
				u.getId().setUid(uid);
			}
			//cal salt
			int x=(int)(Math.random()*10000);
			String salt = CalMD5.getMD5(String.valueOf(x)).substring(0,10);
			u.getId().setSalt(salt);
			
			CalDes calDes=new CalDes();
			String desKey = (String) session.getAttribute("desKey");
			if(desKey!=null){
				String pwd= calDes.decryption(u.getId().getPwdsalt().split(",")[0], desKey);
				//cal hash(pwd+salt)
				String pwdsalt=CalSHA256.getSHA256(pwd+salt);	
				u.getId().setPwdsalt(pwdsalt);
			}
						
			//save into user table
			userDao.save(u);
			//return success
			result.state = Result.STATE_SUC;
			result.descript = "Register Successfully";
			result.data = true;
		} catch (Exception e) {
			result.state = Result.STATE_FAIL;
			result.descript = "Register Failure";
			result.data=false;
			e.printStackTrace();
		}
		return result;
	}
	
	public Result<UserId> queryToResult(User u) throws Exception{
		HttpSession session=ServletActionContext.getRequest().getSession();
		Result<UserId> result = new Result<UserId>();
		String email = u.getId().getEmail();
		if(email == null){
			result.state = Result.STATE_FAIL;
			result.descript = "The Email Can't Be Empty";
			return result;
		}
		CalDes calDes=new CalDes();
		String desKey = (String) session.getAttribute("desKey");
		if(desKey!=null){
			String pwdsalt = calDes.decryption(u.getId().getPwdsalt(), desKey);
			u.getId().setPwdsalt(pwdsalt);	
		}
		
		User qr = userDao.queryToLogin(email, u.getId().getPwdsalt());
		if(qr != null){
			result.state=Result.STATE_SUC;
			result.descript="Login Successfully";
			result.data = qr.getId();
		}else{
			result.state = Result.STATE_FAIL;
			result.descript = "Email doesn't exit";
		}
		return result;
	}
}
