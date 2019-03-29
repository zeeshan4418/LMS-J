package controllerBeans;

import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import bean.users.NotificationBean;
import bean.users.UserBean;
import services.users.UserService;

@ManagedBean(name="notificationMb")
@SessionScoped
public class NotificationMB {

	private int id;
	private UserBean user;
	private int agentId;
	private String message;
	private String date;
	private String status;
	private String del;
	
	public UserService us = new UserService();
		
	
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
	public UserBean getUser() {
		return user;
	}
	public void setUser(UserBean user) {
		this.user = user;
	}
	public int getAgentId() {
		return agentId;
	}
	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	public NotificationMB(int id, UserBean user, int agentId, String message, String date, String status, String del) {
		super();
		this.id = id;
		this.user = user;
		this.agentId = agentId;
		this.message = message;
		date = date;
		this.status = status;
		this.del = del;
	}
	public NotificationMB() {
		super();
	}
	
	public String markAsRead(int id, UserBean ub) throws ClassNotFoundException, SQLException{
		
		NotificationBean bean = new NotificationBean();
		bean.setStatus("1");
		bean.setUser(ub);
		us.updateNotificationAsRead(id, bean);	
		return "Read";
	}
	
}
