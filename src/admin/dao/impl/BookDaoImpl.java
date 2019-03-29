package admin.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import utils.Database;
import admin.dao.BookDAO;
import bean.bookbean.BookBean;
import bean.bookbean.BookIssueBean;

public class BookDaoImpl implements BookDAO {

	private SessionFactory factory = utils.HConnection.getSessionFactory();
	private Session session = null;
	private Transaction tr = null;
	private List<BookBean> list = null;
	private Database connection;

	public BookDaoImpl() {
		connection = new Database();
		list = new ArrayList<>();
	}

	@Override
	public int insert(BookBean bean) throws ClassNotFoundException,
			SQLException {
		session = factory.openSession();
		tr = session.beginTransaction();
		int id = (Integer) session.save(bean);
		session.flush();
		tr.commit();
		session.close();
		return id;
	}

	@Override
	public List<BookBean> getAll() throws ClassNotFoundException, SQLException {

		list = new ArrayList<>();
		session = factory.openSession();
		tr = session.beginTransaction();
		list = session.createQuery("FROM BookBean").list();
		tr.commit();
		session.close();
		factory.close();
		return list;
	}

	@Override
	public BookBean getById(int id) throws ClassNotFoundException, SQLException {
		session = factory.openSession();
		tr = session.beginTransaction();        
        Criteria criteria = session.createCriteria(BookBean.class);
        criteria.add(Restrictions.eq("id", id));
        BookBean bean = (BookBean) criteria.uniqueResult();   
		tr.commit();
		session.close();
		factory.close();
		
		return bean;
	}

	@Override
	public int update(int id, BookBean bean) throws ClassNotFoundException, SQLException {
		
		session = factory.openSession();
		tr = session.beginTransaction();
		BookBean b = (BookBean) session.get(BookBean.class, id);
		b.setBookTitle(bean.getBookTitle());
		b.setBookAuthor(bean.getBookAuthor());
		b.setBookPublisher(bean.getBookPublisher());
		b.setBookLink(bean.getBookLink());
		b.setAvailableCopies(bean.getAvailableCopies());
		b.setTotalCopies(bean.getTotalCopies());
		b.setBookStatus(bean.getBookStatus());
		session.update(b);
		session.getTransaction().commit();
		session.close();
		
		return id;
	}

	@Override
	public int delete(int id) throws ClassNotFoundException, SQLException {
		
		session = factory.openSession();
		tr = session.beginTransaction();
		BookBean b = (BookBean) session.get(BookBean.class, id);
		session.delete(b);
		tr.commit();
		session.close();
		return id;
		
	}

	@Override
	public int softDelete(int id) throws ClassNotFoundException, SQLException {
		session = factory.openSession();
		tr = session.beginTransaction();
		BookBean b = (BookBean) session.get(BookBean.class, id);
		b.setBookStatus("0");
		session.update(b);
		session.getTransaction().commit();
		session.close();
		return id;
	}

	@Override
	public int enableBook(int id) throws ClassNotFoundException, SQLException {
		session = factory.openSession();
		tr = session.beginTransaction();
		BookBean b = (BookBean) session.get(BookBean.class, id);
		b.setBookStatus("1");
		session.update(b);
		session.getTransaction().commit();
		session.close();
		return id;
	}

	@Override
	public List<BookBean> searchBookByKeyWord(String keyWord)
			throws ClassNotFoundException, SQLException {
		List<BookBean> list = new ArrayList<>();
		session = factory.openSession();
		tr = session.beginTransaction();
		String hql = "from BookBean b where b.bookTitle like :title OR b.bookAuthor like :author OR bookPublisher like :publisher";
		list = session.createQuery(hql)
		.setParameter("title", "%"+keyWord+"%")
		.setParameter("author", "%"+keyWord+"%")
		.setParameter("publisher", "%"+keyWord+"%").list();
		tr.commit();
		session.close();
		factory.close();
		return list;
	}

	@Override
	public List<BookIssueBean> getAllBookRequests()
			throws ClassNotFoundException, SQLException {
		List<BookIssueBean> list = new ArrayList<>();
		session = factory.openSession();
		tr = session.beginTransaction();
		list = session.createQuery("FROM BookIssueBean").list();
		tr.commit();
		session.close();
		factory.close();
		return list;
		
	}

	public Long getRequestCount() throws ClassNotFoundException, SQLException {
		
		/*int count = 0;
		count = (int) session.createQuery("select count(*) from BookIssueBean").iterate().next();
		System.out.println(count);
		Criteria criteria = session.createCriteria(BookIssueBean.class);
		criteria.add(Restrictions.eq("status", "0"));
        count = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult(); 
        return count;
		*/
		//session = factory.openSession();
		session = factory.openSession();
		Query query = session.createQuery("select count(*) from BookIssueBean where status = :s").setParameter("s", "0");
		//System.out.print("sss"+list.size());
		Long count=(Long) query.uniqueResult();
		return count;
	}

	@Override
	public int getAvailableCopies(int bookId) throws ClassNotFoundException,
			SQLException {

		String sql = "SELECT * FROM books_copies where status=1";
		connection.open();
		Statement s = connection.initStatementRow();
		ResultSet rs = s.executeQuery(sql);
		rs.last();
		int count = rs.getRow();
		rs.beforeFirst();
		connection.close();
		return count;
	}

	/*
	 * @Override public BookBean getAvailableCopies(int id) throws
	 * ClassNotFoundException, SQLException { BookBean c = null;
	 * connection.open(); String sql = "SELECT * FROM books WHERE book_id=?";
	 * PreparedStatement stmt = connection.initStatement(sql); stmt.setInt(1,
	 * id); ResultSet rs = connection.executeQuery(); while (rs.next()) { c =
	 * new BookBean(); c.setId(rs.getInt("book_id"));
	 * c.setBookTitle(rs.getString("book_title"));
	 * c.setBookAuthor(rs.getString("book_author"));
	 * c.setBookPublisher(rs.getString("book_publisher"));
	 * c.setAvailableCopies(rs.getInt("available_copies"));
	 * c.setTotalCopies(rs.getInt("total_copies"));
	 * c.setBookLink(rs.getString("book_link"));
	 * c.setBookStatus(rs.getString("status")); }
	 * 
	 * connection.close(); return c; }
	 */
}
