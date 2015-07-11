package K_Means;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class DotsPrinter extends JFrame 
    {
	private static int size = 400;
	ArrayList<Dot> dots = new ArrayList<Dot>();
     public DotsPrinter()
         {
         super( "Drawing 2D Shapes" );
         setBackground( Color.gray );
         setSize( size, size );
         extracted();
     }

	private void extracted() {
		show();
	}
	public DotsPrinter(ArrayList<Dot> src){
		super( "Drawing 2D Shapes" );
        setBackground( Color.gray );
        setSize( size, size );
        extracted();
		dots = src;
	}
	
	public void addDots(ArrayList<Dot> src){
		dots.addAll(src);
	}
	
	public void showWindow(){
		addWindowListener(new WindowAdapter() 
        {
        public void windowClosing( WindowEvent e )
            {
        	System.exit( 0 );        	
        }});
	}
     
//     private
    
     public void paint( Graphics g ){
//         int xPoints[] = { 55, 67, 109, 73, 83, 55, 27, 37, 1, 43 };
//         int yPoints[] = { 0, 36, 36, 54, 96, 72, 96, 54, 36, 36 };
        
         Graphics2D g2d = ( Graphics2D ) g;
         
         for( Dot e : dots){
        	 g2d.setColor(e.getColor());
        	 g2d.fillOval(e.getX(), e.getY(), e.getWidth(), e.getWidth());
//        	 g2d.drawOval(e.getX(), e.getY(), e.getWidth(), e.getWidth());
         }
         
//         for( int x : xPoints ){
//        	 for( int y : yPoints ){
//        		 g2d.setColor(new Color( ( int ) ( Math.random() * 256 ),( int ) ( Math.random() * 256 ), 
//        	             ( int ) ( Math.random() * 256 ) ) );
//        		 g2d.drawOval(x, y, 1, 1);
//        	 }
//         }
     }
    
     public static void main( String args[] ) throws InterruptedException{
    	 final int maxIterations = 5; // maximum iterations for algorithm
    	 final int rand_dots_mount = 255;
    	 final int clusters_mount = 10;
    	 
    	 Random generator = new Random (System.currentTimeMillis());
    	 ArrayList<Dot> rand_dots = new ArrayList<Dot>();
    	 for (int i = 0; i < rand_dots_mount; i++){
    		 int x = generator.nextInt(size);
             int y = generator.nextInt(size);
             rand_dots.add(new Dot(x,y,5,Color.black));
    	 }
    	 int amount = clusters_mount;
    	 Dot [] centrs = new Dot[amount];
         boolean[] check = new boolean[size];
         int amountFilled = 0;
         int trial;
         while (amountFilled < amount) {
             trial = generator.nextInt(rand_dots.size());
             if (!check[trial]) {
                 check[trial] = true;
                 centrs[amountFilled] = rand_dots.get(trial).clone() ;
                 amountFilled++;
             }
         }
         ArrayList<Dot> centr_dots = new ArrayList<Dot>();
    	 for( Dot centr : centrs){
    		 centr.setX(centr.getX()+ 5);
        	 centr.setWidth(10);
        	 centr.setColor( new Color( ( int ) ( Math.random() * 256 ),( int ) ( Math.random() * 256 ), 
        	             ( int ) ( Math.random() * 256 ) ) );
        	 centr_dots.add(centr);
         }
//    	 DotsPrinter app = new DotsPrinter(rand_dots);
//    	 app.showWindow();
//    	 Thread.sleep(2000);
    	 
    	 for ( int i=0; i < maxIterations; i++ ){    		 
    		 ArrayList<Cluster> clusters = Algorithm.createClusters(centr_dots, rand_dots);
    		 DotsPrinter app1 = new DotsPrinter();
    		 for ( Cluster cluster : clusters ){    			 
    			 app1.addDots(cluster.toArrayList());
    		 }
    		 boolean fl = false;
    		 ArrayList<Dot> centr_masses = Algorithm.getMassCetrs(clusters);
    		 for(int j=0; j<clusters.size(); j++){
    			 if(
    					 clusters.get(j).getCentroid().getX() == centr_masses.get(j).getX()
    					 && 
    					 clusters.get(j).getCentroid().getY() == centr_masses.get(j).getY()
    					 ){
    				 app1.showWindow();
    				 System.out.println("RESULT HAS FOUND");
    	        	 Thread.sleep(10000);
    	        	 System.exit( 0 );
    			 }
    		 }
    		 centr_dots.clear();
    		 centr_dots = centr_masses;
//    		 app1.addDots(clusters.get(2).toArrayList());
    		 app1.showWindow();
        	 Thread.sleep(2000);
    	 }
    	 
    	 System.out.println("RESULT HASN'T BEEN FOUND IN " + maxIterations + " iterations");
    	 System.exit( 0 );
     }
}