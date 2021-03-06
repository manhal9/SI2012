package ontologyAndDB;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import ontologyAndDB.exception.ViewDoesntExistsException;


public class DBConnection {

	private Connection conn;
	private String url;
	private String username;
	private String password;
	
	protected DBConnection() {
		try {
		
		final Properties dbConnProps = new Properties();
        URL propsUrl = DBConnection.class.getResource("/resources/dbConnection.properties");
        dbConnProps.load(propsUrl.openStream());
        
        url = dbConnProps.getProperty("url", "jdbc:postgresql://localhost/eventmanager");
        username = dbConnProps.getProperty("username", "postgres");
        password = dbConnProps.getProperty("password", "sem_db_event");
        
		conn = DriverManager.getConnection(url, username, password);
		
		}catch (SQLException e){
			System.out.println("Construktor :"+e.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected ResultSet executeQuery (String sqlStatement){
		
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlStatement);	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				        
		return rs;
	}
	
	protected void executeUpdate (String sqlStatement){

		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sqlStatement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	protected void disconnect(){
		try {
			conn.close();
		}catch (SQLException e){
			System.out.println("Disconnect :" + e.toString());
		}
	}
	
	/**
	 * If a VIEW with this name already exists, it will be overwritten
	 * @param viewName the Name of the View to be created
	 * @param sqlStatement the statement witch fills the view
	 * @throws SQLException 
	 */
	protected void createView ( String viewName, String sqlStatement){
		
		/*
		 ResultSet rs = this.executeQuery("SELECT * FROM pg_views where viewname='"+viewName+"'");
		 
		if (rs.next()){
			Statement stmt = null;
			stmt = conn.createStatement();
			stmt.executeUpdate("DROP VIEW "+viewName);
			stmt.close();
			
			}		*/		
		
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("CREATE OR REPLACE VIEW "+ viewName +" AS "+ sqlStatement);	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	
}
