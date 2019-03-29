package admin.dao;

import java.sql.SQLException;
import java.util.List;

import bean.bookbean.BookBean;
import bean.bookbean.BookIssueBean;

public interface BookDAO extends GenericDAO<BookBean> {

	int enableBook(int id)throws ClassNotFoundException,SQLException;
	int update(int id, BookBean bean)throws ClassNotFoundException,SQLException;
	List<BookBean> searchBookByKeyWord(String keyWord) throws ClassNotFoundException, SQLException;

	List<BookIssueBean> getAllBookRequests() throws ClassNotFoundException, SQLException;

	Long getRequestCount() throws ClassNotFoundException, SQLException;

	int getAvailableCopies(int bookId) throws ClassNotFoundException, SQLException;

}
