import java.awt.Point;

public class ResizableArraySet implements SetInterface {

	// Variables
	private Point[] points;
	private int count;

	// Constructors

	// default constructor
	public ResizableArraySet () {
		points = new Point[10];
	}

	// constructor allowing for specified size
	public ResizableArraySet(int size) {
		points = new Point[size];
	}


	// Methods

	/**
	 * Gets the current number of entries in this set.
	 * 
	 * @return The integer number of entries currently in the set.
	 */
	@Override
	public int getSize() {
		return count;
	}

	/**
	 * Sees whether this set is empty.
	 * 
	 * @return True if the set is empty, or false if not.
	 */
	@Override
	public boolean isEmpty() {
		if (count == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Adds a new entry to this set, avoiding duplicates.
	 * 
	 * @param newEntry
	 *            The point to be added as a new entry.
	 * @return True if the addition is successful, or false if the item already
	 *         is in the set.
	 */
	@Override
	public boolean add(Point newValue) {
		// if set is empty
		if (isEmpty()) {
			points[0] = newValue;
			count++;
			return true;
		}
		// if set not empty, check for duplicates
		if (contains(newValue)) {
			return false;
		}
		// check if set is full
		if (count == points.length) {
			// if true, create temp array to store values
			Point[] pointsTemp = points.clone();
			// double size of set
			points = new Point[points.length*2];
			// copy all values back in
			for (int i = 0; i < pointsTemp.length; i++) {
				points[i] = pointsTemp[i];
			}
			// add point, increase count
			points[count] = newValue;
			count++;
			return true;
		}
		// if set is not full, just add point
		points[count] = newValue;
		count++;
		return true;
	}

	/**
	 * Shrink the set based on capacity and count
	 * 
	 * @return The newly shrunken array
	 */
	public void shrinkSet() {
		// create temp array
		Point[] defaultTemp = points.clone();
		// check capacity
		if (points.length < 20) {
			// decrease size to 10
			points = new Point[10];
		} else {
			// if length more than 20, halve size
			points = new Point[points.length/2];
		}
		// copy values back into array
		for (int i = 0; i < points.length; i++) {
			points[i] = defaultTemp[i];
		}
	}

	/**
	 * Removes a specific entry from this set, if possible.
	 * 
	 * @param anEntry
	 *            The entry to be removed.
	 * @return True if the removal was successful, or false if not.
	 */
	@Override
	public boolean remove(Point aValue) {
		// check if set is empty
		if (isEmpty()) {
			return false;
		}
		// check for point
		for (int i = 0; i < count; i++) {
			if(points[i].equals(aValue)) {
				// remove value by putting last value
				// into spot, set last value to null
				points[i] = points[count - 1];
				points[count - 1] = null;
				count--;
				// if value found, check size of set to see if shrink
				// is needed
				if (count<= points.length/3) {
					shrinkSet();
				}
				return true;
			}
		}
		// if value not found
		return false;
	}	

	/**
	 * Removes one unspecified entry from this set, if possible.
	 * 
	 * @return Either the removed entry, if the removal was successful, or null.
	 */
	@Override
	public Point remove() {
		// if set is empty
		if (isEmpty()) {
			return null;
		}
		// remove the last item
		Point ptRmvd = new Point();
		ptRmvd = points[count - 1];
		points[count - 1] = null;
		count--;
		if (count<= points.length/3) {
			shrinkSet();
		}
		return ptRmvd;
	}

	/** Removes all entries from this set. */
	@Override
	public void clear() {
		for (int i = 0; i < count; i++) {
			points[i] = null;
		}
		count = 0;
	}

	/**
	 * Tests whether this set contains a given entry.
	 * 
	 * @param anEntry
	 *            The entry to locate.
	 * @return True if the set contains anEntry, or false if not.
	 */
	@Override
	public boolean contains(Point anEntry) {
		// if set is empty
		if (isEmpty()) {
			return false;
		}
		// check whole set
		for (int i = 0; i < count; i++) {
			if (points[i].equals(anEntry)) {
				return true;
			}
		}
		// if entry not found
		return false;
	}

	/**
	 * Computes the union of this set with a given set
	 * 
	 * @param anotherSet
	 *            another set
	 * @return the union of this set with anotherSet
	 */
	@Override
	public SetInterface union(SetInterface anotherSet) {
		// type cast anotherSet to a ResizableArraySet
		ResizableArraySet set = (ResizableArraySet)anotherSet;
		// check the size of each set
		int lengthOne = count;
		int lengthTwo = set.getSize();
		// create a set to house the union of the two sets
		SetInterface unionSet = (SetInterface) (new ResizableArraySet(lengthOne+lengthTwo));
		// fill the union (add method will prevent duplicates)
		for (int i = 0; i < lengthOne; i++) {
			unionSet.add(points[i]);
		}
		for (int i = 0; i < lengthTwo; i++) {
			unionSet.add(set.getPoints()[i]);
		}
		return unionSet;
	}

	/**
	 * Computes the intersection of this set with a given set
	 * 
	 * @param anotherSet
	 *            another set
	 * @return the intersection of this set with anotherSet
	 */
	@Override
	public SetInterface intersection(SetInterface anotherSet) {
		// type cast anotherSet to a ResizableArraySet
		ResizableArraySet set = (ResizableArraySet) anotherSet;
		// create a set to house the intersection of the two sets
		// max size of set would be the max value of the size of the sets
		int max = Math.max(count, set.getSize());
		SetInterface intersectSet = (SetInterface) (new ResizableArraySet(max));
		// check for same element, if same add to intersectSet
		// two cases: if points is bigger, run loop through points
		// if set is bigger, run loop through set
		if (max == count) {
			for (int i = 0; i < count; i++) {
				// if the two share a point
				if (set.contains(points[i])) {
					intersectSet.add(points[i]);
				}
			}
		}
		// if set is bigger or if they are equal
		for (int i = 0; i < set.getSize(); i++) {
			// if the two share a point
			if (this.contains(set.getPoints()[i])) {
				intersectSet.add(set.getPoints()[i]);
			}
		}
		return intersectSet;
	}

	/**
	 * Computes the elements in this set except for those 
	 * that are also in anotherSet
	 *
	 * @param anotherSet  another set
	 * @return the result of removing all elements of 
	 * this set that are in anotherSet
	 */
	@Override
	public SetInterface difference(SetInterface anotherSet) {
		// type cast anotherSet to a ResizableArraySet
		ResizableArraySet set = (ResizableArraySet) anotherSet;
		// create a set to house the difference of the two sets
		// max size of set would be the max value of the size of the sets
		int max = Math.max(count, set.getSize());
		SetInterface differenceSet = (SetInterface) (new ResizableArraySet(max));
		// check for same element, if not same add to differnceSet
		for (int i = 0; i < count; i++) {
			// if the two don't share a point
			if (!set.contains(points[i])) {
				differenceSet.add(points[i]);
			}
		}
		return differenceSet;


	}

	/**
	 * Retrieves all entries that are in this set.
	 * 
	 * @return A newly allocated array of all the entries in the set, where the
	 *         size of the array is equal to the number of entries in the set.
	 */
	@Override
	public Point[] toArray() {
		// create array whose size is equal to number of entries
		// in the set (no nulls)
		Point[] allPoints = new Point[count];
		// add points to array
		for (int i = 0; i < count; i++) {
			allPoints[i] = points[i];
		}
		// return array
		return allPoints;
	}

	// Getters/Setters

	// this will allow me to get the length of the set
	public Point[] getPoints() {
		return points;
	}

}
