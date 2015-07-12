package K_Means;

import java.awt.Color;

public class DNAStrand extends ClusterMember {
	enum base {A,G,C,T};
	private base[] set;
	DNAStrand(int size){
		set = new base[size];
	}
	public DNAStrand clone(){
		DNAStrand res = new DNAStrand(set.length);
		res.set = set.clone();
		return res;
	}
	public double distance(ClusterMember otherCl){
		DNAStrand other = (DNAStrand) otherCl;
		if( length() != other.length()){
			System.out.print("NOT SAME LENGTH (src,other): " + length() + "," + other.length() );
			return -1;
		}
		double res = 0;
		for(int i = 0; i<set.length; i++){
			if( this.set[i] == other.set[i] )
				res += 1;
		}
		return res;
	}
	public void setDnaSet(int index, base val){
		set[index] = val;
	}
	public base getSetValue( int index ){
		return set[index];
	}
	
	public boolean equals(DNAStrand other){
		for(int i=0;i<set.length;i++){
			if(set[i] != other.set[i])
				return false;
		}		
		return true;
	}
	
	public int length(){
		return set.length;
	}
	
	public String toString(){
		String res = "[";
		for(base val : set){
			res += "[";
			String str = new String();
			if(val==base.A)
				str="A";
			else
				if(val==base.G)
					str="G";
				else
					if(val==base.C)
						str="C";
					else
						if(val==base.T)
							str="T";
						else
							System.out.print("UNKNOWN TYPE: " + val );
			res += str + "]";
		}
		res += "]";
		return res;
	}
}
