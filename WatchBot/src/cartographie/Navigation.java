package cartographie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;
import javax.swing.JSpinner.ListEditor;

import rec.robotino.com.*;
import static java.lang.Math.abs;

/**
 * The class Robot demonstrates the usage of the most common robot component classes.
 * Furthermore it shows how to handle events and receive incoming camera images.
 */
public class Navigation
{
	
	private static final Logger LOGGER = Logger.getLogger( Navigation.class.getName() );
	private static FileHandler fileTxt;

	protected final Com _com;                    // port
	protected final OmniDrive _omniDrive;        // motorisation directionnelle
	protected final Bumper _bumper;
	protected final List<DistanceSensor> _distanceSensors;
	
	protected final float SLOW_VELOCITY = 0.08f;
	protected final float MEDIUM_VELOCITY = 0.16f;
	protected final float VELOCITY = 0.24f;
	protected final float FAST_VELOCITY = 0.32f;
	protected final float ANGULARVELOCITY = 0.02f;
	private String hostname;
	static ArrayList<Float> capteurs = new ArrayList<Float>();
	
	private boolean carto = true;		//Booléen pour arrêter le robot quand il en reçoit l'ordre
	
	public Navigation(Com communication, OmniDrive omniDrive, Bumper bumper)
	{
		_com = communication;
		_omniDrive = omniDrive;
		_bumper = bumper;
		_distanceSensors = new ArrayList<DistanceSensor>();
		try {
			fileTxt = new FileHandler("Navigation.txt");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 LOGGER.addHandler(fileTxt);
	}
	
	private void init()
	{
		_omniDrive.setComId(_com.id());       // recupere l'id
		_bumper.setComId(_com.id());
		for(int i=0; i<9; ++i)
		{
			DistanceSensor s = new DistanceSensor();
			s.setSensorNumber(i);
			s.setComId(_com.id());
			_distanceSensors.add(s);
		}
		
	}
	
	public ArrayList<Float> getCapteurs() {
		return capteurs;
	}

	public void setCapteurs(ArrayList<Float> capteurs) {
		this.capteurs = capteurs;
	}
	
	public void setVelocity(float vx, float vy, float omega)
	{
		_omniDrive.setVelocity( vx, vy, omega );
	}
	

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
    	//System.out.println("Acquisition Capteurs");
    		int winSize=100 ;
    		capteurs.clear();        		
    		for (int i = 0; i < _distanceSensors.size(); i++)
    		{
    			float sum = 0;
    			
    			for(int j=0; j<winSize; j++){
    				sum += _distanceSensors.get(i).voltage();
    			}
    			float average = sum / winSize;
    			capteurs.add(average);  // ajoute dans la liste
    			LOGGER.log(Level.SEVERE,i + ":" + capteurs.get(i)+ " ");// retourne moyenne distance
    		}
    }

    //Localisation d'un mur pour le positionnement initial du robot
    public void initPos() throws InterruptedException 
    {
    	float x_dir = 0.0f;			 // + => avance sinon recule
    	float y_dir = 0f;		     // + => gauche sinon droite
    	boolean once = true;		 // Dans l'avancement du debut, permet de le faire qu'une seule fois
    	//System.out.println("INITIALISATION");
    	getCapteursValue(capteurs);
    	carto =  true;
		//-----------------------------------------------------------//
		//        Le robot avance jusqu'au mur pour s'arrêter        //
		//-----------------------------------------------------------//
    	while(capteurs.get(0)<2.0f && once && carto){
    		getCapteursValue(capteurs);	

    		x_dir = 180.0f*(2.0f-capteurs.get(0));
    		y_dir = 0;  
    		
    		
    		_omniDrive.setVelocity ( x_dir, y_dir , 0.0f );  
    		 
    		if (capteurs.get(1) > 1.8f){
    			_omniDrive.setVelocity ( 0, 0 , 0.0f );
    			once=false;
    		}
    		if (capteurs.get(8)> 1.8f){
    			_omniDrive.setVelocity ( 0, 0 , 0.0f );
    			once=false;
    			}
    		if (capteurs.get(0)>1.8f || capteurs.get(8)> 1.8f || capteurs.get(1) > 1.8f){
    			once=false;
    		}
    	} 
    	
    	LOGGER.log(Level.SEVERE,"la valeur de once est = " + once);
    	_omniDrive.setVelocity ( 0, 0 , 0.0f );

		//-----------------------------------------------------------//
		//              Le robot tourne pour se placer               //
    	//-----------------------------------------------------------//

    	
		getCapteursValue(capteurs);	
		float decalage13 = 0.7519738f;	
		float epsilon13 =0.1f;
		LOGGER.log(Level.SEVERE,(decalage13 + capteurs.get(1) - epsilon13) + " < " + capteurs.get(3) + " < " + (decalage13 + capteurs.get(1)+ epsilon13));
    	while((!(((decalage13 + capteurs.get(1) - epsilon13) < capteurs.get(3)) && capteurs.get(3) < (decalage13 + capteurs.get(1)+ epsilon13))) && carto)
    	{
    		LOGGER.log(Level.SEVERE,(decalage13 + capteurs.get(1) - epsilon13) + " < " + capteurs.get(3) + " < " + (decalage13 + capteurs.get(1)+ epsilon13));
    		_omniDrive.setVelocity ( 0, 0 , -25.0f );
    		getCapteursValue(capteurs);	
    	}
   	
    	_omniDrive.setVelocity ( 0, 0 , 0.0f );
    	
 }
    


