package controllerBeans;

import java.io.Serializable;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import bean.users.AuthBean;
import bean.users.RolesBean;
import bean.users.UserBean;
import services.users.UserAuthService;
import services.users.UserService;

@ManagedBean(name="registerMB")
@RequestScoped
public class RegisterMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Integer id;
	public String firstName;
	public String lastName;
	public String email;
	public String password;
	public String confirmPassword;
	public String message;
	
	public String save() throws ClassNotFoundException, SQLException {
		
		UserService userService = new UserService();
		UserAuthService authService = new UserAuthService();		
		
		String error = checkValidation(this.firstName, this.lastName, this.email, this.password,
				confirmPassword);
		
		if(error != null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Please Fill All Fields",
							error));
			return "error";
		}
		int rec = checkUserExisit(email);
		if (rec < 0) {
				FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Email Already Exsist",
							"Email Already Exsist"));
			return "email";
		} 
		else {
			UserBean ub = new UserBean();
			ub.setFirstName(this.firstName);
			ub.setLastName(this.lastName);
			RolesBean rb = new RolesBean();
			ub.setRoles(userService.getRoleById(3));
			ub.setStatus("2");
			int id = userService.insert(ub);
			
			AuthBean ab = new AuthBean();
			ab.setEmail(email);
			ab.setPassword(password);
			ab.setUser(userService.getById(id));
			authService.insert(ab);
		}
		return "Success";
	}
	
	private int checkUserExisit(String email2) {
		AuthBean bean = null;
		UserService userService = new UserService();
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

	private String checkValidation(String firstName2, String lastName2, String email2, String password2,
			String confirmPassword2) {
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

	public RegisterMB() {
		
	}
	
	
	public RegisterMB(Integer id, String firstName, String lastName,
			String email, String password, String confirmPassword,
			String message) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
}
