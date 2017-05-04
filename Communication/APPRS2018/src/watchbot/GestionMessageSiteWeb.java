package watchbot;

import Communication.InterfaceCommunication;

public class GestionMessageSiteWeb {
	private static InterfaceCommunication com = InterfaceCommunication.newInterfaceCommunication();
	
	public static void main(String[] args) {
		com.sendMessage(args[0],args[1],args[2]);
	}


}
