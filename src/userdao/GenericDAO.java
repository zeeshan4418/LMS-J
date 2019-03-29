package userdao;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T> {

	List<T> getAll() throws ClassNotFoundException,SQLException;
    T getById(int id)throws ClassNotFoundException,SQLException;
    int insert(T t)throws ClassNotFoundException,SQLException;
    int update(int id, T t)throws ClassNotFoundException,SQLException;
    int delete(int id)throws ClassNotFoundException,SQLException;
    int softDelete(int id)throws ClassNotFoundException,SQLException;
}
