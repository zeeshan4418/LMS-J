package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import services.users.UserService;
import admin.services.AdminServices;
import bean.bookbean.BookBean;
import bean.bookbean.BookIssueBean;
import bean.users.AuthBean;
import bean.users.NotificationBean;
import bean.users.RolesBean;
import bean.users.UserBean;

@WebServlet(urlPatterns = { "/admin/dashboard", "/admin/book/*","/admin/Dashboard", "/admin/users/*" })
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdminServices adminService = new AdminServices();

	
	 private UserService userService = new UserService();
	// UserAuthService authService = new UserAuthService();
	

	public AdminController() {
		super();

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String page = SessionChk(req, res);
		if (page != null) {
			HttpSession session = req.getSession(false);
			RolesBean rb = (RolesBean) session.getAttribute("userRole");
			if (rb.getId() != 1) {
				res.sendRedirect(page);
			}
		}
		String action = req.getRequestURI();
		String parameter[] = action.split("/");
		System.out.print(Arrays.toString(parameter));
		System.out.print(parameter[3]);
		switch (parameter[3]) {
		case "dashboard":
			res.sendRedirect("/Library/admin/index.jsp");
			break;
		case "Dashboard":
			res.sendRedirect("/Library/admin/index.jsp");
			break;
		case "book":
			if (parameter[4].equals("add")) {
				String bookTitle = req.getParameter("bookTitle");
				String bookAuthor = req.getParameter("bookAuthor");
				String bookPublisher = req.getParameter("bookPublisher");
				String bookStatus = req.getParameter("bookStatus");
				String bookLink = req.getParameter("bookLink");
				Integer bookCopies = Integer.parseInt(req
						.getParameter("bookCopies"));

				if (bookLink == null) {
					bookLink = "0";
				}
				if (bookStatus == null) {
					bookStatus = "0";
				} else {
					bookStatus = "1";
				}

				try {
					int id = adminService.insertBook(new BookBean(bookTitle,
							bookAuthor, bookPublisher, bookStatus, bookLink,
							bookCopies, bookCopies));
					HttpSession session = req.getSession(false);
					if (id > -1) {
						page = "/Library/admin/Books.jsp";
						session.setAttribute("successMessage",
								"Book Add Successfully");
						res.sendRedirect(page);
					} else {
						page = "/Library/admin/Books/AddBook.jsp";

						req.setAttribute("bookTitle", bookTitle);
						req.setAttribute("bookAuthor", bookAuthor);
						req.setAttribute("bookPublisher", bookPublisher);
						req.setAttribute("bookLink", bookLink);
						req.setAttribute("bookStatus", bookStatus);
						req.setAttribute("errorMessage", "Book not Add");
						req.getRequestDispatcher(page).forward(req, res);
					}

				}// try closes
				catch (ClassNotFoundException | SQLException e) {
					System.out.println("Book Not Inserted");
				}// catch close
			} else if (parameter[4].equals("delete")) {
				int id = Integer.parseInt(parameter[5]);
				try {
					adminService.deleteBook(id);
					res.sendRedirect("/Library/admin/Books.jsp");
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			} else if (parameter[4].equals("disable")) {
				int id = Integer.parseInt(parameter[5]);
				try {
					adminService.softDeleteBook(id);
					res.sendRedirect("/Library/admin/Books.jsp");
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			} else if (parameter[4].equals("enable")) {
				int id = Integer.parseInt(parameter[5]);
				try {
					adminService.enableBook(id);
					res.sendRedirect("/Library/admin/Books.jsp");
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			} else if (parameter[4].equals("search")) {
				String keyWord = req.getParameter("search");
				try {
					req.setAttribute("search",
							adminService.getBookByKeyWord(keyWord));
					req.getRequestDispatcher("Books.jsp").forward(req, res);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				PrintWriter w = res.getWriter();
				w.write(keyWord);
			} else if (parameter[4].equals("acceptRequest")) {
				int id = Integer.parseInt(parameter[5]);
				HttpSession session = req.getSession(false);
				UserBean ub = (UserBean) session.getAttribute("user");
				try {
					// int availableCopies = adminService.getAvailableCopies();
					BookIssueBean b = new BookIssueBean();
					b.setStatus("1");
					b.setAgentId(ub.getId());
					adminService.acceptBookRequest(id, b);
					res.sendRedirect("/Library/admin/BookRequest.jsp");
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (parameter[4].equals("decline")) {
				int id = Integer.parseInt(parameter[5]);
				HttpSession session = req.getSession(false);
				UserBean ub = (UserBean) session.getAttribute("user");
				try {
					adminService.rejectedBookRequest(id, ub.getId());
					res.sendRedirect("/Library/admin/BookRequest.jsp");
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (parameter[4].equals("acceptRejected")) {
				int id = Integer.parseInt(parameter[5]);
				HttpSession session = req.getSession(false);
				UserBean ub = (UserBean) session.getAttribute("user");
				try {
					BookIssueBean b = new BookIssueBean();
					b.setStatus("1");
					b.setAgentId(ub.getId());
					adminService.acceptBookRequest(id, b);
					res.sendRedirect("/Library/admin/BookRequest.jsp");
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case "users":
			HttpSession session = req.getSession(false);
			if (parameter[4].equals("active")) {
				int id = Integer.parseInt(parameter[5]);
				UserBean ub = new UserBean();
				ub.setStatus("1");
				adminService.setActiveUser(id, ub);
				AuthBean auth = new AuthBean();
				ub.setId(id);
				auth.setStatus("1");
				auth.setUser(ub);
				adminService.setActiveUserAuth(id, auth);

				NotificationBean nbean = new NotificationBean();
				UserBean b = (UserBean) session.getAttribute("user");
				int agentId = b.getId();
				nbean.setAgentId(agentId);
				nbean.setUser(ub);
				nbean.setMessage("Your Account is Approved");
				long millis=System.currentTimeMillis();  
		        String date = new Date(millis).toString();
				nbean.setDate(date);
				nbean.setStatus("0");
				try {
					userService.insertNotification(nbean);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				session.setAttribute("successMessage", "User Successfuly Activated");
				res.sendRedirect("/Library/admin/UsersApprove.jsp");
			}
			else if (parameter[4].equals("deactive")) {
				int id = Integer.parseInt(parameter[5]);
				UserBean ub = new UserBean();
				ub.setStatus("0");
				adminService.setActiveUser(id, ub);
				AuthBean auth = new AuthBean();
				auth.setStatus("1");
				ub.setId(id);
				auth.setUser(ub);
				adminService.setActiveUserAuth(id, auth);
				
				NotificationBean nbean = new NotificationBean();
				UserBean b = (UserBean) session.getAttribute("user");
				int agentId = b.getId();
				nbean.setAgentId(agentId);
				nbean.setUser(ub);
				nbean.setMessage("Your Account is Approved");
				long millis=System.currentTimeMillis();  
		        String date = new Date(millis).toString();
				nbean.setDate(date);
				nbean.setStatus("0");
				try {
					userService.insertNotification(nbean);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				
				
				session.setAttribute("successMessage", "User Successfuly Deactivated");
				res.sendRedirect("/Library/admin/UsersApprove.jsp");
			}
			if(parameter[4].equals("banned")){
				
				int id = Integer.parseInt(parameter[5]);
				UserBean ub = new UserBean();
				ub.setStatus("2");
				adminService.setActiveUser(id, ub);
				AuthBean auth = new AuthBean();
				auth.setStatus("2");
				ub.setId(id);
				auth.setUser(ub);
				adminService.setActiveUserAuth(id, auth);
				
				NotificationBean nbean = new NotificationBean();
				UserBean b = (UserBean) session.getAttribute("user");
				int agentId = b.getId();
				nbean.setAgentId(agentId);
				nbean.setUser(ub);
				nbean.setMessage("Your Account is Approved");
				long millis=System.currentTimeMillis();  
		        String date = new Date(millis).toString();
				nbean.setDate(date);
				nbean.setStatus("0");
				try {
					userService.insertNotification(nbean);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				
				session.setAttribute("successMessage", "User Successfuly Banned");
				res.sendRedirect("/Library/admin/UsersApprove.jsp");
			}
			
			break;
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doGet(req, res);
	}

	private String SessionChk(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String page = null;
		HttpSession session = req.getSession(false);
		if (session.getAttribute("user") != null) {
			RolesBean rb = (RolesBean) session.getAttribute("userRole");
			int id = rb.getId();
			if (id == 2) {
				page = "/Library/agent/index.jsp";
			} else if (id == 3) {
				page = "/Library/user/index.jsp";
			}
		}
		return page;
	}

}
