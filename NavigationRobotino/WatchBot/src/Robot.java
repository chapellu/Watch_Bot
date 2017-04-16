import org.jgrapht.*;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.*;


import java.util.ArrayList;
import java.lang.Object;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;

import javax.swing.JSpinner.ListEditor;

import rec.robotino.com.Bumper;
import rec.robotino.com.Com;
import rec.robotino.com.DistanceSensor;
import rec.robotino.com.Motor;
import rec.robotino.com.OmniDrive;
import rec.robotino.com.Odometry;


/**
 * The class Robot demonstrates the usage of the most common robot component classes.
 * Furthermore it shows how to handle events and receive incoming camera images.
 */
public class Robot 
{
	
	protected final Com _com;                    // port
	protected final OmniDrive _omniDrive;         // motorisation directionnelle
	protected final Bumper _bumper;
	protected final Odometry _odometry;
	protected final List<DistanceSensor> _distanceSensors;
	
	ServeurWB serveur;
	
	protected final SimpleGraph<Integer, DefaultEdge> graphe;
	
	protected final float SLOW_VELOCITY = 0.08f;
	protected final float MEDIUM_VELOCITY = 0.16f;
	protected final float VELOCITY = 0.24f;
	protected final float FAST_VELOCITY = 0.32f;
	protected final float ANGULARVELOCITY = 0.02f;
	static ArrayList<Float> capteurs = new ArrayList<Float>();
	int id_salle_en_cours;
	
	public ArrayList<Float> getCapteurs() {
		return capteurs;
	}
	
	public ServeurWB getServeur() {
		return serveur;
	}

	public void setCapteurs(ArrayList<Float> capteurs) {
		this.capteurs = capteurs;
	}

	public Robot()
	{
		_com = new Com();
		_omniDrive = new OmniDrive();
		_bumper = new Bumper();
		_distanceSensors = new ArrayList<DistanceSensor>();
		_odometry = new Odometry();
		
		serveur = new ServeurWB();
		
		graphe = new SimpleGraph<Integer, DefaultEdge>(DefaultEdge.class);
		
		init();
	}
	
	private void init()
	{
		_omniDrive.setComId(_com.id());       // recupere l'id
		_bumper.setComId(_com.id());
		_odometry.setComId(_com.id());
		for(int i=0; i<9; ++i)
		{
			DistanceSensor s = new DistanceSensor();
			s.setSensorNumber(i);
			s.setComId(_com.id());
			_distanceSensors.add(s);
		}
		
	}
	
	public boolean isConnected()
	{
		return _com.isConnected();
	}

	public void connect(String hostname, boolean block)
	{
		System.out.println("Connecting...");
		_com.setAddress(hostname);
		_com.connect(block);;
	}

	public void disconnect()
	{
		_com.disconnect();;
	}
	
	//Fait avancer Rob
	public void setVelocity(float vx, float vy, float omega)
	{
		_omniDrive.setVelocity( vx, vy, omega );
	}
	
	//Fait tourner Rob
    public void rotate(double nombre, boolean sens) throws InterruptedException // nombre = Nombre de quarts de tour
    {
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;

        while (_bumper.value() == false && elapsedTime<nombre*1750 )
        {
			elapsedTime = System.currentTimeMillis() - startTime;
			if(sens){ //Vers la gauche = trigo
				_omniDrive.setVelocity(10, 0, 60);
			}
			else{
				_omniDrive.setVelocity(10, 0, -60);
			}
        }
        Thread.sleep(50);   // pause
        _omniDrive.setVelocity(0, 0, 0); // stop 
    }

    	
    	/*************************************Quentin,Damien et Nicolas****************************************/
    
    
    public void getCapteursValue(ArrayList<Float> capteurs)
    {
    	//ArrayList<Float> capteurs = new ArrayList<Float>();
    	//while (_com.isConnected() && false == _bumper.value() )
        //{   		
    		capteurs.clear();         		
    		for (int i = 0; i < _distanceSensors.size(); i++)
    		{
    			//System.out.println(i + ":" + _distanceSensors.get(i).voltage() + " ");   // retourne distance  			
    			capteurs.add(_distanceSensors.get(i).voltage());  // ajoute dans la liste
    		}
        //}
    }
    
