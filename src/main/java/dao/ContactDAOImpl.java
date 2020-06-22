package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.Contact;
import entities.Phone;
import entities.User;
import util.JdbcUtil;

public class ContactDAOImpl implements ContactDAO {

	public Long insert(Contact contact) {
		String sql = "INSERT INTO TB_CONTACT (NAME, EMAIL, USER_ID) VALUES (?,?,?)";
		
		Long id = null;
		
		Connection conn;
		try {
			conn = JdbcUtil.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, contact.getName());
			ps.setString(2, contact.getEmail());
			ps.setLong(3, contact.getUser().getId());
			
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			
			while (rs.next()) {
				id = rs.getLong(1);
			}
			
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public void update(Contact contact) {
		String sql = "UPDATE TB_CONTACT SET NAME=?, EMAIL=? WHERE ID=?";

		Connection conn;
		try{
			conn = JdbcUtil.getConnection();
			
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, contact.getName());
			ps.setString(2, contact.getEmail());
			ps.setLong(3, contact.getId());
			ps.execute();
			ps.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(Contact contact) {
		String sql = "DELETE FROM TB_CONTACT WHERE ID=?";

		Connection conn;
		try {
			conn = JdbcUtil.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setLong(1, contact.getId());
			ps.execute();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Contact> searchContact(User user, String keyword) {
		String sql = "SELECT C.ID ID_CONTACT, C.NAME, C.EMAIL, "
					+"P.ID ID_PHONE, P.TYP, P.DDD, P.NUMBR "
					+"FROM TB_CONTACT C LEFT JOIN TB_PHONE P "
					+"ON (C.ID=P.CONTACT_ID) "
					+"WHERE C.USER_ID=? " + inCondition(user, keyword);
		
		List<Contact> listContacts = new ArrayList<Contact>();
		
		Connection conn;
		try {
			conn = JdbcUtil.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setLong(1, user.getId());
			
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				Phone phone = new Phone();
				Contact contact = new Contact();
				
				phone.setId(rs.getLong("ID_PHONE"));
				phone.setTyp(rs.getString("TYP"));
				phone.setDdd(rs.getInt("DDD"));
				phone.setNumbr(rs.getString("NUMBR"));
				
				contact.addPhone(phone);
				
				contact.setId(rs.getLong("ID_CONTACT"));
				contact.setName(rs.getString("NAME"));
				contact.setEmail(rs.getString("EMAIL"));

				listContacts.add(contact);
			}
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return listContacts;
	}
	
	private String inCondition(User user, String keyword) {
		String where = " ";
		
		if (keyword != null) {
			where = "AND "
					+"(C.NAME LIKE'%"+keyword+"%' OR "
					+"UPPER (C.EMAIL) LIKE'%"+keyword+"%' OR "
					+"P.TYP LIKE'%"+keyword+"%' OR "
					+"P.DDD LIKE'%"+keyword+"%' OR "
					+"P.NUMBR LIKE'%"+keyword+"%')";
		}
		return where;
	}
}
