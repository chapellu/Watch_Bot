
public class CptLumW {
	
	public native boolean valeurCptLumW(int numPin, String hostname);
	
	static {
		System.loadLibrary("rec_robotino_com_c");
		System.loadLibrary("CptLumW");
	}
	
	
}