    			//-----------------------------------------------------------//
				//              placer le robot contre le mur                //
				//-----------------------------------------------------------//
    
    public void initPos() throws InterruptedException //Initialisation de la position du robot
    {
    	float x_robot = 0;
    	float y_robot = 0;
    	float angle_rotation = 0;
    	//Programme de suivi de mur de Quentin, Damien et Nicolas   	
    	float x_dir = 0.0f; // + => avance sinon recule
    	float y_dir = 0f;// + => gauche sinon droite
    	float distanceMurMin = 0.5f; //si on est superieur a cette valeur, on est proche du mur
    	float distanceMurMax = 1.7f; //si on est inferieur a cette valeur, on est loin du mur
    	   			
    	getCapteursValue(capteurs);
    	x_dir = 200;
    	y_dir = 0;   
 		while(0.7f > capteurs.get(0))
 		{
 			getCapteursValue(capteurs);
 			Thread.sleep( 200 ); // ralenti l'acquisition de données venant des capteurs
        	_omniDrive.setVelocity ( x_dir, y_dir , 0.0f );  // avance
        	System.out.println("Y6666666666666666666666666666666666666666666666666666666");
        	x_robot = _odometry.x();
    		y_robot = _odometry.y();
    		angle_rotation = _odometry.phi();
    		System.out.println("déplacement x= "+x_robot+" ; déplacement y = "+y_robot+" ; rotation = "+angle_rotation);
 		}
    	while(distanceMurMin < capteurs.get(1) /*&& capteurs.get(1) < distanceMurMax*/ || distanceMurMin < capteurs.get(0) /*&& capteurs.get(0) < distanceMurMax*/)
    	{

    		System.out.println("Y1111111111111111111111111111111111111111111111111111111111");
    		getCapteursValue(capteurs);    		
    		_omniDrive.setVelocity ( x_dir, y_dir , 0.0f );  // avance
    		x_robot = _odometry.x();
    		y_robot = _odometry.y();
    		angle_rotation = _odometry.phi();
    		System.out.println("déplacement x= "+x_robot+" ; déplacement y = "+y_robot+" ; rotation = "+angle_rotation);
    	
    		
    		//Cas d'une arrivée droite
    		if((0.8f < capteurs.get(0) && 0.8f > capteurs.get(1) && capteurs.get(1)>0.5f && 0.8f > capteurs.get(8)&& capteurs.get(8)>0.5f)){ //capteur avant
    			while (capteurs.get(1) < 0.7f || capteurs.get(2) < 1.75f || capteurs.get(3) < 0.4f){	//arrive droit
    				_omniDrive.setVelocity ( 0, 0 , -50 ); //rotation a droite
    				getCapteursValue(capteurs);
    				System.out.println("Arrivée droite");
    				x_robot = _odometry.x();
    	    		y_robot = _odometry.y();
    	    		angle_rotation = _odometry.phi();
    	    		System.out.println("déplacement x= "+x_robot+" ; déplacement y = "+y_robot+" ; rotation = "+angle_rotation);
    			}
    		}
    			
    		if((0.8f < capteurs.get(1) && 0.6f < capteurs.get(0))){	//arrive deviant sur la droite
    			while (capteurs.get(1) < 0.7f || capteurs.get(2) < 1.75f || capteurs.get(3) < 0.4f){
    				_omniDrive.setVelocity ( 0, 0 , -50 ); //rotation a droite
    				getCapteursValue(capteurs);
    				System.out.println("Arrivée déviance droite");
    				x_robot = _odometry.x();
    	    		y_robot = _odometry.y();
    	    		angle_rotation = _odometry.phi();
    	    		System.out.println("déplacement x= "+x_robot+" ; déplacement y = "+y_robot+" ; rotation = "+angle_rotation);
    			}
    		}
    			
    		if((0.8f < capteurs.get(8) && 0.6f < capteurs.get(0))){	//arrive deviant sur la gauche
    			while (capteurs.get(1) < 0.7f || capteurs.get(2) < 1.75f || capteurs.get(3) < 0.4f){
    				_omniDrive.setVelocity ( 0, 0 , -50 ); //rotation a droite
    				getCapteursValue(capteurs);
    				System.out.println("Arrivée déviance gauche");
    				x_robot = _odometry.x();
    	    		y_robot = _odometry.y();
    	    		angle_rotation = _odometry.phi();
    	    		System.out.println("déplacement x= "+x_robot+" ; déplacement y = "+y_robot+" ; rotation = "+angle_rotation);
    			}				
    		}
    			
    		Thread.sleep( 200 ); // ralenti l'acquisition de données venant des capteurs
        	_omniDrive.setVelocity ( x_dir, y_dir , 0.0f );  // avance
        	
    	}    	

     }	
    	
    
    public void followWalls() throws InterruptedException   // suit le mur
    {
    	float x_robot = 0;
    	float y_robot = 0;
    	int salle = 0; //A CHANGER!!
    	float angle_rotation = 0;
    	//Programme de suivi de mur de Quentin, Damien et Nicolas   	
    	float x_dir = 0.0f; // + => avance sinon recule
    	float y_dir = 0f;// + => gauche sinon droite
    	float distanceMurMin = 0.5f; //si on est superieur a cette valeur, on est proche du mur
    	float distanceMurMax = 2.5f; //si on est inferieur a cette valeur, on est loin du mur
    	 		
    	getCapteursValue(capteurs);
    	x_dir = 200;
    	y_dir = 0;   
 		
    	while(distanceMurMin < capteurs.get(2) && capteurs.get(2) < distanceMurMax || distanceMurMin < capteurs.get(1) && capteurs.get(1) < distanceMurMax || distanceMurMin < capteurs.get(3) && capteurs.get(3) < distanceMurMax){
    		getCapteursValue(capteurs);
    		_omniDrive.setVelocity ( x_dir, y_dir , 0.0f );  // avance
    		System.out.println("Y555555555555555555555555555555555555555555");

  				
    			   			
    		//-----------------------------------------------------------//
    		// suivre le mur a gauche si le robot devie trop vers le mur //
    		//-----------------------------------------------------------//
    			
    		if(2.0f < capteurs.get(1) || capteurs.get(2) < 1.0f){                                       //capteur avant gauche s'approche du mur
    			_omniDrive.setVelocity ( x_dir*0.8f, y_dir , -15.0f*capteurs.get(1) );  //robot devie vers la droite
    			System.out.println("Y777777777777777777777777777777777777777777777");
    		}
    			
    		if(0.5f > capteurs.get(1) || capteurs.get(2) < 1.0f){ // capteur avant gauche s'eloigne du mur
    			_omniDrive.setVelocity ( x_dir*0.8f, y_dir , 15.0f*capteurs.get(1) );   //robot devie vers la gauche
    			System.out.println("Y8888888888888888888888888888888888888888888888");
    		}
    			
 		
    		//-----------------------------------------------------------//
    		//                 disparition du mur a gauche               //
    		//-----------------------------------------------------------//
    			
    			
    		if(distanceMurMax < capteurs.get(2) && distanceMurMax < capteurs.get(1) && capteurs.get(0) < distanceMurMin){
    			_omniDrive.setVelocity ( x_dir, y_dir , 0 );  // on recupere les valeurs ci-dessus
    			this.rotate(1, true); //rotation 90° a gauche
    			serveur.ajoutObstacle(salle, y_robot, x_robot);
    		}
    		x_robot = _odometry.x();
    		y_robot = _odometry.y();
    		angle_rotation = _odometry.phi();
    		System.out.println("déplacement x= "+x_robot+" ; déplacement y = "+y_robot+" ; rotation = "+angle_rotation);
    		
    	}
    	
    	Thread.sleep( 200 ); // ralenti l'acquisition de données venant des capteurs
    	_omniDrive.setVelocity ( x_dir, y_dir , 0.0f );  // avance
   }
    
    
    //Programme permettant de relever la position (x,y,phi) courante de Rob et ajoutant un obstacle à la base de données
    public void odometry() throws InterruptedException
    {
    	float x_robot = 0;
    	float y_robot = 0;
    	float angle_rotation = 0;
    	   	
    	float x_dir = 200.0f;
    	float y_dir = 0.0f;
    	
    	getCapteursValue(capteurs); 
    	_odometry.set(0,0,0);
    	while(2.0f > capteurs.get(0)){ 
    		if(capteurs.get(0) > 0.7f)
    		{
    			this.rotate(2, true);     			
    			serveur.ajoutObstacle(51, x_robot, y_robot); // Ajout d'un obstacle lors d'une rotation du robot dans BDD
    		}
    		getCapteursValue(capteurs);
        	x_robot = _odometry.x();
    		y_robot = _odometry.y();
    		angle_rotation = _odometry.phi();
        	System.out.println("déplacement x = " + x_robot + " ; déplacement y = " + y_robot + " ; rotation = " + angle_rotation);
        	Thread.sleep( 200 );
        	_omniDrive.setVelocity ( x_dir, y_dir , 0.0f );
    	}

    }
    
