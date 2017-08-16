package adt.heap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Before;
import org.junit.Test;

public class StudentMinHeapTest {

	HeapImpl<Integer> heap;

	@Before
	public void setUp() {
		// TODO Instancie seu comparator para fazer sua estrutura funcionar como
		// uma min heap aqui. Use instanciacao anonima da interface
		// Comparator!!!!
		Comparator<Integer> comparator = (o1, o2) -> o2.compareTo(o1);
		heap = new HeapImpl<Integer>(comparator);
	}

	@Test
	public void testBuild() {
		heap.buildHeap(new Integer[] { 82, 6, 99, 12, 34, 64, 58, 1 });

		assertEquals(8, heap.size());
		assertFalse(heap.isEmpty());

		verifyHeap(new Integer[] { 1, 6, 58, 12, 34, 64, 99, 82 });
	}

	@Test
	public void testInsert() {
		heap.insert(8);
		heap.insert(12);
		heap.insert(-2);
		heap.insert(7);
		heap.insert(8);
		heap.insert(-5);
		heap.insert(14);
		heap.insert(3);
		heap.insert(-10);
		heap.insert(0);

		assertEquals(10, heap.size());
		assertFalse(heap.isEmpty());

		verifyHeap(new Integer[] { -10, -5, -2, 3, 0, 8, 14, 12, 7, 8 });
	}

	@Test
	public void testRemove() {
		heap.insert(22);
		heap.insert(45);
		heap.insert(38);
		heap.insert(17);
		heap.insert(40);
		heap.insert(15);
		heap.insert(26);
		heap.insert(79);
		heap.insert(53);
		heap.insert(30);

		assertEquals(new Integer(15), heap.extractRootElement());
		assertEquals(new Integer(17), heap.extractRootElement());
		assertEquals(new Integer(22), heap.extractRootElement());
		assertEquals(new Integer(26), heap.extractRootElement());
		assertEquals(new Integer(30), heap.extractRootElement());

		assertEquals(5, heap.size());
		assertFalse(heap.isEmpty());

		verifyHeap(new Integer[] { 38, 40, 79, 45, 53 });
	}

	@Test
	public void testSort() {
		assertArrayEquals(new Integer[] { 5, 6, 12, 20, 34, 43, 49, 92 },
				heap.heapsort(new Integer[] { 34, 92, 5, 12, 49, 20, 43, 6 }));

		assertEquals(0, heap.size());
		assertTrue(heap.isEmpty());

		assertArrayEquals(new Integer[] {}, heap.toArray());
	}

	private void verifyHeap(Integer[] expected) {
		boolean isHeap = true;

		Comparable<Integer>[] original = heap.toArray();

		Arrays.sort(expected);
		Arrays.sort(original);

		if (Arrays.equals(expected, original) == false)
			isHeap = false;

		original = heap.toArray();

		for (int i = 0; i < original.length; i++) {
			if (2 * i + 1 < original.length && original[i].compareTo((Integer) original[2 * i + 1]) > 0)
				isHeap = false;
			if (2 * i + 2 < original.length && original[i].compareTo((Integer) original[2 * i + 2]) > 0)
				isHeap = false;
		}

		assertTrue(isHeap);
	}

	@Test
	public void mergeArrays() {
		Integer[] odd = new Integer[] { 1, 3, 5, 7, 9, 11, 13, 15, 17, 19 };
		Integer[] even = new Integer[] { 0, 2, 4, 6, 8, 12, 14 };

		assertArrayEquals(new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15, 17, 19 },
				heap.mergeArrays(odd, even));
	}

	@Test
	public void testSortLevel() {
		heap.insert(8);
		heap.insert(12);
		heap.insert(-2);
		heap.insert(7);
		heap.insert(8);
		heap.insert(-5);
		heap.insert(14);
		heap.insert(3);
		heap.insert(-10);
		heap.insert(0);

		verifyHeap(new Integer[] { -10, -5, -2, 3, 0, 8, 14, 12, 7, 8 });

		assertArrayEquals(new Integer[] { -10 }, heap.sortLevel(0));
		assertArrayEquals(new Integer[] { -5, -2 }, heap.sortLevel(1));
		assertArrayEquals(new Integer[] { 0, 3, 8, 14 }, heap.sortLevel(2));
		assertArrayEquals(new Integer[] { 7, 8, 12 }, heap.sortLevel(3));
	}
}
