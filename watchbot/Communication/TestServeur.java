package Communication;

public class TestServeu implements InterfaceMessageRecu{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InterfaceCommunication com = InterfaceCommunication.newInterfaceCommunication();
		com.startEcoute(com);
		com.sendMessage("Ordinateur", "Ordre", "Plop");
		//com.sendMessage("Robotino Standard", "Ordre", "Plop");
		
		//com.closeEcoute();
	}

	

}
