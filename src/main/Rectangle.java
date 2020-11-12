//ONOMA: OTHONAS GKAVARDINAS 
//AM: 2620

package main;

class Rectangle{
	private int dimensionality;
	private double[] lowerBound;
	private double[] upperBound;
	
	public Rectangle(int dimensionality){
		this.dimensionality = dimensionality;
		lowerBound = new double[dimensionality];
		upperBound = new double[dimensionality];
	}
	
	public Rectangle(int dimensionality, double[] lowerBound, double[] upperBound){
		this.dimensionality = dimensionality;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}
	
	public int getDimensionality(){
		return dimensionality;
	}
	
	public String toString(){
		String tempString="";
		for (int i=0; i<lowerBound.length; i++){
			tempString+="["+lowerBound[i]+","+upperBound[i]+"]";
		}
		return tempString;
	}

	public Rectangle copy(){
		Rectangle copy = new Rectangle(dimensionality, lowerBound, upperBound);
		String tempString ="{";
		double[][] bounds = { lowerBound, upperBound };
		for (double[] bound : bounds) {
			for (int i=0; i<bound.length; i++)
				tempString += "" + bound[i] + ",";
			tempString = tempString.substring(0, tempString.length()-1);
			tempString+="}, {";
		}
		System.out.println("Copy set to ("+dimensionality+", "+tempString+")");
		return copy;
	}
	
	public double[] project(int dimension){
		return new double[] { lowerBound[dimension], upperBound[dimension] };
		
	}
	
	public void enlarge(Rectangle other){
		for (int i=0; i<lowerBound.length; i++)
			for(int j=0; j<lowerBound.length; j++){
				if (lowerBound[j] > other.project(j)[i])
					lowerBound[j] = other.project(j)[i];
				if (upperBound[j] < other.project(j)[i])
					upperBound[j] = other.project(j)[i];
			}
	}
	
	public boolean intersects(Rectangle other){
		int counter = 0;
		for (int i=0; i<lowerBound.length; i++)
			if(lowerBound[i] == other.lowerBound[i] || upperBound[i] == other.upperBound[i]
			|| (lowerBound[i]<other.lowerBound[i] && upperBound[i]>other.upperBound[i])
			|| (lowerBound[i]>other.lowerBound[i] && upperBound[i]<other.upperBound[i])
			|| (upperBound[i]>other.lowerBound[i] && upperBound[i]<other.upperBound[i])
			|| (lowerBound[i]>other.lowerBound[i] && lowerBound[i]<other.upperBound[i]))
				counter++;;
		if (counter == dimensionality)
			return true;
		return false;
	}
	
	public boolean inside(Rectangle other){
		int counter = 0;
		for (int i=0; i<lowerBound.length; i++)
			if(lowerBound[i]>=other.lowerBound[i] && upperBound[i]<=other.upperBound[i]) counter++;
		if (counter==dimensionality)
			return true;
		return false;
	}
	
	public double mindist(Rectangle other){
		double distance = 0;
		if(this.intersects(other))
			return distance;
		else{
			for (int i=0; i<lowerBound.length; i++){
				if(upperBound[i]<other.lowerBound[i])
					distance+=(other.lowerBound[i]-upperBound[i])*(other.lowerBound[i]-upperBound[i]);
				else if(this.lowerBound[i]>other.upperBound[i])
					distance+=(lowerBound[i]-other.upperBound[i])*(lowerBound[i]-other.upperBound[i]);
			}
			return Math.sqrt(distance);
		}
	}
	
	public double maxdist(Rectangle other){
		double distance = 0;
		for (int i=0; i<lowerBound.length; i++){
			if(upperBound[i]<other.lowerBound[i])
				distance+=(other.upperBound[i]-lowerBound[i])*(other.upperBound[i]-lowerBound[i]);
			else if(lowerBound[i]>other.upperBound[i])
				distance+=(upperBound[i]-other.lowerBound[i])*(upperBound[i]-other.lowerBound[i]);
		}
		return Math.sqrt(distance);
	}
}