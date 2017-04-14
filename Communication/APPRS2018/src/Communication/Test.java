package Communication;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		InterfaceCommunication com = InterfaceCommunication.newInterfaceCommunication();
		com.sendMessage("Ordinateur", "Ordre", "Plop");
		//com.sendMessage("Nao Gris", "Ordre", "Plop");
		//BaseDeDonnee bd = BaseDeDonnee.connect();
		//System.out.println(bd.getIP("Nao Gris"));
		//System.out.println(bd.getNom("193.48.125.62"));
	}
}
