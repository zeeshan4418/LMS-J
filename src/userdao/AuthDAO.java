package userdao;

import java.sql.SQLException;

import bean.users.AuthBean;
import bean.users.UserBean;

public interface AuthDAO extends GenericDAO<AuthBean> {

	AuthBean getByEmail(String email) throws ClassNotFoundException,
			SQLException;

	AuthBean getByEmailAndPass(String email, String password)
			throws ClassNotFoundException, SQLException;
	
	AuthBean getEmailByUser(UserBean ub)
			throws ClassNotFoundException, SQLException;
	
}
