package controllerBeans;

import java.sql.SQLException;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import admin.services.AdminServices;
import bean.bookbean.BookBean;

@ManagedBean(name = "bookMb")
@ApplicationScoped
public class BookMB {

	private Integer id;
	private String bookTitle;
	private String bookAuthor;
	private String bookPublisher;
	private String bookStatus;
	private String bookLink;
	private int totalCopies;
	private int availableCopies;
	public String message;
	private String keyWord;
	private List<BookBean> list = null;

	private AdminServices as = new AdminServices();

	public List<BookBean> getBooks() throws ClassNotFoundException,
			SQLException {

		if (this.keyWord != null) {
			list = as.getBookByKeyWord(this.keyWord);
		} else {
			list = as.getAllBooks();
		}
		return list;
	}

	public String getBookByKeyWord() {
		
		return "";
	}

	public String deleteBook(int id) throws ClassNotFoundException, SQLException {
		as.deleteBook(id);
		return "Successfuly Delete";	
	}

	public String saveBook() throws ClassNotFoundException, SQLException {

		if (this.bookLink == null) {
			this.bookLink = "0";
		}
		if (this.bookStatus == null) {
			this.bookStatus = "0";
		} else {
			this.bookStatus = "1";
		}

		int id = as.insertBook(new BookBean(this.bookTitle, this.bookAuthor,
				this.bookPublisher, this.bookStatus, this.bookLink,
				this.totalCopies, this.totalCopies));

		if (id > -1) {

			return "Book Add Successfully";
		} else {
			return "Book Not Added";
		}

	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public String getBookPublisher() {
		return bookPublisher;
	}

	public void setBookPublisher(String bookPublisher) {
		this.bookPublisher = bookPublisher;
	}

	public String getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}

	public String getBookLink() {
		return bookLink;
	}

	public void setBookLink(String bookLink) {
		this.bookLink = bookLink;
	}

	public int getTotalCopies() {
		return totalCopies;
	}

	public void setTotalCopies(int totalCopies) {
		this.totalCopies = totalCopies;
	}

	public int getAvailableCopies() {
		return availableCopies;
	}

	public void setAvailableCopies(int availableCopies) {
		this.availableCopies = availableCopies;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public BookMB(Integer id, String bookTitle, String bookAuthor,
			String bookPublisher, String bookStatus, String bookLink,
			int totalCopies, int availableCopies) {
		super();
		this.id = id;
		this.bookTitle = bookTitle;
		this.bookAuthor = bookAuthor;
		this.bookPublisher = bookPublisher;
		this.bookStatus = bookStatus;
		this.bookLink = bookLink;
		this.totalCopies = totalCopies;
		this.availableCopies = availableCopies;
	}

	public BookMB() {
		super();
	}

}
