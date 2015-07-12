package K_Means;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;

public class DotsPrinter extends JFrame 
    {
	private static int size = 400;
	ArrayList<Dot> dots = new ArrayList<Dot>();
	public DotsPrinter(){
		super( "Drawing 2D Shapes" );
	    setBackground( Color.gray );
	    setSize( size, size );
	    extracted();
	}
     
     
	@SuppressWarnings("deprecation")
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
	
	public int getPlotSize(){
		return size;
	}
	
	public void addDots(ArrayList<ClusterMember> arrayList){
		for(ClusterMember m : arrayList)
			dots.add((Dot) m);
	}
	
	public void clearDots(){
		dots.clear();
		update(getGraphics());
	}
	
	public void showWindow(){
		addWindowListener(new WindowAdapter(){
        public void windowClosing( WindowEvent e )
            {System.exit( 0 );}
        });
		Graphics g = getGraphics();
		g.clearRect(0, 0, size, size);
		update(g);
	}
     
//     private
    
     public void paint( Graphics g ){        
         Graphics2D g2d = ( Graphics2D ) g;
         
         for( Dot e : dots){
        	 g2d.setColor(e.getColor());
        	 g2d.fillOval(e.getX(), e.getY(), e.getWidth(), e.getWidth());
         }
     }
}