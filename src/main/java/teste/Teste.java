package teste;

import java.sql.Connection;

import util.JdbcUtil;

public class Teste {

	public static void main(String[] args) {
		
		Connection c = JdbcUtil.getConnection();
		
		if (c == null) {
			System.out.println("não conectou!");
		} else {
			System.out.println("Conectou!");
		}
		
	}

}
