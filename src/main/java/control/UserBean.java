package control;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dao.UserDAO;
import dao.UserDAOImpl;
import entities.User;
import util.GrowlView;

@ManagedBean(name = "UserBean")
@SessionScoped
public class UserBean {
	
	private User newUser;
	private List<User> registeredUsers;
	private UserDAO userDao;

	private GrowlView message;
	
	public UserBean() {
		this.message = new GrowlView();
		this.newUser = new User();
		this.registeredUsers = new ArrayList<User>();
		this.userDao = new UserDAOImpl();
	}
	
	public String registerUser() {
		
		boolean msg = true;		// SE A LISTA COMEÇAR VAZIA, VAI DIRETO PARA O INSERT
		
		this.registeredUsers = userDao.findAll();
		
		for (User listUsers : registeredUsers) {
			if (listUsers.getUsername().equals(this.newUser.getUsername())) {
				msg= false;
			}
		}
		
		if (msg) {
			this.userDao.insert(this.newUser);
			this.newUser = new User();
			message.setSuccessMessage("Cadastro realizado com sucesso!");
			message.saveMessage(true);
			return "index.xhtml";
		} else {
			this.newUser = new User();
			message.setErrorMessage("Este username já está em uso!");
			message.saveMessage(false);
		}
		return null;
	}
	
	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}
}
