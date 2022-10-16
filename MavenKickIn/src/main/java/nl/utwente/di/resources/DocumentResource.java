package nl.utwente.di.resources;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nl.utwente.di.dao.AccessDao;
import nl.utwente.di.dao.MailDao;
import nl.utwente.di.dao.UsersDao;
import nl.utwente.di.model.Mail;
import nl.utwente.di.model.User;



@Path("search")
public class DocumentResource extends MailResource{
	
	private User user = UsersDao.instance.getCurrentUser();
	

			
	public DocumentResource() {
		super(MailDao.getIds(), "Kickin messages");
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Mail> getMessages() {
		return getAllMessages();
	}
	
	@GET
    @Path("/emails")   
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getEmails1(){
        
        return MailDao.getEmails();
        
    }
	
	
	 
	@POST
	@Path("/org/{name}")
	public void AddOrganization(@PathParam("name") String idname) {
		String[] d = idname.split("~");
		int i = Integer.parseInt(d[0]);
		AccessDao.addDocOrg(i, d[1]);

	}
	
	@POST
	@Path("/event/{name}")
	public void AddEvent(@PathParam("name") String idname) {
		String[] d = idname.split("~");
		int i = Integer.parseInt(d[0]);
		AccessDao.addDocEvent(i, d[1]);

	}
	
	
	
	
	@DELETE
	@Path("/org/{name}")
	public void DeleteOrganization(@PathParam("name") String idname) {
		String[] d = idname.split("~");
		int i = Integer.parseInt(d[0]);
		AccessDao.removeDocOrg(i, d[1]);

	}
	
	@DELETE
	@Path("/event/{name}")
	public void DeleteEvent(@PathParam("name") String idname) {
		String[] d = idname.split("~");
		int i = Integer.parseInt(d[0]);
		AccessDao.removeDocEvent(i, d[1]);

	}
	
	
	public void writeLog(String log) {
		
		//path to log txt file, this can be changed to any location
		try(FileWriter fw = new FileWriter("C:\\Development\\eclipse\\jee-2021-03\\workspace\\MavenKickIn\\src\\main\\webapp\\Log\\log.txt", true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
			    out.println(log + "\n");
			} catch (IOException e) {
			    e.printStackTrace();
			}
		
		
	}
	
	@POST
	@Path("/Log/{log}")
	public void putLog(@PathParam("log") String log) {
		String logWithUser = user.getFullname() + log;
		System.out.println(logWithUser);
		writeLog(logWithUser);
	}
	

	
	
	
	
	
	

}
