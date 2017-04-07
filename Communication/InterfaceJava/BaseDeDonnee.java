package Communication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDeDonnee {
	
	public Connection conn = null;
	private static BaseDeDonnee instance = null;
	private java.sql.Statement st = null;
	
private BaseDeDonnee(){
	try {
	 	 Class.forName("com.mysql.jdbc.Driver");
	 	 String url = "jdbc:mysql://192.168.118.28:3306/Pi";
	 	 conn = DriverManager.getConnection(url, "watchbot", "app2018");
	 	 st = conn.createStatement();
	 	 System.out.println("conn built");
	 } catch (SQLException e) {
	 	 e.printStackTrace();
	 } catch (ClassNotFoundException e) {
	 	 e.printStackTrace();
	 }
}

public static BaseDeDonnee connect(){
	if (instance == null){instance = new BaseDeDonnee();}
	return(instance);
}

public String getIP(String dest){
	String sql = "SELECT IP from nomIP where nom='"+dest+"';";
		try {
			ResultSet utilisateur = st.executeQuery(sql);
			utilisateur.next();
			return(utilisateur.getString("IP"));
		} catch (SQLException e){
			e.printStackTrace();
		}
		return (null);
}

public String getNom(String ip){
	String sql = "SELECT nom from nomIP where ip='"+ip+"';";
	try {
		ResultSet utilisateur = st.executeQuery(sql);
		utilisateur.next();
		return(utilisateur.getString("nom"));
	} catch (SQLException e){
		sql = "INSERT INTO nomIP VALUES ('Ordinateur', '"+ip+"');";
		try {
			st.executeUpdate(sql);
			return("Ordinateur");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//e.printStackTrace();
	}
	return (null);
}
}

