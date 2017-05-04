package Communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MessageEntrant implements Runnable{

	private Socket socket;
	private BufferedReader in = null;
	private InterfaceMessageRecu abonne;
	private static final Logger LOGGER = Logger.getLogger( MessageEntrant.class.getName() );
	private static FileHandler fileTxt;
	
	public MessageEntrant(Socket s, InterfaceMessageRecu abonne){
		socket = s;
		this.abonne =abonne;
		try {
			fileTxt = new FileHandler("MessageEntrant.txt");
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
		// TODO Auto-generated method stub
		try {
			LOGGER.log(Level.INFO,"Receiving message...");
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			Gson gson = new GsonBuilder().create();
			String s = in.readLine();
			Message mes = gson.fromJson(s,Message.class);
			if (mes.getType()==TypeMessage.Requete){
				LOGGER.log(Level.INFO,"Requete from Nao : " + mes);
				String res = handleRequete(mes);
				LOGGER.log(Level.INFO,"Sending back to Nao : " + res);
				PrintWriter out = new PrintWriter(socket.getOutputStream());
				out.println(res);
				out.flush();
			}
			else {
				abonne.newMessageRecu(mes);
			}
			
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,e.toString(),e);
		}
		
	}
	
	private String handleRequete(Message message) {
		// TODO Auto-generated method stub
		String mes = message.getMessage();
		InterfaceCommunication com = InterfaceCommunication.newInterfaceCommunication();
		
		if(InterfaceCommunication.validate(mes)){
			return( com.getBd().getNom(mes));
		}
		else{
			return( com.getBd().getIP(mes));
		}
	}

}
