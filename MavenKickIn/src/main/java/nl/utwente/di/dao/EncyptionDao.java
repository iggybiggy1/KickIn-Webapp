package nl.utwente.di.dao;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import nl.utwente.di.DBUtil;

public class EncyptionDao {
	
	
	
	public EncyptionDao() {
		
	}
	
	public static String generateSalt() {
		
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[16];
		random.nextBytes(bytes);
		String s = Base64.getEncoder().encodeToString(bytes);
		return s;
		
	}
	
	public static String HashPassword(String password, byte[] salt) {
		byte[] hash = null;
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 60000, 256);
		
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			hash = factory.generateSecret(spec).getEncoded();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		
		String h = Base64.getEncoder().encodeToString(hash);
		
		return h;
		
	}
	
	
	private static final String SECRET_KEY
    = "SUPPERSECRETKEYYYYAHAHA";

	

	public static String encrypt(String strToEncrypt, String SALT)
    {
        try {
  
            byte[] ivP = { 0, 0, 0, 0, 0, 0, 0, 0,
                          0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivspec
                = new IvParameterSpec(ivP);
  
            SecretKeyFactory factory
                = SecretKeyFactory.getInstance(
                    "PBKDF2WithHmacSHA256");
            
            KeySpec spec = new PBEKeySpec(
                SECRET_KEY.toCharArray(), SALT.getBytes(),
                65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(
                tmp.getEncoded(), "AES");
  
            Cipher cipher = Cipher.getInstance(
                "AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,
                        ivspec);
            return Base64.getEncoder().encodeToString(
                cipher.doFinal(strToEncrypt.getBytes(
                    StandardCharsets.UTF_8)));
        }
        catch (Exception e) {
            System.out.println("Error while encrypting: "
                               + e.toString());
        }
        return null;
    }
  
    public static String decrypt(String strToDecrypt, String SALT)
    {
        try {
  
            byte[] ivP = { 0, 0, 0, 0, 0, 0, 0, 0,
                          0, 0, 0, 0, 0, 0, 0, 0 };
  
            IvParameterSpec ivspec
                = new IvParameterSpec(ivP);
  
            SecretKeyFactory factory
                = SecretKeyFactory.getInstance(
                    "PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(
                SECRET_KEY.toCharArray(), SALT.getBytes(),
                65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(
                tmp.getEncoded(), "AES");
  
            Cipher cipher = Cipher.getInstance(
                "AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey,
                        ivspec);
            return new String(cipher.doFinal(
                Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e) {
            System.out.println("Error while decrypting: "
                               + e.toString());
        }
        return null;
    }
    
    
    public static String getSalt(String username) {
    	
    	if(username.contains("@")) {
    		Connection con;
    		try {
    			con = DBUtil.getConnection();
    			con.setAutoCommit(false);
    			Statement st = con.createStatement();
    			PreparedStatement st1 = con.prepareStatement(
    					"SELECT u.extra1, u.extra2 "+
    					"FROM userdata u "+
    					"WHERE u.email = ? "
    				);
    			
    			st1.setString(1, username);
    		
    			ResultSet rs = st1.executeQuery();
    			String encodedsalt = null;
    			String saltsalt = null;
    			String decodedSalt = null;
    			while (rs.next())
    			{
    				encodedsalt = rs.getString(1);
    				saltsalt = rs.getString(2);
    				
    			}
    			rs.close();
    			st1.close(); 
    			
    			if (saltsalt != null) {
    				decodedSalt = decrypt(encodedsalt, saltsalt);
    			}
    			return decodedSalt;
      		} catch(SQLException e) {
    			System.err.println("Oops: " + e.getMessage() );
    			System.err.println("SQLState: " + e.getSQLState() );
    		}
    		
    		
    	} else {
    		Connection con;
    		try {
    			con = DBUtil.getConnection();
    			con.setAutoCommit(false);
    			Statement st = con.createStatement();
    			PreparedStatement st1 = con.prepareStatement(
    					"SELECT u.extra1, u.extra2 "+
    					"FROM userdata u "+
    					"WHERE u.username = ? "
    				);
    			
    			st1.setString(1, username);
    		
    			ResultSet rs = st1.executeQuery();
    			String encodedsalt = null;
    			String saltsalt = null;
    			String decodedSalt = null;
    			while (rs.next())
    			{
    				encodedsalt = rs.getString(1);
    				saltsalt = rs.getString(2);
    				
    			}
    			rs.close();
    			st1.close(); 
    			
    			if (saltsalt != null) {
    				decodedSalt = decrypt(encodedsalt, saltsalt);
    			}
    			return decodedSalt;
      		} catch(SQLException e) {
    			System.err.println("Oops: " + e.getMessage() );
    			System.err.println("SQLState: " + e.getSQLState() );
    		}
    	}
		return null;
    	
    	
		

    	
    }

}
