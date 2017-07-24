package adt.bst.extended;

import java.util.Comparator;
import java.util.LinkedList;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bst.BSTNode.Builder;

/**
 * Implementacao de SortComparatorBST, uma BST que usa um comparator interno em
 * suas funcionalidades e possui um metodo de ordenar um array dado como
 * parametro, retornando o resultado do percurso desejado que produz o array
 * ordenado.
 * 
 * @author Adalberto
 *
 * @param <T>
 */
public class SortComparatorBSTImpl<T extends Comparable<T>> extends BSTImpl<T> implements SortComparatorBST<T> {

	private Comparator<T> comparator;

	public SortComparatorBSTImpl(Comparator<T> comparator) {
		super();
		this.comparator = comparator;
	}

	@Override
	public T[] sort(T[] array) {
		T[] aux = null;
		
		if (array != null) {
			root = new Builder().build();
			
			for (T t : array) {
				insert(t);
			}
			
			aux = order();
		}

		return aux;
	}

	@Override
	public T[] reverseOrder() {

		LinkedList<T> list = listAllInReverseOrder(root, new LinkedList<T>());

		return makeArrayFromList(list);
	}

	
	private LinkedList<T> listAllInReverseOrder(BSTNode<T> node, LinkedList<T> list) {
		if (!node.isEmpty()) {
			listAllInReverseOrder((BSTNode<T>) node.getRight(), list);
			list.add(node.getData());
			listAllInReverseOrder((BSTNode<T>) node.getLeft(), list);
		}

		return list;
	}

	@Override
	protected BSTNode<T> search(T element, BSTNode<T> node) {
		BSTNode<T> aux = node;

		if (!node.isEmpty() && !node.getData().equals(element)) {
			if (comparator.compare(node.getData(), element) < 0) {
				aux = search(element, (BSTNode<T>) node.getRight());
			} else if (comparator.compare(node.getData(), element) > 0) {
				aux = search(element, (BSTNode<T>) node.getLeft());
			}
		}

		return aux;
	}

	@Override
	protected void insert(T element, BSTNode<T> node) {
		if (node.isEmpty()) {
			node.setData(element);

			BSTNode<T> nilLeft = (BSTNode<T>) new BSTNode.Builder<T>().parent(node).build();
			BSTNode<T> nilRight = (BSTNode<T>) new BSTNode.Builder<T>().parent(node).build();
			node.setLeft(nilLeft);
			node.setRight(nilRight);

		} else if (!node.getData().equals(element)) {
			if (comparator.compare(element, node.getData()) > 0) {
				insert(element, (BSTNode<T>) node.getRight());
			} else if (comparator.compare(element, node.getData()) < 0) {
				insert(element, (BSTNode<T>) node.getLeft());
			}
		}
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

}
