package controllerBeans;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import services.users.UserService;
import admin.services.AdminServices;
import bean.bookbean.BookBean;
import bean.bookbean.BookIssueBean;
import bean.users.NotificationBean;
import bean.users.UserBean;

@ManagedBean(name = "bookIssueMB")
@SessionScoped
public class BookIssueMB {

	private Integer id;
	private UserBean user;
	private BookBean book;
	private int agentId;
	private String dateOfIssue;
	private String requestDate;
	private String isReturn;
	private String status;

	private AdminServices as = new AdminServices();
	public UserService us = new UserService();

	public String removeBookIssue(BookBean bean, UserBean u)
			throws ClassNotFoundException, SQLException {

		/*
		 * FacesContext facesContext = FacesContext.getCurrentInstance();
		 * HttpSession session = (HttpSession)
		 * facesContext.getExternalContext().getSession(true); UserBean ub =
		 * (UserBean) session.getAttribute("user");
		 */

		BookBean b = new BookBean();
		b.setId(bean.getId());
		us.removeRequestBookByBookBeanUserBean(b, u);

		return "Book Request Remove";
	}

	public String acceptBookIssue(BookBean b, UserBean u)
			throws ClassNotFoundException, SQLException {

		String message = "";
		long millis = System.currentTimeMillis();
		String requestDate = new Date(millis).toString();
		try {
			BookIssueBean ib = new BookIssueBean();
			ib.setUser(u);
			ib.setAgentId(0);
			ib.setBook(b);
			ib.setRequestDate(requestDate);
			ib.setIsReturn("0");
			ib.setStatus("0");
			ib.setDateOfIssue("NULL");

			Long countlimit = us.getBookIssueLimit(u);
			if (countlimit < 5) {
				us.requestBook(ib);
				NotificationBean nb = new NotificationBean();
				nb.setAgentId(u.getId());
				nb.setDate(requestDate);
				nb.setMessage("Send Request For Book Issue");
				nb.setUser(u);
				nb.setStatus("0");
				us.insertNotification(nb);
				message = "book issued";
			} else {
				message = "Book issued Limit is Over";
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return message;
		// return "";
	}

	public String acceptRequestByAdmin(int id, int bookId, BookBean bb) throws ClassNotFoundException,
			SQLException {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext()
				.getSession(true);
		UserBean ub = (UserBean) session.getAttribute("user");
		
		/* Update Book Available Copies */
		BookBean book = new BookBean();
		book.setAvailableCopies((bb.getAvailableCopies()-1));
		book.setTotalCopies(bb.getTotalCopies());
		book.setBookAuthor(bb.getBookAuthor());
		book.setBookLink(bb.getBookLink());
		book.setBookPublisher(bb.getBookPublisher());
		book.setBookStatus(bb.getBookStatus());
		book.setBookTitle(bb.getBookTitle());
		
		as.update(bookId, book);
		
		
		BookIssueBean b = new BookIssueBean();
		b.setStatus("1");
		b.setAgentId(ub.getId());
		as.acceptBookRequest(id, b);

		return "Book Issued";
	}

	public String acceptRejectedRequestByAdmin(int id, int bookId, BookBean bb)
			throws ClassNotFoundException, SQLException {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext()
				.getSession(true);
		UserBean ub = (UserBean) session.getAttribute("user");
		
		/* Update Book Available Copies */
		BookBean book = new BookBean();
		book.setAvailableCopies((bb.getAvailableCopies()-1));
		book.setTotalCopies(bb.getTotalCopies());
		book.setBookAuthor(bb.getBookAuthor());
		book.setBookLink(bb.getBookLink());
		book.setBookPublisher(bb.getBookPublisher());
		book.setBookStatus(bb.getBookStatus());
		book.setBookTitle(bb.getBookTitle());
		
		as.update(bookId, book);
		
		
		
		BookIssueBean b = new BookIssueBean();
		b.setStatus("1");
		b.setAgentId(ub.getId());
		as.acceptBookRequest(id, b);

		return "Book Issued";
	}

	public String rejectedBookRequest(int id, int bookId, BookBean bb)
			throws ClassNotFoundException, SQLException {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		UserBean ub = (UserBean) session.getAttribute("user");
		
		/* Update Book Available Copies */
		BookBean book = new BookBean();
		book.setAvailableCopies((bb.getAvailableCopies()+1));
		book.setTotalCopies(bb.getTotalCopies());
		book.setBookAuthor(bb.getBookAuthor());
		book.setBookLink(bb.getBookLink());
		book.setBookPublisher(bb.getBookPublisher());
		book.setBookStatus(bb.getBookStatus());
		book.setBookTitle(bb.getBookTitle());
		
		as.update(bookId, book);
		
		
		as.rejectedBookRequest(id, ub.getId());

		return "Book Issue Decline";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public BookBean getBook() {
		return book;
	}

	public void setBook(BookBean book) {
		this.book = book;
	}

	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	public String getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(String dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	public String getIsReturn() {
		return isReturn;
	}

	public void setIsReturn(String isReturn) {
		this.isReturn = isReturn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BookIssueMB(Integer id, UserBean user, BookBean book, int agentId,
			String dateOfIssue, String requestDate, String isReturn,
			String status) {
		super();
		this.id = id;
		this.user = user;
		this.book = book;
		this.agentId = agentId;
		this.dateOfIssue = dateOfIssue;
		this.requestDate = requestDate;
		this.isReturn = isReturn;
		this.status = status;
	}

	public BookIssueMB() {
		super();
	}

	public AdminServices getAs() {
		return as;
	}

	public void setAs(AdminServices as) {
		this.as = as;
	}

	public UserService getUs() {
		return us;
	}

	public void setUs(UserService us) {
		this.us = us;
	}

}
