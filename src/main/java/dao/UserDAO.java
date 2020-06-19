package dao;

import java.util.List;

import entities.User;

public interface UserDAO {

	public void insert(User user);
	public void update(User user);
	public void remove(User user);
	public List<User> findAll();
	public User findByUsername(String username);
}
