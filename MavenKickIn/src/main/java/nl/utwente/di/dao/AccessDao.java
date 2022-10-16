package nl.utwente.di.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import nl.utwente.di.DBUtil;

public class AccessDao {
	
	
	
	public AccessDao() {
		
	}
	
	public static int randomNumda(){
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

        String num = a1 + b1 + c1 + d1 + e1;
        int n = Integer.parseInt(num);
        return n;
    }
	
	
	
	public static void addDocOrg(int did, String name) {
		int r = randomNumda();
		Connection con;
		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			PreparedStatement st = con.prepareStatement(
					"INSERT INTO docorgaccess VALUES(?, ?, ?)"
					);
			st.setInt(1, r);
			st.setInt(2, did);
			st.setString(3, name);

	        st.execute();
            con.commit();
            st.close();
            

			
  		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
	}
	
	public static void addDocEvent(int did, String name) {
		int r = randomNumda();
		Connection con;
		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			PreparedStatement st = con.prepareStatement(
					"INSERT INTO doceventaccess VALUES(?, ?, ?)"
					);
			st.setInt(1, r);
			st.setInt(2, did);
			st.setString(3, name);

	        st.execute();
            con.commit();
            st.close();
            

			
  		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
	}
	
	public static void removeDocOrg(int did, String name) {
		Connection con;
		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			PreparedStatement st = con.prepareStatement(
					"DELETE FROM docorgaccess WHERE did = ? AND organizationname = ?"
					);
	
			st.setInt(1, did);
			st.setString(2, name);
 
	        st.execute();
            con.commit();
            st.close();
            

			
  		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
	}
	
	public static void removeDocEvent(int did, String name) {
		Connection con;
		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			PreparedStatement st = con.prepareStatement(
					"DELETE FROM doceventaccess WHERE did = ? AND eventname = ?"
					);
	
			st.setInt(1, did);
			st.setString(2, name);

	        st.execute();
            con.commit();
            st.close();
           
			
  		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
	}
	
	
	public static void addUserOrg(int uid, String name) {
		int r = randomNumda();
		Connection con;
		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			PreparedStatement st = con.prepareStatement(
					"INSERT INTO userorgaccess VALUES(?, ?, ?)"
					);
			st.setInt(1, r);
			st.setInt(2, uid);
			st.setString(3, name);

	        st.execute();
            con.commit();
            st.close();

			
  		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
	}
	
	public static void addUserEvent(int uid, String name) {
		int r = randomNumda();
		Connection con;
		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			PreparedStatement st = con.prepareStatement(
					"INSERT INTO usereventaccess VALUES(?, ?, ?)"
					);
			st.setInt(1, r);
			st.setInt(2, uid);
			st.setString(3, name);

	        st.execute();
            con.commit();
            st.close();

			
  		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
	}
	
	public static void removeUserOrg(int uid, String name) {
		Connection con;
		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			PreparedStatement st = con.prepareStatement(
					"DELETE FROM userorgaccess WHERE uid = ? AND organizationname = ?"
					);
			
			st.setInt(1, uid);
			st.setString(2, name);

	        st.execute();
            con.commit();
            st.close();

			
  		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
	}
	
	public static void removeAllUserOrg(int uid) {
		Connection con;
		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			PreparedStatement st = con.prepareStatement(
					"DELETE FROM userorgaccess WHERE uid = ? "
					);
			
			st.setInt(1, uid);

	        st.execute();
            con.commit();
            st.close();

			
  		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
	}
	
	public static void removeAllUserEvent(int uid) {
		Connection con;
		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			PreparedStatement st = con.prepareStatement(
					"DELETE FROM usereventaccess WHERE uid = ? "
					);
			
			st.setInt(1, uid);
 
	        st.execute();
            con.commit();
            st.close();

			
  		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
	}
	
	public static void removeUserEvent(int uid, String name) {
		Connection con;
		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			PreparedStatement st = con.prepareStatement(
					"DELETE FROM usereventaccess WHERE uid = ? AND eventname = ?"
					);
			
			st.setInt(1, uid);
			st.setString(2, name);
 
	        st.execute();
            con.commit();
            st.close();

			
  		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
	}
	
	
	
	

}
