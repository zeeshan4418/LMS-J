package admin.services;

import java.sql.SQLException;
import java.util.List;

import admin.dao.BookDAO;
import admin.dao.impl.BookDaoImpl;
import bean.bookbean.BookBean;
import bean.bookbean.BookIssueBean;
import bean.users.AuthBean;
import bean.users.RolesBean;
import bean.users.UserBean;
import userdao.AuthDAO;
import userdao.RolesDAO;
import userdao.UserDAO;
import userdaoimp.AuthDaoImpl;
import userdaoimp.RoleDaoImpl;
import userdaoimp.UserDaoImpl;

public class AdminServices {

	private UserDAO userDAO = new UserDaoImpl();
	private AuthDAO authDAO = new AuthDaoImpl();
	private RolesDAO roleDAO = new RoleDaoImpl();
	private BookDAO bookDAO = new BookDaoImpl();

	public int insert(UserBean c) throws ClassNotFoundException, SQLException {
		return userDAO.insert(c);
	}

	public int update(int id, UserBean c) throws ClassNotFoundException, SQLException {
		return userDAO.update(id, c);
	}

	public int delete(int id) throws ClassNotFoundException, SQLException {
		return userDAO.delete(id);
	}

	public int softDelete(int id) throws ClassNotFoundException, SQLException {
		return userDAO.softDelete(id);
	}

	public List<UserBean> getAll() throws ClassNotFoundException, SQLException {
		return userDAO.getAll();
	}

	public UserBean getById(int id) throws ClassNotFoundException, SQLException {
		return userDAO.getById(id);
	}

	public AuthBean getByEmail(String email) throws ClassNotFoundException, SQLException {
		return authDAO.getByEmail(email);
	}

	public RolesBean getByRoleId(int id) throws ClassNotFoundException, SQLException {
		return roleDAO.getById(id);
	}

	public AuthBean getByEmailAndPass(String email, String password) throws ClassNotFoundException, SQLException {
		return authDAO.getByEmailAndPass(email, password);
	}

	public int insertBook(BookBean c) throws ClassNotFoundException, SQLException {
		return bookDAO.insert(c);
	}

	public int update(int id, BookBean c) throws ClassNotFoundException, SQLException {
		return bookDAO.update(id, c);
	}

	public int deleteBook(int id) throws ClassNotFoundException, SQLException {
		return bookDAO.delete(id);
	}

	public int softDeleteBook(int id) throws ClassNotFoundException, SQLException {
		return bookDAO.softDelete(id);
	}

	public List<BookBean> getAllBooks() throws ClassNotFoundException, SQLException {
		return bookDAO.getAll();
	}

	public BookBean getBookById(int id) throws ClassNotFoundException, SQLException {
		return bookDAO.getById(id);
	}
	
	public UserBean getUserById(int id) throws ClassNotFoundException, SQLException {
		return userDAO.getById(id);
	}

	public int enableBook(int id) throws ClassNotFoundException, SQLException {
		return bookDAO.enableBook(id);
	}

	public List<BookBean> getBookByKeyWord(String keyWord) throws ClassNotFoundException, SQLException {

		return bookDAO.searchBookByKeyWord(keyWord);

	}

	public List<BookIssueBean> getAllBookRequests() throws ClassNotFoundException, SQLException {

		return bookDAO.getAllBookRequests();

	}

	public int acceptBookRequest(int id, BookIssueBean b) throws ClassNotFoundException, SQLException {
		
		return userDAO.acceptBookRequest(id, b);
	}

	public int rejectedBookRequest(int id, int userId) throws ClassNotFoundException, SQLException {
		
		return userDAO.rejectedBookRequest(id, userId);
	}
	
	public Long getRequestCount() throws ClassNotFoundException, SQLException{
		
		return bookDAO.getRequestCount();
	}

	public int getAvailableCopies(int bookId) throws ClassNotFoundException, SQLException {

		return bookDAO.getAvailableCopies(bookId);
	}

	public int setActiveUser(int id, UserBean ub) {
		return userDAO.setActiveUser(id, ub);
	}
	
	public int setActiveUserAuth(int id, AuthBean auth) {
		return userDAO.setActiveUserAuth(id, auth);
	}

	
	
}
 