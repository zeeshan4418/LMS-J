package userdaoimp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import userdao.AuthDAO;
import utils.Database;
import bean.users.AuthBean;
import bean.users.UserBean;

public class AuthDaoImpl implements AuthDAO {

	private SessionFactory factory = utils.HConnection.getSessionFactory();
	private Session session = null;
	private Transaction tr = null;
	private List<AuthBean> list = null;
	private Database connection;

	public AuthDaoImpl() {
		list = new ArrayList<>();
		connection = new Database();
	}

	@Override
	public int insert(AuthBean c) throws ClassNotFoundException, SQLException {
		session = factory.openSession();
		tr = session.beginTransaction();
		int id = (Integer) session.save(c);
		tr.commit();
		session.close();
		return id;
	}

	@Override
	public List<AuthBean> getAll() throws ClassNotFoundException, SQLException {
		list = new ArrayList<>();
		session = factory.openSession();
		tr = session.beginTransaction();
		list = session.createQuery("FROM AuthBean").list();
		tr.commit();
		session.close();
		factory.close();
		return list;

	}

	@Override
	public AuthBean getById(int id) throws ClassNotFoundException, SQLException {
		session = factory.openSession();
		tr = session.beginTransaction();
		Criteria criteria = session.createCriteria(AuthBean.class);
		criteria.add(Restrictions.eq("id", id));
		AuthBean bean = (AuthBean) criteria.uniqueResult();
		tr.commit();
		session.close();
		factory.close();

		return bean;
	}

	@Override
	public AuthBean getByEmail(String email) throws ClassNotFoundException,
			SQLException {
		session = factory.openSession();
		tr = session.beginTransaction();
		Criteria criteria = session.createCriteria(AuthBean.class);
		criteria.add(Restrictions.eq("email", email));
		AuthBean bean = (AuthBean) criteria.uniqueResult();
		tr.commit();
		session.close();
		factory.close();
		return bean;
	}

	@Override
	public AuthBean getByEmailAndPass(String email, String password)
			throws ClassNotFoundException, SQLException {
		session = factory.openSession();
		tr = session.beginTransaction();
		Criteria criteria = session.createCriteria(AuthBean.class);
		criteria.add(Restrictions.eq("email", email));
		criteria.add(Restrictions.eq("password", password));
		criteria.add(Restrictions.eq("status", "1"));
		AuthBean bean = (AuthBean) criteria.uniqueResult();
		tr.commit();
		session.close();
		factory.close();
		return bean;
	}

	@Override
	public int update(int id, AuthBean c) throws ClassNotFoundException,
			SQLException {
		session = factory.openSession();
		tr = session.beginTransaction();
		AuthBean b = (AuthBean) session.get(AuthBean.class, id);
		b.setEmail(c.getEmail());
		b.setPassword(c.getPassword());
		b.setUser(c.getUser());
		b.setStatus(c.getStatus());
		session.update(b);
		session.getTransaction().commit();
		session.close();
		return id;
	}

	@Override
	public int delete(int id) throws ClassNotFoundException, SQLException {
		session = factory.openSession();
		tr = session.beginTransaction();
		AuthBean b = (AuthBean) session.get(AuthBean.class, id);
		session.delete(b);
		tr.commit();
		session.close();
		return id;
	}

	@Override
	public int softDelete(int id) throws ClassNotFoundException, SQLException {
		session = factory.openSession();
		tr = session.beginTransaction();
		AuthBean b = (AuthBean) session.get(AuthBean.class, id);
		b.setStatus("0");
		session.update(b);
		session.getTransaction().commit();
		session.close();
		return id;
	}

	@Override
	public AuthBean getEmailByUser(UserBean ub) throws ClassNotFoundException,
			SQLException {
		session = factory.openSession();
		Criteria criteria = session.createCriteria(AuthBean.class);
		criteria.add(Restrictions.eq("user", ub));
		AuthBean bean = (AuthBean) criteria.uniqueResult();
		session.close();
		factory.close();
		return bean;
	}
}