    //Programme permettant de créer le graph du bâtiment étudié
    public boolean creationGraph(){
    	ArrayList<Float> Coord_porte = new ArrayList<Float>() ; //Liste de coordonnées des portes d'un bâtiment
    	ArrayList<Integer> ID_salle = new ArrayList<Integer>() ;//Liste des id des salles d'un bâtiment
    	ArrayList<Integer> ID_salle_Dest_And_Prov = new ArrayList<Integer>() ; //Liste comprenant les ids des salles de dest et de prov correspondant à une porte
    	ID_salle = serveur.recupID_salle();
    	Coord_porte = serveur.recup_Toute_Coord_portes();
    	
    	//Ajout des noeuds au graphe
    	for(int i=0;i<ID_salle.size();i++)
    	{
    		graphe.addVertex(ID_salle.get(i));
    	}

    	//Ajout des arcs au graphe
    	for(int i=0; i<Coord_porte.size();i=i+2)
    	{
    		float x_porte = Coord_porte.get(i);
    		float y_porte = Coord_porte.get(i+1);
    		for(int j=0;j<ID_salle.size();j++)
    		{
    			ID_salle_Dest_And_Prov = serveur.RecupIDsalleDestANDProv(x_porte, y_porte);
    			int salle_dest = ID_salle_Dest_And_Prov.get(0);
    			int salle_prov = ID_salle_Dest_And_Prov.get(1);

    			//Ajout des arcs entre les noeuds
    			graphe.addEdge(salle_dest,salle_prov);
    			
    		}	   		
    	}
    	ConnectivityInspector ci = new ConnectivityInspector(graphe);
    	if(ci.isGraphConnected()) 
    		return true;
    	else
    		return false;
    }
    
