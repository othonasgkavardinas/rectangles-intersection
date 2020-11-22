//ONOMA: OTHONAS GKAVARDINAS 
//AM: 2620

package main;

import java.util.ArrayList;

class RectanglesTest
{
	public static void main(String args[])
	{
		RectangleSet rset = new RectangleSet(10, 2, 100, 5);

		// print rectangle set
		System.out.println("Generated collection 1 of rectangles:\n");
		System.out.println(rset);
		System.out.println("\n\n");
		
		// test range query
		double lbounds[] = {20,20};
		double ubounds[] = {70,70};
		Rectangle range = new Rectangle(2, lbounds, ubounds);

		// range intersection test
		ArrayList<Rectangle> intsectres = rset.intersectsRange(range);		
		System.out.println("Results of range intersection with "+range+" :\n");
		for (Rectangle r: intsectres)
			System.out.println(r+"\n");
		System.out.println("");
		
		// inside range test
		ArrayList<Rectangle> inres = rset.insideRange(range);		
		System.out.println("Rectangles inside "+range+" :\n");
		for (Rectangle r: inres)
			System.out.println(r);
		System.out.println("\n");
		
		// test MBR
		System.out.println();
		System.out.println("MBR of Collection 1:");
		System.out.println(rset.mbr());
		System.out.println("\n");
		
		// test projection
		double projlen [] = rset.averageProjectionLength();
		for (int j=0; j<rset.getDimensionality(); j++)
			System.out.println("Collection 1, average projection length at dimension " + j + " = " + projlen[j]);

		// test intersection join
		System.out.println();
		RectangleSet rset2 = new RectangleSet(10, 2, 100, 5);
		System.out.println("Generated collection 2 of rectangles:");
		System.out.println(rset2);
		ArrayList<Rectangle []> jres = rset.intersectionJoin(rset2);
		System.out.println("Intersection join results:");
		for (Rectangle[] p: jres)
			System.out.println(p[0].toString() + p[1].toString());

		// test mindist join
		System.out.println();
		ArrayList<Rectangle []> farpairs = rset.farPairs(rset2,100);
		System.out.println("Far pairs:");
		for (Rectangle[] p: farpairs)
			System.out.println(p[0].toString() + p[1].toString());

		// test maxdist join
		System.out.println();
		ArrayList<Rectangle []> closepairs = rset.closePairs(rset2,10);
		System.out.println("Close pairs:");
		for (Rectangle[] p: closepairs)
			System.out.println(p[0].toString() + p[1].toString());
	}
}

