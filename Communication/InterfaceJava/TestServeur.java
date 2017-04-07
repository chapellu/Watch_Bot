package Communication;

public class TestServeur implements InterfaceMessageRecu{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InterfaceCommunication com = InterfaceCommunication.newInterfaceCommunication();
		com.startEcoute(this);
		com.sendMessage("Ordinateur", "Ordre", "Plop");
		//com.sendMessage("Robotino Standard", "Ordre", "Plop");
		
		//com.closeEcoute();
	}

	@Override
	public void newMessageRecu(String s) {
		// TODO Auto-generated method stub
		
	}

}
