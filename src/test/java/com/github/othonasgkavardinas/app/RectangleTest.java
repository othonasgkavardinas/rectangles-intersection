package com.github.othonasgkavardinas.app;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RectangleTest {

	@Test
	public void copyTest() {
		Rectangle rectangle1 = new Rectangle(2, new double[] { 1, 2 }, new double[] { 3, 4 });
		Rectangle rectangle2 = rectangle1.copy();
		assertNotEquals(rectangle1, rectangle2);
		assertEquals(rectangle1.getDimensionality(), rectangle2.getDimensionality());
		for(int i=0; i<rectangle1.getDimensionality(); i++)
			assertArrayEquals(rectangle1.project(i), rectangle2.project(i), 0);		
	}
	
	@Test
	public void projectTest() {
		Rectangle rectangle = new Rectangle(2, new double[] { 1, 2 }, new double[] { 3, 4 });		
		assertArrayEquals(rectangle.project(0), new double[] { 1, 3 }, 0);
		assertArrayEquals(rectangle.project(1), new double[] { 2, 4 }, 0);
	}
	
	@Test
	public void enlargeTest() {
		Rectangle rectangle1 = new Rectangle(2, new double[] { 3, 2 }, new double[] { 4, 5 });		
		Rectangle rectangle2 = new Rectangle(2, new double[] { 1, 3 }, new double[] { 4, 8 });		
		rectangle1.enlarge(rectangle2);
		assertArrayEquals(rectangle1.project(0), new double[] { 1, 4 }, 0);
		assertArrayEquals(rectangle1.project(1), new double[] { 2, 8 }, 0);
	}
	
	@Test
	public void intersectsTest() {
		Rectangle rectangle1 = new Rectangle(2, new double[] { 1, 1 }, new double[] { 5, 5 });
		Rectangle rectangle2 = new Rectangle(2, new double[] { 3, 3 }, new double[] { 8, 8 });
		Rectangle rectangle3 = new Rectangle(2, new double[] { 9, 9 }, new double[] { 10, 10 });
		assertTrue(rectangle1.intersects(rectangle2));
		assertFalse(rectangle1.intersects(rectangle3));
	}
	
	@Test
	public void insideTest() {
		Rectangle rectangle1 = new Rectangle(2, new double[] { 1, 1 }, new double[] { 5, 5 });
		Rectangle rectangle2 = new Rectangle(2, new double[] { 2, 2 }, new double[] { 5, 5 });
		Rectangle rectangle3 = new Rectangle(2, new double[] { 3, 3 }, new double[] { 8, 8 });
		assertTrue(rectangle2.inside(rectangle1));
		assertFalse(rectangle3.inside(rectangle1));
	}
	
	@Test
	public void minDistTest() {
		Rectangle rectangle1 = new Rectangle(2, new double[] { 1, 2 }, new double[] { 3, 4 });
		Rectangle rectangle2 = new Rectangle(2, new double[] { 25, 26 }, new double[] { 40, 43 });
		assertEquals((int)rectangle1.minDistance(rectangle2), 31);
	}

	@Test
	public void maxDistTest() {
		Rectangle rectangle1 = new Rectangle(2, new double[] { 1, 2 }, new double[] { 3, 4 });
		Rectangle rectangle2 = new Rectangle(2, new double[] { 25, 26 }, new double[] { 40, 43 });
		assertEquals((int)rectangle1.maxDistance(rectangle2), 56);
	}
}
