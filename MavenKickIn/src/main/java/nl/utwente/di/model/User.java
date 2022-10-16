package nl.utwente.di.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nl.utwente.di.DBUtil;

public class User {
    private String id;
    private String fullname;
    private String email;
    private String password;
    private String isAdmin;
    
    
    
    
    public void setisAdmin(String b) {
    	this.isAdmin = b;
    }
    
    public String getisAdmin() {
    	return isAdmin;
    }
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<String> accOrg(){
		String id = this.id;
		int uid = Integer.parseInt(id);
		Connection con;
		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			Statement st = con.createStatement();
			PreparedStatement st1 = con.prepareStatement(
					"SELECT u.organizationname "+
					"FROM userorgaccess u "+
					"WHERE u.uid = ? "
				);
			
			st1.setInt(1, uid);
			
			List<String> results = new ArrayList<>();
			ResultSet rs = st1.executeQuery();
			while (rs.next())
			{
				results.add(rs.getString(1));
				
			}
			rs.close();
			st1.close(); 
			return results;
			
			
			
  		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
		return null;
		
		
		
	}
	
	public List<String> accEvent(){
		String id = this.id;
		int uid = Integer.parseInt(id);
		
		Connection con;
		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			Statement st = con.createStatement();
			PreparedStatement st1 = con.prepareStatement(
					"SELECT e.eventname "+
					"FROM usereventaccess e "+
					"WHERE e.uid = ? "
				);
			
			st1.setInt(1, uid);
			
			List<String> results = new ArrayList<>();
			ResultSet rs = st1.executeQuery();
			while (rs.next())
			{
				results.add(rs.getString(1));
				
			}
			rs.close();
			st1.close(); 
			return results;
			
			
			
  		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
		return null;
		
		
		
	}
 
      
     
}