    //Programme calculant le chemin le plus court via le graphe entre 2 noeuds
    public List plusCourtChemin(SimpleGraph graphe, int salle_depart, int salle_destination){
    	DijkstraShortestPath di = new DijkstraShortestPath(graphe,salle_depart,salle_destination);
    	return(di.getPathEdgeList());   		
    }
    
    public ArrayList<Float> transformationCheminVersCoord(List chemin){
    	
		ArrayList<Float> coordPortes = new ArrayList<Float>();
		ArrayList<Float> pointPassage = new ArrayList<Float>();
		
		//On ressort le premier couple d'id salle venant de dijkstra
		for(int i=0; i<chemin.size(); i++)
		{
			
			//conversion des couples en string
			String parcours = chemin.get(i).toString();
			
			//Découpage de la chaîne en 2
			String chaine1 = parcours.substring(parcours.indexOf("(")+1);
			String chaine2 = parcours.substring(parcours.indexOf(":")+2);
			
			//Obtention id_salle_prov
			chaine1 = chaine1.replace(chaine2, "");
			int id_salle_prov=0;
			String[] splited = chaine1.split(" ");
			for (String current : splited) {
				try {
		            id_salle_prov = Integer.parseInt (current);
		        } 
		        catch (NumberFormatException e) {
		       
		        }
		    }
			
			//Obtention id_salle_dest
			chaine2 = chaine2.replace(")", "");
			int id_salle_dest = Integer.parseInt(chaine2);
			
			//Récupération des coordonnées des portes en fonction des salles prov et dest
			coordPortes = serveur.RecupCoordonneesPorteSachantSalleProvAndDest(id_salle_prov,id_salle_dest);
			for(int k=0;k<coordPortes.size();k++)
			{
				//ajout des coordonnées dans une arrayList
				pointPassage.add(coordPortes.get(k));
			}
			
		}
		
		System.out.println(pointPassage);	
    	return(pointPassage);
    }
    
