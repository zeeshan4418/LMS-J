package userdaoimp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import userdao.UserDAO;
import utils.Database;
import bean.bookbean.BookBean;
import bean.bookbean.BookIssueBean;
import bean.users.AuthBean;
import bean.users.UserBean;

public class UserDaoImpl implements UserDAO {
	
	private SessionFactory factory = utils.HConnection.getSessionFactory();
	private Session session = null;
	private Transaction tr = null;
	private List<UserBean> list = null;
	private Database connection;

	public UserDaoImpl() {
		list = new ArrayList<>();
		//connection = new Database();
	}

	@Override
	public int insert(UserBean c) throws ClassNotFoundException, SQLException {
		session = factory.openSession();
		tr = session.beginTransaction();
		int id = (Integer) session.save(c);
		tr.commit();
		session.close();
		return id;
	}

	@Override
	public List<UserBean> getAll() throws ClassNotFoundException, SQLException {
		list = new ArrayList<>();
		session = factory.openSession();
		tr = session.beginTransaction();
		list = session.createQuery("FROM UserBean").list();
		tr.commit();
		session.close();
		factory.close();
		return list;
	}

	@Override
	public UserBean getById(int id) throws ClassNotFoundException, SQLException {
		session = factory.openSession();
		tr = session.beginTransaction();        
        Criteria criteria = session.createCriteria(UserBean.class);
        criteria.add(Restrictions.eq("id", id));
        UserBean bean = (UserBean) criteria.uniqueResult();   
		tr.commit();
		session.close();
		factory.close();
		
		return bean;
	}

	@Override
	public int update(int id, UserBean c) throws ClassNotFoundException, SQLException {

		session = factory.openSession();
		tr = session.beginTransaction();
		UserBean b = (UserBean) session.get(UserBean.class, id);
		b.setFirstName(c.getFirstName());
		b.setLastName(c.getLastName());
		b.setStatus(c.getStatus());
		b.setRoles(c.getRoles());
		session.update(b);
		session.getTransaction().commit();
		session.close();
		return id;
	}

	@Override
	public int delete(int id) throws ClassNotFoundException, SQLException {
		session = factory.openSession();
		tr = session.beginTransaction();
		UserBean b = (UserBean) session.get(UserBean.class, id);
		session.delete(b);
		tr.commit();
		session.close();
		return id;
	}

	@Override
	public int softDelete(int id) throws ClassNotFoundException, SQLException {
		session = factory.openSession();
		tr = session.beginTransaction();
		UserBean b = (UserBean) session.get(UserBean.class, id);
		b.setStatus("0");
				
		return id;
	}

	@Override
	public int requestBook(BookIssueBean i) throws ClassNotFoundException, SQLException {
		session = factory.openSession();
		tr = session.beginTransaction();
		int id = (Integer) session.save(i);
		tr.commit();
		session.close();
		return id;
	}

	@Override
	public BookIssueBean getBookIssueById(int id) throws ClassNotFoundException, SQLException {
		session = factory.openSession();
		tr = session.beginTransaction();        
        Criteria criteria = session.createCriteria(UserBean.class);
        criteria.add(Restrictions.eq("id", id));
        BookIssueBean bean = (BookIssueBean) criteria.uniqueResult();   
		tr.commit();
		session.close();
		factory.close();
		
		return bean;
	}

	@Override
	public BookIssueBean getBookIssueRequestByBookId(int id, int approve) throws ClassNotFoundException, SQLException {
		
		session = factory.openSession();
		tr = session.beginTransaction();        
        Criteria criteria = session.createCriteria(UserBean.class);
        criteria.add(Restrictions.eq("bookId", id));
        if(approve != -1){
        	criteria.add(Restrictions.eq("agentId", approve));
		}
        BookIssueBean bean = (BookIssueBean) criteria.uniqueResult();   
		tr.commit();
		session.close();
		factory.close();
		
		return bean;
	}

	@Override
	public List<BookIssueBean> getBookIssueByUserId(int id) throws ClassNotFoundException, SQLException {
		
		session = factory.openSession();
		tr = session.beginTransaction();        
        Criteria criteria = session.createCriteria(BookIssueBean.class);
        criteria.add(Restrictions.eq("bookId", id));
        criteria.add(Restrictions.eq("agentId", 0));
        List<BookIssueBean> list = criteria.list();   
		
		session.close();
		factory.close();
		
		return list;
	}

