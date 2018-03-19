package com.lovebear.Dao;

import com.lovebear.Util.CalSHA256;
import com.lovebear.entity.User;

public class UserDao extends BaseDao<User>{

	public User queryToLogin(String email,String pwd) {
		String hql0 = "from User where email=?";
		User u0= queryEntity(hql0,email);
		if(u0==null)
			return null;
		String salt = u0.getId().getSalt();
		String pwdsalt=CalSHA256.getSHA256(pwd+salt);
		String hql = "from User where (email=? and pwdsalt=?)";
		User u = queryEntity(hql,email, pwdsalt);
		return u;
	}
	
	public void save(User u){
		super.save(u);
	}
}
