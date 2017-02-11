package Communication;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class InterfaceCommunication{
	
	private String ip;  
	private String nom;
	private BaseDeDonnee bd;
	private int portServeur = 50001;
	private static Thread t;
	private static InterfaceCommunication instance = null;
	private ServerSocket socketserver;
	
	private InterfaceCommunication(){
		// On recupere l'adresse IP de la machine afin de definir celui qui envoi le message
		// On se connecte à la base afin de connaitre son nom
		try {
			ip = InetAddress.getLocalHost ().getHostAddress ();
			bd = BaseDeDonnee.connect();
			nom = bd.getNom(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			ip = "0.0.0.0";
			nom = "erreur";
			e.printStackTrace();
		}
	}
	
	// Utilisation d'un Singleton cela permet de rendre unique l'interface et la connexion à la BD
	public static InterfaceCommunication newInterfaceCommunication(){
		if(instance==null){
			instance = new InterfaceCommunication();
		}
		return(instance);
	}
	
	// Methode pour envoyer un message. Le destinataire est ici connu par son nom
	public boolean sendMessage(String destinataire, String type, String message){
		//System.out.println("Envoi...");
		PrintWriter out = null;
		String ipDest = bd.getIP(destinataire);
		Message mes = new Message(nom, ip, destinataire, ipDest, type, message);
		String messageJson = crypterMessage(mes);
		
        try {
			Socket socket = new Socket(ipDest, portServeur);
			out = new PrintWriter(socket.getOutputStream());
			out.println(messageJson);
			out.flush();
			socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	// Methode pour envoyer un message. Le destinataire est ici connu par son IP
	public boolean sendMessageIP(String ipDest, String type, String message){
		//System.out.println("Envoi...");
		PrintWriter out = null;
		String destinataire = bd.getNom(ipDest);
		Message mes = new Message(nom, ip, destinataire, ipDest, type, message);
		String messageJson = crypterMessage(mes);
		
        try {
			Socket socket = new Socket(ipDest, portServeur);
			out = new PrintWriter(socket.getOutputStream());
			out.println(messageJson);
			out.flush();
			socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	// Cette methode permet a une application de recevoir des message a condition qu'elle  implements InterfaceMessageRecu
	public void startEcoute(InterfaceMessageRecu abonne){
		try {
			socketserver = new ServerSocket(portServeur);
			t = new Thread(new Accepter_connexion(socketserver,abonne));
			t.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Cette methode ferme l'ecoute. L'application ne peut plus recevoir de message.
	// Surement à modifié, fermeture des threads ?
	public void closeEcoute(){
		try {
			socketserver.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Cette methode permet de transformer un Message en Json puis celui-ci en String afin d'être envoyé
	public String crypterMessage(Message mes){
		GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return(gson.toJson(mes));
	}
	
	/*
	 * Methode a rediger mais surtout à définir plus particulièrement
	public Message decrypterMessage(String mes){
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return(gson.toJson(mes));
	}

	public void newMessageRecu(String mess) {
		System.out.println(mess);
		decrypterMessage(mess);
	}
	*/
}
