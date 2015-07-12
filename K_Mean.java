package K_Means;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

import K_Means.DNAStrand.base;

//import java.util.ArrayList;

public class K_Mean {
	final static int maxIterations = 10; // maximum iterations for algorithm
	final static int rand_dots_mount = 100;
	final static int clusters_mount = 3;
	private static DotsPrinter dots_printer;
	 
	public static Cluster[] createClusters(ClusterMember [] centr_dots,ClusterMember [] other_dots){
		Cluster [] clusters = new Cluster [centr_dots.length];
		for(int i = 0; i<centr_dots.length; i++){
			clusters[i] = new Cluster(centr_dots[i]);
		}
//		for ( ClusterMember centr : centr_dots ){
//			clusters
////			;.add(new Cluster(centr));
//		}
		for ( ClusterMember dot : other_dots ){
			Cluster cluster = findMin(dot, clusters);
			cluster.addMember(dot);
		}	
		return clusters;
		
	}
	private static Cluster findMin(ClusterMember dot, Cluster[] clusters){
		double minDistance = dot.distance(clusters[0].getCentroid());
		int i = 0;
		int clusterIndex = i;
		while (++i < clusters.length){
			double distance = dot.distance(clusters[i].getCentroid());
			if( minDistance > distance ){
				minDistance = distance;
//				System.out.println(distance);
				clusterIndex = i;
			}
//			System.out.println(distance);
		}
//		System.out.println(i);
		return clusters[clusterIndex];
	}
	

	public static Dot getDotsMassCentr(Cluster cluster){
		Dot res = ((Dot)cluster.getCentroid()).clone();
		int rcX = 0; int rcY = 0;
		ArrayList<ClusterMember> pointer_to_mem = cluster.getMembers();
		Dot[] members = new Dot[pointer_to_mem.size()];
		for(int i=0; i<pointer_to_mem.size();i++){
			members[i] = (Dot) pointer_to_mem.get(i);
		}
		for(Dot m : members){
			rcX += m.getX();
			rcY += m.getY();
		}
		res.setX( (int) ( rcX/members.length ) );
		res.setY( (int) ( rcY/members.length ) );
		
		return res;
	}
	
	public static Dot[] getDotsMassCetrs(Cluster[] clusters){
		Dot[] res = new Dot[clusters.length];
		for(int i = 0; i < res.length; i++){
			res[i] = getDotsMassCentr(clusters[i]);
		}
		return res;
	}
	
	public static DNAStrand getDNAMassCentr(Cluster cluster){
		DNAStrand res = ((DNAStrand)cluster.getCentroid()).clone();
//		int rcX = 0; int rcY = 0;
		
		ArrayList<ClusterMember> pointer_to_mem = cluster.getMembers();
//		DNAStrand[] members = new DNAStrand[pointer_to_mem.size()];
		for( int j=0; j < res.length(); j++){
			NavigableMap<DNAStrand.base, Integer> map = new TreeMap<DNAStrand.base, Integer>();
			map.put(DNAStrand.base.A, 0);
			map.put(DNAStrand.base.G, 0);
			map.put(DNAStrand.base.C, 0);
			map.put(DNAStrand.base.T, 0);
			for(int i=0; i<pointer_to_mem.size();i++){
				DNAStrand.base val = ((DNAStrand)pointer_to_mem.get(i)).getSetValue(j);
				Integer counter = map.get(val);
				map.replace(val, ++counter);
			}
			DNAStrand.base maxVal = map.firstKey();
			Integer maxEntries = map.get(maxVal);
			for(DNAStrand.base val : map.keySet()){
				if(maxEntries <= map.get(val)){
					maxVal = val;
					maxEntries = map.get(maxVal);
				}
			}
			res.setDnaSet(j, maxVal);
		}		
		return res;
	}
	
	public static DNAStrand[] getDNAMassCetrs(Cluster[] clusters){
		DNAStrand[] res = new DNAStrand[clusters.length];
		for(int i = 0; i < res.length; i++){
			res[i] = getDNAMassCentr(clusters[i]);
		}
		return res;
	}
	
