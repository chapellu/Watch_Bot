package Main;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import Communication.InterfaceCommunication;

import cartographie.Navigation;
import rec.robotino.com.*;
import watchbot.Etat;



public class Robot {
	
	private  Com _com; 
	private  OmniDrive _omniDrive;       
	private  Bumper _bumper;
	private  final Logger LOGGER = Logger.getLogger( Robot.class.getName() );
	private  FileHandler fileTxt;
	private Etat etat = Etat.Repos;
	private Navigation robotino;
	
	static {
		//Chargement des bibliothèques nécessaires pour utiliser les capteurs à induction et lumineux
		System.loadLibrary("rec_robotino_com_c");
		System.loadLibrary("CapteurInduction");
		System.loadLibrary("CapteurLumineux");
	}

	
	public Robot (){
		_com = new Com();
		_omniDrive = new OmniDrive();
		_bumper = new Bumper();
		robotino = new Navigation(_com, _omniDrive, _bumper);
		// carto to add
		try {
			fileTxt = new FileHandler("Robot.txt");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 LOGGER.addHandler(fileTxt);
		
		String hostname = System.getProperty("localhost", "127.0.0.1");
		connect(hostname, true);
		
		MessageManager bird = new MessageManager(this);
		InterfaceCommunication netCom = InterfaceCommunication.newInterfaceCommunication();
		netCom.startEcoute(bird);
	}
	
	public static void main(String[] args) {
		Robot robotino = new Robot();
	}
	
		
	public boolean isConnected()
	{
		return _com.isConnected();
	}

	public void connect(String hostname, boolean block)
	{
		LOGGER.log(Level.INFO,"Connecting...");
		_com.setAddress(hostname);
		_com.connect(block);;
	}

	public void disconnect()
	{
		_com.disconnect();
	}
	
	public void startCartographie(){
		etat = Etat.Cartographie;
		robotino.modeSuiviMur();
	}
	
	public void stopCartographie(){
		etat = Etat.Repos;
		try {
			robotino.stopFollowWalls();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startSurveillance(){
		etat = Etat.Surveillance;
	}
	
	public void stopSurveillance(){
		etat = Etat.Repos;
	}
	
	public void manuel(String ordre){
		if(ordre.equals("avancer") || ordre.equals("avance"))
		{
			try {
				robotino.avance(4);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(ordre.equals("reculer") || ordre.equals("recule"))
		{
			try {
				robotino.avance(-4);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(ordre.equals("droite") || ordre.equals("tourner"))
		{
			try {
				robotino.rotate(1,false);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}     
		else if(ordre.equals("gauche"))
		{
			try {
				robotino.rotate(1,true);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		else if(ordre.equals("arret"))
		{
			_omniDrive.setVelocity(0, 0, 0.0f);
			try {
				Thread.sleep(50);
				_omniDrive.setVelocity(0, 0, 0.0f);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}   
    }
	
}
