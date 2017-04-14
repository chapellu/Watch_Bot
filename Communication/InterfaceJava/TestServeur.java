package Communication;

public class TestServeur implements InterfaceMessageRecu{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InterfaceCommunication com = InterfaceCommunication.newInterfaceCommunication();
		//com.startEcoute(com);
		//com.sendMessage("Ordinateur 0", "Ordre", "Plop");
		//com.sendMessage("Nao orange", "Ordre", "Plop");
		com.sendMessage("193.48.125.63", "Ordre", "Plop");
		//com.closeEcoute();
		//com.sendMessage("Robotino Standard", "Ordre", "Plop");
		
		//com.closeEcoute();
	}

	@Override
	public void newMessageRecu(String s) {
		// TODO Auto-generated method stub
		
	}

}
