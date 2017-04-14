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
	 	 String url = "jdbc:mysql://193.48.125.196:3306/Pi";
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
		String sql1 = "SELECT nom from nomIP where nom Like 'Ordinateur%';";
		int idOrdi = 0;
		try{
			ResultSet numOrdi = st.executeQuery(sql1);
			numOrdi.next();
			String num = numOrdi.getString("nom");
			String [] tokens = num.split(" ");
			idOrdi = Integer.valueOf(tokens[1]);
		}catch (SQLException e1) {
			//e1.printStackTrace();
		}
		sql = "INSERT INTO nomIP VALUES ('Ordinateur "+idOrdi+"', '"+ip+"');";
		try {
			st.executeUpdate(sql);
			return("Ordinateur "+idOrdi);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		}
		//e.printStackTrace();
	return (null);
}
}

