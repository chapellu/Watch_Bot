package watchbot;

import java.io.IOException;
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
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 LOGGER.addHandler(fileTxt);
	}
	
	public static Watchbot create(){
		if (instance == null){
			LOGGER.log(Level.FINE, "Creating instance of Watchbot");
			instance = new Watchbot();}
		LOGGER.log(Level.FINE, "Returning instance of Watchbot");
		return(instance);
		}
		
	public void startSurveillance(){
		if (etat==Etat.Repos) {
			etat=Etat.Surveillance;
			LOGGER.log(Level.FINE, "Surveillance started");
		}
	}
	
	public void stopSurveillance(){
		if (etat==Etat.Surveillance) {
			etat=Etat.Repos;
			LOGGER.log(Level.FINE, "Surveillance stopped");
		}
	}
	
	public void startCartographie(){
		if (etat==Etat.Repos) {
			etat=Etat.Cartographie;
			LOGGER.log(Level.FINE, "Cartographie started");
		}
	}
	
	public void stopCartographie(){
		if (etat==Etat.Cartographie) {
			etat=Etat.Repos;
			LOGGER.log(Level.FINE, "Cartographie stopped");
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
		LOGGER.log(Level.WARNING,mes.getMessage());
		
	}
	
	private void handleOrder(Message mes) {
		try { 
			switch(Ordre.valueOf(mes.getMessage())){
			case startSurveillance:
				startSurveillance();
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
				LOGGER.log(Level.FINE, mes.toString());
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
			LOGGER.log(Level.FINE, "ip: "+mes+" \n");
			LOGGER.log(Level.FINE, com.getBd().getNom(mes)+" \n");
			com.sendMessage("Nao Orange", "Message", com.getBd().getNom(mes));
		}
		else{
			LOGGER.log(Level.FINE, "nom destinataire: "+mes +" \n");
			LOGGER.log(Level.FINE, com.getBd().getIP(mes));
			com.sendMessage("Nao Orange", "Message", com.getBd().getIP(mes));
		}
	}

	
}
