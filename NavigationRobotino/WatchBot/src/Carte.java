
import java.awt.AWTException;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.geom.Line2D;
 

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Carte extends JPanel{
	
	private String url ="jdbc:mysql://tp-epu.univ-savoie.fr:3308/simonw";
	private String login = "simonw";
	private String passwd = "dwxa992m";
	private static ServeurWB instance = null;
	
	java.sql.Connection connexion = null;
	java.sql.Statement st = null;
	ServeurWB sys = new ServeurWB();
	
	//Position de Robotino à récupérer du programme de navigation
	int posX_Rob = 25;
	int posY_Rob = 25;
	
	
	
	public Carte () {
        super();
        try {
			// Etape 1 : Chargement du driver
			Class.forName("com.mysql.jdbc.Driver");
			// Etape 2 : Récupération de la connexion
			connexion= DriverManager.getConnection(url,login,passwd);
			// Etape 3 : Création d'un statement
			st = connexion.createStatement();
			System.out.println("Connexion établie");
		}
		catch (SQLException e1){
			e1.printStackTrace();
		}
		catch (ClassNotFoundException e2){
			e2.printStackTrace();
		}
    }
	
	//Méthode traçant des lignes en utilisant les coordonnées enregistrées par Robotino
	public void drawLines(Graphics g, int id_salle) {
        Graphics2D g2d = (Graphics2D) g;
        ArrayList<ArrayList> tab= sys.RecupCoordonneesMur(id_salle);
        for(int i=0; i< tab.size()-1; i++){
        	ArrayList <Float> couple = tab.get(i);
        	ArrayList <Float> couple2 = tab.get(i+1);
        	g2d.drawLine(couple.get(0).intValue(), couple.get(1).intValue(), couple2.get(0).intValue(), couple2.get(1).intValue());
        	System.out.println("coordonnée"+i+": ("+couple+","+couple2+")");
        }
        
        //Permet de tracer le dernier trait à savoir dernière coord vers première
    	if(tab.size()>0){
    		ArrayList <Float> couple = tab.get(tab.size()-1);
        	ArrayList <Float> couple2 = tab.get(0);
        	g2d.drawLine(couple.get(0).intValue(), couple.get(1).intValue(), couple2.get(0).intValue(), couple2.get(1).intValue());
    	}
 
    }
	
	//Méthode traçant les différents traits et le point
	public void paint(Graphics g) {
		ArrayList <Integer> tabIdSalle= sys.recupID_salle();
        super.paint(g);
        for(int i=0; i<tabIdSalle.size(); i++)
        {
        	drawLines(g,tabIdSalle.get(i));
        	System.out.println("id salle: " + tabIdSalle.get(i));
        }
            g.fillOval(posX_Rob, posX_Rob, 10, 10);
        }               
   
	
	public void rogner (Graphics g_arg){
 	   try
 	   {
 		   BufferedImage image = ImageIO.read(new File("e:/Capture.png"));
 		   g_arg.drawImage(image.getSubimage(200,200,100,100), 0, 0, this);
 		   System.out.println("bite");
 	   }
 	   catch (IOException e)
 	   {/*...*/}
    }
	
	public static void main(String[] args) throws AWTException {
       JFrame frame = new JFrame();
       
       frame.getContentPane().add(new Carte());
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(480, 480);
       frame.setVisible(true);
       
       System.out.println("Carte générée");
       
       //Temporisation avant de faire une capture d'écran
       int n = 0;
       while(n!=2){
    	   Robot robot = new Robot(); 
           BufferedImage image = robot.createScreenCapture(new Rectangle( frame.getX(), frame.getY(), frame.getWidth(), frame.getHeight() ));
           
           File imageFile = new File("./Capture.png");
           try
           {
        	   imageFile.createNewFile();
        	   ImageIO.write(image, "png", imageFile);
        	   System.out.println("Capture enregistrée");
           }
           catch(Exception ex)
           {
        	   ex.printStackTrace();
           }
    	   n++;}
       

	
       
       }
	}