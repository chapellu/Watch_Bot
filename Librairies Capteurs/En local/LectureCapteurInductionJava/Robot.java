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
	public native float valeurCapteurInduction(int numPin);
	 
	static {
		System.loadLibrary("rec_robotino_com_c");
		System.loadLibrary("CapteurInduction");
	}
	
	protected final Com _com;
	protected final Bumper _bumper;
	
	public Robot()
	{
		//_com = new MyCom();
		_com = new Com();
		_bumper = new Bumper();

	}
	
	public boolean isConnected()
	{
		return _com.isConnected();
	}

	//public void connect(String hostname, boolean block)
	public void connect(String hostname)
	{
		System.out.println("Connecting...");
		_com.setAddress( hostname );
		//_com.connect(block);;
	}

	public void disconnect()
	{
		_com.disconnect();;
	}
	  
    public void readValue()
    {

		float valeurCptInduction;
		CapteurInduction classeValeurCptInduction = new CapteurInduction();
		
    	boolean run = true;
    	
    	while(run){
    		
    		valeurCptInduction = classeValeurCptInduction.valeurCapteurInduction(1);
    		System.out.println(valeurCptInduction);
			
    		try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
	
	/**
	 * The class MyCom derives from rec.robotino.api2.Com and implements some of the virtual event handling methods.
	 * This is the standard approach for handling these Events.
	 */
	class MyCom extends Com
	{
		Timer _timer;
		
		public MyCom()
		{
			_timer = new Timer();
			_timer.scheduleAtFixedRate(new OnTimeOut(), 0, 20);
		}
		
		class OnTimeOut extends TimerTask
		{
			public void run()
			{
				processEvents();
			}
		}

		@Override
		public void connectedEvent()
		{
			System.out.println( "Connected" );
		}


		public void errorEvent(String errorStr)
		{
			System.err.println( "Error: " + errorStr );
		}

		@Override
		public void connectionClosedEvent()
		{
			System.out.println( "Disconnected" );
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		//String hostname = System.getProperty("localhost", "193.48.125.37");
		String hostname = System.getProperty("localhost", "127.0.0.1");
		Robot robotino = new Robot();		
		try
		{
			//robotino.connect(hostname, true);
			robotino.connect(hostname);
			robotino.readValue();
			robotino.disconnect();
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
}
