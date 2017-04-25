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
			t = TypeMessage.valueOf("Inconnu : " + type);
		}
		
		AuteurPrecedent = new Communicant(auteur, ipAuteur);
		Destinataire = new Communicant (destinataire, ipDest);
		Date = new DateJson();
		this.type = t;
		if (t==TypeMessage.Ordre){
			try {
				this.message = Ordre.valueOf(message).toString(); // Convertir mon String type en enum typeMessage
			}
			catch (IllegalArgumentException e) {
				this.message = "Inconnu : " + message;
			}
		}
		this.message = message;
	}

	public Communicant getAuteurPrecedent() {
		return AuteurPrecedent;
	}

	public Communicant getDestinataire() {
		return Destinataire;
	}

	public DateJson getDate() {
		return Date;
	}

	public TypeMessage getType() {
		return type;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "Message [AuteurPrecedent=" + AuteurPrecedent
				+ ", Destinataire=" + Destinataire + ", Date=" + Date
				+ ", type=" + type + ", message=" + message + "]";
	}
	
	
}
