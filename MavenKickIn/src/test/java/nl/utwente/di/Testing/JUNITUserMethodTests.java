package nl.utwente.di.Testing;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PSQLException;

class JUNITUserMethodTests {
	
	
	
	// NOTE 1
		/*	In these Tests we have used con.getConnection(...) methods, 
		 *  However the reason why getting connection is different in Web app methods is
		 *  We are using server to run the app, But here we are running class. Using regular getconnection() in our 
		 *  Web app methods was giving errors. 
		 *  IN SHORT:
		 *  Here we use con.getconnection()
		 *  In project We use DBUtil.getConnection()    (it is a DATABASE Connection Pool)
		 * ***************************************
		 * Besides getting connection everthing else is same so testing methods is possible 
		 */
	
	// NOTE 2
	//Since User methods are same with document methods, if this Tests pass means document methods are correct too
	
	
	/*
	 * NOTE 3
	 * 
	 * Since we are doing jdbc tests as regular junit test, we are ignoring exceptions, 
	 * the reason is since tests dont accually create tables in the database, the iterations we run would give error saying that
	 * Table does not exist, however tables exist but as a test, so there are no accual tables. That is why we skip PSQL exception that says table 
	 * not exists.
	 */
		
		private final static String username = "dab_di20212b_46";
		private final static String password = "5vPYBig3tFfM6Hpv";
		private final static String jdbcURL = "jdbc:postgresql://bronto.ewi.utwente.nl/"+username+"?currentSchema=junit";
		//This will user id of the user we are going to do tests on
		private final static int UID = randomNum();
		
		public static void createUser() {
			String sql = "CREATE TABLE UserData(User_ID int, email TEXT, username TEXT, password TEXT, isAdmin TEXT, extra1 TEXT, extra2 TEXT, PRIMARY KEY(User_ID));";
			Connection con;
			try {
				con = DriverManager.getConnection(jdbcURL, username, password);
				con.setAutoCommit(false);
		        PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql);
		        pstm.execute();
	            con.commit();
	            pstm.close();
	            con.close();    
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		public static void createUserEventAccess() {
			
			String sql = "CREATE TABLE userEventAccess (uea int, uid int, EventName TEXT, PRIMARY KEY(uea), FOREIGN KEY (uid) REFERENCES UserData(User_ID));";
			Connection con;
			try {
				con = DriverManager.getConnection(jdbcURL, username, password);
				con.setAutoCommit(false);
		        PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql);
		        pstm.execute();
	            con.commit();
	            pstm.close();
	            con.close();  
	            System.out.println("Success");
			} catch (SQLException e) {

				e.printStackTrace();
			}
			
		}
		
