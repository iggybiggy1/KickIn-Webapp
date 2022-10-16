package nl.utwente.di.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Users {
	private String id;
	private String name;
	private String email;
	private List<String> accessEvent;
	private List<String> accessOrg;
	
	public Users(String id, String name, String email, List<String> accessEvent, List<String> accessOrg) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.accessEvent = accessEvent;
		this.accessOrg = accessOrg;
	}
	
	public Users() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getAccessEvent() {
		return accessEvent;
	}

	public void setAccessEvent(List<String> accessEvent) {
		this.accessEvent = accessEvent;
	}

	public List<String> getAccessOrg() {
		return accessOrg;
	}

	public void setAccessOrg(List<String> accessOrg) {
		this.accessOrg = accessOrg;
	}
	
	
	
	
	
 	

}
