package Communication;

import java.util.ArrayList;

public class Message {
	
	private Communicant AuteurPrecedent;
	private Communicant Destinataire;
	private DateJson Date;
	private TypeMessage type;
	private String message;
		
	public Message(String auteur, String ipAuteur, String destinataire, String ipDest, String type, String message){
		TypeMessage t;
		try {
			t = TypeMessage.valueOf(type); // Convertir mon String type en enum typeMessage
		}
		catch (IllegalArgumentException e) {
			t = TypeMessage.valueOf("Inconnu");
		}
		
		AuteurPrecedent = new Communicant(auteur, ipAuteur);
		Destinataire = new Communicant (destinataire, ipDest);
		Date = new DateJson();
		this.type = t;
		this.message = message;
	}
	
}
