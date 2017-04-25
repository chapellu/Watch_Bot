package Communication;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class InterfaceCommunication implements InterfaceMessageRecu{
	
	private String ip;  
	private String nom;
	private BaseDeDonnee bd;
	private int portServeur = 50003;
	public static Thread t;
	private static InterfaceCommunication instance = null;
	private ServerSocket socketserver;
	static private final String IPV4_REGEX = "(([0-1]?[0-9]{1,2}\\.)|(2[0-4][0-9]\\.)|(25[0-5]\\.)){3}(([0-1]?[0-9]{1,2})|(2[0-4][0-9])|(25[0-5]))";
	static private Pattern PATTERN = Pattern.compile(IPV4_REGEX);
	private static final Logger LOGGER = Logger.getLogger( InterfaceCommunication.class.getName() );
	private static FileHandler fileTxt;
	
	private InterfaceCommunication(){
		// On recupere l'adresse IP de la machine afin de definir celui qui envoi le message
		try {
			ip = InetAddress.getLocalHost ().getHostAddress ();
			bd = BaseDeDonnee.connect();
			nom = bd.getNom(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fileTxt = new FileHandler("InterfaceCommunication.txt");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 LOGGER.addHandler(fileTxt);
		
		// Acces base de donnee
	};
	
	public static InterfaceCommunication newInterfaceCommunication(){
		if(instance==null){
			LOGGER.log(Level.FINE, "Creating instance of InterfaceCommunication");
			instance = new InterfaceCommunication();
		}
		LOGGER.log(Level.FINE, "Returning instance of InterfaceCommunication");
		return(instance);
	}
	
	public boolean sendMessage(String destinataire, String type, String message){
		String nomDest = null;
		String ipDest = null;
		Message mes;
		PrintWriter out = null;
		if(validate(destinataire)){
			nomDest = bd.getNom(destinataire);
			ipDest=destinataire;
			mes = new Message(nom, ip, nomDest, ipDest, type, message);
		}
		else{
			nomDest = destinataire;
			ipDest = bd.getIP(destinataire);
			mes = new Message(nom, ip, nomDest, ipDest, type, message);
		}
		LOGGER.log(Level.INFO, "Sending : " + mes);
		String messageJson = crypterMessage(mes);
        try {
			Socket socket = new Socket(ipDest, portServeur);
			out = new PrintWriter(socket.getOutputStream());
			out.println(messageJson);
			out.flush();
//			System.out.println(socket.getRemoteSocketAddress());
//			System.out.println(messageJson);
//			System.out.println(messageJson.length());
			
			socket.close();
		} catch (UnknownHostException e) {
			LOGGER.log(Level.SEVERE,e.toString(),e);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,e.toString(),e);
		}
		return true;
	}
	
	public void startEcoute(InterfaceMessageRecu abonne){
		try {
			socketserver = new ServerSocket(portServeur);
			LOGGER.log(Level.INFO,"listening on port " + portServeur);
			t = new Thread(new Accepter_connexion(socketserver,abonne));
			t.start();
            Thread.currentThread();
			Thread.yield();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,e.toString(),e);
		}
		
	}
	
	public void closeEcoute(){
		try {
			LOGGER.log(Level.INFO,"Serveur closed " + socketserver.isClosed());
			Accepter_connexion.arret();
			socketserver.close();			
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,e.toString(),e);
		}
	}
	
	public String crypterMessage(Message mes){
		GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return(gson.toJson(mes));
	}
	
	
	public void decrypterMessage(String mes){
		/*
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return(gson.toJson(mes));
		*/
		System.out.println("Recu " + mes);
	}

	public void newMessageRecu(Message mess) {
		LOGGER.log(Level.INFO,mess.toString());
		decrypterMessage(mess.toString());
	}

	public static boolean validate(final String s) {
	    return PATTERN.matcher(s).matches();
	}

	public String getIp() {
		return ip;
	}


	public String getNom() {
		return nom;
	}


	public BaseDeDonnee getBd() {
		return bd;
	}


	public int getPortServeur() {
		return portServeur;
	}

	public void setPortServeur(int portServeur) {
		this.portServeur = portServeur;
	}

	public static InterfaceCommunication getInstance() {
		return instance;
	}


	public ServerSocket getSocketserver() {
		return socketserver;
	}

	
	
}
