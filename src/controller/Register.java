package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import services.users.UserAuthService;
import services.users.UserService;
import bean.users.AuthBean;
import bean.users.CompUserBean;
import bean.users.RolesBean;
import bean.users.UserBean;

@WebServlet(urlPatterns = { "/login", "/register" })
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserService();
	private UserAuthService authService = new UserAuthService();

	public Register() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		String page = SessionChk(req, res);
		if (page != null) {
			res.sendRedirect(page);
		} else {
			String action = req.getRequestURI();
			String parameter[] = action.split("/");
			if (parameter.length < 2) {
				checkLogin(req, res);
			} else {
				switch (parameter[2]) {
				case "login":
					checkLogin(req, res);
					break;
				case "register":
					checkRegister(req, res);
					break;
				default:
					res.sendRedirect("/Library/login.jsp");
					break;
				}
			}
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doGet(req, res);
	}

	protected void checkLogin(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		CompUserBean cub = checkUserAuth(req, res);
		if (cub != null) {
			String page = null;
			HttpSession session = req.getSession(false);
			session.setAttribute("user", cub.getUb());
			session.setAttribute("userRole", cub.getRb());
			RolesBean rb = cub.getRb();
			int id = rb.getId();
			if (id == 1) {
				page = "/Library/admin/index.jsp";
			} else if (id == 2) {
				page = "/Library/agent/index.jsp";
			} else if (id == 3) {
				page = "/Library/user/index.jsp";
			}
			res.sendRedirect(page);
		} else {

			HttpSession session = req.getSession(false);
			session.setAttribute("errorMessageLogin", "Invalid Logins");
			res.sendRedirect("/Library/login.jsp");

		}
	}

	protected void checkRegister(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String status = "0";
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String confirmPassword = req.getParameter("confirmPassword");
		String error = checkValidation(firstName, lastName, email, password,
				confirmPassword);
		
		if (error != null) {
			redirect(req, res, error);
		}
		int rec = checkUserExisit(email);
		if (rec < 0) {
			redirect(req, res, "This Email Already Exsist");
		} else {

			try {
				
				UserBean ub = new UserBean();
				ub.setFirstName(firstName);
				ub.setLastName(lastName);
				RolesBean rb = new RolesBean();
				ub.setRoles(userService.getRoleById(3));
				ub.setStatus("2");
				int id = userService.insert(ub);
				
				AuthBean ab = new AuthBean();
				ab.setEmail(email);
				ab.setPassword(password);
				ab.setUser(userService.getById(id));
				authService.insert(ab);

			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			} finally {

				HttpSession session = req.getSession(false);
				// save message in session
				session.setAttribute("successMessage",
						"Successfully Registered..");
				res.sendRedirect("/Library/register.jsp");
			}

		}
	}

	private void redirect(HttpServletRequest req, HttpServletResponse res,
			String error) throws ServletException, IOException {

		req.setAttribute("firstName", req.getParameter("firstName"));
		req.setAttribute("lastName", req.getParameter("lastName"));
		req.setAttribute("email", req.getParameter("email"));
		req.setAttribute("errorMessage", error);
		req.getRequestDispatcher("/register.jsp").forward(req, res);

	}

	protected String checkValidation(String firstName, String lastName,
			String email, String password, String confirmPassword) {

		String error = null;

		if (firstName.equals("")) {
			error = "Please Enter First Name";
		}

		else if (lastName.equals("")) {
			error = "Please Enter Last Name";
		}

		else if (email.equals("")) {
			error = "Please Enter Email";
		}

		else if (password.equals("")) {
			error = "Please Enter Password";
		}

		else if (confirmPassword.equals("")) {
			error = "Please Enter Confirm Password";
		}

		else if ((firstName.equals("")) || (lastName.equals(""))
				|| (email.equals("")) || (password.equals(""))
				|| (confirmPassword.equals(""))) {
			error = "Please Fill Fields";
		}

		else if (!confirmPassword.equals(password)) {
			error = "Password Didn't Match";
		} else {
			error = null;

		}
		return error;
	}

	private int checkUserExisit(String email) {

		AuthBean bean = null;
		try {
			bean = userService.getByEmail(email);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Email No Found ---- ");
		}
		// System.out.println("Nulll  - - - "+bean);
		if (bean != null)
			return -1;
		else
			return 1;

	}

	private CompUserBean checkUserAuth(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {

		String email = req.getParameter("email");
		String password = req.getParameter("password");
		AuthBean auth = null;
		UserBean ub = null;
		RolesBean rb = null;
		try {
			auth = userService.getByEmailAndPass(email, password);
			if (auth != null) {
				ub = auth.getUser();
				rb = ub.getRoles();
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Email No Found ---- ");
		}

		if (auth != null) {
			return new CompUserBean(ub, auth, rb);
		}

		return null;

	}

	private String SessionChk(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String page = null;
		HttpSession session = req.getSession(false);
		
		if (session.getAttribute("user") != null) {
			RolesBean rb = (RolesBean) session.getAttribute("userRole");
			int id = rb.getId();
			if (id == 1) {
				page = "/Library/admin/index.jsp";
			} else if (id == 2) {
				page = "/Library/agent/index.jsp";
			} else if (id == 3) {
				page = "/Library/user/index.jsp";
			}

		}
		
		return page;
	}

}
