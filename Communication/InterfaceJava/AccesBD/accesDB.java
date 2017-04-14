package AccesBD;

import Communication.InterfaceCommunication;
import Communication.InterfaceMessageRecu;
import Communication.Message;

public class accesDB implements InterfaceMessageRecu{	
	static InterfaceCommunication com = InterfaceCommunication.newInterfaceCommunication();	
	
	@Override
	public void newMessageRecu(Message mes) {
		// TODO Auto-generated method stub
		handleRequete(mes);
	}
	
	public void handleRequete(Message message){
		// TODO Auto-generated method stub
		String mes = message.getMessage();
		
		if(InterfaceCommunication.validate(mes)){
			System.out.println("ip: "+mes+" \n");
			System.out.println(com.getBd().getNom(mes)+" \n");
			com.sendMessage("Nao Orange", "Message", com.getBd().getNom(mes));
		}
		else{
			System.out.println("nom destinataire: "+mes +" \n");
			System.out.println(com.getBd().getIP(mes));
			com.sendMessage("Nao Orange", "Message", com.getBd().getIP(mes));
		}
	}

	

	
	





}
