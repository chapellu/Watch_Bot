package Communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MessageEntrant implements Runnable{

	private Socket socket;
	private BufferedReader in = null;
	private InterfaceMessageRecu abonne;
	
	public MessageEntrant(Socket s, InterfaceMessageRecu abonne){
		socket = s;
		this.abonne =abonne;
	}
	public void run() {
		// TODO Auto-generated method stub
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			abonne.newMessageRecu(in.readLine());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
