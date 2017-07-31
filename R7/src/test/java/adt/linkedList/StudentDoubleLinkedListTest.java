package adt.linkedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StudentDoubleLinkedListTest {

	private DoubleLinkedList<Integer> lista1;
	private DoubleLinkedList<Integer> lista2;
	private DoubleLinkedList<Integer> lista3;

	@Before
	public void setUp() throws Exception {
		this.getImplementations();

		// Lista com 3 elementos.
		lista1.insert(3);
		lista1.insert(2);
		lista1.insert(1);

		// Lista com 1 elemento.
		lista3.insert(1);
	}

	private void getImplementations() {
		// TODO O aluno deve ajustar aqui para instanciar sua implementação
		lista1 = new RecursiveDoubleLinkedListImpl<>();
		lista2 = new RecursiveDoubleLinkedListImpl<>();
		lista3 = new RecursiveDoubleLinkedListImpl<>();
	}

	// Métodos de DoubleLinkedList

	@Test
	public void testInsertFirst() {
		lista1.insertFirst(4);
		Assert.assertArrayEquals(new Integer[] { 4, 3, 2, 1 }, lista1.toArray());
	}

	@Test
	public void testRemoveFirst() {
		lista1.removeFirst();
		Assert.assertArrayEquals(new Integer[] { 2, 1 }, lista1.toArray());
	}

	@Test
	public void testRemoveLast() {
		lista1.removeLast();
		Assert.assertArrayEquals(new Integer[] { 3, 2 }, lista1.toArray());
	}

	@Test
	public void testIsEmpty() {
		Assert.assertFalse(lista1.isEmpty());
		Assert.assertTrue(lista2.isEmpty());
	}

	@Test
	public void testSize() {
		Assert.assertEquals(3, lista1.size());
		Assert.assertEquals(0, lista2.size());
	}

	@Test
	public void testSearch() {
		Assert.assertTrue(2 == lista1.search(2));
		Assert.assertNull(lista1.search(4));
		Assert.assertFalse(3 == lista1.search(2));
	}

	@Test
	public void testInsert() {
		Assert.assertEquals(3, lista1.size());
		lista1.insert(5);
		lista1.insert(7);
		Assert.assertEquals(5, lista1.size());

		Assert.assertEquals(0, lista2.size());
		lista2.insert(4);
		lista2.insert(7);
		Assert.assertEquals(2, lista2.size());
	}

	@Test
	public void testRemove() {
		Assert.assertEquals(3, lista1.size());
		lista1.remove(2);
		lista1.remove(1);
		Assert.assertEquals(1, lista1.size());

		lista2.insert(3);
		Assert.assertArrayEquals(new Integer[] { 3 }, lista2.toArray());
		lista2.remove(3);
		Assert.assertEquals(lista2.size(), 0);
		lista2.remove(3);
		Assert.assertArrayEquals(new Integer[] {}, lista2.toArray());
		Assert.assertEquals(lista2.size(), 0);
		lista2.insert(1);
		lista2.insert(2);
		lista2.remove(2);
		Assert.assertArrayEquals(new Integer[] { 1 }, lista2.toArray());
		lista2.insert(2);
		lista2.remove(1);
		Assert.assertArrayEquals(new Integer[] { 2 }, lista2.toArray());
		lista2.remove(2);
	}

	@Test
	public void testToArray() {
		Assert.assertArrayEquals(new Integer[] {}, lista2.toArray());
		Assert.assertArrayEquals(new Integer[] { 3, 2, 1 }, lista1.toArray());
	}

	@Test
	public void testAll() {
		Assert.assertEquals(lista2.size(), 0);
		Assert.assertArrayEquals(new Integer[] {}, lista2.toArray());
		lista2.insert(3);
		Assert.assertArrayEquals(new Integer[] { 3 }, lista2.toArray());
		Assert.assertEquals(lista2.size(), 1);
		lista2.insert(4);
		Assert.assertArrayEquals(new Integer[] { 3, 4 }, lista2.toArray());
		Assert.assertEquals(lista2.size(), 2);
		lista2.insert(4);
		Assert.assertArrayEquals(new Integer[] { 3, 4, 4 }, lista2.toArray());
		Assert.assertEquals(lista2.size(), 3);
		lista2.remove(4);
		Assert.assertArrayEquals(new Integer[] { 3, 4 }, lista2.toArray());
		Assert.assertEquals(lista2.size(), 2);
		lista2.remove(4);
		Assert.assertArrayEquals(new Integer[] { 3 }, lista2.toArray());
		Assert.assertEquals(lista2.size(), 1);
		lista2.remove(4);
		Assert.assertArrayEquals(new Integer[] { 3 }, lista2.toArray());
		Assert.assertEquals(lista2.size(), 1);
		lista2.remove(3);
		Assert.assertArrayEquals(new Integer[] {}, lista2.toArray());
		Assert.assertEquals(lista2.size(), 0);
	}

	@Test
	public void testFirsts() {
		lista2.insertFirst(1);
		Assert.assertArrayEquals(new Integer[] { 1 }, lista2.toArray());
		lista2.insertFirst(2);
		Assert.assertArrayEquals(new Integer[] { 2, 1 }, lista2.toArray());
		lista2.insert(2);
		Assert.assertArrayEquals(new Integer[] { 2, 1, 2 }, lista2.toArray());
		lista2.removeFirst();
		Assert.assertArrayEquals(new Integer[] { 1, 2 }, lista2.toArray());
		lista2.removeLast();
		Assert.assertArrayEquals(new Integer[] { 1 }, lista2.toArray());
		lista2.removeFirst();
		Assert.assertArrayEquals(new Integer[] {}, lista2.toArray());

		lista2.insert(1);
		lista2.insertFirst(2);
		Assert.assertArrayEquals(new Integer[] { 2, 1 }, lista2.toArray());
	}

	@Test
	public void testLasts() {
		lista2.insert(1);
		Assert.assertArrayEquals(new Integer[] { 1 }, lista2.toArray());
		lista2.insert(2);
		Assert.assertArrayEquals(new Integer[] { 1, 2 }, lista2.toArray());
		lista2.insert(2);
		Assert.assertArrayEquals(new Integer[] { 1, 2, 2 }, lista2.toArray());
		lista2.removeLast();
		Assert.assertArrayEquals(new Integer[] { 1, 2 }, lista2.toArray());
		lista2.removeLast();
		Assert.assertArrayEquals(new Integer[] { 1 }, lista2.toArray());
		lista2.removeFirst();
		Assert.assertArrayEquals(new Integer[] {}, lista2.toArray());

		lista2.insert(1);
		lista2.insert(1);
		Assert.assertArrayEquals(new Integer[] { 1, 1 }, lista2.toArray());
	}
}