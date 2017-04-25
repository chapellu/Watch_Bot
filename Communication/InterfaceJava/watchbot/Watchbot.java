package watchbot;

import Communication.InterfaceMessageRecu;
import Communication.Message;
import Communication.Ordre;



public class Watchbot implements InterfaceMessageRecu{
	private static Watchbot instance = null;
	private Etat etat = Etat.Repos;
	
	private Watchbot(){
		
	}
	
	public static Watchbot create(){
		if (instance == null){instance = new Watchbot();}
		return(instance);
		}
		
	public void startSurveillance(){
		if (etat==Etat.Repos) {
			etat=Etat.Surveillance;
			System.out.println("surveillance started");
		}
	}
	
	public void stopSurveillance(){
		if (etat==Etat.Surveillance) {
			etat=Etat.Repos;
			System.out.println("surveillance stopped");
		}
	}
	
	public void startCartographie(){
		if (etat==Etat.Repos) {
			etat=Etat.Cartographie;
			System.out.println("Cartographie started");
		}
	}
	
	public void stopCartographie(){
		if (etat==Etat.Cartographie) {
			etat=Etat.Repos;
			System.out.println("Cartographie stopped");
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
		System.out.println(mes.getMessage());
		
	}
	
	private void handleOrder(Message mes) {
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
			break;
		}
		
	}
	
	private void handleMessage(Message mes) {
		// TODO Auto-generated method stub
		
	}

	
	
	private void handleErreur(Message mes) {
		// TODO Auto-generated method stub
		
	}

	
	
	private void handleInconnu(Message mes) {
		// TODO Auto-generated method stub
		
	}

	

	private void handleRequete(Message mes) {
		// TODO Auto-generated method stub
		
	}

	
}