  //Méthode visant à choisir aléatoirement la salle de destination
    public int choixSalleDestination(int salleEnCours)
    {
    	ArrayList<Integer> ListeSalles = new ArrayList<Integer>();
    	ListeSalles = serveur.recupID_salle();
    	for(int i=0; i<ListeSalles.size();i++)
    	{
    		if(ListeSalles.get(i) == salleEnCours)
    			ListeSalles.remove(i);
    	}
    	int indexSalleChoisie = (int) Math.floor(Math.random()*(ListeSalles.get(ListeSalles.size()-1) - ListeSalles.get(0)));
    	int salleChoisie = ListeSalles.get(indexSalleChoisie);
    	return salleChoisie;
    	
    }
    
    public void deplacement(ArrayList<Float> pointPassage)
    {
    	
    	for(int i=0;i<pointPassage.size();i=i+2)
    	{
    		_odometry.set(0,0,0);
    		float x_porte = pointPassage.get(i);
    		float y_porte = pointPassage.get(i+1);
    		float x_robot = _odometry.x();
    		float seuil = 1.0f;
    		System.out.println("x porte = "+x_porte);
    		System.out.println("x robot = "+x_robot);
    		System.out.println("omega robot = "+_odometry.phi());
    		float y_robot = _odometry.y();
    		System.out.println("y robot = "+y_robot);
    		if((y_robot - y_porte)!=0.0f)
    		{
	    		float degrees = (x_robot-x_porte)/(y_robot-y_porte);
	    		System.out.println("degrés: "+degrees);
	    		float radians = (float) Math.toRadians(degrees);
	    		System.out.println("rad: "+radians);
	    		float omega = (float) Math.toDegrees(Math.atan(degrees));
	    		System.out.println("omega = "+omega);
	    		if(_odometry.x()>x_porte)
	    		{
	    			
	    			while(omega - _odometry.phi()>seuil)
	    			{
	    				//System.out.println("youhou!!");
	    				_omniDrive.setVelocity(0.0f,0.0f,30);
	    				//System.out.println("omega = "+omega);
	    				//System.out.println(omega - _odometry.phi());
	    			}
	    			
	    			while(x_robot>x_porte+200 )
	    	    	{
	    				//System.out.println("Ici!");
	    	    		_omniDrive.setVelocity(200.0f,0.0f,0.0f);
	    	    	}
	    			while(x_robot>x_porte )
	    	    	{
	    				System.out.println("Ou là!");
	    	    		_omniDrive.setVelocity(75.0f,0.0f,0.0f);
	    	    	}
	    			//Ajouter un retour à l'angle = 0 pour passer la porte. Traversée jusqu'à un point dans la salle et ronde
	    			omega = 0;
	    			while(omega - _odometry.phi()>seuil)
	    			{
	    				//System.out.println("youhou!!");
	    				_omniDrive.setVelocity(0.0f,0.0f,30);
	    				//System.out.println("omega = "+omega);
	    				//System.out.println(omega - _odometry.phi());
	    			}
	    		}
	    		
	    		if(_odometry.x()<x_porte)
	    		{
	    			while(omega - _odometry.phi()>seuil)
	    			{
	    				//System.out.println("youhou2!!");
	    				//System.out.println(_odometry.phi());
	    				_omniDrive.setVelocity(0.0f,0.0f,30);
	    				System.out.println("omega = "+omega);
	    				System.out.println(omega - _odometry.phi());
	    			}
	    			while(x_robot<x_porte )
	        		{
	   					//System.out.println("Ici2!");
	   	    			_omniDrive.setVelocity(200.0f,0.0f,0.0f);
	   	    		}
	   				while(x_robot<x_porte )
    	    		{
	   					System.out.println("Ou là2!");
	    	    		_omniDrive.setVelocity(75.0f,0.0f,0.0f);
	    	    	}
	    		}
	    		else
	    			System.out.println("je suis bloqué!!");
	    		
    		}
    		else
    		{
    			System.out.println("coucou je suis là");
	    		while(x_robot<x_porte )
	    		{
	    			_omniDrive.setVelocity(200.0f,0.0f,0.0f);
	    		}
    		}
    		
    	}
    }
    
