package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.User;
import util.JdbcUtil;

public class UserDAOImpl implements UserDAO {

	public void insert(User user) {
		String sql = "INSERT INTO TB_USER (NAME, USERNAME, PASSWORD) VALUES (?,?,?)";
		
		Connection conn;
		try {
			conn = JdbcUtil.getConnection();
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, user.getName());
			ps.setString(2, user.getUsername());
			ps.setString(3,	user.getPassword());
			
			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(User user) {
		String sql = "UPDATE TB_USER SET PASSWORD=?, NAME=? WHERE USERNAME=?";
		
		Connection conn;
		try {
			conn = JdbcUtil.getConnection();
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, user.getPassword());
			ps.setString(2, user.getName());
			ps.setString(3, user.getUsername());
			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void remove(User user) {
		String sql = "DELETE FROM TB_USER WHERE USERNAME=?";
		
		Connection conn;
		try {
			conn = JdbcUtil.getConnection();
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, user.getUsername());
			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<User> findAll() {
		String sql = "SELECT ID, NAME, USERNAME, PASSWORD FROM TB_USER";
		
		List<User> listUsers = new ArrayList<User>();
		
		Connection conn;
		try {
			conn = JdbcUtil.getConnection();
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getLong("ID"));
				user.setName(rs.getString("NAME"));
				user.setUsername(rs.getString("USERNAME"));
				user.setPassword(rs.getString("PASSWORD"));
				
				listUsers.add(user);
			}
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listUsers;
	}
	
	public User findByUsername(String email) {
		String sql="SELECT ID, NAME, USERNAME, PASSWORD FROM TB_USER WHERE USERNAME=?";
		
		User user = new User();

		Connection conn;
		try {
			conn = JdbcUtil.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, email);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				user.setId(rs.getLong("ID"));
				user.setName(rs.getString("NAME"));
				user.setName(rs.getString("USERNAME"));
				user.setPassword(rs.getString("PASSWORD"));
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return user;
	}
}