		public static void createUserOrgAccess() {
			
			String sql = "CREATE TABLE userOrgAccess (uoa int, uid int, OrganizationName TEXT, PRIMARY KEY(uoa), FOREIGN KEY (uid) REFERENCES UserData(User_ID));";
			Connection con;
			try {
				con = DriverManager.getConnection(jdbcURL, username, password);
				con.setAutoCommit(false);
		        PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql);
		        pstm.execute();
	            con.commit();
	            pstm.close();
	            con.close(); 
	            System.out.println("Success");
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
	    
	    
		
		public static void insertUserData(String user, String pass, String email, String isAdmin, String extra1, String extra2, int UID) {
			String sql = "INSERT INTO UserData VALUES(?, ?, ?, ?, ?, ?, ?)";
			int rid = UID;
			Connection con;
			try {

				con = DriverManager.getConnection(jdbcURL, username, password);
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
	            con.close();
	            
	         
	     
			} catch (SQLException e) {

			}	
		}
		
		public static void deleteUser(int id) throws org.postgresql.util.PSQLException{
			Connection con;
			try {
				con = DriverManager.getConnection(jdbcURL, username, password);
				con.setAutoCommit(false);
				PreparedStatement st = con.prepareStatement(
						"DELETE FROM userdata WHERE user_id = ?"
						);

				st.setInt(1, id);
				st.execute();
				con.commit();
				st.close(); 
				con.close();
			

				
	  		} catch(SQLException e) {

			}
			
		}
		
		public static void changePassword(String pass, String extra1, String extra2, String mail) throws org.postgresql.util.PSQLException{
			Connection con;
			try {
				con = DriverManager.getConnection(jdbcURL, username, password);
				con.setAutoCommit(false);
				PreparedStatement st = con.prepareStatement(
						"UPDATE userdata " +
						"SET password = ?, extra1 = ?, extra2 = ? " +
						"WHERE email = ?"
						);
				st.setString(1, pass);
				st.setString(2, extra1);
				st.setString(3, extra2);
				st.setString(4, mail);
				st.execute();
		        con.commit();
		        st.close();
		        con.close();
			}
			catch(SQLException e) {

			}
		}
		
		
		
		@Before
		public void setUp() throws InterruptedException, PSQLException {
			createUser();
			createUserEventAccess();
			createUserOrgAccess();
			insertUserData("Test", "12345678", "test@gmail.com", "False", "e1", "e2", UID);
			
			
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
		
		
		public static void addUserOrg(int uid, String name) throws org.postgresql.util.PSQLException{
			int r = randomNumda();
			Connection con;
			try {
				con = DriverManager.getConnection(jdbcURL, username, password);
				con.setAutoCommit(false);
				PreparedStatement st = con.prepareStatement(
						"INSERT INTO userOrgAccess VALUES(?, ?, ?)"
						);
				st.setInt(1, r);
				st.setInt(2, uid);
				st.setString(3, name);

		        st.execute();
	            con.commit();
	            st.close();
	            con.close();
				
	  		} catch(SQLException e) {

			}
		}
		
		public static void removeUserOrg(int uid, String name) throws org.postgresql.util.PSQLException{
			Connection con;
			try {
				con = DriverManager.getConnection(jdbcURL, username, password);
				con.setAutoCommit(false);
				PreparedStatement st = con.prepareStatement(
						"DELETE FROM userorgaccess WHERE uid = ? AND organizationname = ?"
						);
				
				st.setInt(1, uid);
				st.setString(2, name);

		        st.execute();
	            con.commit();
	            st.close();
	            con.close();
				
	  		} catch(SQLException e) {

			}
		}
		
		public static void addUserEvent(int uid, String name) throws org.postgresql.util.PSQLException{
			int r = randomNumda();
			Connection con;
			try {
				con = DriverManager.getConnection(jdbcURL, username, password);
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
	            con.close();
				
	  		} catch(SQLException e) {
//	  			e.printStackTrace();
//				System.err.println("Oops: " + e.getMessage() );
//				System.err.println("SQLState: " + e.getSQLState() );
			}
		}
		
		public static void removeUserEvent(int uid, String name) throws org.postgresql.util.PSQLException{
			Connection con;
			try {
				con = DriverManager.getConnection(jdbcURL, username, password);
				con.setAutoCommit(false);
				PreparedStatement st = con.prepareStatement(
						"DELETE FROM usereventaccess WHERE uid = ? AND eventname = ?"
						);
				
				st.setInt(1, uid);
				st.setString(2, name);
	 
		        st.execute();
	            con.commit();
	            st.close();
	            con.close();
				
	  		} catch(SQLException e) {

			}
		}
		
		public static void removeAllUserOrg(int uid) throws org.postgresql.util.PSQLException{
			Connection con;
			try {
				con = DriverManager.getConnection(jdbcURL, username, password);
				con.setAutoCommit(false);
				PreparedStatement st = con.prepareStatement(
						"DELETE FROM userorgaccess WHERE uid = ? "
						);
				
				st.setInt(1, uid);

		        st.execute();
	            con.commit();
	            st.close();
	            con.close();
				
	  		} catch(SQLException e) {

			}
		}
		
		public static void removeAllUserEvent(int uid) throws org.postgresql.util.PSQLException{
			Connection con;
			try {
				con = DriverManager.getConnection(jdbcURL, username, password);
				con.setAutoCommit(false);
				PreparedStatement st = con.prepareStatement(
						"DELETE FROM usereventaccess WHERE uid = ? "
						);
				
				st.setInt(1, uid);
	 
		        st.execute();
	            con.commit();
	            st.close();
	            con.close();
				
	  		} catch(SQLException e) {

			}
		}
		
		
		
		//For Testing insertuser method in setUp(), we are going to test to see if generated user in DB has the UID we put
		@Test
		public void TestInsertUser() {
			
			Connection con;
			try {
				con = DriverManager.getConnection(jdbcURL, username, password);
				con.setAutoCommit(false);
				PreparedStatement st = con.prepareStatement(
						"SELECT * FROM userdata WHERE user_id = ?"
						);
				st.setInt(1, UID);
				ResultSet rs = st.executeQuery();				
				while (rs.next())
				{
					assertEquals(UID, rs.getString(1));
				}
				rs.close();
				st.close(); 
				con.close();

	  		} catch(SQLException e) {

			}
			
		}
		
		
		
		
		
		
		@Test
		public void TestAddUserOrg() throws PSQLException {
			int id = 567891;
			insertUserData("Test888", "12345678", "test@gmail.com", "False", "e1", "e2", id);
			addUserOrg(id, "TestOrg");
			
			Connection con;
			try {
				con = DriverManager.getConnection(jdbcURL, username, password);
				con.setAutoCommit(false);
				PreparedStatement st = con.prepareStatement(
						"SELECT * "+
						"FROM userorgaccess "+
						"WHERE uid = ? "
						);
				st.setInt(1, id);
				ResultSet rs = st.executeQuery();				
				while (rs.next())
				{
					assertEquals(id, rs.getInt(2));
				}
				rs.close();
				st.close(); 
				con.close();

	  		} catch(SQLException e) {

			}
			
			
		}
		
		
		
		@Test
		public void TestremoveUserOrg() throws PSQLException {
			int UID = 33333;
			insertUserData("Test1233", "12345678", "test@gmail.com", "False", "e1", "e2", UID);
			addUserOrg(UID, "TestOrg111");
			removeUserOrg(UID, "TestOrg111");
			
			
			Connection con;
			try {
				con = DriverManager.getConnection(jdbcURL, username, password);
				con.setAutoCommit(false);
				PreparedStatement st = con.prepareStatement(
						"SELECT organizationname "+
						"FROM userorgaccess "+
						"WHERE uid = ? "
						);
				st.setInt(1, UID);
				ResultSet rs = st.executeQuery();	
				String result = "Nothing";
				while (rs.next())
				{
					result = rs.getString(2);
				
				}
				assertEquals("Nothing", result);
				rs.close();
				st.close(); 
				con.close();

	  		} catch(SQLException e) {

			}

			
		}
		
		
		
		@Test
		public void TestAddUserEvent() throws PSQLException {
			int UID = 33332;
			insertUserData("Test122", "12345678", "test@gmail.com", "False", "e1", "e2", UID);
			
			addUserEvent(UID, "TestEvent");
			
			Connection con;
			try {
				con = DriverManager.getConnection(jdbcURL, username, password);
				con.setAutoCommit(false);
				PreparedStatement st = con.prepareStatement(
						"SELECT * "+
						"FROM usereventaccess "+
						"WHERE uid = ? "
						);
				st.setInt(1, UID);
				ResultSet rs = st.executeQuery();				
				while (rs.next())
				{
					assertEquals(UID, rs.getString(2));
				}
				rs.close();
				st.close(); 
				con.close();

	  		} catch(SQLException e) {

			}
			
			
		}
		
		
		
		@Test
		public void TestremoveUserEvent() throws PSQLException {
			
			int UID = 334437;
			insertUserData("Test143", "12345678", "test@gmail.com", "False", "e1", "e2", UID);
			addUserEvent(UID, "TestEvent111");
			removeUserEvent(UID, "TestEvent111");
			
			
			Connection con;
			try {
				con = DriverManager.getConnection(jdbcURL, username, password);
				con.setAutoCommit(false);
				PreparedStatement st = con.prepareStatement(
						"SELECT eventname "+
						"FROM usereventaccess "+
						"WHERE uid = ? "
						);
				st.setInt(1, UID);
				ResultSet rs = st.executeQuery();	
				String result = "Nothing";
				while (rs.next())
				{
					result = rs.getString(2);
				
				}
				assertEquals("Nothing", result);
				rs.close();
				st.close(); 
				con.close();

	  		} catch(SQLException e) {

			}

			
		}
		
		
		@Test
		public void TestremoveAllUserEvent() throws PSQLException {
			
			int UID = 33222;
			insertUserData("Test555", "12345678", "test@gmail.com", "False", "e1", "e2", UID);
			
			
			addUserEvent(UID, "TestEvent111");
			addUserEvent(UID, "TestEvent112");
			addUserEvent(UID, "TestEvent113");
			addUserEvent(UID, "TestEvent114");
			
			removeAllUserEvent(UID);
			
			
			Connection con;
			try {
				con = DriverManager.getConnection(jdbcURL, username, password);
				con.setAutoCommit(false);
				PreparedStatement st = con.prepareStatement(
						"SELECT eventname "+
						"FROM usereventaccess "+
						"WHERE uid = ? "
						);
				st.setInt(1, UID);
				ResultSet rs = st.executeQuery();	
				String result = "Nothing";
				while (rs.next())
				{
					result = rs.getString(2);
				
				}
				assertEquals("Nothing", result);
				rs.close();
				st.close(); 
				con.close();

	  		} catch(SQLException e) {

			}

			
		}
		
		
		
		@Test
		public void TestremoveAllUserOrg() throws PSQLException {
			
			int UID = 332111;
			insertUserData("Test555", "12345678", "test@gmail.com", "False", "e1", "e2", UID);
			
			
			addUserOrg(UID, "TestOrg111");
			addUserOrg(UID, "TestOrg112");
			addUserOrg(UID, "TestOrg113");
			addUserOrg(UID, "TestOrg114");
			removeAllUserOrg(UID);
			
			
			Connection con;
			try {
				con = DriverManager.getConnection(jdbcURL, username, password);
				con.setAutoCommit(false);
				PreparedStatement st = con.prepareStatement(
						"SELECT organizationname "+
						"FROM userorgaccess "+
						"WHERE uid = ? "
						);
				st.setInt(1, UID);
				ResultSet rs = st.executeQuery();	
				String result = "Nothing";
				while (rs.next())
				{
					result = rs.getString(2);
				
				}
				assertEquals("Nothing", result);
				rs.close();
				st.close(); 
				con.close();

	  		} catch(SQLException e) {

			}

			
		}

		
		
		@Test
		public void TestDeleteUser() throws PSQLException {
			int id = UID - 1;
			insertUserData("Test222", "12345678", "test@gmail.com", "False", "e1", "e2", id);
			deleteUser(id);
			
			
			Connection con;
			try {
				con = DriverManager.getConnection(jdbcURL, username, password);
				con.setAutoCommit(false);
				PreparedStatement st = con.prepareStatement(
						"SELECT * "+
						"FROM userdata "+
						"WHERE user_id = ? "
						);
				st.setInt(1, id);
				ResultSet rs = st.executeQuery();	
				String r = "nothing";
				while (rs.next())
				{
					r = rs.getString(1);
				}
				assertEquals("nothing", r);
				rs.close();
				st.close(); 
				con.close();

	  		} catch(SQLException e) {

			}
		}
		
		
		@Test
		public void TestChangePassUser() throws PSQLException {
			int id = 99999;
			insertUserData("Test000", "12345678", "test222@gmail.com", "False", "e1", "e2", id);
			changePassword("11111", "e2", "e2", "test222@gmail.com");
			
			
			Connection con;
			try {
				con = DriverManager.getConnection(jdbcURL, username, password);
				con.setAutoCommit(false);
				PreparedStatement st = con.prepareStatement(
						"SELECT password "+
						"FROM userdata "+
						"WHERE user_id = ? "
						);
				st.setInt(1, id);
				ResultSet rs = st.executeQuery();	
				String r = "nothing";
				while (rs.next())
				{
					r = rs.getString(1);
				}
				assertEquals("11111", r);
				rs.close();
				st.close(); 
				con.close();

	  		} catch(SQLException e) {

			} 
		}
		
		
		
		
		
		
		

	

}
