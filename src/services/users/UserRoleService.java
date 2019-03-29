package services.users;

import java.sql.SQLException;
import java.util.List;

import bean.users.RolesBean;
import userdao.RolesDAO;
import userdaoimp.RoleDaoImpl;

public class UserRoleService {
	
	private RolesDAO roleDAO = new RoleDaoImpl();

	public int insert(RolesBean c) throws ClassNotFoundException, SQLException {
		return roleDAO.insert(c);
	}

	public int update(int id, RolesBean c) throws ClassNotFoundException, SQLException {
		return roleDAO.update(id, c);
	}

	public int delete(int id) throws ClassNotFoundException, SQLException {
		return roleDAO.delete(id);
	}
	
	public int softDelete(int id) throws ClassNotFoundException, SQLException {
		return roleDAO.softDelete(id);
	}

	public List<RolesBean> getAll() throws ClassNotFoundException, SQLException {
		return roleDAO.getAll();
	}

	public RolesBean getById(int id) throws ClassNotFoundException, SQLException {
		return roleDAO.getById(id);
	}
	
	

}
