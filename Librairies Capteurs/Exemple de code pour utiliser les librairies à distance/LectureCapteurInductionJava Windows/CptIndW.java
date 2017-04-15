
public class CptIndW {
	
	public native float valeurCptIndW(int numPin, String hostname);

	static {
		System.loadLibrary("rec_robotino_com_c"); 	//Appel de la librairie n�cessaires pour utiliser les objets de Robotino
		System.loadLibrary("CptIndW");		//Notre librairie pour appeler la m�thode en C qui nous retournera la valeur des capteurs � Induction
	}
}
