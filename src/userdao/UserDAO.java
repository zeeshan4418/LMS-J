package userdao;

import java.sql.SQLException;
import java.util.List;

import bean.bookbean.BookBean;
import bean.bookbean.BookIssueBean;
import bean.users.AuthBean;
import bean.users.NotificationBean;
import bean.users.UserBean;

public interface UserDAO extends GenericDAO<UserBean> {

	int requestBook(BookIssueBean i) throws ClassNotFoundException, SQLException;

	//BookIssueBean getBookIssueRequestById(int id) throws ClassNotFoundException, SQLException;
	
	int removeRequestBookByBookBeanUserBean(BookBean b, UserBean ub) throws ClassNotFoundException, SQLException;

	BookIssueBean getBookIssueById(int id) throws ClassNotFoundException, SQLException;

	BookIssueBean getBookIssueRequestByBookId(int id, int approve) throws ClassNotFoundException, SQLException;
	
	BookIssueBean getBookIssueRequestByBookIdUserId(int id, int userId, int approve) throws ClassNotFoundException, SQLException;

	List<BookIssueBean> getBookIssueByUserId(int id) throws ClassNotFoundException, SQLException;

	int acceptBookRequest(int id, BookIssueBean bean) throws ClassNotFoundException, SQLException;

	int rejectedBookRequest(int id, int userId) throws ClassNotFoundException, SQLException;

	Long getBookIssueLimit(UserBean user) throws ClassNotFoundException, SQLException;
	
	BookIssueBean getBookIssueRequestByBookBeanUserBean(BookBean id, UserBean userId,int approve, String status) throws ClassNotFoundException, SQLException;

	int setActiveUser(int id, UserBean ub);

	int setActiveUserAuth(int id, AuthBean auth);

	List<UserBean> searchUserByKeyWord(String keyWord);

}