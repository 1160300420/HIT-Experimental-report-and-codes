package com.lovebear.adminDao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.lovebear.entity.Items;

public class adItemDao extends BaseDao<Items>{

	//find all the Users
		public List<Items> itemFindAll(){
			String hql="from Items";
			List<Items> list=itemFind(hql);
			return list;
		}
		
		//delete one news by uniquekey
		@SuppressWarnings("unchecked")
		public void itemDelete(final String uniquekey) {
			this.getHibernateTemplate().execute(new HibernateCallback() {  
			            
				@Override  
				public Object doInHibernate(Session session) throws HibernateException,  
						 SQLException {  
					String hql = "delete from Items data where data.id.itemId=?";
						    
					Query q = session.createQuery(hql);   
					q.setParameter(0, uniquekey);
					q.executeUpdate();
					return null;  
				}  
			});  
		}
		
		public List<Items> itemFind(final String hql,final Object...params) {
			return getHibernateTemplate().execute(new HibernateCallback<List<Items>>() {

				@Override
				public List<Items> doInHibernate(Session session) throws HibernateException,
						SQLException {
					
					Query q = session.createQuery(hql);
					for(int i=0;i<params.length;i++){
						q.setParameter(i, params[i]);
					}
					List<Items> list = q.list();
					if(list!=null){
						return list;
					}
					return null;
				}
			});
		}
		
		@SuppressWarnings("unchecked")
		public Items itemFindByUniq(final String uniquekey) {
			return this.getHibernateTemplate().execute(new HibernateCallback<Items>() {  
			            
				@Override  
				public Items doInHibernate(Session session) throws HibernateException,  
						 SQLException {  
					String hql = "from Items data where data.id.itemId=?";
						    
					Query q = session.createQuery(hql);   
					q.setParameter(0, uniquekey);
					List<Items> list = q.list();
					if(list!=null){
						return list.get(0);
					}
					return null;
				}  
			});  
		}
		
		@SuppressWarnings("unchecked")
		public void itemAddByUniq(final int num, final String uniquekey,final int already) {
			
			this.getHibernateTemplate().execute(new HibernateCallback() {  
			            
				@Override  
				public Object doInHibernate(Session session) throws HibernateException,  
						 SQLException {  
					int newinventory=num+already;
					String hql = "update Items data set data.id.inventory=? where data.id.itemId=?";
						    
					Query q = session.createQuery(hql);   
					q.setParameter(0, newinventory);
					q.setParameter(1, uniquekey);
					q.executeUpdate();
					return null;  
				}  
			});  
		}
		
		@SuppressWarnings("unchecked")
		public void itemReduceByUniq(final int num, final String uniquekey,final int already) {
			
			this.getHibernateTemplate().execute(new HibernateCallback() {  
			            
				@Override  
				public Object doInHibernate(Session session) throws HibernateException,  
						 SQLException {  
					int newinventory=already-num;
					if(newinventory<0){
						return null;
					}
					String hql = "update Items data set data.id.inventory=? where data.id.itemId=?";
						    
					Query q = session.createQuery(hql);   
					q.setParameter(0, newinventory);
					q.setParameter(1, uniquekey);
					q.executeUpdate();
					return null;  
				}  
			});  
		}
		
		@SuppressWarnings("unchecked")
		public void itemRenameByUniq(final String newname, final String uniquekey) {
			
			this.getHibernateTemplate().execute(new HibernateCallback() {  
			            
				@Override  
				public Object doInHibernate(Session session) throws HibernateException,  
						 SQLException {  

					String hql = "update Items data set data.id.itemName=? where data.id.itemId=?";
						    
					Query q = session.createQuery(hql);   
					q.setParameter(0, newname);
					q.setParameter(1, uniquekey);
					q.executeUpdate();
					return null;  
				}  
			});  
		}
		
		@SuppressWarnings("unchecked")
		public void itemPriceChangeByUniq(final String newprice, final String uniquekey) {
			
			this.getHibernateTemplate().execute(new HibernateCallback() {  
			            
				@Override  
				public Object doInHibernate(Session session) throws HibernateException,  
						 SQLException {  

					String hql = "update Items data set data.id.itemPrice=? where data.id.itemId=?";
						    
					Query q = session.createQuery(hql);   
					q.setParameter(0, newprice);
					q.setParameter(1, uniquekey);
					q.executeUpdate();
					return null;  
				}  
			});  
		}
}
