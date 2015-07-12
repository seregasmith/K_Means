package K_Means;

import java.util.ArrayList;

public class Cluster {
	private ArrayList<ClusterMember> members;
	private ClusterMember centroid;
	
//	public Cluster(  ) {
//		members = new ArrayList<ClusterMember> ();
//	}
	
	public Cluster( ClusterMember centroid ){
		this.centroid = centroid;
		members = new ArrayList<ClusterMember> ();
	}
	public void addMember( ClusterMember src){
		ClusterMember member = src.clone();
		member.setColor(centroid.getColor());
		members.add(member);
	}
	
	public ArrayList<ClusterMember> toArrayList(){
		ArrayList<ClusterMember> res = (ArrayList<ClusterMember>)members.clone();
		res.add(centroid);
		return res;
	}
	public ArrayList<ClusterMember> getMembers(){
		return members;
	}
	
//	public ClusterMember[] getMembers(){
//		ClusterMember[] res = new ClusterMember[members.size()]; 
//		for(int i=0; i<res.length; i++){
//			res[i] = members.get(i);
//		}
//		return res;
//	}
	
	public ClusterMember getCentroid() {
		return centroid;
	}
	
//	public abstract ClusterMember getMassCentr();
}
