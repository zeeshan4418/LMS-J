package bean.users;

public class CompUserBean {
	
	private UserBean ub;
	private AuthBean auth;
	private RolesBean rb;
	
	
	public CompUserBean(UserBean ub, AuthBean auth, RolesBean rb) {
		super();
		this.ub = ub;
		this.auth = auth;
		this.rb = rb;
	}


	public CompUserBean(UserBean ub, AuthBean auth) {
		super();
		this.ub = ub;
		this.auth = auth;
	}


	public CompUserBean(UserBean ub, RolesBean rb) {
		super();
		this.ub = ub;
		this.rb = rb;
	}


	public CompUserBean() {
		super();
	}


	public UserBean getUb() {
		return ub;
	}


	public void setUb(UserBean ub) {
		this.ub = ub;
	}


	public AuthBean getAuth() {
		return auth;
	}


	public void setAuth(AuthBean auth) {
		this.auth = auth;
	}


	public RolesBean getRb() {
		return rb;
	}


	public void setRb(RolesBean rb) {
		this.rb = rb;
	}
	

}
