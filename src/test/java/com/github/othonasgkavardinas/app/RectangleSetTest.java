package com.github.othonasgkavardinas.app;


import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;

import org.junit.Test;

public class RectangleSetTest {

	@Test
	public void intersectsRangeTest() {
		double[][][] expectedResults = { {
			{ 16, 24 }, 
			{ 48, 55 } },
			{ { 38, 48 },
			{ 65, 73 } } };
		
		RectangleSet rset = new RectangleSet(10, 2, 100, 5);
		Rectangle range = new Rectangle(2, new double[] { 20, 20 }, new double[] { 70, 70 });
		ArrayList<Rectangle> intsectres = rset.intersectsRange(range);
		for(int i=0; i<intsectres.size(); i++)
			for(int j=0; j<intsectres.get(i).getDimensionality(); j++)
				assertArrayEquals(intsectres.get(i).project(j), expectedResults[i][j], 1);
	}
	
	@Test
	public void insideRangeTest() {
		double[][][] expectedResults = { {
			{ 16, 24 },
			{ 48, 55 } } };
		
		RectangleSet rset = new RectangleSet(10, 2, 100, 5);
		Rectangle range = new Rectangle(2, new double[] { 10, 20 }, new double[] { 10, 70 });
		
		ArrayList<Rectangle> inres = rset.insideRange(range);		
		for (int i=0; i<inres.size(); i++)
			for(int j=0; j<inres.get(i).getDimensionality(); j++)
				assertArrayEquals(inres.get(i).project(j), expectedResults[i][j], 1);	
	}

	@Test
	public void mbrTest() {
		double[][] expectedResults = { {
			2, 0 },
			{ 7, 0 } };
		
		RectangleSet rset = new RectangleSet(10, 2, 100, 5);
		Rectangle mbr = rset.mbr();		
		for(int i=0; i<mbr.getDimensionality(); i++)
				assertArrayEquals(mbr.project(i), expectedResults[i], 1);	
	}
	
	@Test
	public void averageProjectionLengthTest() {
		RectangleSet rset = new RectangleSet(10, 2, 100, 5);
		double projlen [] = rset.averageProjectionLength();
		assertArrayEquals(projlen, new double[] { 5, 5}, 1);
	}

	@Test
	public void intersectionJoinTest() {
		double[][][] expectedResults = { { { 16.743963693382018, 24.05364156714859 }, { 48.66952625826231, 55.04370051176339 } }, { { 27.34638716969296, 33.32183994766498 }, { 94.6322621725737, 98.48415401998089 } }, { { 27.34638716969296, 33.32183994766498 }, { 94.6322621725737, 98.48415401998089 } }, { { 85.33309276948663, 94.12491794821143 }, { 10.140175427022825, 12.889715087377674 } }, { { 0.8577956719237634, 2.3238122483889456 }, { 90.98128849570034, 96.448686067685 } }, { { 61.46972948404621, 62.51463634655593 }, { 73.52326717258263, 77.63122912749326 } }, { { 38.81605684586645, 48.72328470301428 }, { 65.85279296627607, 73.31520701949938 } }, { { 75.71606429060859, 83.88903500470182 }, { 84.66650681510069, 89.93350116114935 } }, { { 6.966841416622567, 8.30623982249149 }, { 62.449968517406475, 72.23571191888487 } }, { { 7.171728391554933, 14.322038530059677 }, { 2.520253303765182, 7.149831487989411 } }, { { 30.392763055658712, 33.87696535357536 }, { 88.56113337162479, 97.15469888517127 } }, { { 30.392763055658712, 33.87696535357536 }, { 88.56113337162479, 97.15469888517127 } } };

		RectangleSet rset = new RectangleSet(10, 2, 100, 5);
		RectangleSet rset2 = new RectangleSet(10, 2, 100, 5);
		ArrayList<Rectangle[]> jres = rset.intersectionJoin(rset2);
		for (int i=0; i<jres.size(); i++)
			for(int j=0; j<jres.get(i)[0].getDimensionality(); j++)
				assertArrayEquals(jres.get(i)[0].project(j), expectedResults[i][j], 0);	
	}
	
	@Test
	public void farPairsTest() {
		double[][][] expectedResults = { { { 85.33309276948663, 94.12491794821143 }, { 10.140175427022825, 12.889715087377674 } }, { { 0.8577956719237634, 2.3238122483889456 }, { 90.98128849570034, 96.448686067685 } } };
				
		RectangleSet rset = new RectangleSet(10, 2, 100, 5);
		RectangleSet rset2 = new RectangleSet(10, 2, 100, 5);
		ArrayList<Rectangle []> farpairs = rset.farPairs(rset2,100);
		for (int i=0; i<farpairs.size(); i++)
			for(int j=0; j<farpairs.get(i)[0].getDimensionality(); j++)
				assertArrayEquals(farpairs.get(i)[0].project(j), expectedResults[i][j], 0);	
	}
	
	@Test
	public void closePairsTest() {
		double[][][] expectedResults = { { { 16.743963693382018, 24.05364156714859 }, { 48.66952625826231, 55.04370051176339 } }, { { 27.34638716969296, 33.32183994766498 }, { 94.6322621725737, 98.48415401998089 } }, { { 27.34638716969296, 33.32183994766498 }, { 94.6322621725737, 98.48415401998089 } }, { { 85.33309276948663, 94.12491794821143 }, { 10.140175427022825, 12.889715087377674 } }, { { 0.8577956719237634, 2.3238122483889456 }, { 90.98128849570034, 96.448686067685 } }, { { 61.46972948404621, 62.51463634655593 }, { 73.52326717258263, 77.63122912749326 } }, { { 38.81605684586645, 48.72328470301428 }, { 65.85279296627607, 73.31520701949938 } }, { { 75.71606429060859, 83.88903500470182 }, { 84.66650681510069, 89.93350116114935 } }, { { 6.966841416622567, 8.30623982249149 }, { 62.449968517406475, 72.23571191888487 } }, { { 7.171728391554933, 14.322038530059677 }, { 2.520253303765182, 7.149831487989411 } }, { { 30.392763055658712, 33.87696535357536 }, { 88.56113337162479, 97.15469888517127 } }, { { 30.392763055658712, 33.87696535357536 }, { 88.56113337162479, 97.15469888517127 } } };

		RectangleSet rset = new RectangleSet(10, 2, 100, 5);
		RectangleSet rset2 = new RectangleSet(10, 2, 100, 5);
		ArrayList<Rectangle []> closepairs = rset.closePairs(rset2,10);
		for (int i=0; i<closepairs.size(); i++)
			for(int j=0; j<closepairs.get(i)[0].getDimensionality(); j++)
				assertArrayEquals(closepairs.get(i)[0].project(j), expectedResults[i][j], 0);	
	}
}
