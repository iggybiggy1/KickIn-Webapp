package nl.utwente.di.Testing;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JUNITEncyptionTest {
	
	
	
	
	/*
	 * 
	 * These are the methods used in encrypting and hashing passwords, salts
	 * 
	 * 
	 * 
	 */
	
	
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
    
    
    private String pass;
    private String salt;
    
    @BeforeEach
    public void setUp() {
    	pass = "G12345678!";
    	salt = generateSalt();   	
    }
    
    
    
    
    //Here we prove that with the correct salt we can get validation for the hashed passwords;
	@Test
	void testHashing() {
		String p = "G12345678!";
		String hash = HashPassword(pass, Base64.getDecoder().decode(salt));
		String hash2 = HashPassword(p, Base64.getDecoder().decode(salt));			
		assertEquals(true, hash2.equals(hash2));
		
	}
	
	
	//Here we prove that without correct salt hashes are different
	@Test
	void testRandomSaltVSActualSalt() {
		String randomSalt = generateSalt();
		String hash = HashPassword(pass, Base64.getDecoder().decode(salt));
		String hash2 = HashPassword(pass, Base64.getDecoder().decode(randomSalt));	
		assertEquals(false, hash.equals(hash2));
		
	}
	
	
	
	@Test
	void testEncryptDecrypt() {
		String hash = HashPassword(pass, Base64.getDecoder().decode(salt));
		
		String saltsalt = generateSalt();
		String encodedSalt = encrypt(salt, saltsalt);
		String decodedSalt = decrypt(encodedSalt, saltsalt);
		
		String hash2 = HashPassword(pass, Base64.getDecoder().decode(decodedSalt));	
		
		assertEquals(true, hash2.equals(hash2));
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
