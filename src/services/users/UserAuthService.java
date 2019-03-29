package services.users;

import java.sql.SQLException;
import java.util.List;
import bean.users.AuthBean;
import userdao.AuthDAO;
import userdaoimp.AuthDaoImpl;

public class UserAuthService {

	private AuthDAO authDAO = new AuthDaoImpl();

	public int insert(AuthBean c) throws ClassNotFoundException, SQLException {
		return authDAO.insert(c);
	}

	public int update(int id, AuthBean c) throws ClassNotFoundException, SQLException {
		return authDAO.update(id, c);
	}

	public int delete(int id) throws ClassNotFoundException, SQLException {
		return authDAO.delete(id);
	}

	public int softDelete(int id) throws ClassNotFoundException, SQLException {
		return authDAO.softDelete(id);
	}

	public List<AuthBean> getAll() throws ClassNotFoundException, SQLException {
		return authDAO.getAll();
	}

	public AuthBean getById(int id) throws ClassNotFoundException, SQLException {
		return authDAO.getById(id);
	}

}
