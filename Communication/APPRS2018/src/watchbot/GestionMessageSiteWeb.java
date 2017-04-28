package watchbot;


public class GestionMessageSiteWeb {
	 
	public static void main(String[] args) {
		Watchbot watchbot = Watchbot.create();
		try { 
			switch(args[0]){
			case "startSurveillance":
				watchbot.startSurveillance();
				break;
			case "stopSurveillance":
				watchbot.stopSurveillance();
				break;
			case "startCartographie":
				watchbot.startCartographie();
				break;
			case "stopCartographie":
				watchbot.stopCartographie();
				break;
			case "avance":
				break;
			case "recule":
				break;
			case "gauche":
				break;
			case "droite":
				break;
			default:
				System.out.println(args[0]);
				break;
			}
	}
	catch (Exception e){};
}
}
