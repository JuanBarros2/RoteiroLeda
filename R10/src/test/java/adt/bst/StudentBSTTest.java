package adt.bst;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import adt.bst.BSTImpl;
import adt.bt.BTNode;

public class StudentBSTTest {

	private BSTImpl<Integer> tree;
	private BTNode<Integer> NIL = new BTNode<Integer>();

	private void fillTree() {
		Integer[] array = { 6, 23, -34, 5, 9, 2, 0, 76, 12, 67, 232, -40 };
		for (int i : array) {
			tree.insert(i);
		}
	}

	@Before
	public void setUp() {
		tree = new BSTImpl<>();
	}

	@Test
	public void testInit() {
		assertTrue(tree.isEmpty());
		assertEquals(0, tree.size());
		assertEquals(-1, tree.height());

		assertEquals(NIL, tree.getRoot());

		assertArrayEquals(new Integer[] {}, tree.order());
		assertArrayEquals(new Integer[] {}, tree.preOrder());
		assertArrayEquals(new Integer[] {}, tree.postOrder());

		assertEquals(NIL, tree.search(12));
		assertEquals(NIL, tree.search(-23));
		assertEquals(NIL, tree.search(0));

		assertEquals(null, tree.minimum());
		assertEquals(null, tree.maximum());

		assertEquals(null, tree.sucessor(12));
		assertEquals(null, tree.sucessor(-23));
		assertEquals(null, tree.sucessor(0));

		assertEquals(null, tree.predecessor(12));
		assertEquals(null, tree.predecessor(-23));
		assertEquals(null, tree.predecessor(0));
	}

	@Test
	public void testMinMax() {
		tree.insert(6);
		assertEquals(new Integer(6), tree.minimum().getData());
		assertEquals(new Integer(6), tree.maximum().getData());

		tree.insert(23);
		assertEquals(new Integer(6), tree.minimum().getData());
		assertEquals(new Integer(23), tree.maximum().getData());

		tree.insert(-34);
		assertEquals(new Integer(-34), tree.minimum().getData());
		assertEquals(new Integer(23), tree.maximum().getData());

		tree.insert(5);
		assertEquals(new Integer(-34), tree.minimum().getData());
		assertEquals(new Integer(23), tree.maximum().getData());

		tree.insert(9);
		assertEquals(new Integer(-34), tree.minimum().getData());
		assertEquals(new Integer(23), tree.maximum().getData());
	}

	@Test
	public void testSucessorPredecessor() {

		fillTree(); // -40 -34 0 2 5 6 9 12 23 67 76 232

		assertEquals(null, tree.predecessor(-40));
		assertEquals(new Integer(-34), tree.sucessor(-40).getData());

		assertEquals(new Integer(-40), tree.predecessor(-34).getData());
		assertEquals(new Integer(0), tree.sucessor(-34).getData());

		assertEquals(new Integer(-34), tree.predecessor(0).getData());
		assertEquals(new Integer(2), tree.sucessor(0).getData());

		assertEquals(new Integer(0), tree.predecessor(2).getData());
		assertEquals(new Integer(5), tree.sucessor(2).getData());
	}

	@Test
	public void testSize() {
		fillTree(); // -40 -34 0 2 5 6 9 12 23 67 76 232

		int size = 12;
		assertEquals(size, tree.size());

		while (!tree.isEmpty()) {
			tree.remove(tree.getRoot().getData());
			assertEquals(--size, tree.size());
		}
	}

	@Test
	public void testHeight() {
		fillTree(); // -40 -34 0 2 5 6 9 12 23 67 76 232

		Integer[] preOrder = new Integer[] { 6, -34, -40, 5, 2, 0, 23, 9, 12,
				76, 67, 232 };
		assertArrayEquals(preOrder, tree.preOrder());
		assertEquals(4, tree.height());

		tree.remove(0);
		assertEquals(3, tree.height());

		tree.remove(2);
		assertEquals(3, tree.height());
	}

	@Test
	public void testRemove() {
		fillTree(); // -40 -34 0 2 5 6 9 12 23 67 76 232

		Integer[] order = { -40, -34, 0, 2, 5, 6, 9, 12, 23, 67, 76, 232 };
		assertArrayEquals(order, tree.order());

		tree.remove(6);
		order = new Integer[] { -40, -34, 0, 2, 5, 9, 12, 23, 67, 76, 232 };
		assertArrayEquals(order, tree.order());

		tree.remove(9);
		order = new Integer[] { -40, -34, 0, 2, 5, 12, 23, 67, 76, 232 };
		assertArrayEquals(order, tree.order());

		assertEquals(NIL, tree.search(6));
		assertEquals(NIL, tree.search(9));

	}

	@Test
	public void testSearch() {

		fillTree(); // -40 -34 0 2 5 6 9 12 23 67 76 232

		assertEquals(new Integer(-40), tree.search(-40).getData());
		assertEquals(new Integer(-34), tree.search(-34).getData());
		assertEquals(NIL, tree.search(2534));
	}
	
	
	@Test
	public void testEquals() {
		fillTree();
		
		BSTImpl<Integer> aux = new BSTImpl();
		Integer[] array = { 6, 23, -34, 5, 9, 2, 0, 76, 12, 67, 232, -40 };
		for (int i : array) {
			aux.insert(i);
		}
		assertEquals(tree, aux);
		aux.remove(67);
		assertNotEquals(tree, aux);
		
	}
	
	
	@Test
	public void testDecendent(){
		fillTree();
		assertTrue(tree.isDecendent(2, -34));
		assertTrue(tree.isDecendent(76, 23));
		assertTrue(tree.isDecendent(0, 5));
		assertTrue(tree.isDecendent(12, 9));
		assertTrue(tree.isDecendent(232, 6));
		
		assertFalse(tree.isDecendent(5,23));
		assertFalse(tree.isDecendent(-40, 232));
		assertFalse(tree.isDecendent(12, 76));
		assertFalse(tree.isDecendent(1, 6));
		assertFalse(tree.isDecendent(-34, 23));
		assertFalse(tree.isDecendent(22, 33));
		
		
	}
	
	@Test
	public void testLevel() {
		fillTree();
		Integer[] level = new Integer[] { -40, 5, 9, 76 };

		assertArrayEquals(level, tree.arrayLevel(2));

		level = new Integer[] { 2, 12, 67, 232 };

		assertArrayEquals(level, tree.arrayLevel(3));

		level = new Integer[] { 0 };

		assertArrayEquals(level, tree.arrayLevel(4));
	}

	@Test
	public void testDistance() {
		fillTree();
		
		assertEquals(2, tree.distance(2, -34));
		assertEquals(1, tree.distance(76, 23));
		assertEquals(2, tree.distance(0, 5));
		assertEquals(1, tree.distance(12, 9));
		assertEquals(3, tree.distance(232, 6));
		assertEquals(0, tree.distance(6, 6));
		assertEquals(0, tree.distance(232, 232));
		assertEquals(4, tree.distance(-40, 76));
		assertEquals(2, tree.distance(232, 23));
		assertEquals(6, tree.distance(67, 2));
		assertEquals(5, tree.distance(0, 23));
		assertEquals(0, tree.distance(5, 5));
		assertEquals(5, tree.distance(12, 5));
		assertEquals(6, tree.distance(2, 232));
		assertEquals(1, tree.distance(12, 9));
		assertEquals(4, tree.distance(76, -40));
	}
}
