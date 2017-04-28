public class CapteurInduction {
	
	public native float valeurCapteurInduction(int numPin);
	 

	static {
		//System.loadLibrary("rec_core_lt");
		//System.loadLibrary("rec_robotino_com");
		System.loadLibrary("rec_robotino_com_c");
		//System.loadLibrary("robotinocom");
		System.loadLibrary("CapteurInduction");

	}

	public static void main(String[] args) {
		float valeurCptInduction;
		CapteurInduction classeValeurCptInduction = new CapteurInduction();
		
		
		valeurCptInduction = classeValeurCptInduction.valeurCapteurInduction(2);		
		System.out.println("2: " + valeurCptInduction);
	}
}

