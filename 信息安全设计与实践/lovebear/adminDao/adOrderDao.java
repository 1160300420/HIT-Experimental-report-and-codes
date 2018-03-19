package com.lovebear.adminDao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.lovebear.entity.Orders;

public class adOrderDao extends BaseDao<Orders>{

	//find all the Users
		public List<Orders> orderFindAll(){
			String hql="from Orders";
			List<Orders> list=orderFind(hql);
			return list;
		}
		
		//delete one news by uniquekey
		@SuppressWarnings("unchecked")
		public void orderDelete(final String uniquekey) {
			this.getHibernateTemplate().execute(new HibernateCallback() {  
			            
				@Override  
				public Object doInHibernate(Session session) throws HibernateException,  
						 SQLException {  
					String hql = "delete from Orders data where data.id.orderId=?";
						    
					Query q = session.createQuery(hql);   
					q.setParameter(0, uniquekey);
					q.executeUpdate();
					return null;  
				}  
			});  
		}
		
		public List<Orders> orderFind(final String hql,final Object...params) {
			return getHibernateTemplate().execute(new HibernateCallback<List<Orders>>() {

				@Override
				public List<Orders> doInHibernate(Session session) throws HibernateException,
						SQLException {
					
					Query q = session.createQuery(hql);
					for(int i=0;i<params.length;i++){
						q.setParameter(i, params[i]);
					}
					List<Orders> list = q.list();
					if(list!=null){
						return list;
					}
					return null;
				}
			});
		}
}
