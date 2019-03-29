package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private Connection conn=null;
    private PreparedStatement stmt=null;
    private Statement s=null;
    public Database(){
    	
    }
    
    public void open() throws SQLException,ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "admin");
    }
    
    public PreparedStatement initStatement(String sql) throws SQLException{
        stmt=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        return stmt;
    }
    
    public Statement initStatementRow() throws SQLException{
    	s = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
    	        ResultSet.CONCUR_READ_ONLY);
    	
        return s;
    }
    
    public int executeUpdate() throws SQLException{
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if(rs.next()){
			return rs.getInt(1);
		}
        else{
        	return stmt.executeUpdate();
        }
        
    }
    
    public ResultSet executeQuery() throws SQLException{
        return stmt.executeQuery();
    } 
    
    public void close() throws SQLException{
        if(conn!=null && !conn.isClosed()){
            conn.close();
            conn=null;
        }
    }
	
}
