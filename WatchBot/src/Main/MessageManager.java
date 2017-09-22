package Main;
import java.util.Timer;
import java.util.TimerTask;

import rec.robotino.com.Com;/*
import Robot.MyCom.OnTimeOut;*/
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import cartographie.Navigation;
import Communication.*;


/*********************** Connection *************************/
    public class MessageManager implements InterfaceMessageRecu
	{
		Robot robot = null;
		
		public MessageManager(Robot r)
		{
			robot = r;
		}
		
		@Override
		public void newMessageRecu(Message mes) {
			switch (mes.getType()){
			case Ordre:
				handleOrder(mes);
				break;
			case Message:
				handleMessage(mes);
				break;
			case Erreur:
				handleErreur(mes);
				break;
			case Inconnu:
				handleInconnu(mes);
				break;
			case Requete:
				handleRequeteSQL(mes);
				break;
			default:
				break;
			}
		}


		private void handleRequeteSQL(Message mes) {
			// TODO Auto-generated method stub
				
		}


		private void handleInconnu(Message mes) {
			// TODO Auto-generated method stub
			
		}


		private void handleErreur(Message mes) {
			// TODO Auto-generated method stub
			
		}


		private void handleMessage(Message mes) {
			// TODO Auto-generated method stub
			
		}


		private void handleOrder(Message mes) {
			// TODO Auto-generated method stub
			try { 
				switch(Ordre.valueOf(mes.getMessage())){
				case startCartographie:
					System.out.println("Je dois cartographier");
					robot.startCartographie();
					break;
				case stopCartographie:
					System.out.println("Arret");
					robot.stopCartographie();
					break;
				case avance:
					System.out.println("Je dois avancer");
					robot.manuel("avance");
					break;
				case recule:
					System.out.println("Je dois reculer");
					robot.manuel("recule");
					break;
				case droite:
					robot.manuel("droite");
					break;
				case gauche:
					robot.manuel("gauche");
					break;
				case startSurveillance:
					robot.startSurveillance();
					break;
				case stopSurveillance:
					robot.stopSurveillance();
					break;
				default:
					System.out.println(mes);
					break;
				}
			}
			catch (Exception e){System.out.println(e);};			
		}
	}
