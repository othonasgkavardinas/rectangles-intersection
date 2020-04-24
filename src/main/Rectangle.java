//ONOMA: OTHONAS GKAVARDINAS 
//AM: 2620

package main;

class Rectangle{
	private int dimensionality;
	private double[] lowerBound;
	private double[] upperBound;
	
	public Rectangle(int dimensionality){
		this.dimensionality = dimensionality;
		this.lowerBound = new double[dimensionality];
		this.upperBound = new double[dimensionality];
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
		for (int i=0; i<lowerBound.length; i++){
			tempString+=""+lowerBound[i]+",";
		}
		tempString=tempString.substring(0, tempString.length()-1);
		tempString+="}, {";
		for (int i=0; i<upperBound.length; i++){
			tempString+=""+upperBound[i]+",";
		}
		tempString=tempString.substring(0, tempString.length()-1);
		tempString+="}";
		System.out.println("Copy set to ("+dimensionality+", "+tempString+")");
		return copy;
	}
	
	public double[] project(int dimension){
		double[] tempArray = {lowerBound[dimension],upperBound[dimension]};
		return tempArray;
	}
	
	public void enlarge(Rectangle other){
		for (int i=0; i<lowerBound.length; i++){
			for(int j=0; j<lowerBound.length; j++){
				if (this.lowerBound[j] > other.project(j)[i]){
					this.lowerBound[j] = other.project(j)[i];
				}
				if (this.upperBound[j] < other.project(j)[i]){
					this.upperBound[j] = other.project(j)[i];
				}
			}
		}
	}
	
	public boolean intersects(Rectangle other){
		int counter = 0;
		for (int i=0; i<lowerBound.length; i++){
			if(this.lowerBound[i]==other.lowerBound[i] || this.upperBound[i]==other.upperBound[i]) counter++;
			else if(this.lowerBound[i]<other.lowerBound[i] && this.upperBound[i]>other.upperBound[i]) counter++;
			else if(this.lowerBound[i]>other.lowerBound[i] && this.upperBound[i]<other.upperBound[i]) counter++;
			else if(this.upperBound[i]>other.lowerBound[i] && this.upperBound[i]<other.upperBound[i]) counter++;
			else if(this.lowerBound[i]>other.lowerBound[i] && this.lowerBound[i]<other.upperBound[i]) counter++;
		}
		if (counter==dimensionality){
			return true;
		}return false;
	}
	
	public boolean inside(Rectangle other){
		int counter = 0;
		for (int i=0; i<lowerBound.length; i++){
			if(this.lowerBound[i]>=other.lowerBound[i] && this.upperBound[i]<=other.upperBound[i]) counter++;
		}
		if (counter==dimensionality){
			return true;
		}return false;
	}
	
	public double mindist(Rectangle other){
		double distance = 0;
		if(this.intersects(other)){
			return distance;
		}else{
			for (int i=0; i<lowerBound.length; i++){
				if(this.upperBound[i]<other.lowerBound[i]){
					distance+=(other.lowerBound[i]-this.upperBound[i])*(other.lowerBound[i]-this.upperBound[i]);
				}
				else if(this.lowerBound[i]>other.upperBound[i]){
					distance+=(this.lowerBound[i]-other.upperBound[i])*(this.lowerBound[i]-other.upperBound[i]);
				}	
			}
			distance = Math.sqrt(distance);
			return distance;
		}
	}
	
	public double maxdist(Rectangle other){
		double distance = 0;
		for (int i=0; i<lowerBound.length; i++){
			if(this.upperBound[i]<other.lowerBound[i]){
				distance+=(other.upperBound[i]-this.lowerBound[i])*(other.upperBound[i]-this.lowerBound[i]);
			}
			else if(this.lowerBound[i]>other.upperBound[i]){
				distance+=(this.upperBound[i]-other.lowerBound[i])*(this.upperBound[i]-other.lowerBound[i]);
			}
		}
		distance = Math.sqrt(distance);
		return distance;
	}
}