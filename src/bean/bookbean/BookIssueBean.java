package bean.bookbean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import bean.users.UserBean;

@Entity
@Table(name="book_issue")
public class BookIssueBean {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="b_issue_id")
	private int id;
	
	@ManyToOne
	@JoinColumn (name = "user_id")
	private UserBean user;
	
	@ManyToOne
	@JoinColumn (name = "book_id")
	private BookBean book;
	
	@Column(name="approve_by")
	private int agentId;
	
	@Column(name="date_of_issue")
	private String dateOfIssue;
	
	@Column(name="request_date")
	private String requestDate;
	
	@Column(name="is_return")
	private String isReturn;
	
	@Column(name="status")
	private String status;
	
	
	public BookIssueBean() {
		super();
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

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
		
}
