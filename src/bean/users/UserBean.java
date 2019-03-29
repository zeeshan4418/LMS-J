package bean.users;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserBean  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private int id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "status")
	private String status;

	
	@ManyToOne
	@JoinColumn (name = "role_id")
	private RolesBean roles;
	
	@OneToOne(mappedBy="user")
	private AuthBean authBean;
	
	@OneToOne(mappedBy="user")
	private NotificationBean nBean;
	
	public UserBean() {

	}

	/*public UserBean(int roleId, String firstName, String lastName, String status) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.status = status;
		this.roleId = roleId;
	}

	public UserBean(int id, int roleId, String firstName, String lastName,
			String status) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.status = status;
		this.roleId = roleId;
	}*/

	/*public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}*/

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	

}
