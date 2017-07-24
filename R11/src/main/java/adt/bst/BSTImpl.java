package adt.bst;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import adt.bst.BSTNode.Builder;
import util.Util;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		return height(root);
	}

	private int height(BSTNode<T> node) {
		int result = -1;

		if (!node.isEmpty()) {
			int heightLeft = height((BSTNode<T>) node.getLeft());
			int heightRight = height((BSTNode<T>) node.getRight());
			result = Math.max(heightLeft, heightRight) + 1;
		}

		return result;
	}

	@Override
	public BSTNode<T> search(T element) {
		BSTNode<T> aux = null;
		if (element != null) {
			aux = search(element, root);
		}

		return aux;
	}

	protected BSTNode<T> search(T element, BSTNode<T> node) {
		BSTNode<T> aux = node;

		if (!node.isEmpty() && !node.getData().equals(element)) {
			if (node.getData().compareTo(element) < 0) {
				aux = search(element, (BSTNode<T>) node.getRight());
			} else if (node.getData().compareTo(element) > 0) {
				aux = search(element, (BSTNode<T>) node.getLeft());
			}
		}

		return aux;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			insert(element, root);
		}
	}

	protected void insert(T element, BSTNode<T> node) {
		if (node.isEmpty()) {
			node.setData(element);

			BSTNode<T> nilLeft = (BSTNode<T>) new BSTNode.Builder<T>().parent(node).build();
			BSTNode<T> nilRight = (BSTNode<T>) new BSTNode.Builder<T>().parent(node).build();
			node.setLeft(nilLeft);
			node.setRight(nilRight);

		} else if (!node.getData().equals(element)) {
			if (element.compareTo(node.getData()) > 0) {
				insert(element, (BSTNode<T>) node.getRight());
			} else if (element.compareTo(node.getData()) < 0) {
				insert(element, (BSTNode<T>) node.getLeft());
			}

		}
	}

	@Override
	public BSTNode<T> maximum() {
		return maximumLocal(root);
	}

	private BSTNode<T> maximumLocal(BSTNode<T> node) {
		BSTNode<T> aux = node;

		if (node.isEmpty()) {
			aux = null;
		} else {
			while (aux.getRight() != null && !aux.getRight().isEmpty()) {
				aux = (BSTNode<T>) aux.getRight();
			}
		}

		return aux;
	}

	@Override
	public BSTNode<T> minimum() {
		return mininumLocal(root);
	}

	private BSTNode<T> mininumLocal(BSTNode<T> node) {
		BSTNode<T> aux = node;

		if (node.isEmpty()) {
			aux = null;
		} else {
			while (aux.getLeft() != null && !aux.getLeft().isEmpty()) {
				aux = (BSTNode<T>) aux.getLeft();
			}
		}

		return aux;
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> result = search(element);

		result = sucessorRecursivo(result);

		return result;
	}

	private BSTNode<T> sucessorRecursivo(BSTNode<T> node) {
		if (node.isEmpty()) {
			node = null;
		} else {
			if (!node.getRight().isEmpty()) {
				return mininumLocal((BSTNode<T>) node.getRight());
			}
			return getFirstLeftUp(node);
		}
		return null;
	}

	private BSTNode<T> getFirstLeftUp(BSTNode<T> node) {
		BSTNode<T> parent = (BSTNode<T>) node.getParent();
		if (parent != null && parent.getRight().equals(node)) {
			return getFirstLeftUp(parent);
		}
		return parent;
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> result = search(element);

		if (result.isEmpty()) {
			result = null;
		} else {
			if (!result.getLeft().isEmpty()) {
				result = maximumLocal((BSTNode<T>) result.getLeft());
			} else {
				BSTNode<T> temp = result;
				result = (BSTNode<T>) result.getParent();

				while (result != null && result.getLeft().equals(temp)) {
					temp = result;
					result = (BSTNode<T>) result.getParent();
				}

			}
		}
		return result;
	}

	@Override
	public void remove(T element) {
		if (element != null) {
			BSTNode<T> node = search(element);
			if (!node.isEmpty())
				remove(node);
		}
	}

	public void remove(BSTNode<T> node) {
		if (node.isLeaf()) {
			node.setData(null);
			node.setLeft(null);
			node.setRight(null);
		} else if (!node.getLeft().isEmpty() && !node.getRight().isEmpty()) {
			BSTNode<T> sucessor = sucessorRecursivo(node);
			node.setData(sucessor.getData());
			remove(sucessor);
		} else {
			removeOneLeaf(node);
		}
	}

	private void removeOneLeaf(BSTNode<T> node) {
		BSTNode<T> aux = null;

		if (!node.getLeft().isEmpty()) {
			aux = (BSTNode<T>) node.getLeft();
		} else {
			aux = (BSTNode<T>) node.getRight();
		}

		if (node.getParent() == null) {
			aux.setParent(null);
			root = aux;
		} else {

			if (isLeftSon(node)) {
				node.getParent().setLeft(aux);
			} else {
				node.getParent().setRight(aux);
			}

			aux.setParent(node.getParent());
		}
	}

	private boolean isLeftSon(BSTNode<T> node) {
		return !node.getParent().isEmpty() && !node.getParent().getLeft().isEmpty()
				&& node.getParent().getLeft().getData().equals(node.getData());
	}

	@Override
	public T[] preOrder() {
		LinkedList<T> list = listAllInPreOrder(root, new LinkedList<T>());

		return makeArrayFromList(list);
	}

	private LinkedList<T> listAllInPreOrder(BSTNode<T> node, LinkedList<T> list) {
		if (!node.isEmpty()) {
			list.add(node.getData());
			listAllInPreOrder((BSTNode<T>) node.getLeft(), list);
			listAllInPreOrder((BSTNode<T>) node.getRight(), list);
		}

		return list;
	}

	@Override
	public T[] order() {

		LinkedList<T> list = listAllInOrder(root, new LinkedList<T>());

		return makeArrayFromList(list);
	}

	private LinkedList<T> listAllInOrder(BSTNode<T> node, LinkedList<T> list) {
		if (!node.isEmpty()) {
			listAllInOrder((BSTNode<T>) node.getLeft(), list);
			list.add(node.getData());
			listAllInOrder((BSTNode<T>) node.getRight(), list);
		}

		return list;
	}

	@Override
	public T[] postOrder() {
		LinkedList<T> list = listAllInPostOrder(root, new LinkedList<T>());

		return makeArrayFromList(list);
	}

	private LinkedList<T> listAllInPostOrder(BSTNode<T> node, LinkedList<T> list) {
		if (!node.isEmpty()) {
			listAllInPostOrder((BSTNode<T>) node.getLeft(), list);
			listAllInPostOrder((BSTNode<T>) node.getRight(), list);
			list.add(node.getData());
		}

		return list;
	}

	protected T[] makeArrayFromList(List<T> list) {

		int size = list.size();
		T[] array = Util.makeArrayOfComparable(size);
		for (int i = 0; i != size; i++) {
			array[i] = list.get(i);
		}
		return array;
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft()) + size((BSTNode<T>) node.getRight());
		}
		return result;
	}

}