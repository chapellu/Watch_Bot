import java.util.Timer;
import java.util.TimerTask;

import rec.robotino.com.Com;/*
import Robot.MyCom.OnTimeOut;*/

    /*********************** Connection *************************/
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
