package dao;

import java.util.List;

import entities.Phone;

public interface PhoneDAO {

	public void insert(Phone phone);
	public void update(Phone phone);
	public void remove(Phone phone);
	public List<Phone> findAll();
}
