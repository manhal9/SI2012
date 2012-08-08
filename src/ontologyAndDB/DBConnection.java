package ontologyAndDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConnection {

	private Connection conn;
	private String url;
	private String username;
	private String password;
	
	public DBConnection() {
		
		try {
		url = "jdbc:postgresql://localhost/eventmanager";
		username = "postgres";
		password = "sem_db_event";
		conn = DriverManager.getConnection(url, username, password);
		
		}catch (SQLException e){
			System.out.println("Construktor :"+e.toString());
		}
	}
	
	public ResultSet executeQuery (String sqlStatement)throws SQLException{
		
		Statement stmt = null;
		ResultSet rs = null;
		    try {
		        stmt = conn.createStatement();
		        rs = stmt.executeQuery(sqlStatement);			        
		    } catch (SQLException e) {
		        System.out.println(e.toString());
		        }
		return rs;
	}

	
	public void disconnect(){
		try {
			conn.close();
		}catch (SQLException e){
			System.out.println("Disconnect :" + e.toString());
		}
	}
	
	
	
	
}
