package K_Means;

import java.util.ArrayList;

public class Cluster {
	private ArrayList<Dot> members;
	private Dot centroid;
	
	public Cluster( Dot centroid ) {
		this.centroid = centroid;
		members = new ArrayList<Dot> ();
	}
	
	public void addMember( Dot src){
		Dot member = src.clone();
		member.setColor(centroid.getColor());
		members.add(member);
	}
	
	public ArrayList<Dot> toArrayList(){
		ArrayList<Dot> res = (ArrayList<Dot>)members.clone();
		res.add(centroid);
		return res;
	}
	
	public Dot getCentroid() {
		return centroid;
	}
	
	public Dot getMassCentr(){
		Dot res = centroid.clone();
		int rcX = 0; int rcY = 0;
		for(Dot m : members){
			rcX += m.getX();
			rcY += m.getY();
		}
		res.setX( (int) ( rcX/members.size() ) );
		res.setY( (int) ( rcY/members.size() ) );
		
		return res;
	}
}
