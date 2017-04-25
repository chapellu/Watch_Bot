package Communication;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
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
		// Acces base de donnee
	};
	
	public static InterfaceCommunication newInterfaceCommunication(){
		if(instance==null){
			instance = new InterfaceCommunication();
		}
		return(instance);
	}
	
	public boolean sendMessage(String destinataire, String type, String message){
		String nomDest = null;
		String ipDest = null;
		Message mes;
		System.out.println("Envoi...");
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
		String messageJson = crypterMessage(mes);
        try {
        	System.out.println("---------------ip: "+ipDest);
			Socket socket = new Socket(ipDest, portServeur);
			out = new PrintWriter(socket.getOutputStream());
			out.println(messageJson);
			out.flush();
			System.out.println(socket.getRemoteSocketAddress());
			System.out.println(messageJson);
			System.out.println(messageJson.length());
			
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
	
	public void closeEcoute(){
		try {
			Accepter_connexion.arret();
			socketserver.close();
			System.out.println("Serveur closed " + socketserver.isClosed());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
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
		System.out.println(mess);
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
