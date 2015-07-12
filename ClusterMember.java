package K_Means;

import java.awt.Color;

public abstract class ClusterMember {
	private Color color = Color.black;
	ClusterMember(){
		
	}
	public abstract ClusterMember clone();
	public abstract double distance(ClusterMember other);
	public Color getColor(){
		return color;
	}
	public void setColor(Color src){
		color = new Color(src.getRGB());
	}
}
