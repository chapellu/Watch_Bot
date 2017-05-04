package Communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Accepter_connexion implements Runnable {

	private Socket socket = null;
	private ServerSocket socketserver = null;
	public Thread t1;
	private InterfaceMessageRecu abonne;
	private static boolean running = true;
	private static final Logger LOGGER = Logger.getLogger( Accepter_connexion.class.getName() );
	private static FileHandler fileTxt;
	
	public Accepter_connexion(ServerSocket ss, InterfaceMessageRecu abonne){
		 socketserver = ss;
		 this.abonne = abonne;
		 try {
			fileTxt = new FileHandler("Accepter_connexion.txt");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 LOGGER.addHandler(fileTxt);
		}
	
	public void run() {
		try {
            while(running){
	            socket = socketserver.accept();
	            LOGGER.log(Level.FINE,"Incomming message");
	            t1 = new Thread(new MessageEntrant(socket,abonne));
	            t1.start();
	            Thread.currentThread();
				Thread.yield();
            }
        } catch (IOException e) {
        	LOGGER.log(Level.SEVERE, e.toString(), e);
    }
	}
	
	public static void arret(){
		running = false;
	}
	

}
