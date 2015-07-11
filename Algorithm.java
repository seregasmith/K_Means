package K_Means;

import java.util.ArrayList;

public class Algorithm {
	public static ArrayList<Cluster> createClusters(ArrayList<Dot> centr_dots,ArrayList<Dot> other_dots){
		ArrayList<Cluster> clusters = new ArrayList<Cluster>();
		for ( Dot centr : centr_dots ){
			clusters.add(new Cluster(centr));
		}
		for ( Dot dot : other_dots ){
			Cluster cluster = findMin(dot, clusters);
			cluster.addMember(dot);
		}	
		return clusters;
		
	}
	private static Cluster findMin(Dot dot, ArrayList<Cluster> clusters){
		double minDistance = dot.distance(clusters.get(0).getCentroid());
		int i = 0;
		int clusterIndex = i;
		while (++i < clusters.size()){
			if( minDistance > dot.distance(clusters.get(i).getCentroid()) ){
				minDistance = dot.distance(clusters.get(i).getCentroid());
				clusterIndex = i;
			}
		}
//		System.out.println(i);
		return clusters.get(clusterIndex);
	}
	
	public static ArrayList<Dot> getMassCetrs(ArrayList<Cluster> clusters){
		ArrayList<Dot> res = new ArrayList<Dot>();
		for( Cluster cluster : clusters ){
			Dot c = cluster.getMassCentr();
			res.add(c);
		}
		return res;
	}
}
