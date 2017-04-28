package watchbot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import Communication.InterfaceCommunication;
import Communication.InterfaceMessageRecu;
import Communication.Message;
import Communication.Ordre;



public class Watchbot implements InterfaceMessageRecu{
	private static Watchbot instance = null;
	private Etat etat = Etat.Repos;
	private boolean utilisateurPresent = true;
	private static final Logger LOGGER = Logger.getLogger( Watchbot.class.getName() );
	private static FileHandler fileTxt;
	
	private Watchbot(){
		try {
			fileTxt = new FileHandler("Watchbot.txt");
			LOGGER.addHandler(fileTxt);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static Watchbot create(){
		if (instance == null){
			LOGGER.log(Level.INFO, "Creating instance of Watchbot");
			instance = new Watchbot();}
		LOGGER.log(Level.INFO, "Returning instance of Watchbot");
		return(instance);
		}
		
	public void startSurveillance(String seuil){
		//if (etat==Etat.Repos) {
			String fichier ="/var/www/html/Watch_Bot/scriptsD6T/flagscript.txt";

	        //creation ou ajout dans le fichier texte
	        try {
	            FileWriter fw = new FileWriter (fichier);
	            BufferedWriter bw = new BufferedWriter (fw);
	            PrintWriter fichierSortie = new PrintWriter (bw);	            
	            fichierSortie.println ("script=True"+"\n"+seuil);
	            fichierSortie.close();
	        }
	        catch (Exception e){
	            System.out.println(e.toString());
	        }
	        
	        //Lancement du script 
	        try {
	        	Process p = Runtime.getRuntime().exec("");				
	        	etat=Etat.Surveillance;
				LOGGER.log(Level.FINE, "Surveillance started");
	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
		//}
	}
	
	public void stopSurveillance(){
		//if (etat==Etat.Surveillance) {
	        String fichier ="/var/www/html/Watch_Bot/scriptsD6T/flagscript.txt";

			 //création ou ajout dans le fichier texte
	        try {
	            FileWriter fw = new FileWriter (fichier);
	            BufferedWriter bw = new BufferedWriter (fw);
	            PrintWriter fichierSortie = new PrintWriter (bw);
	            fichierSortie.println ("script=False"+"\n"+"0");
	            fichierSortie.close();
	            etat=Etat.Repos;
				LOGGER.log(Level.FINE, "Surveillance stopped");
	        }
	        catch (Exception e){
	            System.out.println(e.toString());
	        }
			
		//}
	}
	
	public void startCartographie(){
		if (etat==Etat.Repos) {
			etat=Etat.Cartographie;
			LOGGER.log(Level.INFO, "Cartographie started");
		}
	}
	
	public void stopCartographie(){
		if (etat==Etat.Cartographie) {
			etat=Etat.Repos;
			LOGGER.log(Level.INFO, "Cartographie stopped");
		}
	}

	@Override
	public void newMessageRecu(Message mes) {
		switch (mes.getType()){
		case Ordre:
			handleOrder(mes);
			break;
		case Message:
			handleMessage(mes);
			break;
		case Erreur:
			handleErreur(mes);
			break;
		case Inconnu:
			handleInconnu(mes);
			break;
		case Requete:
			handleRequete(mes);
			break;
		default:
			break;
		}
		LOGGER.log(Level.SEVERE,mes.getMessage());
		
	}
	
	private void handleOrder(Message mes) {
		try { 
			//On regarde si on a des arguments
			Ordre ordre;
			String arg = "";
			if(mes.getMessage().contains(":")){
				ordre = Ordre.valueOf(mes.getMessage().split(":")[0]);
				arg = mes.getMessage().split(":")[1];
			} else {
				ordre = Ordre.valueOf(mes.getMessage());
			}
			
			//On lance la m
			switch(ordre){
			case startSurveillance:
				startSurveillance(arg);
				break;
			case stopSurveillance:
				stopSurveillance();
				break;
			case startCartographie:
				startCartographie();
				break;
			case stopCartographie:
				stopCartographie();
				break;
			case avance:
				break;
			case recule:
				break;
			case gauche:
				break;
			case droite:
				break;
			default:
				LOGGER.log(Level.INFO, mes.toString());
				break;
			}
		}
		catch (Exception e){LOGGER.log(Level.SEVERE,e.toString(),e);};
		
		
	}
	
	private void handleMessage(Message mes) {
		if (mes.getMessage().equals("leaving")){
			utilisateurPresent = false;
			LOGGER.log(Level.FINE, "false");
		}
		else if (mes.getMessage().equals("coming")){
			utilisateurPresent = true;
			LOGGER.log(Level.FINE, "true");
		}
		else{
			LOGGER.log(Level.FINE, "handleMessage error");
		}
		
	}

	
	
	private void handleErreur(Message mes) {
		// TODO Auto-generated method stub
		
	}

	
	
	private void handleInconnu(Message mes) {
		// TODO Auto-generated method stub
		
	}

	

	private void handleRequete(Message message) {
		// TODO Auto-generated method stub
		String mes = message.getMessage();
		InterfaceCommunication com = InterfaceCommunication.newInterfaceCommunication();
		
		if(InterfaceCommunication.validate(mes)){
			LOGGER.log(Level.INFO, "ip: "+mes+" \n");
			LOGGER.log(Level.INFO, com.getBd().getNom(mes)+" \n");
			com.sendMessage("Nao Orange", "Message", com.getBd().getNom(mes));
		}
		else{
			LOGGER.log(Level.INFO, "nom destinataire: "+mes +" \n");
			LOGGER.log(Level.INFO, com.getBd().getIP(mes));
			com.sendMessage("Nao Orange", "Message", com.getBd().getIP(mes));
		}
	}

	
}
