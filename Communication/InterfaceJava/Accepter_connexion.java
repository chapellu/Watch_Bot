package Communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Accepter_connexion implements Runnable {

	private Socket socket = null;
	private ServerSocket socketserver = null;
	public Thread t1;
	private InterfaceMessageRecu abonne;
	
	public Accepter_connexion(ServerSocket ss, InterfaceMessageRecu abonne){
		 socketserver = ss;
		 this.abonne = abonne;
		}
	
	public void run() {
		try {
            while(true){
	            socket = socketserver.accept();
	            t1 = new Thread(new MessageEntrant(socket,abonne));
	            t1.start();
            }
        } catch (IOException e) {
        	e.printStackTrace();
            System.err.println("Erreur serveur");
        
    }
	}

}
