package com.lovebear.adminDao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.lovebear.entity.User;

public class adUserDao extends BaseDao<User>{

	//find all the Users
	public List<User> userFindAll(){
		String hql="from User";
		List<User> list=userFind(hql);
		return list;
	}
	
	//delete one news by uniquekey
	@SuppressWarnings("unchecked")
	public void userDelete(final String uniquekey) {
		this.getHibernateTemplate().execute(new HibernateCallback() {  
		            
			@Override  
			public Object doInHibernate(Session session) throws HibernateException,  
					 SQLException {  
				String hql = "delete from User data where data.id.uniquekey=?";
					    
				Query q = session.createQuery(hql);   
				q.setParameter(0, uniquekey);
				q.executeUpdate();
				return null;  
			}  
		});  
	}
	
	public List<User> userFind(final String hql,final Object...params) {
		return getHibernateTemplate().execute(new HibernateCallback<List<User>>() {

			@Override
			public List<User> doInHibernate(Session session) throws HibernateException,
					SQLException {
				
				Query q = session.createQuery(hql);
				for(int i=0;i<params.length;i++){
					q.setParameter(i, params[i]);
				}
				List<User> list = q.list();
				if(list!=null){
					return list;
				}
				return null;
			}
		});
	}
}
