package nl.utwente.di.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Path;

import nl.utwente.di.dao.MailDao;
import nl.utwente.di.model.Mail;



@Path("/message")
public class MailResource {
	private String[] msgIDs;
	private String title;
	
	
	public MailResource() {
		
	}
	
	public MailResource(String[] ids, String title) {
		setMsgIDs(ids);
		setTitle(title);
	}

	public String[] getMsgIDs() {
		return msgIDs;
	}

	public void setMsgIDs(String[] msgIDs) {
		this.msgIDs = msgIDs;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public List<Mail> getAllMessages() {
		List<Mail> ms = new ArrayList<Mail>();
		for (String id : msgIDs)
			ms.add(MailDao.instance.getModel().get(id));
		return ms;
	}
	
	
	
	

}
