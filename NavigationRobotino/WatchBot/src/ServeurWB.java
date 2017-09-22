import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;


public class ServeurWB {
	
	//information d'accès à la base de données
			private String url ="jdbc:mysql://tp-epu.univ-savoie.fr:3308/simonw";
			private String login = "simonw";
			private String passwd = "dwxa992m";
			private static ServeurWB instance = null;
			
			java.sql.Connection connexion = null;
			java.sql.Statement st = null;
			
			
			
			public ServeurWB (){		//Penser au type (changer)
			try {
				// Etape 1 : Chargement du driver
				Class.forName("com.mysql.jdbc.Driver");
				// Etape 2 : Récupération de la connexion
				connexion= DriverManager.getConnection(url,login,passwd);
				// Etape 3 : Création d'un statement
				st = connexion.createStatement();
				System.out.println("Connexion établie");
			}
			catch (SQLException e1){
				e1.printStackTrace();
			}
			catch (ClassNotFoundException e2){
				e2.printStackTrace();
			}
			}


//Méthodes de récupération

		public ArrayList<Float> RecupCoordonneesPorte(int id_salle_prov){
			String sql = "SELECT x_porte, y_porte FROM porte WHERE porte.id_salle_prov ="+ id_salle_prov;
			ArrayList<Float> coordonneesPorte = new ArrayList<Float>();
			try{
				ResultSet query = st.executeQuery(sql);
				while(query.next()){
					float x_porte = Float.valueOf(query.getString("x_porte"));
					float y_porte = Float.valueOf(query.getString("y_porte"));
					coordonneesPorte.add(x_porte);
					coordonneesPorte.add(y_porte);
				}
				return(coordonneesPorte);
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			return null;
		}
		
		public ArrayList<Float> RecupCoordonneesPorteSachantSalleProvAndDest(int id_salle_prov, int id_salle_dest){
			String sql = "SELECT x_porte, y_porte FROM porte WHERE id_salle_prov ="+id_salle_prov+" AND id_Salle_destination ="+id_salle_dest;
			ArrayList<Float> coordonneesPorte = new ArrayList<Float>();
			try{
				ResultSet query = st.executeQuery(sql);
				while(query.next()){
					float x_porte = Float.valueOf(query.getString("x_porte"));
					float y_porte = Float.valueOf(query.getString("y_porte"));
					coordonneesPorte.add(x_porte);
					coordonneesPorte.add(y_porte);
				}
				return(coordonneesPorte);
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			return null;
		}
		
		public int RecupIdSalleDest(float x_porte, float y_porte){
			String sql = "SELECT id_Salle_destination FROM porte WHERE x_porte="+x_porte+" AND y_porte="+y_porte;
			int id_salle_dest = 1000;
			try{
				ResultSet query = st.executeQuery(sql);
				while(query.next()){
					id_salle_dest = query.getInt("id_salle_destination");					
				}
				return id_salle_dest;
				
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			return 1000;

		}
		
		@SuppressWarnings("unchecked")
		public ArrayList<ArrayList> RecupCoordonneesMur(int id_salle){
			String sql = "SELECT x_obstacle, y_obstacle FROM obstacle WHERE obstacle.id_salle ="+ id_salle;
			ArrayList<ArrayList> coordonneesObstacles = new ArrayList<ArrayList>();
			ArrayList<Float> coordonneesMur = new ArrayList<Float>();
			try{
				ResultSet query = st.executeQuery(sql);
				while(query.next()){
					float x_obstacle = Float.valueOf(query.getString("x_obstacle"));
					float y_obstacle = Float.valueOf(query.getString("y_obstacle"));
					coordonneesMur.add(x_obstacle);
					coordonneesMur.add(y_obstacle);
					coordonneesObstacles.add((ArrayList<Float>)coordonneesMur.clone());
					coordonneesMur.clear();
				}
				return(coordonneesObstacles);
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			return null;
		}
		
		public void ajoutObstacle(int id_salle, float x_mur, float y_mur){
			String sql="INSERT INTO obstacle (x_obstacle,y_obstacle,id_salle) VALUES('"+x_mur+"','"+y_mur+"','"+id_salle+"');";
			try{
				st.executeUpdate(sql);
			}
			catch (SQLException e){
				e.printStackTrace();
			}
		}
		
		public void ajoutPorte(int id_salle_provenance,int id_salle_destination, float x_porte, float y_porte){
			String sql="INSERT INTO porte (x_porte,y_porte,id_Salle_destination, id_salle_prov) VALUES('"+x_porte+"','"+y_porte+"','"+id_salle_destination+"','"+id_salle_provenance+"');";
			try{
				st.executeUpdate(sql);
			}
			catch (SQLException e){
				e.printStackTrace();
			}
		}
		
		public void ajoutSalle(float x_repere_salle,float y_repere_salle, int carto_effectuee, int id_bat, int surv_Effectuee){
			String sql="INSERT INTO salle (x_salle,y_salle, carto_Effectuee,id_Bat,surv_Effectuee) VALUES('"+x_repere_salle+"','"+y_repere_salle+"','"+carto_effectuee+"','"+id_bat+"','"+surv_Effectuee+"');";
			try{
				st.executeUpdate(sql);
			}
			catch (SQLException e){
				e.printStackTrace();
			}
		}
		
		public void ajoutBatiment(){
			String sql="INSERT INTO batiment () VALUES();";
			try{
				st.executeUpdate(sql);
			}
			catch (SQLException e){
				e.printStackTrace();
			}
		}
		
		public void suppressionBatiment(int id_bat){
			String sql="DELETE FROM batiment WHERE id_Batiment="+id_bat;
			try{
				st.executeUpdate(sql);
			}
			catch (SQLException e){
				e.printStackTrace();
			}
		}
		
		public void suppressionSalle(int id_salle){
			String sql="DELETE FROM salle WHERE id_Salle="+id_salle;
			try{
				st.executeUpdate(sql);
			}
			catch (SQLException e){
				e.printStackTrace();
			}
		}
		
		public void suppressionObstacle(int id_salle, float x_obstacle, float y_obstacle){
			String sql="DELETE FROM salle WHERE id_Salle="+id_salle+"x_obstable="+x_obstacle+"y_obstacle="+y_obstacle;
			try{
				st.executeUpdate(sql);
			}
			catch (SQLException e){
				e.printStackTrace();
			}
		}
		public ArrayList<Float> RecupCoordonneesSalle(int id_Salle){
			String sql = "SELECT x_salle, y_salle FROM salle WHERE salle.id_Salle ="+ id_Salle;
			ArrayList<Float> coordonneesSalle = new ArrayList<Float>() ;
			try{
				ResultSet query = st.executeQuery(sql);
				while(query.next()){
					float x_salle = Float.valueOf(query.getString("x_salle"));
					float y_salle = Float.valueOf(query.getString("y_salle"));
					coordonneesSalle.add(x_salle);
					coordonneesSalle.add(y_salle);
				}
				return(coordonneesSalle);
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			return null;
		}
		
		public boolean cartoEffectuee(int id_Salle){
			String sql = "SELECT carto_Effectuee FROM salle WHERE salle.id_Salle ="+id_Salle;
			boolean carto_Effectuee = false;
			try{
				ResultSet query = st.executeQuery(sql);
				while(query.next()){
					if (query.getInt("carto_Effectuee") == 1){
						 carto_Effectuee = true;
						 System.out.println("La cartographie a ete effectuee dans la salle n° :"+id_Salle); 
					}
				
				}
				return(carto_Effectuee);
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			return false;
		}
		
		public boolean cartoBatimentComplete(int id_bat){
			String sql = "SELECT carto_Effectuee FROM salle WHERE salle.id_bat ="+id_bat;
			ArrayList<Integer> carto = new ArrayList<Integer>();
			boolean carto_Complete = true;
			try{
				ResultSet query = st.executeQuery(sql);
				while(query.next()){
					int carto_Effectuee = Integer.valueOf(query.getString("carto_Effectuee"));
					carto.add(carto_Effectuee);
				}
					for(int i=0; i < carto.size(); i++){
						//System.out.println(carto.get(i));
						if (carto.get(i)==1) {
							carto_Complete = carto_Complete&&true;
						}
						else carto_Complete = false;
					}
					if (carto_Complete = true){
						System.out.println("La cartographie a ete effectuee dans le batiment n° : "+id_bat);
					}
					return(carto_Complete);
					
				}
			catch(SQLException e){
				e.printStackTrace();
			}
			return false;
			}
		
		public boolean survEffectuee(int id_Salle){
			String sql = "SELECT surv_Effectuee FROM salle WHERE salle.id_Salle ="+id_Salle;
			boolean surv_Effectuee = false;
			try{
				ResultSet query = st.executeQuery(sql);
				while(query.next()){
					if (query.getInt("surv_Effectuee") == 1){
						 surv_Effectuee = true;
						 System.out.println("La surveillance a ete effectuee dans la salle n° :"+id_Salle); 
					}
				
				}
				return(surv_Effectuee);
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			return (surv_Effectuee);
		}
		
		public void surv (int id_Salle, int surv_Effectuee){
			if (surv_Effectuee == 1 || surv_Effectuee ==0 ){
				String sql = "UPDATE salle SET surv_Effectuee = " + surv_Effectuee + "  WHERE id_Salle = "+ id_Salle; 
				try{
					st.executeUpdate(sql);
					}
				catch(SQLException e){
					e.printStackTrace();
					}
				System.out.println("Votre donnee a bien ete enregistrée");
				}
			else {
				System.out.println("Donnee de surveillance renseignée non correcte");
			}
			}
		
		public ArrayList<Integer> recupID_salle (){
			String sql = "SELECT id_Salle from salle";
			ArrayList<Integer> ID_salle = new ArrayList<Integer>() ;
			try{
				ResultSet query = st.executeQuery(sql);
				while(query.next()){
					int id_Salle = Integer.valueOf(query.getString("id_Salle"));
					ID_salle.add(id_Salle);
				}
				return(ID_salle);
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			return null;
		}
		
		public ArrayList<Float> recup_Toute_Coord_portes(){
			String sql = "SELECT x_porte, y_porte from porte";
			ArrayList<Float> Coord_porte = new ArrayList<Float>() ;
			try{
				ResultSet query = st.executeQuery(sql);
				while(query.next()){
					float x_porte = Float.valueOf(query.getString("x_porte"));
					float y_porte = Float.valueOf(query.getString("y_porte"));
					Coord_porte.add(x_porte);
					Coord_porte.add(y_porte);
				}
				return(Coord_porte);
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			return null;
		}
		
		public ArrayList<Integer> RecupIDsalleDestANDProv(float x_porte, float y_porte){
			ArrayList<Integer> ID_salles = new ArrayList<Integer>();
			String sql = "SELECT id_Salle_destination, id_salle_prov FROM porte WHERE porte.x_porte ="+ x_porte+"AND porte.y_porte ="+ y_porte;
			try{
				ResultSet query = st.executeQuery(sql);
				while(query.next()){
					int id_salle_prov = Integer.valueOf(query.getString("id_salle_prov"));
					int id_salle_dest = Integer.valueOf(query.getString("id_Salle_destination"));
					ID_salles.add(id_salle_dest);
					ID_salles.add(id_salle_prov);
				}
				return(ID_salles);
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			return null;
		}
		
		public ArrayList<Float> RecupCoordonneesPorteNav(int id_salle){
			String sql = "SELECT x_porte, y_porte FROM porte WHERE porte.id_salle_prov ="+ id_salle+"OR porte.id_salle_dest ="+id_salle;
			ArrayList<Float> coordonneesPorte = new ArrayList<Float>();
			try{
				ResultSet query = st.executeQuery(sql);
				while(query.next()){
					float x_porte = Float.valueOf(query.getString("x_porte"));
					float y_porte = Float.valueOf(query.getString("y_porte"));
					coordonneesPorte.add(x_porte);
					coordonneesPorte.add(y_porte);
				}
				return(coordonneesPorte);
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			return null;
		}
		
		
}