	@Override
	public int removeRequestBookByBookBeanUserBean(BookBean b, UserBean ub) throws ClassNotFoundException, SQLException {
		
		session = factory.openSession();
		tr = session.beginTransaction();
		Criteria criteria = session.createCriteria(BookIssueBean.class);
		criteria.add(Restrictions.eq("book", b));
		criteria.add(Restrictions.eq("user", ub));
		BookIssueBean bean = (BookIssueBean) criteria.setMaxResults(1).uniqueResult();
		System.out.println(bean);
		session.delete(bean);
		tr.commit();
		session.close();
		return b.getId();
		
	}


	@Override
	public int acceptBookRequest(int id, BookIssueBean c) throws ClassNotFoundException, SQLException {
		session = factory.openSession();
		tr = session.beginTransaction();
		BookIssueBean b = (BookIssueBean) session.get(BookIssueBean.class, id);
		b.setStatus(c.getStatus());
		b.setAgentId(c.getAgentId());
		session.update(b);
		session.getTransaction().commit();
		session.close();
		return id;
	}

	@Override
	public int rejectedBookRequest(int id, int userId) throws ClassNotFoundException, SQLException {
		
		session = factory.openSession();
		tr = session.beginTransaction();
		BookIssueBean b = (BookIssueBean) session.get(BookIssueBean.class, id);
		b.setStatus("2");
		b.setAgentId(userId);
		session.update(b);
		session.getTransaction().commit();
		session.close();
		return id;
	}
	
	public Long getBookIssueLimit(UserBean user) throws ClassNotFoundException, SQLException {

	    session = factory.openSession();
		Query query = session.createQuery("select count(*) from BookIssueBean where user_id = :userId").setParameter("userId", user);
		Long count=(Long) query.uniqueResult();
		return count;
	    
	}
	
	@Override
	public BookIssueBean getBookIssueRequestByBookIdUserId(int bookId, int userId, int approve) throws ClassNotFoundException, SQLException {
		session = factory.openSession();
		tr = session.beginTransaction();        
        Criteria criteria = session.createCriteria(UserBean.class);
        criteria.add(Restrictions.eq("bookId", bookId));
        criteria.add(Restrictions.eq("userId", userId));
        if(approve != -1){
        	criteria.add(Restrictions.eq("agentId", approve));
		}
        BookIssueBean bean = (BookIssueBean) criteria.uniqueResult();   
		session.close();
		factory.close();
		
		return bean;
	}
	
	@Override
	public BookIssueBean getBookIssueRequestByBookBeanUserBean(BookBean bookId, UserBean userId, int approve, String status) throws ClassNotFoundException, SQLException {
		session = factory.openSession();   
        Criteria criteria = session.createCriteria(BookIssueBean.class);
        criteria.add(Restrictions.eq("book", bookId));
        criteria.add(Restrictions.eq("user", userId));
        if(approve != -1){
        	criteria.add(Restrictions.eq("agentId", approve));
		}
        if(status != null){
        	criteria.add(Restrictions.eq("status", status));
        }
        BookIssueBean bean = (BookIssueBean) criteria.setMaxResults(1).uniqueResult();   
		session.close();
		factory.close();
		
		return bean;
	}

	@Override
	public int setActiveUser(int id, UserBean ub) {
		
		session = factory.openSession();
		tr = session.beginTransaction();
		UserBean b = (UserBean) session.get(UserBean.class, id);
		b.setStatus(ub.getStatus());
		session.update(b);
		session.getTransaction().commit();
		session.close();
		return id;
	}

	@Override
	public int setActiveUserAuth(int id, AuthBean auth) {
		
		session = factory.openSession();
		tr = session.beginTransaction();
		AuthBean b = (AuthBean) session.get(AuthBean.class, id);
		b.setStatus(auth.getStatus());
		session.update(b);
		session.getTransaction().commit();
		session.close();
		return id;
	}

	@Override
	public List<UserBean> searchUserByKeyWord(String keyWord) {
		
		List<UserBean> list = new ArrayList<>();
		session = factory.openSession();
		String hql = "SELECT u from UserBean u INNER JOIN u.authBean auth where auth.email like :em OR u.firstName like :fName OR u.lastName like :lName";
		list = session.createQuery(hql)
		.setParameter("fName", "%"+keyWord+"%")
		.setParameter("em", "%"+keyWord+"%")
		.setParameter("lName", "%"+keyWord+"%").list();
		session.close();
		factory.close();
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
