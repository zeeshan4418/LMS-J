package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import admin.services.AdminServices;
import bean.bookbean.BookBean;
import bean.bookbean.BookIssueBean;
import bean.users.NotificationBean;
import bean.users.RolesBean;
import bean.users.UserBean;
import services.users.UserService;


@WebServlet(urlPatterns = { "/user/dashboard", "/user/book/*","/user/notification/*" })
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdminServices adminService = new AdminServices();
	private UserService userService = new UserService();
    
    public UserController() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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
		switch (parameter[3]) {
		case "dashboard":
			break;
		case "book":
				
				if(parameter[4].equals("issue")) {
					int bookId = Integer.parseInt(parameter[5]);
					int agentId = 0;
					HttpSession session = req.getSession();
					UserBean ub= (UserBean) session.getAttribute("user");
					long millis=System.currentTimeMillis();  
			        String requestDate = new Date(millis).toString();
					try {
						BookIssueBean ib = new BookIssueBean();
						ib.setUser(ub);
						ib.setAgentId(agentId);
						ib.setBook(adminService.getBookById(bookId));
						ib.setRequestDate(requestDate);
						ib.setIsReturn("0");
						ib.setStatus("0");
						ib.setDateOfIssue("NULL");
						
						//ub.getId(), bookId, agentId, "Null", requestDate, "0", "0");
						Long countlimit = userService.getBookIssueLimit(ub);
						if(countlimit < 5){
							userService.requestBook(ib);
						}
						else{
							session.setAttribute("successMessage","You Already Sent 5 Requests.....");
						}
							res.sendRedirect("/Library/user/Books.jsp");
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
				}
				if(parameter[4].equals("remove")) {
					HttpSession session = req.getSession(false);
					int bookId = Integer.parseInt(parameter[5]);
					try {
						BookBean b = new BookBean();
						b.setId(bookId);
						UserBean ub= (UserBean) session.getAttribute("user");
						userService.removeRequestBookByBookBeanUserBean(b, ub);
						res.sendRedirect("/Library/user/Books.jsp");
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
				}
			break;                                                                             
		case "notification":
				if(parameter[4].equals("markAsRead")){
					int id = Integer.parseInt(parameter[5]);
					HttpSession session = req.getSession(false);
					UserBean ub = (UserBean) session.getAttribute("user");
					NotificationBean bean = new NotificationBean();
					bean.setStatus("1");
					bean.setUser(ub);
					try {
						userService.updateNotificationAsRead(id, bean);
						res.sendRedirect("/Library/user/notifications.jsp");
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
				}
				else if(parameter[4].equals("delete")){
					int id = Integer.parseInt(parameter[5]);
					try {
						userService.softDeleteNotification(id);
						res.sendRedirect("/Library/user/notifications.jsp");
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
				}
			break;

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
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
			} else if (id == 1) {
				page = "/Library/admin/index.jsp";
			}
		}
		return page;
	}

}
