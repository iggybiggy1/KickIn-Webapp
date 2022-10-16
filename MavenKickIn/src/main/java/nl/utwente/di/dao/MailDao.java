package nl.utwente.di.dao;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import nl.utwente.di.DBUtil;
import nl.utwente.di.model.Mail;
import nl.utwente.di.model.User;



public enum MailDao {
	instance;
	
	

	
	
	private MailDao() {

		
	}
	
	public Map<String, Mail> getModel() {
		User user = UsersDao.instance.getCurrentUser();
		Connection con;
		Map<String, Mail> messages1 = new HashMap<String, Mail>();

		try {

			con = DBUtil.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT m.id, m.sender, m.sender_email, d.subject, d.content, a.attachmentlink, d.did, m.send_at "+
					"FROM mail m, document d, attachment a "+
					"WHERE m.id = d.mail_id " +
					"AND d.did = a.did "
				);
			
			
			
			List<String> userOrgs = user.accOrg();
			List<String> userEvents = user.accEvent();
			
			while (rs.next())
			{
				List<String> docOrg = accOrg(rs.getInt(7));
				List<String> docEvent = accEvent(rs.getInt(7));
				
				boolean b1 = checkLists(userOrgs, docOrg);
				boolean b2 = checkLists(userEvents, docEvent);
				
//				System.out.println(user.getisAdmin().equals("True") + " or " + b1 + " or " + b2);
				
				
				if(user.getisAdmin().equals("True") || b1 || b2) {
					Mail message = new Mail(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), docOrg, docEvent, rs.getString(8));
					messages1.put(rs.getString(1), message);
				}

			}
			rs.close();
			st.close(); 
			
	
			
			
			
  		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
		return messages1;
		
		
		
		
	}
	
	public static String[] getIds() {
		Connection con;
		try {

			con = DBUtil.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
				"SELECT id "+
				"FROM mail "
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
		}
		return null;
	}
	

	
	
	
	private List<String> accOrg(int did){
		
		Connection con;
		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			Statement st = con.createStatement();
			PreparedStatement st1 = con.prepareStatement(
					"SELECT u.organizationname "+
					"FROM docorgaccess u "+
					"WHERE u.did = ? "
				);
			
			st1.setInt(1, did);
			
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
	
	private List<String> accEvent(int did){
		
		Connection con;
		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			Statement st = con.createStatement();
			PreparedStatement st1 = con.prepareStatement(
					"SELECT u.eventname "+
					"FROM doceventaccess u "+
					"WHERE u.did = ? "
				);
			
			st1.setInt(1, did);
			
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
	
	
	public boolean checkLists(List<String> A, List<String> B) {
		if(A == null || B == null) {
			return false;
		}
		
		
		
		for(String a: A) {
			for(String b: B) {
				if(a.toLowerCase().equals(b.toLowerCase())) {
					return true;
				}
			}
		}
		return false;
		
	}
	
	private static String hostTest = "kickinproject@gmail.com";
	private static String hostPass = "Test12345!";
	private static String hostServer = "smtp.gmail.com";
	
	public static  void mailSend(String Receiver) throws NoSuchAlgorithmException, NoSuchProviderException{

        
        SecureRandom secureRandomGenerator = SecureRandom.getInstance("SHA1PRNG", "SUN");
        byte[] randomBytes = new byte[128];
        secureRandomGenerator.nextBytes(randomBytes);
        
        int randInRange = secureRandomGenerator.nextInt(9999999);
        boolean isCorrect = false;
        
        while(isCorrect) {
            if(randomBytes.length < 7) {
                randInRange = secureRandomGenerator.nextInt(9999999);
                isCorrect = false;
            }else if(randomBytes.length == 7) {
                isCorrect = true;
            }
        }
        
        UsersDao.instance.setCode(randInRange);    
        String recipient = Receiver;
        String sender = "kickinproject@gmail.com";
        String host = hostServer;
        Properties properties = System.getProperties();
        
        properties.put("mail.smtp.host", host);
        
        properties.put("mail.smtp.port", "465");
       
        properties.put("mail.smtp.ssl.enable", "true");
       
        properties.put("mail.smtp.auth", "true");
        
        properties.setProperty("mail.smtp.host", host);
        
         Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
             
            protected PasswordAuthentication getPasswordAuthentication() {
                
                return new PasswordAuthentication(hostTest, hostPass);
            }
             
         });
         
         session.setDebug(true);
		
		try {
			MimeMessage message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress(sender));
			
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			
			message.setSubject("Your code for password recovery, Kick In !!");
			
			message.setText("Here is the security code for password recovery: " + randInRange);
			
			System.out.println("Sending mail ...");
			
			Transport.send(message);
			
			System.out.println("Mail sent succesfully");
			
		}catch (MessagingException mex) {
			mex.printStackTrace();
		}
		
	}
	
	public static List<String> getEmails(){
        
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
