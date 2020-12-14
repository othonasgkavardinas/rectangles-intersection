//ONOMA: OTHONAS GKAVARDINAS 
//AM: 2620

package com.github.othonasgkavardinas.app;

import lombok.Getter;

public class Rectangle{
	private @Getter int dimensionality;
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
	
	public String toString(){
		String tempString="{ " + lowerBound[0] + ", " + upperBound[0] + " }";
		for (int i=1; i<lowerBound.length; i++){
			tempString += ", { " + lowerBound[i] + ", " + upperBound[i] + " }";
		}
		return tempString;
	}

	public Rectangle copy(){
		Rectangle copy = new Rectangle(dimensionality, lowerBound, upperBound);
		printRectangleCopy();
		return copy;
	}
	
	private void printRectangleCopy() {
		String tempString = "";
		double[][] bounds = { lowerBound, upperBound };
		if(lowerBound.length > 0) 
			for (double[] bound : bounds) {
				tempString += "{" + bound[0];
				for (int i=1; i<bound.length; i++)
					tempString += ", " + bound[i];
				tempString+="}, ";
			}
		tempString = tempString.substring(0, tempString.length()-2);
		System.out.println("Copy set to ("+ dimensionality + ", " + tempString + ")");
	}
	
	public double[] project(int dimension){
		return new double[] { lowerBound[dimension], upperBound[dimension] };
	}
	
	public void enlarge(Rectangle other){
		for (int i=0; i<dimensionality; i++) {
			if (lowerBound[i] > other.project(i)[0])
				lowerBound[i] = other.project(i)[0];
			if (upperBound[i] < other.project(i)[1])
				upperBound[i] = other.project(i)[1];
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
	
	public double minDistance(Rectangle other){
		double distance = 0;
		if(this.intersects(other))
			return distance;
		else{
			for (int i=0; i<lowerBound.length; i++){
				if(upperBound[i]<other.lowerBound[i])
					distance+=(other.lowerBound[i]-upperBound[i])*(other.lowerBound[i]-upperBound[i]);
				else if(lowerBound[i]>other.upperBound[i])
					distance+=(lowerBound[i]-other.upperBound[i])*(lowerBound[i]-other.upperBound[i]);
			}
			return Math.sqrt(distance);
		}
	}
	
	public double maxDistance(Rectangle other){
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