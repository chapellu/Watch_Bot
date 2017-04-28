import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;

import javax.swing.JSpinner.ListEditor;

import rec.robotino.com.Bumper;
import rec.robotino.com.Com;
import rec.robotino.com.DistanceSensor;
import rec.robotino.com.Motor;
import rec.robotino.com.OmniDrive;


/**
 * The class Robot demonstrates the usage of the most common robot component classes.
 * Furthermore it shows how to handle events and receive incoming camera images.
 */
public class Robot
{
	
	protected final Com _com;                    // port
	protected final OmniDrive _omniDrive;         // motorisation directionnelle
	protected final Bumper _bumper;
	protected final List<DistanceSensor> _distanceSensors;
	
	protected final float SLOW_VELOCITY = 0.08f;
	protected final float MEDIUM_VELOCITY = 0.16f;
	protected final float VELOCITY = 0.24f;
	protected final float FAST_VELOCITY = 0.32f;
	protected final float ANGULARVELOCITY = 0.02f;
	static ArrayList<Float> capteurs = new ArrayList<Float>();
	
	public ArrayList<Float> getCapteurs() {
		return capteurs;
	}

	public void setCapteurs(ArrayList<Float> capteurs) {
		this.capteurs = capteurs;
	}

	public Robot()
	{
		_com = new MyCom();
		_omniDrive = new OmniDrive();
		_bumper = new Bumper();
		_distanceSensors = new ArrayList<DistanceSensor>();
		
		init();
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
    			System.out.println(i + ":" + capteurs.get(i)+ " ");  // retourne moyenne distance
    		}
    }


    
    
    public void initPos() throws InterruptedException // Positionnement initial du robot
    {
    	float x_dir = 0.0f;			 // + => avance sinon recule
    	float y_dir = 0f;		     // + => gauche sinon droite
    	boolean once = true;		 // Dans l'avancement du debut, permet de le faire qu'une seule fois
    	
    	getCapteursValue(capteurs);
   
		//-----------------------------------------------------------//
		//        Le robot avance jusqu'au mur pour s'arrêter        //
		//-----------------------------------------------------------//
    	while(capteurs.get(0)<2.0f && once){
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
    	
    	System.out.println("la valeur de once est = " + once);
    	_omniDrive.setVelocity ( 0, 0 , 0.0f );

		//-----------------------------------------------------------//
		//              Le robot tourne pour se placer               //
    	//-----------------------------------------------------------//

    	
		getCapteursValue(capteurs);	
		float decalage13 = 0.7519738f;	
		float epsilon13 =0.1f;
		System.out.println((decalage13 + capteurs.get(1) - epsilon13) + " < " + capteurs.get(3) + " < " + (decalage13 + capteurs.get(1)+ epsilon13));
    	while(!(((decalage13 + capteurs.get(1) - epsilon13) < capteurs.get(3)) && capteurs.get(3) < (decalage13 + capteurs.get(1)+ epsilon13)))
    	{
    		System.out.println((decalage13 + capteurs.get(1) - epsilon13) + " < " + capteurs.get(3) + " < " + (decalage13 + capteurs.get(1)+ epsilon13));
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
	
	getCapteursValue(capteurs);
	
while(true){	
	getCapteursValue(capteurs);
	if(capteurs.get(1)>0.2f){	
		while(capteurs.get(1)>0.2f){
			getCapteursValue(capteurs);	
	
			
			/*On distingue deux if, le premier permet de maintenir une trajectoire droite, le second possède une correction linéaire en rotation*/ 
			
			if((decalage13 + capteurs.get(2) - epsilon13) < capteurs.get(3) && capteurs.get(3) < (decalage13 + capteurs.get(1)+ epsilon13)){
			getCapteursValue(capteurs);	
			x_dir = 100.0f*(2.0f-capteurs.get(0));
			y_dir = 0;
			
			System.out.println("STEP 1");
			System.out.println((decalage13 + capteurs.get(1) - epsilon13) + "<" + capteurs.get(3) + "<" + (decalage13 + capteurs.get(1)+ epsilon13));
			_omniDrive.setVelocity ( x_dir, y_dir ,0f);
			}
			
			
			if(!((decalage13 + capteurs.get(1) - epsilon13) < capteurs.get(3) && capteurs.get(3) < (decalage13 + capteurs.get(1)+ epsilon13))){
			getCapteursValue(capteurs);	
			x_dir = 100.0f*(2.0f-capteurs.get(0));
			y_dir = 0;
			
			System.out.println("STEP 2");
			System.out.println((decalage13 + capteurs.get(1) - epsilon13) + "<" + capteurs.get(3) + "<" + (decalage13 + capteurs.get(1)+ epsilon13));
			_omniDrive.setVelocity ( x_dir, y_dir , 50*(0.9f - capteurs.get(1)) ); 
			}	
			
			/* Tourner a droite, vide*/ 
			if(capteurs.get(0) > 1.3f){
				System.out.println("STEP HHHHHHHHHHHHHHHHHHOOOOOOOOOOOOOCCCCCCCCKKKKKKKKKKEEEEEEEEEEEEEYYYYYYYYYY");

				_omniDrive.setVelocity ( 0, 0, 0); 
				getCapteursValue(capteurs);
				System.out.println("STEP 100000000000");
				while((capteurs.get(1) < 0.8f && capteurs.get(3)<1.5f) || capteurs.get(4) < 2.0f){
					getCapteursValue(capteurs);	
					System.out.println("STEP 2222222222222222222222");
					_omniDrive.setVelocity ( 0, 0, -25.0f );
						}
					}
				}
		}
				


	/* Tourner à gauche */ 
	if(capteurs.get(1)<=0.2f){	
		getCapteursValue(capteurs);
		while(capteurs.get(1)<0.2f){
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
	
		
	







/*************************************Programme Principal**********************************/


public static void main(String[] args) throws Exception
{
	String hostname = System.getProperty("localhost", "193.48.125.37");

	Robot robotino = new Robot();
	 
	
	try
	{
		robotino.connect(hostname, true);
		robotino.getCapteursValue(capteurs);	
		robotino.initPos();
		robotino.FollowWalls();
		
		robotino.disconnect();
	
}
	catch (Exception e)
	{
		System.out.println(e.toString());
	}
	
	
	
}}



    
    
    
    
    
    
    
    
 
