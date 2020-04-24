//ONOMA: OTHONAS GKAVARDINAS 
//AM: 2620

package main;

import java.util.Random;
import java.util.ArrayList;

class RectangleSet{
	private int dimensionality;
	private int cardinality;
	private Random randomGenerator = new Random(0);
	private Rectangle[] rectangleList;
	
	public RectangleSet(int cardinality, int dimensionality, int maxdomain, int avlen){
		this.cardinality = cardinality;
		this.dimensionality = dimensionality;
		double randomLength = 0;
		double randomBound = 0;
		rectangleList = new Rectangle[cardinality];
		double[] tempUpperBound = new double[dimensionality];
		double[] tempLowerBound = new double[dimensionality];
		for (int i=0; i<rectangleList.length; i++){
			for (int j=0; j<tempLowerBound.length; j++){
				randomLength = randomGenerator.nextDouble()*(2*avlen);
				do{
					randomBound = randomGenerator.nextDouble()*(maxdomain);
				}while(randomBound-randomLength<0);
				tempUpperBound[j] = randomBound;
				tempLowerBound[j] = randomBound - randomLength;
			}
			rectangleList[i] = new Rectangle(dimensionality,tempLowerBound,tempUpperBound);
			tempUpperBound = new double[dimensionality];
			tempLowerBound = new double[dimensionality];
		}
	}

	public int getDimensionality(){
		return dimensionality;
	}
	
	public int getCardinality(){
		return cardinality;
	}
	
	public Rectangle getRectangle(int target){
		return rectangleList[target];
	}
	
	public String toString(){
		String tempString="";
		for (int i=0; i<rectangleList.length; i++){
			tempString+=(rectangleList[i]);
			if(i<rectangleList.length-1){
				tempString+="\n";
			}
		}
		return tempString;
	}
		
	public ArrayList<Rectangle> intersectsRange(Rectangle other){
		ArrayList<Rectangle> tempArrayList = new ArrayList<Rectangle>();
		for (int i=0; i<rectangleList.length; i++){
			if(other.intersects(rectangleList[i])){
				tempArrayList.add(rectangleList[i]);
			}
		}
		return tempArrayList;
	}
	
	public ArrayList<Rectangle> insideRange(Rectangle other){
		ArrayList<Rectangle> tempArrayList = new ArrayList<Rectangle>();
		for (int i=0; i<rectangleList.length; i++){
			if(rectangleList[i].inside(other)){
				tempArrayList.add(rectangleList[i]);
			}
		}
		return tempArrayList;
	}
	
	public Rectangle MBR(){
		double[] tempLowerBound = new double[dimensionality];
		double[] tempUpperBound = new double[dimensionality];
		for (int i=0; i<dimensionality; i++){
			tempLowerBound[i] = rectangleList[0].project(i)[0];
			tempUpperBound[i] = rectangleList[0].project(i)[1];
			for (int j=0; j<rectangleList.length; j++){
				if(tempLowerBound[i]>rectangleList[j].project(i)[0]){
					tempLowerBound[i] = rectangleList[j].project(i)[0];
				}
				if(tempUpperBound[i]<rectangleList[j].project(i)[1]){
					tempUpperBound[i] = rectangleList[j].project(i)[1];
				}
			}
		}
		Rectangle tempRectangle = new Rectangle(dimensionality, tempLowerBound, tempUpperBound);
		return tempRectangle;
	}
	
	public double[] averageProjectionLength(){
		double[] tempArray = new double[dimensionality];
		for(int i=0; i<tempArray.length; i++){
			for (int j=0; j<rectangleList.length; j++){
				tempArray[i]+=rectangleList[j].project(i)[1] - rectangleList[j].project(i)[0];
			}
			tempArray[i] = tempArray[i]/(double)cardinality;
		}
		return tempArray;
	}
	
	//BONUS+++++
	public ArrayList<Rectangle[]> intersectionJoin(RectangleSet other){
		ArrayList<Rectangle[]> tempArrayList = new ArrayList<Rectangle[]>();
		for (int i=0; i<this.rectangleList.length; i++){
			for (int j=0; j<other.rectangleList.length; j++){
				if (this.rectangleList[i].intersects(other.rectangleList[j])){
					Rectangle[] tempArray = {this.rectangleList[i], other.rectangleList[j]};
					tempArrayList.add(tempArray);
				}
			}
		}
		return tempArrayList;
	}
	
	
	public ArrayList<Rectangle[]> farPairs(RectangleSet other, int dist){
		ArrayList<Rectangle[]> tempArrayList = new ArrayList<Rectangle[]>();
		for (int i=0; i<this.rectangleList.length; i++){
			for (int j=0; j<other.rectangleList.length; j++){
				if(this.rectangleList[i].mindist(other.rectangleList[j]) >= dist){
					Rectangle[] tempArray = {this.rectangleList[i], other.rectangleList[j]};
					tempArrayList.add(tempArray);
				}
			}
		}
		return tempArrayList;
	}
	
	public ArrayList<Rectangle[]> closePairs(RectangleSet other, int dist){
		ArrayList<Rectangle[]> tempArrayList = new ArrayList<Rectangle[]>();
		for (int i=0; i<this.rectangleList.length; i++){
			for (int j=0; j<other.rectangleList.length; j++){
				if(this.rectangleList[i].maxdist(other.rectangleList[j]) <= dist){
					Rectangle[] tempArray = {this.rectangleList[i], other.rectangleList[j]};
					tempArrayList.add(tempArray);
				}
			}
		}
		return tempArrayList;
	}
}