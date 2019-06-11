import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

public class SetTester {

	@Test
	public void testGetSize() {
		ResizableArraySet as = new ResizableArraySet();
		// add points and check size each time
		as.add(new Point(3,4));
		assertTrue(as.getSize() == 1);
		as.add(new Point(3,4));
		assertTrue(as.getSize() == 1);
		as.add(new Point(1,4));
		as.add(new Point(2,4));
		assertTrue(as.getSize() == 3);
	}

	@Test
	public void testIsEmpty() {
		ResizableArraySet as = new ResizableArraySet();
		assertTrue(as.getSize() == 0);
		assertTrue(as.isEmpty());
		// check false after new Point added to empty set
		as.add(new Point(1,1));
		assertFalse(as.isEmpty());
	}

	@Test
	public void testAdd() {
		// create a blank set, default capacity of 10, count = 0
		ResizableArraySet set = new ResizableArraySet();
		// add a point and check the size
		Point pt1 = new Point(7,7);
		assertTrue(set.add(pt1));
		assertEquals(1, set.getSize());

		// ensure no duplicates can be added. test should return false
		assertFalse(set.add(pt1));

		// remove element to make array empty
		assertTrue(set.remove(pt1));

		// fill up array
		for (int i = 0; i < set.getPoints().length; i++) {
			assertTrue(set.add(new Point(i,i)));
			assertEquals(i+1, set.getSize());
		}

		// check if size doubled after adding another element to full array
		assertTrue(set.add(new Point(50,50)));
		assertEquals(20, set.getPoints().length);
	}

	@Test
	public void testRemovePoint() {
		// create a blank set, default capacity of 10, count = 0
		ResizableArraySet set = new ResizableArraySet();
		// try to remove a point from the set, should return false
		Point pt1 = new Point(1,1);
		assertFalse(set.remove(pt1));

		// add the point and check the size
		assertTrue(set.add(pt1));
		assertEquals(1, set.getSize());
		// remove the point and check the size
		assertTrue(set.remove(pt1));
		assertEquals(0, set.getSize());

		// add three points and check size
		Point pt2 = new Point(2,2);
		Point pt3 = new Point(3,3);
		assertTrue(set.add(pt1));
		assertTrue(set.add(pt2));
		assertTrue(set.add(pt3));
		assertEquals(3, set.getSize());

		// remove the last point and check size
		assertTrue(set.remove(pt3));
		assertEquals(2, set.getSize());

		// remove the new last point and check size
		assertTrue(set.remove(pt2));
		assertEquals(1, set.getSize());

		// create new blank set of size 11 to test shrinking
		ResizableArraySet set2 = new ResizableArraySet(11);
		// add one element
		assertTrue(set2.add(pt3));
		assertEquals(11, set2.getPoints().length);
		// remove an element, see if length is now 10
		assertTrue(set2.remove(pt3));
		assertEquals(10, set2.getPoints().length);

		// create new blank set of size 21 to test shrinking
		ResizableArraySet set3 = new ResizableArraySet(28);
		// add one element
		assertTrue(set3.add(pt3));
		assertEquals(28, set3.getPoints().length);
		// remove an element, see if length is now 10
		assertTrue(set3.remove(pt3));
		assertEquals(14, set3.getPoints().length);
	}

	@Test
	public void testRemove() {
		// create a blank set, default capacity of 10, count = 0
		ResizableArraySet set = new ResizableArraySet();
		// try to remove a point from the set, size shouldn't change
		Point pt1 = new Point(1,1);
		set.remove();
		assertEquals(10, set.getPoints().length);

		// add the point and check the size
		assertTrue(set.add(pt1));
		assertEquals(1, set.getSize());
		// remove the point and check the size
		set.remove();
		assertEquals(0, set.getSize());

		// add three points and check size
		Point pt2 = new Point(2,2);
		Point pt3 = new Point(3,3);
		assertTrue(set.add(pt1));
		assertTrue(set.add(pt2));
		assertTrue(set.add(pt3));
		assertEquals(3, set.getSize());

		// remove the last point and check size
		set.remove();
		assertEquals(2, set.getSize());

		// remove another point and check size
		set.remove();
		assertEquals(1, set.getSize());

		// create new blank set of size 11 to test shrinking
		ResizableArraySet set2 = new ResizableArraySet(11);
		// add one element
		assertTrue(set2.add(pt3));
		assertEquals(11, set2.getPoints().length);
		// remove an element, see if length is now 10
		set2.remove();
		assertEquals(10, set2.getPoints().length);

		// create new blank set of size 21 to test shrinking
		ResizableArraySet set3 = new ResizableArraySet(28);
		// add one element
		assertTrue(set3.add(pt3));
		assertEquals(28, set3.getPoints().length);
		// remove an element, see if length is now 10
		set3.remove();
		assertEquals(14, set3.getPoints().length);
	}

