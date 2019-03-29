package admin.dao;

import java.sql.SQLException;
import java.util.List;

import bean.users.NotificationBean;
import bean.users.UserBean;

public interface NotificationsDAO extends GenericDAO<NotificationBean> {

	List<NotificationBean> getAllNotificationByUser(UserBean ub);

	Long getNotificationsCount(UserBean ub);

	int updateStatus(int id, NotificationBean bean) throws ClassNotFoundException, SQLException;

	int softDeleteNotification(int id) throws ClassNotFoundException, SQLException;	
}
