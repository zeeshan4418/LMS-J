package controllerBeans;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import services.users.UserService;
import admin.services.AdminServices;
import bean.users.AuthBean;
import bean.users.NotificationBean;
import bean.users.RolesBean;
import bean.users.UserBean;

@ManagedBean(name="userMb")
@SessionScoped
public class UserMB {

	private int id;
	
	private String firstName;
	
	private String lastName;
	
	private String status;
	
	private RolesBean roles;
	
	private AuthBean authBean;

	private NotificationBean nBean;
	private String keyWord;
	
	private UserBean ub;
	private List<UserBean> list;
	private AdminServices as = new AdminServices();
	public UserService us = new UserService();
	
	public List<UserBean> getAllUsers() throws ClassNotFoundException, SQLException{
	
		if (this.keyWord != null) {
			list = us.getAllUsersByKeyWord(this.keyWord);
		} else {
			list = us.getAll();
		}
		return list;
	}
	
	public AuthBean getEmailById() throws ClassNotFoundException, SQLException {
		
		return us.getEmailByUser(ub);
	}
	
	public String setActiveUser(int id){
		
		UserBean ub = new UserBean();
		ub.setStatus("1");
		as.setActiveUser(id, ub);
		AuthBean auth = new AuthBean();
		ub.setId(id);
		auth.setStatus("1");
		auth.setUser(ub);
		as.setActiveUserAuth(id, auth);

		NotificationBean nbean = new NotificationBean();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		ub = (UserBean) session.getAttribute("user");
		
		int agentId = ub.getId();
		nbean.setAgentId(agentId);
		nbean.setUser(ub);
		nbean.setMessage("Your Account is Approved");
		long millis=System.currentTimeMillis();  
        String date = new Date(millis).toString();
		nbean.setDate(date);
		nbean.setStatus("0");
		try {
			us.insertNotification(nbean);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return "User Successfuly Activated";		
	}
	
	public String deactiveUser(int id){
	
		UserBean ub = new UserBean();
		ub.setStatus("0");
		as.setActiveUser(id, ub);
		AuthBean auth = new AuthBean();
		auth.setStatus("1");
		ub.setId(id);
		auth.setUser(ub);
		as.setActiveUserAuth(id, auth);
		
		NotificationBean nbean = new NotificationBean();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		ub = (UserBean) session.getAttribute("user");
		int agentId = ub.getId();
		nbean.setAgentId(agentId);
		nbean.setUser(ub);
		nbean.setMessage("Your Account is Approved");
		long millis=System.currentTimeMillis();  
        String date = new Date(millis).toString();
		nbean.setDate(date);
		nbean.setStatus("0");
		try {
			us.insertNotification(nbean);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		
		return "User Successfuly Deactivated";		
	}
	
	
	public String banndUser(int id){
		
		UserBean ub = new UserBean();
		ub.setStatus("2");
		as.setActiveUser(id, ub);
		AuthBean auth = new AuthBean();
		auth.setStatus("2");
		ub.setId(id);
		auth.setUser(ub);
		as.setActiveUserAuth(id, auth);
		
		NotificationBean nbean = new NotificationBean();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		ub = (UserBean) session.getAttribute("user");
		int agentId = ub.getId();
		nbean.setAgentId(agentId);
		nbean.setUser(ub);
		nbean.setMessage("Your Account is Approved");
		long millis=System.currentTimeMillis();  
        String date = new Date(millis).toString();
		nbean.setDate(date);
		nbean.setStatus("0");
		try {
			us.insertNotification(nbean);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return "User SuccessFully Banned";
		
	}
	
	
	public UserMB(int id, String firstName, String lastName, String status,
			RolesBean roles) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.status = status;
		this.roles = roles;
	}
	
	public UserService getUs() {
		return us;
	}

	public void setUs(UserService us) {
		this.us = us;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public RolesBean getRoles() {
		return roles;
	}

	public void setRoles(RolesBean roles) {
		this.roles = roles;
	}

	public AuthBean getAuthBean() {
		return authBean;
	}

	public void setAuthBean(AuthBean authBean) {
		this.authBean = authBean;
	}

	public NotificationBean getnBean() {
		return nBean;
	}

	public void setnBean(NotificationBean nBean) {
		this.nBean = nBean;
	}

	public UserBean getUb() {
		return ub;
	}

	public void setUb(UserBean ub) {
		this.ub = ub;
	}

	public UserMB() {
		super();
	}


	public String getKeyWord() {
		return keyWord;
	}


	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
}