	public static Cluster[] createClusters(Object[] centr_dots, Object[] other_dots) {
		ClusterMember [] centrs = new ClusterMember [centr_dots.length];
		ClusterMember [] others = new ClusterMember [other_dots.length];
		for(int i = 0; i<centr_dots.length; i++){
			centrs[i] = (ClusterMember) centr_dots[i]; 
		}
		for(int i = 0; i<other_dots.length; i++){
			others[i] = (ClusterMember) other_dots[i]; 
		}
		
		return createClusters(centrs,others);
	}
	
	public static Dot[] generateRand2dDots(){
		Random generator = new Random (System.currentTimeMillis());
//		ArrayList<Dot> rand_dots = new ArrayList<Dot>();
		Dot[] rands = new Dot[rand_dots_mount];
		for (int i = 0; i < rand_dots_mount; i++){
			int x = generator.nextInt(dots_printer.getPlotSize());
			int y = generator.nextInt(dots_printer.getPlotSize());
			rands[i] = new Dot(x,y,5,Color.black);
//			rand_dots.add(new Dot(x,y,5,Color.black));
		}
		return rands;
	}
	
	public static DNAStrand[] generateRandDNAStrands(){
		Random generator = new Random (System.currentTimeMillis());
//		ArrayList<Dot> rand_dots = new ArrayList<Dot>();
		DNAStrand[] rands = new DNAStrand[rand_dots_mount];
		int strand_size = 20;
		for (int i = 0; i < rand_dots_mount; i++){
			DNAStrand dna = new DNAStrand(strand_size);
			for( int j=0; j < strand_size; j++ ){
				dna.setDnaSet(j, DNAStrand.base.values()[generator.nextInt(4)]);
			}
			rands[i] = dna;
//			rand_dots.add(new Dot(x,y,5,Color.black));
		}
		return rands;
	}
	
	public static Dot[] generateRand2dCentroid(Dot[] rand_dots){
		Random generator = new Random (System.currentTimeMillis());
		int amount = clusters_mount;
   	 	Dot [] centrs = new Dot[amount];
        boolean[] check = new boolean[dots_printer.getPlotSize()];
        int amountFilled = 0;
        int trial;
        while (amountFilled < amount) {
            trial = generator.nextInt(rand_dots.length);
            if (!check[trial]) {
                check[trial] = true;
                Dot c = rand_dots[trial].clone();
                centrs[amountFilled] = c;
                c.setColor( new Color( ( int ) ( Math.random() * 256 ),( int ) ( Math.random() * 256 ), 
       	             ( int ) ( Math.random() * 256 ) ) );
                c.setWidth(10);
                amountFilled++;
            }
        }
        return centrs;
	}
	
	public static DNAStrand[] generateRandDNAStrandsCentroid(DNAStrand[] rand_dots){
		Random generator = new Random (System.currentTimeMillis());
		int amount = clusters_mount;
		DNAStrand [] centrs = new DNAStrand[amount];
        boolean[] check = new boolean[rand_dots.length];
        int amountFilled = 0;
        int trial;
        while (amountFilled < amount) {
            trial = generator.nextInt(rand_dots.length);
            if (!check[trial]) {
                check[trial] = true;
                DNAStrand c = rand_dots[trial].clone();
                centrs[amountFilled] = c;
                c.setColor( new Color( ( int ) ( Math.random() * 256 ),( int ) ( Math.random() * 256 ), 
       	             ( int ) ( Math.random() * 256 ) ) );
//                c.setWidth(10);
                amountFilled++;
            }
        }
        return centrs;
	}
	
	public static int solve2dClusters(Dot[] rand_dots,Dot[] centr_dots) throws InterruptedException{
		for ( int i=0; i < maxIterations; i++ )
		{
			dots_printer.clearDots();
	   		Cluster[] clusters = K_Mean.createClusters(centr_dots, rand_dots);
//	   		DotsPrinter app1 = new DotsPrinter();
	   		for ( Cluster cluster : clusters ){
	   			
	   			dots_printer.addDots(cluster.toArrayList());
	   		}
	   		Dot[] centr_masses = K_Mean.getDotsMassCetrs(clusters);
	   		for(int j=0; j<clusters.length; j++)
	   		{
	   			if(
	   					((Dot)clusters[j].getCentroid()).getX() == centr_masses[j].getX()
	   					&& 
	   					((Dot)clusters[j].getCentroid()).getY() == centr_masses[j].getY()
	   					){
	   				dots_printer.showWindow();
	   				 System.out.println("RESULT HAS FOUND");
	   	        	 Thread.sleep(10000);
	   	        	 return 0;
	   			 }
	   		 }
	   		 centr_dots = centr_masses;
	//   		 app1.addDots(clusters.get(2).toArrayList());
	   		dots_printer.showWindow();
	       	 Thread.sleep(2000);
	   	}
		System.out.println("RESULT HASN'T BEEN FOUND IN " + maxIterations + " iterations");
   	 	return 1;
	}
	
