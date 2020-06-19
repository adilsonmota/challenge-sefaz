package control;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dao.UserDAO;
import dao.UserDAOImpl;
import entities.User;
import util.GrowlView;
import util.SessionUtil;

@ManagedBean(name = "LoginBean")
@SessionScoped
public class LoginBean {

	private String usrLogin;
	private String pswLogin;
	private User current;
	private List<User> registeredUsers;
	private UserDAO userDao;

	private GrowlView message;
	
	public LoginBean() {
		this.message = new GrowlView();
		this.current = new User();
		this.registeredUsers = new ArrayList<User>();
		this.userDao = new UserDAOImpl();
	}

	public String login() {

		boolean msg = false;

		this.registeredUsers = this.userDao.findAll();

		for (User listUsers : registeredUsers) {
			if ((listUsers.getUsername().equalsIgnoreCase(usrLogin) && listUsers.getPassword().equals(pswLogin))) {
				msg = true;
				this.current.setId(listUsers.getId());
				this.current.setName(listUsers.getName());
				this.current.setUsername(listUsers.getUsername());
				this.current.setPassword(listUsers.getPassword());
			}
		}
		if (msg) {
			message.setSuccessMessage("Sucesso no Login!");
			message.saveMessage(true);

			SessionUtil.setParam("logged", current);
			
			return "newContact.xhtml";
		} else {
			message.setErrorMessage("Falha no Login! Verifique seu usuário e senha!");
			message.saveMessage(false);
		}
		usrLogin = null;
		pswLogin = null;
	
		return null;
	}

	
	
	public String getUsrLogin() {
		return usrLogin;
	}
	public void setUsrLogin(String usrLogin) {
		this.usrLogin = usrLogin;
	}

	public String getPswLogin() {
		return pswLogin;
	}
	public void setPswLogin(String pswLogin) {
		this.pswLogin = pswLogin;
	}
}