	@Test
	public void testClear() {
		ResizableArraySet as = new ResizableArraySet();
		// add points to set, check size
		as.add(new Point(3,4));
		as.add(new Point(1,4));
		as.add(new Point(2,4));
		assertTrue(as.getSize() == 3);
		// clear set, check size and isEmpty
		as.clear();
		assertTrue(as.getSize() == 0);
		assertTrue(as.isEmpty());
	}

	@Test
	public void testContains() {
		ResizableArraySet set = new ResizableArraySet();
		// test to see if contains an element, should return false
		Point pt1 = new Point(80, 80);
		assertFalse(set.contains(pt1));
		// add the point and see if set contains element, should be true
		assertTrue(set.add(pt1));
		assertTrue(set.contains(pt1));
		// remove the point and see if set contains, should be false
		set.remove();
		assertFalse(set.contains(pt1));
	}

	@Test
	public void testUnion() {
		// create a blank set, default capacity of 10, count = 0
		ResizableArraySet set = new ResizableArraySet();
		// create a SetInterface set
		SetInterface set2 = (SetInterface) (new ResizableArraySet());

		// fill up both sets, with mostly different elements
		for (int i = 0; i < set.getPoints().length; i++) {
			assertTrue(set.add(new Point(i,i)));
		}
		assertEquals(10, set.getSize());
		for (int i = 8; i < 19; i++) {
			assertTrue(set2.add(new Point(i,i)));
		}
		assertEquals(11, set2.getSize());

		// create the union
		// check size, there should be 19 total items
		// the one item that would not be doubled would 
		// be (9,9) since both have it so it would only
		// show up once
		assertEquals(19, set.union(set2).getSize());
	}

	@Test
	public void testIntersection() {
		// create a blank set, default capacity of 10, count = 0
		ResizableArraySet set = new ResizableArraySet();
		// create a SetInterface set
		SetInterface set2 = (SetInterface) (new ResizableArraySet());

		// fill up both sets, with mostly different elements
		for (int i = 0; i < set.getPoints().length; i++) {
			assertTrue(set.add(new Point(i,i)));
		}
		assertEquals(10, set.getSize());
		for (int i = 8; i < 19; i++) {
			assertTrue(set2.add(new Point(i,i)));
		}
		assertEquals(11, set2.getSize());

		// create the intersection
		// check size, there should be 2 total items
		// the two items that would be shared would 
		// be (8,8) and (9,9) since both have it
		assertEquals(2, set.intersection(set2).getSize());
	}

	@Test
	public void testDifference() {
		// create a blank set, default capacity of 10, count = 0
		ResizableArraySet set = new ResizableArraySet();
		// create a SetInterface set
		SetInterface set2 = (SetInterface) (new ResizableArraySet());

		// fill up both sets, with mostly different elements
		for (int i = 0; i < set.getPoints().length; i++) {
			assertTrue(set.add(new Point(i,i)));
		}
		assertEquals(10, set.getSize());
		for (int i = 8; i < 19; i++) {
			assertTrue(set2.add(new Point(i,i)));
		}
		assertEquals(11, set2.getSize());

		// create the difference
		// check size, there should be 8 total items
		// since there are 8 differences
		// between set and set2
		assertEquals(8, set.difference(set2).getSize());
	}

	@Test
	public void testToArray() {
		// create a blank set, default capacity of 10, count = 0
		ResizableArraySet set = new ResizableArraySet();
		// fill up set
		for (int i = 0; i < set.getPoints().length; i++) {
			assertTrue(set.add(new Point(i,i)));
		}
		// create array
		Point[] points = set.toArray();
		// make sure size of array is equal to size of set
		assertEquals(10, points.length);
	}

}
