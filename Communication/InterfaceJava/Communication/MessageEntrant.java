package Communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
			Gson gson = new GsonBuilder().create();
			Message mes = gson.fromJson(in.readLine(),Message.class);
			abonne.newMessageRecu(mes);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
