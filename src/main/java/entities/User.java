package entities;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	private Long id;
	private String name;
	private String username;
	private String password;
	
	private List<Contact> contacts = new ArrayList<Contact>(); 
	
	public void addContact(Contact contact) {
		contacts.add(contact);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Contact> getContacts() {
		return contacts;
	}
	/*
	 * MÉTODO SET REMOVIDO PARA EVITAR QUE A LISTA SEJA TROCADA
	 */
}