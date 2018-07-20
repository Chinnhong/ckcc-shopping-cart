package ckcc.hibernate.sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;



public class TestHibernate {
	public static void main(String[] args) {
		String jdbcUrl ="jdbc:mysql://127.25.1.1:3306/hibernate?useSSL=false";
		try {
			System.out.println("Connection to database: "+jdbcUrl);
			Connection myConn = DriverManager.getConnection(jdbcUrl,"root","");
			System.out.println("Connect Successfully!");
			String sql = "insert into tblstudent (id,name,gender,email) values ('1','hong','m','com')";
			Statement st = (Statement) myConn.createStatement();
			//st.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
