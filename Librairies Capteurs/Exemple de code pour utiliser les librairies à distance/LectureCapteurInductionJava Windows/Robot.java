import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;

import javax.swing.JSpinner.ListEditor;

import rec.robotino.com.AnalogInput;
import rec.robotino.com.Bumper;
import rec.robotino.com.Com;
import rec.robotino.com.DistanceSensor;
import rec.robotino.com.Motor;
import rec.robotino.com.OmniDrive;


/**
 * The class Robot demonstrates the usage of the most common robot component classes.
 * Furthermore it shows how to handle events and receive incoming camera images.
 */
public class Robot
{
	//Pour l'induction
	public native float valeurCapteurInduction(int numPin, String hostname);
	 
	static {
		System.loadLibrary("CptIndW");
	}
	
	protected final Com _com;
	protected final Bumper _bumper;
	
	public Robot()
	{
		_com = new Com();
		_bumper = new Bumper();
	}

	public void connect(String hostname)
	{
		System.out.println("Connecting...");
		_com.setAddress(hostname);
	}
	
	protected void init(){
		_bumper.setComId(_com.id());
	}

	public void disconnect()
	{
		_com.disconnect();;
	}
	  
    public void readValue(String hostname)
    {

		float valeurCptInduction;
		CptIndW classeValeurCptInduction = new CptIndW();
		
    	boolean run = true;
    	
    	while(run){
    		
    		valeurCptInduction = classeValeurCptInduction.valeurCptIndW(1, hostname);
    		System.out.println(valeurCptInduction);
			
    		try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
	         
	public static void main(String[] args) throws Exception
	{
		String hostname = System.getProperty("localhost", "193.48.125.38");
		//String hostname = System.getProperty("localhost", "127.0.0.1");
		Robot robotino = new Robot();	
		
		try
		{
			robotino.connect(hostname);
			robotino.readValue(hostname);
			robotino.disconnect();
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
}