	public static int solveDNAClusters(DNAStrand[] rand_dnas,DNAStrand[] centr_dnas) throws InterruptedException{
		for ( int i=0; i < maxIterations; i++ )
		{
	   		Cluster[] clusters = K_Mean.createClusters(centr_dnas, rand_dnas);
//	   		DotsPrinter app1 = new DotsPrinter();
//	   		for ( Cluster cluster : clusters ){
//	   			System.out.println("\n" + ((DNAStrand)cluster.getCentroid()).toString() + "\n>>>>>>>>>>>>>");
//	   			for(ClusterMember dnaCl : cluster.getMembers()){
//	   				System.out.println(((DNAStrand)dnaCl).toString());
//	   			}
//	   		}
	   		DNAStrand[] centr_masses = K_Mean.getDNAMassCetrs(clusters);
	   		int fl = 0;
	   		for(int j=0; j<clusters.length; j++){
	   			if(((DNAStrand)clusters[j].getCentroid()).equals(centr_masses[j])){
	   				++fl;
	   			}
	   		}
	   		System.out.println(fl+","+clusters.length);
	   		if(fl == clusters.length){
	   			System.out.println("RESULT HAS FOUND");
	   			for ( Cluster cluster : clusters ){
		   			System.out.println("\n" + ((DNAStrand)cluster.getCentroid()).toString() + "\n>>>>>>>>>>>>>");
		   			for(ClusterMember dnaCl : cluster.getMembers()){
		   				System.out.println(((DNAStrand)dnaCl).toString());
		   			}
		   		}
	   			System.out.println("END of RESULT");
	   			return 0;
	   		}
	   		centr_dnas = centr_masses;
//	   		Dot[] centr_masses = K_Mean.getMassCetrs(clusters);
//	   		for(int j=0; j<clusters.length; j++)
//	   		{
//	   			if(
//	   					((Dot)clusters[j].getCentroid()).getX() == centr_masses[j].getX()
//	   					&& 
//	   					((Dot)clusters[j].getCentroid()).getY() == centr_masses[j].getY()
//	   					){
//	   				dots_printer.showWindow();
//	   				 System.out.println("RESULT HAS FOUND");
//	   	        	 Thread.sleep(10000);
//	   	        	 return 0;
//	   			 }
//	   		 }
//	   		 centr_dnas = centr_masses;
//	//   		 app1.addDots(clusters.get(2).toArrayList());
//	   		dots_printer.showWindow();
//	       	 Thread.sleep(2000);
//	   		System.out.println("RESULT HAS FOUND");
   			for ( Cluster cluster : clusters ){
	   			System.out.println("\n" + ((DNAStrand)cluster.getCentroid()).toString() + "\n>>>>>>>>>>>>>");
	   			for(ClusterMember dnaCl : cluster.getMembers()){
	   				System.out.println(((DNAStrand)dnaCl).toString() + " distance: "
	   						+ ((DNAStrand)dnaCl).distance((DNAStrand)cluster.getCentroid()));
	   			}
	   		}
   			System.out.println("END of RESULT");
	   	}
		System.out.println("RESULT HASN'T BEEN FOUND IN " + maxIterations + " iterations");
   	 	return 1;
	}
	
	public static void main( String args[] ) throws InterruptedException{
		dots_printer = new DotsPrinter();
		Dot[] rands = generateRand2dDots();
		Dot[] centrs = generateRand2dCentroid(rands);
		solve2dClusters(rands,centrs);
		
		DNAStrand[] rands_dna = generateRandDNAStrands();
//		for(DNAStrand dna : rands_dna){
//			System.out.println(dna.toString());
//		}
		DNAStrand[] centrs_dna = generateRandDNAStrandsCentroid(rands_dna);
//		for(DNAStrand dna : centrs_dna){
//			System.out.println(dna.toString());
//		}
		solveDNAClusters(rands_dna, centrs_dna);
	}
}
