package Communication;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseDeDonnee {
	
	public Connection conn = null;
	private static BaseDeDonnee instance = null;
	private java.sql.Statement st = null;
	private static final Logger LOGGER = Logger.getLogger( BaseDeDonnee.class.getName() );
	private static FileHandler fileTxt;
	
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
	try {
		fileTxt = new FileHandler("BaseDeDonnee.txt");
	} catch (SecurityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 LOGGER.addHandler(fileTxt);
}

public static BaseDeDonnee connect(){
	if (instance == null){
		LOGGER.log(Level.FINE, "Creating instance of BaseDeDonnee");
		instance = new BaseDeDonnee();}
	LOGGER.log(Level.FINE, "Returning instance of BaseDeDonnee");
	return(instance);
}

public String getIP(String dest){
	String sql = "SELECT IP from nomIP where nom='"+dest+"';";
	LOGGER.log(Level.INFO,sql);
		try {
			ResultSet utilisateur = st.executeQuery(sql);
			utilisateur.next();
			LOGGER.log(Level.INFO,utilisateur.getString("IP"));
			return(utilisateur.getString("IP"));
		} catch (SQLException e){
			LOGGER.log(Level.SEVERE,e.toString(),e);
		}
		return (null);
}

public String getNom(String ip){
	String sql = "SELECT nom from nomIP where ip='"+ip+"';";
	LOGGER.log(Level.INFO,sql);
	try {
		System.out.println(ip);
		ResultSet utilisateur = st.executeQuery(sql);
		utilisateur.next();
		LOGGER.log(Level.INFO,utilisateur.getString("nom"));
		return(utilisateur.getString("nom"));
	} catch (SQLException e){
		String sql1 = "SELECT nom from nomIP where nom Like 'Ordinateur%';";
		LOGGER.log(Level.INFO,sql1);
		int idOrdi = 0;
		try{
			ResultSet numOrdi = st.executeQuery(sql1);
			numOrdi.next();
			String num = numOrdi.getString("nom");
			String [] tokens = num.split(" ");
			idOrdi = Integer.valueOf(tokens[1])+1;
			LOGGER.log(Level.INFO,String.valueOf(idOrdi));
		}catch (SQLException e1) {
			LOGGER.log(Level.SEVERE,e1.toString(),e1);
		}
		sql = "INSERT INTO nomIP VALUES ('Ordinateur "+idOrdi+"', '"+ip+"');";
		LOGGER.log(Level.INFO,sql);
		try {
			st.executeUpdate(sql);
			return("Ordinateur "+idOrdi);
		} catch (SQLException e2) {
			LOGGER.log(Level.SEVERE,e2.toString(),e2);
		}
		LOGGER.log(Level.SEVERE,e.toString(),e);
	}
	return (null);
}
}