    public boolean ronde(int idSalle) throws InterruptedException
    {
    	ArrayList<Float> coord = serveur.RecupCoordonneesSalle(idSalle);
    	//A prendre en compte 
    	float x_robot = _odometry.x();
		float y_robot = _odometry.y();
		
    	while(x_robot == x_robot + 30 || y_robot == y_robot + 30)
    	{
    		_omniDrive.setVelocity(200.0f,0.0f,0.0f);
    	}
    	this.rotate(1, true);  
    	
    	while(x_robot != x_robot + 100 || y_robot != y_robot + 100 || x_robot != x_robot - 100 || y_robot != y_robot - 100)
    	{
    		_omniDrive.setVelocity(200.0f,0.0f,0.0f);
    	}
    	this.rotate(1, true);  ;
    	
    	while(x_robot != x_robot + 100 || y_robot != y_robot + 100 || x_robot != x_robot - 100 || y_robot != y_robot - 100)
    	{
    		_omniDrive.setVelocity(200.0f,0.0f,0.0f);
    	}
    	this.rotate(1, true);  
    	
    	while(x_robot != x_robot + 100 || y_robot != y_robot + 100 || x_robot != x_robot - 100 || y_robot != y_robot - 100)
    	{
    		_omniDrive.setVelocity(200.0f,0.0f,0.0f);
    	}
    	this.rotate(1, false);  
    	
    	while(x_robot != x_robot + 100 || y_robot != y_robot + 100 || x_robot != x_robot - 100 || y_robot != y_robot - 100)
    	{
    		_omniDrive.setVelocity(200.0f,0.0f,0.0f);
    	}
    	return true;
    }
    
    /**
	 * The class MyCom derives from rec.robotino.api2.Com and implements some of the virtual event handling methods.
	 * This is the standard approach for handling these Events.
	 */
	class MyCom extends Com
	{
		Timer _timer;
		
		public MyCom()
		{
			_timer = new Timer();
			_timer.scheduleAtFixedRate(new OnTimeOut(), 0, 20);
		}
		
		class OnTimeOut extends TimerTask
		{
			public void run()
			{
				processEvents();
			}
		}

		@Override
		public void connectedEvent()
		{
			System.out.println( "Connected" );
		}


		public void errorEvent(String errorStr)
		{
			System.err.println( "Error: " + errorStr );
		}

		@Override
		public void connectionClosedEvent()
		{
			System.out.println( "Disconnected" );
		}
	}

	
/*************************************Programme Principal**********************************/
	
	
	public static void main(String[] args) throws Exception
	{
		String hostname = System.getProperty("localhost", "193.48.125.38");

		Robot robotino = new Robot();
		int id_salle_dep = 19;
		List chemin;
		ArrayList<Float> parcours;
		
		try
		{
			robotino.connect(hostname, true);
			while (robotino._com.isConnected() && false == robotino._bumper.value() )
			{
				
				System.out.println(robotino.creationGraph());
				
				chemin = robotino.plusCourtChemin(robotino.graphe,19,robotino.choixSalleDestination(id_salle_dep));
				chemin.toString();
				System.out.println(chemin);
				parcours = robotino.transformationCheminVersCoord(chemin);
				robotino.deplacement(parcours); //d'une salle A vers une salle B
				System.out.println("Je passe en ronde");
				robotino.ronde(id_salle_dep); //surveillance
				Thread.sleep(200000);
				
			}
			robotino.disconnect();
		
	}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	
}
