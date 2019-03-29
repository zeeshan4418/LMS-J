package services.users;

import java.sql.SQLException;
import java.util.List;

import userdao.AuthDAO;
import userdao.RolesDAO;
import userdao.UserDAO;
import userdaoimp.AuthDaoImpl;
import userdaoimp.NotificationDaoImpl;
import userdaoimp.RoleDaoImpl;
import userdaoimp.UserDaoImpl;
import admin.dao.NotificationsDAO;
import bean.bookbean.BookBean;
import bean.bookbean.BookIssueBean;
import bean.users.AuthBean;
import bean.users.NotificationBean;
import bean.users.RolesBean;
import bean.users.UserBean;

public class UserService {

	private UserDAO userDAO = new UserDaoImpl();
	private AuthDAO authDAO = new AuthDaoImpl();
	private RolesDAO roleDAO = new RoleDaoImpl();
	
	private NotificationsDAO nDAO = new NotificationDaoImpl();

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

	public RolesBean getRoleById(int id) throws ClassNotFoundException, SQLException {
		return roleDAO.getById(id);
	}

	public AuthBean getByEmailAndPass(String email, String password) throws ClassNotFoundException, SQLException {
		return authDAO.getByEmailAndPass(email, password);
	}

	public int requestBook(BookIssueBean i) throws ClassNotFoundException, SQLException {
		return userDAO.requestBook(i);
	}

	public BookIssueBean getBookIssueById(int id) throws ClassNotFoundException, SQLException {

		return userDAO.getBookIssueById(id);
	}

	public BookIssueBean getBookIssueRequestByBookId(int id, int approve) throws ClassNotFoundException, SQLException {

		return userDAO.getBookIssueRequestByBookId(id, approve);
	}
	
	public BookIssueBean getBookIssueRequestByBookIdUserId(int id, int userId,int approve) throws ClassNotFoundException, SQLException {

		return userDAO.getBookIssueRequestByBookIdUserId(id, userId,approve);
	}


	public int removeRequestBookByBookBeanUserBean(BookBean b, UserBean ub) throws ClassNotFoundException, SQLException {

		return userDAO.removeRequestBookByBookBeanUserBean(b, ub);
	}
	
	public Long getBookIssueLimit(UserBean user) throws ClassNotFoundException, SQLException{
		
		return userDAO.getBookIssueLimit(user);
	}
	
	public BookIssueBean getBookIssueRequestByBookBeanUserBean(BookBean id, UserBean userId,int approve, String status) throws ClassNotFoundException, SQLException {

		return userDAO.getBookIssueRequestByBookBeanUserBean(id, userId,approve, status);
	}
	
	public List<NotificationBean> getAllNotificationByUser(UserBean ub){
		
		return nDAO.getAllNotificationByUser(ub);
	}
	
	public AuthBean getEmailByUser(UserBean ub) throws ClassNotFoundException, SQLException{
		
		return authDAO.getEmailByUser(ub);
	}
	
	public List<UserBean> getAllUsersByKeyWord(String keyWord) throws ClassNotFoundException, SQLException {

		return userDAO.searchUserByKeyWord(keyWord);

	}
	
	public int insertNotification(NotificationBean nb) throws ClassNotFoundException, SQLException{
		
		return nDAO.insert(nb);
	}
	
	public Long getNotificationsCount(UserBean ub){
		
		return nDAO.getNotificationsCount(ub);
	}

	public int updateNotificationAsRead(int id, NotificationBean bean) throws ClassNotFoundException, SQLException {
		
		return nDAO.updateStatus(id, bean);
	}
	
	public int softDeleteNotification(int id) throws ClassNotFoundException, SQLException{
		
		return nDAO.softDeleteNotification(id);
	}

	
	
}
