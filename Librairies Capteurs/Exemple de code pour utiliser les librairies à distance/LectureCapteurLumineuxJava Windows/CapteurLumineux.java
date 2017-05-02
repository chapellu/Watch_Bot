public class CapteurLumineux {
	
	public native boolean valeurCapteurLumineux(int numPin);
	 
	/*static {
		System.loadLibrary("rec_robotino_com_c");
		System.loadLibrary("CapteurLumineux");
	}*/

	/*public static void main(String[] args) {
		boolean valeurCptLumineux5;
		boolean valeurCptLumineux6;
		CapteurLumineux classeValeurCptLumineux = new CapteurLumineux();
		
		
		valeurCptLumineux5 = classeValeurCptLumineux.valeurCapteurLumineux(5);
		valeurCptLumineux6 = classeValeurCptLumineux.valeurCapteurLumineux(6);
		if(valeurCptLumineux5 && valeurCptLumineux6){
			System.out.println("Detection vide 2 capteurs");
		}
		else if(valeurCptLumineux5 || valeurCptLumineux6){
			System.out.println("Detection vide 1 capteurs");
		}
		else{
			System.out.println("Sol");
		}
	}*/
}

