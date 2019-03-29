package bean.bookbean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="books")
public class BookBean {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="book_id")
	private int id;
	
	@Column(name="book_title")
	private String bookTitle;
	
	@Column(name="book_author")
	private String bookAuthor;
	
	@Column(name="book_publisher")
	private String bookPublisher;
	
	@Column(name="status")
	private String bookStatus;
	
	@Column(name="book_link")
	private String bookLink;
	
	@Column(name="total_copies")
	private int totalCopies;
	
	@Column(name="available_copies")
	private int availableCopies;
	
	public BookBean() {
		super();
	}


	public BookBean(String bookTitle, String bookAuthor, String bookPublisher,
			String bookStatus, String bookLink, int totalCopies,
			int availableCopies) {
		super();
		this.bookTitle = bookTitle;
		this.bookAuthor = bookAuthor;
		this.bookPublisher = bookPublisher;
		this.bookStatus = bookStatus;
		this.bookLink = bookLink;
		this.totalCopies = totalCopies;
		this.availableCopies = availableCopies;
	}


	public BookBean(int id, String bookTitle, String bookAuthor,
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


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getBookTitle() {
		return bookTitle;
	}


	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
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


	@Override
	public String toString() {
		return "BookBean [id=" + id + ", bookTitle=" + bookTitle
				+ ", bookAuthor=" + bookAuthor + ", bookPublisher="
				+ bookPublisher + ", bookStatus=" + bookStatus + ", bookLink="
				+ bookLink + ", totalCopies=" + totalCopies
				+ ", availableCopies=" + availableCopies + "]";
	}
	
	
	
}
