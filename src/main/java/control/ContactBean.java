package control;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dao.ContactDAO;
import dao.ContactDAOImpl;
import dao.PhoneDAO;
import dao.PhoneDAOImpl;
import dao.UserDAO;
import dao.UserDAOImpl;
import entities.Contact;
import entities.Phone;
import entities.User;
import util.GrowlView;
import util.SessionUtil;

@ManagedBean(name = "ContactBean")
@SessionScoped
public class ContactBean {
	
	private String keyword;
	
	private String newPassword;
	
	private User currentUser;
	private Contact newContact;
	private Phone newPhone;
	
	private Contact selectedContact;
	private Phone selectedPhone;
	
	private List<Phone> listPhones;
	private List<Phone> deletePhones;
	private List<Contact> listContacts;
	
	private ContactDAO contactDao;
	private PhoneDAO phoneDao;
	private UserDAO userDao;
	
	private GrowlView message;
	
	public ContactBean() {
		this.message = new GrowlView();
		this.currentUser = new User();
		this.newContact = new Contact();
		this.newPhone = new Phone();
		this.selectedContact = new Contact();
		this.selectedPhone = new Phone();
		
		this.listPhones = new ArrayList<Phone>();
		this.deletePhones = new ArrayList<Phone>();
		this.listContacts = new ArrayList<Contact>();
		
		this.contactDao = new ContactDAOImpl();
		this.phoneDao = new PhoneDAOImpl();
		this.userDao = new UserDAOImpl();
		
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
    	if (this.newContact.getName() != null) {
    		
    		this.newContact.setUser(this.currentUser);
    		this.newContact.setId(this.contactDao.insert(this.newContact));
    		
	    	for (Phone phone : listPhones) {
				phone.setContact(this.newContact);
				this.phoneDao.insert(phone);
			}
    	}
    	this.newContact = new Contact();
    	this.listPhones = new ArrayList<Phone>();
    }
	
	public void search() {
		this.listContacts = contactDao.searchContact(this.currentUser, this.keyword);
	}
	
	public String selectContact() {
		if (this.selectedContact == null) {
			message.setErrorMessage("Nenhum contato selecionado");
			message.saveMessage(false);
		} else {
			this.listPhones = this.phoneDao.findByContact(this.selectedContact);
			return "updateContact.xhtml";
		}
		return null;
	}
	
	public String updateAll() {
		if (	(this.selectedContact.getName() != null) && 
				(this.selectedContact.getEmail() != null)	) {
    		
				this.contactDao.update(this.selectedContact);
			
	    	for (Phone ph : deletePhones) {
				this.phoneDao.delete(ph.getId());
			}
	    	for (Phone phone : listPhones) {
	    		if (phone.getId() == null) {
	    			phone.setContact(this.selectedContact);
					this.phoneDao.insert(phone);
				}
				
			}
    	}
    	this.selectedContact = new Contact();
    	this.listPhones = new ArrayList<Phone>();
    	search();
    	return "search.xhtml";
	}
	
	public String prepDelPhone() {
		this.deletePhones.add(this.selectedPhone);
		this.selectedPhone = new Phone();
		return null;
	}
	
	public String deleteContact() {
		if (	(this.selectedContact.getName() != null) && 
				(this.selectedContact.getEmail() != null)	) {
			this.contactDao.delete(this.selectedContact);
			this.selectedContact = new Contact();
			this.listPhones = new ArrayList<Phone>();
		} else {
			message.setErrorMessage("Nenhum contato selecionado");
			message.saveMessage(false);
		} 
		search();
		return "search.xhtml";
	}
	
	public void updateUser() {
		if (this.newPassword != null && !this.newPassword.isEmpty()) {
			this.currentUser.setPassword(newPassword);
		}
		if (this.currentUser.getName() != null) {
			this.userDao.update(this.currentUser);
			message.setSuccessMessage("Dados alterados com sucesso!");
			message.saveMessage(true);
		} 
	}
	
	public String cancel() {
		this.listContacts = new ArrayList<Contact>();
		this.selectedContact = new Contact();
		this.listPhones = new ArrayList<Phone>();
		this.keyword = null;
		return "search.xhtml";
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

	public Contact getSelectedContact() {
		return selectedContact;
	}

	public void setSelectedContact(Contact selectedContact) {
		this.selectedContact = selectedContact;
	}

	public Phone getSelectedPhone() {
		return selectedPhone;
	}

	public void setSelectedPhone(Phone selectedPhone) {
		this.selectedPhone = selectedPhone;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
