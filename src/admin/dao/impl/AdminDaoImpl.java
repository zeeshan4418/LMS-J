package admin.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import userdao.UserDAO;
import utils.Database;
import admin.dao.AdminDAO;
import bean.bookbean.BookIssueBean;
import bean.users.UserBean;

public class AdminDaoImpl implements AdminDAO {

	private Database connection;

	public AdminDaoImpl() {
		connection = new Database();
	}
}
