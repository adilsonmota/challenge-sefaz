package control;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dao.ContactDAO;
import dao.ContactDAOImpl;
import dao.PhoneDAO;
import dao.PhoneDAOImpl;
import entities.Contact;
import entities.Phone;
import entities.User;
import util.SessionUtil;

@ManagedBean(name = "ContactBean")
@SessionScoped
public class ContactBean {
	
	private String keyword;
	
	private User currentUser;
	private Contact newContact;
	private Phone newPhone;
	
	private List<Phone> listPhones;
	private List<Contact> listContacts;
	
	private ContactDAO contactDao;
	private PhoneDAO phoneDao;
	
	public ContactBean() {
		
		this.currentUser = new User();
		this.newContact = new Contact();
		this.newPhone = new Phone();
		
		this.listPhones = new ArrayList<Phone>();
		this.listContacts = new ArrayList<Contact>();
		
		this.contactDao = new ContactDAOImpl();
		this.phoneDao = new PhoneDAOImpl();
		
		currentUser();
	}
	
	private void currentUser() {
		Object obj = SessionUtil.getParam("logged");
		this.currentUser  = (User) obj;
	}
	
    public String reinit() {
    	this.newPhone = new Phone();
        return null;
    }
    
    public void saveAll() {
    	
    	System.out.println("entrou no método");
    	
    	if (this.newContact.getName() != null) {
    		
    		System.out.println("Entrou no if");
    		
    		this.newContact.setUser(this.currentUser);
    		this.newContact.setId(this.contactDao.insert(this.newContact));
    		
	    	for (Phone phone : listPhones) {
	    		
	    		System.out.println("entrou no for each");
	    		
				phone.setContact(this.newContact);
				phoneDao.insert(phone);
			}
    	}
    	this.newContact = new Contact();
    	this.listPhones = new ArrayList<Phone>();
    }
	
	public void search() {
		this.listContacts = contactDao.searchContact(currentUser, keyword);
	}
    
	public List<Phone> getListPhones() {
		return listPhones;
	}

	public Contact getNewContact() {
		return newContact;
	}

	public void setNewContact(Contact newContact) {
		this.newContact = newContact;
	}

	public Phone getNewPhone() {
		return newPhone;
	}

	public void setNewPhone(Phone newPhone) {
		this.newPhone = newPhone;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<Contact> getListContacts() {
		return listContacts;
	}
}
