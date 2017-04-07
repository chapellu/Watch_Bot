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
	public static Thread t;
	private static InterfaceCommunication instance = null;
	private ServerSocket socketserver;
	
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
		System.out.println("Envoi...");
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
			socketserver.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String crypterMessage(Message mes){
		GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return(gson.toJson(mes));
	}
	
	/*
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
