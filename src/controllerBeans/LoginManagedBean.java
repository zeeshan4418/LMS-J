package controllerBeans;


import java.io.Serializable;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import services.users.UserService;
import utils.SessionUtils;
import bean.users.AuthBean;
import bean.users.CompUserBean;
import bean.users.RolesBean;
import bean.users.UserBean;


@ManagedBean(name="loginMB")
@SessionScoped
public class LoginManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	public String email;
	public String password;
	public String message;
	
	private UserService userService = new UserService();
	
	
	public String getAuth(){
		String page = null;
		CompUserBean cub = checkAuth(email, password);
		if (cub != null) {
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("user", cub.getUb());
			session.setAttribute("userRole", cub.getRb());
			RolesBean rb = cub.getRb();
			int id = rb.getId();
			if (id == 1) {
				page = "admin";
			} else if (id == 2) {
				page = "/Library/agent/index.jsp";
			} else if (id == 3) {
				page = "user";
			}
			
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Please Enter Email and Password",
							"Please enter correct Email and Password"));
			page = "login";

		}
		
		return page;
	}
	
	private CompUserBean checkAuth(String email, String password) {
		
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
	

	public LoginManagedBean() {
		super();
	}
	public LoginManagedBean(String email, String password, String message) {
		super();
		this.message = message;
		this.email = email;
		this.password = password;
	}
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	//logout event, invalidate session
	public String logout() {
		
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
	
		return "login";
	}
	
	
}
