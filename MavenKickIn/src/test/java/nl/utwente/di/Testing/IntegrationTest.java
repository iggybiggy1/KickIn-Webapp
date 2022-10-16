package nl.utwente.di.Testing;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import nl.utwente.di.model.Mail;
import nl.utwente.di.model.Users;

class IntegrationTest {
	
	private final static String username = "dab_di20212b_46";
	private final static String password = "5vPYBig3tFfM6Hpv";
	private final static String jdbcURL = "jdbc:postgresql://bronto.ewi.utwente.nl/"+username+"?currentSchema=dab_di20212b_46";
	
	
	
	Mail mail;
	Users user;
	
	@BeforeEach
	void setUp() {
		mail = new Mail("162610", "Kick-In Commissie", "info@kick-in.nl", "Infomail April Kick-In 2020", null, null, null, null, null, "2020-04-02 12:00:01");
		user = new Users("9485200", "tadmin", "togrul.garalov@gmail.com", null, null);
	}
	
	
	//This is the method similar with same query in MailDao Class. However instead of all message object we will just check one
	@Test
	void testMailDao() {
		Connection con;
		try {

			con = DriverManager.getConnection(jdbcURL, username, password);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT m.id, m.sender, m.sender_email, d.subject, d.content, a.attachmentlink, d.did, m.send_at "+
					"FROM mail m, document d, attachment a "+
					"WHERE m.id = d.mail_id " +
					"AND d.did = a.did " + 
					"AND m.id = 162610"
				);
			
			Mail message = null;
			while (rs.next())
			{
				List<String> docOrg = null;
				List<String> docEvent = null;
				message = new Mail(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), docOrg, docEvent, rs.getString(8));

			}
			rs.close();
			st.close(); 
			con.close();
			
			
			assertEquals(mail.getId(), message.getId());
			assertEquals(mail.getSender(), message.getSender());
			assertEquals(mail.getSender_email(), message.getSender_email());
			assertEquals(mail.getSubject(), message.getSubject());
			assertEquals(mail.getSend_at(), message.getSend_at());
			
			
			
  		} catch(SQLException e) {
		}
	}
	
	
	
	@Test
	void testUsersDao() {
		Connection con;
		try {
			con = DriverManager.getConnection(jdbcURL, username, password);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT u.user_id, u.username, u.email "+
					"FROM userdata u " +
					"WHETE u.user_id = 9485200"
				);
		
			Users u = null;
			while (rs.next())
			{
				
				u = new Users(rs.getString(1), rs.getString(2), rs.getString(3), null, null);
				
			}
			
			
			rs.close();
			st.close(); 
			con.close();
		
			assertEquals(user.getId(), u.getId());
			assertEquals(user.getEmail(), u.getEmail());
			assertEquals(user.getName(), u.getName());
			
			
			
  		} catch(SQLException e) {
		}
	}

}
