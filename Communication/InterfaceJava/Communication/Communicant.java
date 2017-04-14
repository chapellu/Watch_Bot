package Communication;

public class Communicant {
	private String nom;
	private String IP;
	
	public Communicant (String nom, String ip){
		this.nom = nom;
		IP = ip;
	}

	public String getNom() {
		return nom;
	}

	public String getIP() {
		return IP;
	}

	@Override
	public String toString() {
		return "Communicant [nom=" + nom + ", IP=" + IP + "]";
	}
	
	
}
