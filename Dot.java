package K_Means;

import java.awt.Color;

public class Dot extends ClusterMember {
	private int x,y; // coordinates
	private int width;
	private Color color = Color.black;
	
	public Dot (){
		super();
	}
	
//	public Dot( Dot src ){
//		this.color = src.color;
//		this.x = src.x;
//		this.y = src.y;
//	}
	
//	public Dot( ClusterMember src ){
//		if( src.getClass().equals(Dot.class) )
//			Dot((Dot)src);
//	}

	public Dot( int x, int y, int width, Color color){
		this.x = x;
		this.y = y;
		this.width = width;
		this.color = color;
	}
	
	public Color getColor(){
		return color;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getWidth(){
		return width;
	}
	
	public Dot clone(){
		return new Dot(x,y,width,new Color(color.getRGB()));
	}
	
	public void setX( int src){
		x = src;
	}
	public void setY(int src){
		y = src;
	}
	public void setWidth( int src){
		width = src;
	}
	public void setColor(Color src){
		color = new Color(src.getRGB());
	}
//	public double distance(Dot other){
//		return Math.sqrt(Math.pow(x - other.x,2) + Math.pow(y - other.y,2));
//	}
	public double distance(ClusterMember otherCl){
		Dot other = (Dot) otherCl;
		return Math.sqrt(Math.pow(x - other.x,2) + Math.pow(y - other.y,2));
	}
}
