package nl.utwente.di.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import nl.utwente.di.DBUtil;
import nl.utwente.di.model.User;

public class UserDao {
	
	
	
	
	public User checkLogin(String uname, String pass) {
		Connection con;
		if(uname.contains("@")) {
			try {
				con = DBUtil.getConnection();
				con.setAutoCommit(false);
				PreparedStatement st = con.prepareStatement(
						"SELECT user_id, email, username, password, isadmin "+
						"FROM UserData "+
						"WHERE email = ? "
						);
				st.setString(1, uname);
				ResultSet rs = st.executeQuery();				
				String p = "23r322d34f43";
				String id = "";
				String email = "";
				String name = "";
				String isAdmin = "";
				while (rs.next())
				{
					p = rs.getString(4);
					id = rs.getString(1);
					email = rs.getString(2);
					name = rs.getString(3);
					isAdmin = rs.getString(5);
				}
				rs.close();
				st.close(); 
				
			
				User user = null;
				if(pass.equals(p)) {
					user = new User();
					user.setId(id);
					user.setEmail(email);
					user.setFullname(name);
					user.setPassword(pass);
					user.setisAdmin(isAdmin);
					UsersDao.instance.setUser(user);
					return user;
				} else {
					return user;
				}
	  		} catch(SQLException e) {
				System.err.println("Oops: " + e.getMessage() );
				System.err.println("SQLState: " + e.getSQLState() );
			}
	
		} else {
			try {
				con = DBUtil.getConnection();
				con.setAutoCommit(false);
				PreparedStatement st = con.prepareStatement(
						"SELECT user_id, email, username, password, isadmin "+
						"FROM UserData "+
						"WHERE username = ? "
						);
				st.setString(1, uname);
				ResultSet rs = st.executeQuery();	
				String p = "23r322d34f43";
				String id = "";
				String email = "";
				String name = "";
				String isAdmin = "";
				User user = null;
				while (rs.next())
				{
					p = rs.getString(4);
					id = rs.getString(1);
					email = rs.getString(2);
					name = rs.getString(3);
					isAdmin = rs.getString(5);
				}
				rs.close();
				st.close(); 
				
			
				if(pass.equals(p)) {
					user = new User();
					user.setId(id);
					user.setEmail(email);
					user.setFullname(name);
					user.setPassword(pass);
					user.setisAdmin(isAdmin);
					UsersDao.instance.setUser(user);
					return user;
				} else {
					return user;
				}
	  		} catch(SQLException e) {
	  			
				System.err.println("Oops: " + e.getMessage() );
				System.err.println("SQLState: " + e.getSQLState() );
			}
	
		}
		return null;
		
	}
	
	public static void deleteUser(int id) {
		AccessDao.removeAllUserOrg(id);
		AccessDao.removeAllUserEvent(id);
		System.out.println("Deleting user with " + id);
		Connection con;
		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			PreparedStatement st = con.prepareStatement(
					"DELETE FROM userdata WHERE user_id = ?"
					);

			st.setInt(1, id);
			st.execute();
			con.commit();
			st.close(); 
			
		

			
  		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
		
	}

	
	
	public static void insertUserData(String user, String pass, String email, String isAdmin, String extra1, String extra2) {
		String sql = "INSERT INTO UserData VALUES(?, ?, ?, ?, ?, ?, ?)";
		int rid = randomNum();
		Connection con;
		try {

			con = DBUtil.getConnection();
			con.setAutoCommit(false);
	        PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql);
	        pstm.setInt(1, rid);
	        pstm.setString(2, email);
	        pstm.setString(3, user);
	        pstm.setString(4, pass);
	        pstm.setString(5, isAdmin);
	        pstm.setString(6, extra1);
	        pstm.setString(7, extra2);
	        pstm.execute();
            con.commit();
            pstm.close();
            
         
     
		} catch (SQLException e) {

			e.printStackTrace();
		}	
	}
	
    public static int randomNum(){
        int a = (int) (Math.random()*10);
        String a1 = "" + a;
        int b = (int) (Math.random()*10);
        String b1 = "" + b;
        int c = (int) (Math.random()*10);
        String c1 = "" + c;
        int d = (int) (Math.random()*10);
        String d1 = "" + d;
        int e = (int) (Math.random()*10);
        String e1 = "" + e;
        int f = (int) (Math.random()*10);
        String f1 = "" + f;
        int g = (int) (Math.random()*10);
        String g1 = "" + g;
        int h = (int) (Math.random()*10);
        String h1 = "" + h;

        String num = a1 + b1 + c1 + d1 + e1 + f1 + g1 + h1;
        int n = Integer.parseInt(num);
        return n;
    }
	
	
	
	
	public static void changePassword(String password, String extra1, String extra2, String mail) {
		Connection con;
		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			PreparedStatement st = con.prepareStatement(
					"UPDATE userdata " +
					"SET password = ?, extra1 = ?, extra2 = ? " +
					"WHERE email = ?"
					);
			st.setString(1, password);
			st.setString(2, extra1);
			st.setString(3, extra2);
			st.setString(4, mail);
			st.execute();
	        con.commit();
	        st.close();
		}
		catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
	}
	
	
	
	

	


}
