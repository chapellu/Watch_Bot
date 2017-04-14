package watchbot;

import Communication.InterfaceCommunication;
import Communication.InterfaceMessageRecu;

public class serveur {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Watchbot watchbot = Watchbot.create();
		InterfaceCommunication com = InterfaceCommunication.newInterfaceCommunication();
		com.startEcoute(watchbot);
		com.sendMessage("193.48.125.65", "Ordre", "startSurveillance");
	}

}
