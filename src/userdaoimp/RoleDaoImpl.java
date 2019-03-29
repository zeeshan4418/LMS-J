package userdaoimp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import userdao.RolesDAO;
import bean.users.RolesBean;

public class RoleDaoImpl implements RolesDAO {

	private SessionFactory factory = utils.HConnection.getSessionFactory();
	private Session session = null;
	private Transaction tr = null;
	private List<RolesBean> list = null;

	public RoleDaoImpl() {
		list = new ArrayList<>();
	}

	@Override
	public int insert(RolesBean c) throws ClassNotFoundException, SQLException {
		session = factory.openSession();
		tr = session.beginTransaction();
		int id = (Integer) session.save(c);
		tr.commit();
		session.close();
		return id;
	}

	@Override
	public List<RolesBean> getAll() throws ClassNotFoundException, SQLException {
		list = new ArrayList<>();
		session = factory.openSession();
		tr = session.beginTransaction();
		list = session.createQuery("FROM RolesBean").list();
		tr.commit();
		session.close();
		factory.close();
		return list;
	}

	@Override
	public RolesBean getById(int id) throws ClassNotFoundException,
			SQLException {

		session = factory.openSession();
		tr = session.beginTransaction();
		Criteria criteria = session.createCriteria(RolesBean.class);
		criteria.add(Restrictions.eq("id", id));
		RolesBean bean = (RolesBean) criteria.uniqueResult();
		tr.commit();
		session.close();
		factory.close();

		return bean;
	}

	@Override
	public int update(int id, RolesBean c) throws ClassNotFoundException,
			SQLException {
		session = factory.openSession();
		tr = session.beginTransaction();
		RolesBean b = (RolesBean) session.get(RolesBean.class, id);
		b.setRole(c.getRole());
		session.update(b);
		session.getTransaction().commit();
		session.close();		
		
		return id;
	}

	@Override
	public int delete(int id) throws ClassNotFoundException, SQLException {
		session = factory.openSession();
		tr = session.beginTransaction();
		RolesBean b = (RolesBean) session.get(RolesBean.class, id);
		session.delete(b);
		tr.commit();
		session.close();
		return id;
	}

	@Override
	public int softDelete(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

}
