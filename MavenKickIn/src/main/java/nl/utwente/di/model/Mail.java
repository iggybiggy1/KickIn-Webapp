package nl.utwente.di.model;



import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Mail {
	private String id;
	private String sender;
	private String sender_email;
	private String subject;
	private String content;
	private String attachment;
	private String did;
	private List<String> accOrg;
	private List<String> accEvent;
	private String send_at;
	
	
	
	public Mail() {
		
	}
	




	public Mail(String id, String sender, String sender_email, String subject, String content, String attachment, String did, List<String> accOrg, List<String> accEvent, String send_at) {
		setSender(sender);
		setSender_email(sender_email);
		setSubject(subject);
		setContent(content);
		setAttachment(attachment);
		setId(id);
		setDid(did);
		setAccOrg(accOrg);
		setAccEvent(accEvent);
		setSend_at(send_at);

	}
	
	
	
	public String getSend_at() {
		return send_at;
	}





	public void setSend_at(String send_at) {
		this.send_at = send_at;
	}





	public List<String> getAccOrg() {
		return accOrg;
	}





	public void setAccOrg(List<String> accOrg) {
		this.accOrg = accOrg;
	}





	public List<String> getAccEvent() {
		return accEvent;
	}





	public void setAccEvent(List<String> accEvent) {
		this.accEvent = accEvent;
	}





	public String getDid() {
		return did;
	}





	public void setDid(String did) {
		this.did = did;
	}



	
	public String getId() {
		return id;
	}





	public void setId(String id) {
		this.id = id;
	}

	public String getSender_email() {
		return sender_email;
	}





	public void setSender_email(String sender_email) {
		this.sender_email = sender_email;
	}





	public String getSubject() {
		return subject;
	}





	public void setSubject(String subject) {
		this.subject = subject;
	}





	public String getContent() {
		return content;
	}





	public void setContent(String content) {
		this.content = content;
	}





	public String getAttachment() {
		return attachment;
	}





	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}





	public String getSender() {
		return sender;
	}
	
	public void setSender(String s) {
		this.sender = s;
	}
	
	

}