    	//-----------------------------------------------------------//
		//              		Suivi de mur			             //
		//-----------------------------------------------------------//

    public void FollowWalls ()throws InterruptedException{
	
	float x_dir = 0.0f;			 // + => avance sinon recule
	float y_dir = 0f;		     // + => gauche sinon droite
	float decalage13 = 0.7519738f;	
	float epsilon13 =0.1f;
	
	//System.out.println("SUIVI DE MUR");
	getCapteursValue(capteurs);
	carto =  true;
	while(carto){

	getCapteursValue(capteurs);
	if(capteurs.get(1)>0.2f){	
		while(capteurs.get(1)>0.2f && carto){
			getCapteursValue(capteurs);	
	
			
			/*On distingue deux if, le premier permet de maintenir une trajectoire droite, le second possede une correction lineaire en rotation*/ 
			
			if((decalage13 + capteurs.get(2) - epsilon13) < capteurs.get(3) && capteurs.get(3) < (decalage13 + capteurs.get(1)+ epsilon13)){
			getCapteursValue(capteurs);	
			x_dir = 100.0f*(2.0f-capteurs.get(0));
			y_dir = 0;
			
			LOGGER.log(Level.SEVERE,(decalage13 + capteurs.get(1) - epsilon13) + "<" + capteurs.get(3) + "<" + (decalage13 + capteurs.get(1)+ epsilon13));
			_omniDrive.setVelocity ( x_dir, y_dir ,0f);
			}
			
			
			if(!((decalage13 + capteurs.get(1) - epsilon13) < capteurs.get(3) && capteurs.get(3) < (decalage13 + capteurs.get(1)+ epsilon13))){
			getCapteursValue(capteurs);	
			x_dir = 100.0f*(2.0f-capteurs.get(0));
			y_dir = 0;
			
			LOGGER.log(Level.INFO,(decalage13 + capteurs.get(1) - epsilon13) + "<" + capteurs.get(3) + "<" + (decalage13 + capteurs.get(1)+ epsilon13));
			_omniDrive.setVelocity ( x_dir, y_dir , 50*(0.9f - capteurs.get(1)) ); 
			}	
			
			/* Tourner a droite, vide*/ 
			if(capteurs.get(0) > 1.3f){

				_omniDrive.setVelocity ( 0, 0, 0); 
				getCapteursValue(capteurs);
				while((capteurs.get(1) < 0.8f && capteurs.get(3)<1.5f) || capteurs.get(4) < 2.0f && carto){
					getCapteursValue(capteurs);	
					_omniDrive.setVelocity ( 0, 0, -25.0f );
						}
					}
				}
		}
				


	/* Tourner a gauche */ 
	if(capteurs.get(1)<=0.2f){	
		getCapteursValue(capteurs);
		while(capteurs.get(1)<0.2f && carto){
			x_dir = 100;
			y_dir = 0;
			getCapteursValue(capteurs);
			
			_omniDrive.setVelocity(x_dir, y_dir , 0);
			if(capteurs.get(2)<1.5){
				_omniDrive.setVelocity( x_dir, y_dir , 50*(2.0f - capteurs.get(3)));
				}
			}
		}
	}	
}

   
    public void stopFollowWalls ()throws InterruptedException{
    	carto = false;
    	_omniDrive.setVelocity(0, 0, 0.0f);
    }
		//-----------------------------------------------------------//
		//              		Mode manuel				             //
		//-----------------------------------------------------------//
    //Avance : paramètre positif / Recule : paramètre négatif
    public void avance(int nombre)throws InterruptedException //Nombre de fois 10cms
    {
		long startTime = System.currentTimeMillis();
	    long elapsedTime = 0;

	    while (elapsedTime<abs(nombre)*950 && !_bumper.value())
	    {
			elapsedTime = System.currentTimeMillis() - startTime;
			if(nombre>0){
				_omniDrive.setVelocity(120, 0, 0); //vitesse : 80 -> 1600 ou 120 -> 1060
			}
			else if(nombre < 0){
				_omniDrive.setVelocity(-120, 0, 0); //vitesse : 80 -> 1600 ou 120 -> 1060
			}
	
	    }
	    Thread.sleep(50);
	    _omniDrive.setVelocity(0, 0, 0);
    }
    
//}






/*************************************Programme Principal**********************************/

	public void modeSuiviMur()
	{
		//String hostname = System.getProperty("localhost", "193.48.125.37");
	
		//Navigation robotino = new Navigation();
		 
		try
		{
			//this.connect(hostname, true);
			this.init();
			this.initPos();
			this.FollowWalls();
			
			//this.disconnect();
		
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			LOGGER.log(Level.SEVERE,e.toString());
		}
	}
}





    
    
    
    
    
    
    
    
 
