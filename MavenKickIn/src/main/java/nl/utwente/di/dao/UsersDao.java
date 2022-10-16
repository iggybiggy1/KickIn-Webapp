package nl.utwente.di.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.utwente.di.DBUtil;
import nl.utwente.di.model.User;
import nl.utwente.di.model.Users;



public enum UsersDao {
	instance;
	
	
	private User user = null;
	private int code;
	private String email;
	
	
	private List<String> accOrg(int uid){
		
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
	
	private List<String> accEvent(int uid){
		
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

	
	private UsersDao() {
		
	}
	
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public User getCurrentUser() {
		return this.user;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	
	public Map<String, Users> getModel() {
		User user = UsersDao.instance.getCurrentUser();
		Map<String, Users> users1 = new HashMap<String, Users>();
		Connection con;
		try {
			con = DBUtil.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT u.user_id, u.username, u.email "+
					"FROM userdata u "
				);
		
			while (rs.next())
			{
				if(user.getisAdmin().equals("True")) {
					List<String> uo = accOrg(rs.getInt(1));
					List<String> ue = accEvent(rs.getInt(1));
					
					Users u = new Users(rs.getString(1), rs.getString(2), rs.getString(3), ue, uo);
					users1.put(rs.getString(1), u);
				}
				
			}
			rs.close();
			st.close(); 
		
			
			
  		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
		return users1;
		
		
		
	}
	
	public static String[] getIds() {
		Connection con;
		try {
			con = DBUtil.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
				"SELECT user_id "+
				"FROM userdata "
				);
			List<String> r = new ArrayList<>();
			
			while (rs.next())
			{
				r.add(rs.getString(1));
				
			}
			rs.close();
			st.close(); 
		
			
			String[] u = new String[r.size()];
			for(int i = 0; i < r.size(); i++) {
				u[i] = r.get(i);
			}
			
			return u;
			
  		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
			return null;
		}
	}
	
	public static List<String> getNames() {
		Connection con;
		try {
			con = DBUtil.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
				"SELECT username "+
				"FROM userdata "
				);
			List<String> r = new ArrayList<>();
			
			while (rs.next())
			{
				r.add(rs.getString(1));
				
			}
			rs.close();
			st.close(); 
			
			
			
			return r;
			
  		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
			return null;
		}
	}
	
	public static List<String> getEmails() {
		Connection con;
		try {
			con = DBUtil.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
				"SELECT email "+
				"FROM userdata "
				);
			List<String> r = new ArrayList<>();
			
			while (rs.next())
			{
				r.add(rs.getString(1));
				
			}
			rs.close();
			st.close(); 
			
			
			
			return r;
			
  		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
			return null;
		}
	}
	
	

}
