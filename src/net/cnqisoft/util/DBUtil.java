package net.cnqisoft.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBUtil {
		
	   public static Connection getConnection(){
	        Connection conn = null;
	        try {
	        	ResourceBundle resource = ResourceBundle.getBundle("config");
	    	    String url = resource.getString("url");
	    	    String username = resource.getString("username");
	    	    String password = resource.getString("password");
	    	  	String driverName = "com.mysql.jdbc.Driver";
	            Class.forName(driverName);
	            conn = DriverManager.getConnection(url,username,password);
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }catch (Exception e){
	            e.printStackTrace();
	        }
	        return conn;
	    }
}
