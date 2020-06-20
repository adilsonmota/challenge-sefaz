package dao;

import java.util.List;

import entities.Contact;
import entities.Phone;

public interface PhoneDAO {

	public void insert(Phone phone);
	public void delete(Long id);
	public List<Phone> findByContact(Contact contact);
}
