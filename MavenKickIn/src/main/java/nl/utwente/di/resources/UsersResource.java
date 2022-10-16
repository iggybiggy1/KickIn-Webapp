package nl.utwente.di.resources;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nl.utwente.di.dao.AccessDao;
import nl.utwente.di.dao.EncyptionDao;
import nl.utwente.di.dao.MailDao;
import nl.utwente.di.dao.UserDao;
import nl.utwente.di.dao.UsersDao;
import nl.utwente.di.model.User;
import nl.utwente.di.model.Users;


@Path("users")
public class UsersResource {
	
	private String[] UIDs;
	private String title;
	
	
	public UsersResource() {
		
	}

	public UsersResource(String[] uIDs, String title) {
		setUIDs(UsersDao.getIds());
		setTitle("Users database");
	}



	public String[] getUIDs() {
		return UIDs;
	}



	public void setUIDs(String[] uIDs) {
		UIDs = uIDs;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}



	

	@GET
	@Path("/emails")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Users> getUserEmails() {
		
		List<Users> ms = new ArrayList<Users>();
		
		for (String id : UsersDao.getIds()) {
			ms.add(UsersDao.instance.getModel().get(id));
			
		}
		System.out.println(ms);
		return ms;
		
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Users> getUsers() {
		
		List<Users> ms = new ArrayList<Users>();
		
		for (String id : UsersDao.getIds()) {
			ms.add(UsersDao.instance.getModel().get(id));			
		}		
		return ms;
		
	}
	
	
	
	
	@GET
	@Path("/usernames")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getUserNames(){
		return UsersDao.getNames();
		
	}
	
	@GET
	@Path("/OnlyEmails")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getOnlyEmails(){
		return UsersDao.getEmails();
		
	}
	
	@GET
	@Path("/getCurrentUser")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUsername() {
		
		User username = UsersDao.instance.getCurrentUser();
		return username;
	}
	
	
	@DELETE
	@Path("{ID}")
	public void RemoveUser(@PathParam("ID") int id) {
		UserDao.deleteUser(id);
	}
	
	
	@POST
	@Path("/create/{details}")
	public void Adduser(@PathParam("details") String d) {
		
		User user = UsersDao.instance.getCurrentUser();
		
		if(user.getisAdmin().equals("True")) {
			
			String[] userdetails = d.split("~");
			String name = userdetails[0];
			
			//Hashing ================
			String pass = userdetails[1];
			String salt = EncyptionDao.generateSalt();
			String hashed = EncyptionDao.HashPassword(pass, Base64.getDecoder().decode(salt));
			
			String saltsalt = EncyptionDao.generateSalt();
			String encodedsalt = EncyptionDao.encrypt(salt, saltsalt);
			
			// ================
			
			String email = userdetails[2];
			String isadmin = userdetails[3];
			String extra1 = encodedsalt;
		    String extra2 = saltsalt;
			
		    UserDao.insertUserData(name, hashed, email, isadmin, extra1, extra2);
			
		}
		


	}
	
	@GET
	@Path("/codeForgotPassword")
	@Produces(MediaType.APPLICATION_JSON)
	public int getCode() {
		
		int code = UsersDao.instance.getCode();
		return code;
		
		
	}
	
	@PUT
	@Path("/changePassword/{details}")
	public void passChange(@PathParam("details") String p) {
	
		String salt = EncyptionDao.generateSalt();
		String hashed = EncyptionDao.HashPassword(p, Base64.getDecoder().decode(salt));
		
		String saltsalt = EncyptionDao.generateSalt();
		String encodedsalt = EncyptionDao.encrypt(salt, saltsalt);
		
		String extra1 = encodedsalt;
	    String extra2 = saltsalt;
		
		
		String email = UsersDao.instance.getEmail(); 
		UserDao.changePassword(hashed, extra1, extra2, email);
		
		
	}
	
	
	@POST
	@Path("/forgotPassword/{details}")
	public void passRecovery(@PathParam("details") String id) throws NoSuchAlgorithmException, NoSuchProviderException {
		String recoveryEmail = id;
		UsersDao.instance.setEmail(recoveryEmail);
		System.out.println(recoveryEmail);
		MailDao.mailSend(recoveryEmail);
	}
	
	
	@POST
	@Path("/org/{name}")
	public void AddOrganization(@PathParam("name") String idname) {
		String[] d = idname.split("~");
		int i = Integer.parseInt(d[0]);
		AccessDao.addUserOrg(i, d[1]);

	}
	
	@POST
	@Path("/event/{name}")
	public void AddEvent(@PathParam("name") String idname) {
		String[] d = idname.split("~");
		int i = Integer.parseInt(d[0]);

		AccessDao.addUserEvent(i, d[1]);

	}

	
	@DELETE
	@Path("/org/{name}")
	public void DeleteOrganization(@PathParam("name") String idname) {
		String[] d = idname.split("~");
		int i = Integer.parseInt(d[0]);
		
		AccessDao.removeUserOrg(i, d[1]);

	}
	
	@DELETE
	@Path("/event/{name}")
	public void DeleteEvent(@PathParam("name") String idname) {
		String[] d = idname.split("~");
		int i = Integer.parseInt(d[0]);
		

		AccessDao.removeUserEvent(i, d[1]);

	}
	
	
	
	
	

}
