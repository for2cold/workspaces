package com.for2cold.activiti.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class H2DBTest {

	@Test
	public void test() {
		try {
			
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection("jdbc:h2:mem:activiti", "root", "");
			Statement stmt = conn.createStatement();
			
			String sql = "create table person(id int auto_increment primary key, name varchar(200));"
					+ " insert into person(name) values('for2cold');";
			
			stmt.execute(sql);
			
			
			PreparedStatement pst = conn.prepareStatement("select * from person");
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				System.out.println("id: " + rs.getInt(1));
				System.out.println("name: " + rs.getString(2));
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
	}
}
