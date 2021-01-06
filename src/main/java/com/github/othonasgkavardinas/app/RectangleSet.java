package com.github.othonasgkavardinas.app;

import java.util.Random;

import lombok.Getter;

import java.util.ArrayList;

public class RectangleSet{
	private @Getter int dimensionality;
	private @Getter int cardinality;
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
				do
					randomBound = randomGenerator.nextDouble()*(maxdomain);
				while(randomBound-randomLength<0);
				tempUpperBound[j] = randomBound;
				tempLowerBound[j] = randomBound - randomLength;
			}
			rectangleList[i] = new Rectangle(dimensionality,tempLowerBound,tempUpperBound);
			tempUpperBound = new double[dimensionality];
			tempLowerBound = new double[dimensionality];
		}
	}
	
	public Rectangle findRectangle(int target){
		return rectangleList[target];
	}
	
	public String toString(){
		String tempString="";
		for (int i=0; i<rectangleList.length; i++){
			tempString+=(rectangleList[i]);
			if(i<rectangleList.length-1)
				tempString+="\n";
		}
		return tempString;
	}
		
	public ArrayList<Rectangle> intersectsRange(Rectangle other){
		ArrayList<Rectangle> tempArrayList = new ArrayList<Rectangle>();
		for (int i=0; i<rectangleList.length; i++)
			if(other.intersects(rectangleList[i]))
				tempArrayList.add(rectangleList[i]);
		return tempArrayList;
	}
	
	public ArrayList<Rectangle> insideRange(Rectangle other){
		ArrayList<Rectangle> tempArrayList = new ArrayList<Rectangle>();
		for (int i=0; i<rectangleList.length; i++)
			if(rectangleList[i].inside(other))
				tempArrayList.add(rectangleList[i]);
		return tempArrayList;
	}
	
	public Rectangle mbr(){
		double[] tempLowerBound = new double[dimensionality];
		double[] tempUpperBound = new double[dimensionality];
		int[] dimensions = { 0, 1 };
		for (int dimension: dimensions)
			for (int i=0; i<dimensionality; i++){
				tempLowerBound[i] = rectangleList[dimension].project(i)[dimension];
				for (int j=0; j<rectangleList.length; j++)
					if(tempLowerBound[i]>rectangleList[j].project(i)[dimension])
						tempLowerBound[i] = rectangleList[j].project(i)[dimension];
			}
		return new Rectangle(dimensionality, tempLowerBound, tempUpperBound);
	}
	
	public double[] averageProjectionLength(){
		double[] tempArray = new double[dimensionality];
		for(int i=0; i<tempArray.length; i++){
			for (int j=0; j<rectangleList.length; j++)
				tempArray[i]+=rectangleList[j].project(i)[1] - rectangleList[j].project(i)[0];
			tempArray[i] = tempArray[i]/(double)cardinality;
		}
		return tempArray;
	}
	
	//BONUS+++++
	public ArrayList<Rectangle[]> intersectionJoin(RectangleSet other){
		Consumer consumer = (x, y) -> x.intersects(y);
		return makeListOfRectangles(other, consumer);
	}
	
	
	public ArrayList<Rectangle[]> farPairs(RectangleSet other, int dist){
		Consumer consumer = (x, y) -> x.minDistance(y) >= dist;
		return makeListOfRectangles(other, consumer);
	}
	
	public ArrayList<Rectangle[]> closePairs(RectangleSet other, int dist){
		Consumer consumer = (x, y) -> x.maxDistance(y) <= dist;
		return makeListOfRectangles(other, consumer);
	}
	
	public ArrayList<Rectangle[]> makeListOfRectangles(RectangleSet other, Consumer consumer) {
		ArrayList<Rectangle[]> tempArrayList = new ArrayList<Rectangle[]>();
		for (int i=0; i<rectangleList.length; i++)
			for (int j=0; j<other.rectangleList.length; j++)
				if(consumer.useMethod(rectangleList[i], rectangleList[j])){
					Rectangle[] tempArray = {rectangleList[i], other.rectangleList[j]};
					tempArrayList.add(tempArray);
				}
		return tempArrayList;
	}
	
	private interface Consumer {
		boolean useMethod(Rectangle r1, Rectangle r2);
	}
}
