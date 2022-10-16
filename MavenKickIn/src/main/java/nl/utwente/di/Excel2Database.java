package nl.utwente.di;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * IN HERE THESE ARE THE METHODS WE USED TO CREATE AND FILL THE DATABASE
 *
 */


public class Excel2Database {
	private final static String username = "dab_di20212b_46";
	private final static String password = "5vPYBig3tFfM6Hpv";
	private final static String jdbcURL = "jdbc:postgresql://bronto.ewi.utwente.nl/"+username+"?currentSchema=dab_di20212b_46";
	
	
	//creates a random 8 digit number for user id
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
    
    //7 digit random num for did
    public static int randomNumDid(){
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

        String num = a1 + b1 + c1 + d1 + e1 + f1 + g1;
        int n = Integer.parseInt(num);
        return n;
    }
    

    
    
	
	
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
            System.out.println("Successfully created User table");
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
	
	public static void createMail() {
		String sql = "CREATE TABLE mail (ID int, EventID int, OrgID int, Send_at TEXT, Sender TEXT, Sender_email TEXT, PRIMARY KEY(ID));";
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
	
	public static void createDocument() {
		String sql = "CREATE TABLE Document (did int, mail_ID int NOT NULL, subject TEXT, content TEXT, PRIMARY KEY(did), FOREIGN KEY (mail_ID) REFERENCES mail(ID));";
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
	
	public static void createDocEvent() {
		String sql = "CREATE TABLE DocEventAccess (dea int, did int, EventName TEXT, PRIMARY KEY(dea), FOREIGN KEY (did) REFERENCES Document(did));";
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
	
	public static void createDocOrg() {
		
		String sql = "CREATE TABLE DocOrgAccess (doa int, did int, OrganizationName TEXT, PRIMARY KEY(doa), FOREIGN KEY (did) REFERENCES Document(did));";
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
	
	
	
	public static void createAttachment() {
		String sql = "CREATE TABLE Attachment (did int, AttachmentLink TEXT, PRIMARY KEY(did), FOREIGN KEY (did) REFERENCES Document(did));";
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

	
	public static void fillMail() {
        try{
            Connection con = DriverManager.getConnection(jdbcURL, username, password);
            con.setAutoCommit(false);
            PreparedStatement pstm = null ;
            FileInputStream input = new FileInputStream("Data export.xlsx");
            Workbook workbook = new XSSFWorkbook(input);
            Sheet sheet = workbook.getSheetAt(0);            
            DataFormatter Stringform = new DataFormatter();
            FormulaEvaluator Formeval = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
            Row row;
            int i = 1;
            while(sheet.getRow(i) != null) {
                row = sheet.getRow(i);
                int id = (int) row.getCell(0).getNumericCellValue();
                int event_id = (int) row.getCell(1).getNumericCellValue();
                int org_id = (int) row.getCell(2).getNumericCellValue();
                
                Cell cell3 = row.getCell(3);
                Formeval.evaluate(cell3); // Returns string
                String cellValueStr = Stringform.formatCellValue(cell3,Formeval);
                
                
                String send_at= cellValueStr;                
                String sender = row.getCell(4).getStringCellValue();
                String sender_email = row.getCell(5).getStringCellValue();               

                String sql = "INSERT INTO mail VALUES(?, ?, ?, ?, ?, ?)";
                pstm = (PreparedStatement) con.prepareStatement(sql);
                pstm.setInt(1, id);
                pstm.setInt(2, event_id);
                pstm.setInt(3, org_id);
                pstm.setString(4, send_at);
                pstm.setString(5, sender);
                pstm.setString(6, sender_email);
                pstm.execute();
                System.out.println("Import rows "+i);
                i++;
            }
            con.commit();
            pstm.close();
            con.close();
            input.close();
            System.out.println("Success import excel to mysql table");
        }catch(SQLException ex){
            System.out.println(ex);
        }catch(IOException ioe){
            System.out.println(ioe);
        }
		
	}
	
	public static void fillDocument() {
        try{
            Connection con = DriverManager.getConnection(jdbcURL, username, password);
            con.setAutoCommit(false);
            PreparedStatement pstm = null ;
            FileInputStream input = new FileInputStream("Data export.xlsx");
            Workbook workbook = new XSSFWorkbook(input);
            Sheet sheet = workbook.getSheetAt(0);            
            Row row;
            int i = 1;
            while(sheet.getRow(i) != null) {
                row = sheet.getRow(i);
                int mail_id = (int) row.getCell(0).getNumericCellValue();                
                int did = randomNumDid();
                String subject = row.getCell(6).getStringCellValue();
                String content = row.getCell(7).getStringCellValue(); 
                String sql = "INSERT INTO Document VALUES(?, ?, ?, ?)";
                pstm = (PreparedStatement) con.prepareStatement(sql);
                pstm.setInt(1, did);
                pstm.setInt(2, mail_id);
                pstm.setString(3, subject);
                pstm.setString(4, content);
                pstm.execute();
                System.out.println("Import rows "+i);
                i++;
            }
            con.commit();
            pstm.close();
            con.close();
            input.close();
            workbook.close();
            System.out.println("Success import excel to mysql table");
        }catch(SQLException ex){
            System.out.println(ex);
        }catch(IOException ioe){
            System.out.println(ioe);
        }
		
	}
	
	public static List<Integer> getDids() {
		Connection con;
		try {
			con = DriverManager.getConnection(jdbcURL, username, password);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
				"SELECT did "+
				"FROM Document "
				);
			List<Integer> dids = new ArrayList<>();
			while (rs.next())
			{
				dids.add(rs.getInt(1));
			}
			rs.close();
			st.close(); 
			
			return dids;
			
  		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
		return null;
			
	}
	
	public static void fillAttachment() {
        try{
            Connection con = DriverManager.getConnection(jdbcURL, username, password);
            con.setAutoCommit(false);
            PreparedStatement pstm = null ;
            FileInputStream input = new FileInputStream("Data export.xlsx");
            Workbook workbook = new XSSFWorkbook(input);
            Sheet sheet = workbook.getSheetAt(0);            
            DataFormatter Stringform = new DataFormatter();
            FormulaEvaluator Formeval = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
            Row row;
            int i = 1;
            List<Integer> dids = getDids();
            while(sheet.getRow(i) != null) {
                row = sheet.getRow(i);
                int did = dids.get(i - 1);

                Cell cell8 = row.getCell(8);
                Formeval.evaluate(cell8); // Returns string
                String cellValueStr8 = Stringform.formatCellValue(cell8,Formeval);                
                String attachments = cellValueStr8;

                String sql = "INSERT INTO Attachment VALUES(?, ?)";
                pstm = (PreparedStatement) con.prepareStatement(sql);
                pstm.setInt(1, did);
                pstm.setString(2, attachments);
                pstm.execute();
                System.out.println("Import rows "+i);
                i++;
            }
            con.commit();
            pstm.close();
            con.close();
            input.close();
            System.out.println("Success import excel to mysql table");
        }catch(SQLException ex){
            System.out.println(ex);
        }catch(IOException ioe){
            System.out.println(ioe);
        }
		
	}



	public static void main(String[] args) {
//		createUser();
//		createUserEventAccess();
//		createUserOrgAccess();
//		createMail();
//		createDocument();
//		createDocEvent();
//		createDocOrg();
//		createAttachment();
		
//		fillMail();
//		fillDocument();
//		fillAttachment();
            
	}

}
