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

import utils.Database;
import admin.dao.NotificationsDAO;
import bean.users.NotificationBean;
import bean.users.UserBean;

public class NotificationDaoImpl implements NotificationsDAO {

	private SessionFactory factory = utils.HConnection.getSessionFactory();
	private Session session = null;
	private Transaction tr = null;
	private List<NotificationBean> list = null;
	private Database connection;

	public NotificationDaoImpl() {
		list = new ArrayList<>();
		connection = new Database();
	}

	@Override
	public List<NotificationBean> getAll() throws ClassNotFoundException,
			SQLException {
		list = new ArrayList<>();
		session = factory.openSession();
		tr = session.beginTransaction();
		list = session.createQuery("FROM NotificationBean").list();
		tr.commit();
		session.close();
		factory.close();
		return list;
	}

	@Override
	public NotificationBean getById(int id) throws ClassNotFoundException,
			SQLException {
		session = factory.openSession();
		tr = session.beginTransaction();        
        Criteria criteria = session.createCriteria(NotificationBean.class);
        criteria.add(Restrictions.eq("id", id));
        NotificationBean bean = (NotificationBean) criteria.uniqueResult();   
		tr.commit();
		session.close();
		factory.close();
		return bean;
	}

	@Override
	public int insert(NotificationBean t) throws ClassNotFoundException,
			SQLException {
		session = factory.openSession();
		tr = session.beginTransaction();
		int id = (Integer) session.save(t);
		tr.commit();
		session.close();
		return id;
	}

	@Override
	public int update(int id, NotificationBean t) throws ClassNotFoundException,
			SQLException {
		session = factory.openSession();
		tr = session.beginTransaction();
		NotificationBean b = (NotificationBean) session.get(NotificationBean.class, id);
		b.setAgentId(t.getAgentId());
		b.setDate(t.getDate());
		b.setMessage(t.getMessage());
		b.setStatus(t.getStatus());
		b.setUser(t.getUser());
		session.update(b);
		session.getTransaction().commit();
		session.close();
		return id;
	}

	@Override
	public int delete(int id) throws ClassNotFoundException, SQLException {
		session = factory.openSession();
		NotificationBean b = (NotificationBean) session.get(NotificationBean.class, id);
		session.delete(b);
		session.close();
		return id;
	}

	@Override
	public int softDeleteNotification(int id) throws ClassNotFoundException, SQLException {
		
		session = factory.openSession();
		tr = session.beginTransaction();
		NotificationBean b = (NotificationBean) session.get(NotificationBean.class, id);
		b.setDel("0");
		session.update(b);
		session.getTransaction().commit();
		session.close();
		return id;
	}

	@Override
	public List<NotificationBean> getAllNotificationByUser(UserBean ub) {
		
		list = new ArrayList<>();
		session = factory.openSession();
		Criteria criteria = session.createCriteria(NotificationBean.class);
        criteria.add(Restrictions.eq("user", ub));
        criteria.add(Restrictions.eq("del", "1"));
		list = criteria.list();
        session.close();
		factory.close();
		return list;
	}

	@Override
	public Long getNotificationsCount(UserBean ub) {
		session = factory.openSession();
		Query query = session.createQuery("select count(*) from NotificationBean where user = :u AND status = :s")
				.setParameter("u", ub)
				.setParameter("s", "0");
		
		Long count=(Long) query.uniqueResult();
		return count;
	}
	
	@Override
	public int updateStatus(int id, NotificationBean t) throws ClassNotFoundException,
			SQLException {
		session = factory.openSession();
		tr = session.beginTransaction();
		NotificationBean b = (NotificationBean) session.get(NotificationBean.class, id);
		b.setStatus(t.getStatus());
		session.update(b);
		session.getTransaction().commit();
		session.close();
		return id;
	}

	@Override
	public int softDelete(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
