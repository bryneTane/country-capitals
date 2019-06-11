package fr.esigelec.jee;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAO {
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}		
	}

	
	private String url = "jdbc:mysql://localhost/capitals";
	private String username = "countrycapital";
	private String password = "123";
	Connection conn = null;
	
	public DAO() {}
	
	private boolean connect() {
		 try {
			 conn = DriverManager.getConnection(url,
			 username, password);
		 } catch (SQLException ex) {
			 return false;
		 }
		 return true;
	}
	
	private void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public List<String> getCountryCode() {
		List<String> codes = new ArrayList<>();
		if (connect()) {
			String query = "SELECT country_code FROM country_capitals";
			try {
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					codes.add(rs.getString(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close();
			}
		}
		return codes;
	}
	
	public String getCountryName(String countryCode) {
		String name = "";
		if (connect()) {
			String query = "SELECT country_name FROM country_capitals WHERE country_code=?";
			try {
				PreparedStatement ps = conn.prepareStatement(query);
				ps.setString(1, countryCode);
				ResultSet rs = ps.executeQuery();
				if(rs.next()){
					name = rs.getString(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close();
			}
		}
		return name;
	}
	
	public String getCapital(String countryCode) {
		String capital = "";
		if (connect()) {
			String query = "SELECT country_capital FROM country_capitals WHERE country_code=?";
			try {
				PreparedStatement ps = conn.prepareStatement(query);
				ps.setString(1, countryCode);
				ResultSet rs = ps.executeQuery();
				if(rs.next()){
					capital = rs.getString(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close();
			}
		}
		return capital;
	}
	
	public String validateUserCredentials(String user, String password) {
		MessageDigest md;
		StringBuilder sb = new StringBuilder();
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

	        for (byte b : hashInBytes) {
	            sb.append(String.format("%02x", b));
	        }
	        
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		password = sb.toString();
		if (connect()) {
			String query = "SELECT name FROM user WHERE email=? AND password=?";
			try {
				PreparedStatement ps = conn.prepareStatement(query);
				ps.setString(1, user);
				ps.setString(2, password);
				ResultSet rs = ps.executeQuery();
				if(rs.next())
					return rs.getString(1);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close();
			}
		}
		return null;
	}
	
	public boolean createNewUser(String email, String name, String password){
		MessageDigest md;
		StringBuilder sb = new StringBuilder();
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

	        for (byte b : hashInBytes) {
	            sb.append(String.format("%02x", b));
	        }
	        
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		password = sb.toString();
		if(connect()) {
			String query = "SELECT * FROM user WHERE email = ? ";
			
			PreparedStatement ps,ps2;
			try {
				ps = conn.prepareStatement(query);
				ps.setString(1, email);
				ResultSet rs = ps.executeQuery();
				if(rs.next())
					return false;
				else {
					query = "INSERT INTO user(name,email,password) VALUES(?,?,?) ";
					ps2 = conn.prepareStatement(query);
					ps2.setString(1, name);
					ps2.setString(2, email);
					ps2.setString(3, password);
					ps2.execute();
					return true;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				close();
			}
		}

		return false;
	}
	
	
	public boolean createNewCountry(String name, String code, String capital){
		if(connect()) {
			String query = "SELECT * FROM country_capitals WHERE country_code = ? ";
			
			PreparedStatement ps,ps2;
			try {
				ps = conn.prepareStatement(query);
				ps.setString(1, code);
				ResultSet rs = ps.executeQuery();
				if(rs.next())
					return false;
				else {
					query = "INSERT INTO country_capitals(country_name,country_code,country_capital) VALUES(?,?,?) ";
					ps2 = conn.prepareStatement(query);
					ps2.setString(1, name);
					ps2.setString(2, code);
					ps2.setString(3, capital);
					ps2.execute();
					return true;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				close();
			}			
		}

		return false;
	}
	
	public int getID(String name, String code, String capital) {
		int id = 0;
		if (connect()) {
			String query = "SELECT id FROM country_capitals WHERE (country_code=? AND country_name=? AND country_capital=?)";
			try {
				PreparedStatement ps = conn.prepareStatement(query);
				ps.setString(1, code);
				ps.setString(2, name);
				ps.setString(3, capital);
				ResultSet rs = ps.executeQuery();
				if(rs.next()){
					id = rs.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close();
			}
		}
		return id;
	}
	
	public boolean modifyCountry(int id, String name, String code, String capital){
		if(connect()) {
			String query = "UPDATE country_capitals SET country_name=?, country_code=?, country_capital=? WHERE id=?";
			
			PreparedStatement ps;
			try {
				ps = conn.prepareStatement(query);
				ps.setString(1, name);
				ps.setString(2, code);
				ps.setString(3, capital);
				ps.setInt(4, id);
				ps.execute();
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				close();
			}			
		}
		return false;
	}
	
//	public List <String> getCountryCode(){
//		List<String> list = new ArrayList <String>();
//		list.add("FR");
//		list.add("ES");
//		list.add("PT");
//		return list;
//	}
//	
//	public String getCountryName(String countryCode) {
//		switch (countryCode) {
//			case "FR":
//				return "France";
//			case "PT":
//				return "Portugal";
//			case "ES":
//				return "Spain";
//			default:
//				return null;
//		}
//	}
//	
//	public String getCapital(String countryCode) {
//		switch (countryCode) {
//			case "FR":
//				return "Paris";
//			case "PT":
//				return "Lisbon";
//			case "ES":
//				return "Madrid";
//			default:
//				return null;
//		}
//	}
//	
//	public int validateUserCredentials(String login, String password){
//		if(login != null && password != null) {
//			if((login != "") && (password != "")) {
//				if (login.equals("user") && password.equals("2019")) {
//					return 1;
//				}else {
//					return 2;
//				}
//			}else {
//				return 3;
//			}			
//		}else {
//			return 3;
//		}
//	}
	
//	public static void main (String [] args) {
//		System.out.println(new DAO().getCapital("CM"));
//	}
}
