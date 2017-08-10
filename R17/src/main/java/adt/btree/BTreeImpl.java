package adt.btree;

import java.util.LinkedList;
import java.util.List;

public class BTreeImpl<T extends Comparable<T>> implements BTree<T> {

	protected BNode<T> root;
	protected int order;

	public BTreeImpl(int order) {
		this.order = order;
		this.root = new BNode<T>(order);
	}

	@Override
	public BNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return this.root.isEmpty();
	}

	@Override
	public int height() {
		return height(this.root, -1);
	}

	private int height(BNode<T> node, int result) {
		if (!node.isEmpty()) {
			result++;
			if (!node.isLeaf()) {
				result = height(node.getChildren().get(0), result);
			}
		}

		return result;
	}

	@Override
	public BNode<T>[] depthLeftOrder() {
		List<BNode<T>> list = new LinkedList<BNode<T>>();
		depthLeftOrder(root, list);
		return makeArrayFromListNode(list);
	}

	private void depthLeftOrder(BNode<T> node, List<BNode<T>> list) {
		if (!node.isEmpty()) {
			list.add(node);

			for (int i = 0; i < node.getChildren().size(); i++) {
				depthLeftOrder(node.getChildren().get(i), list);
			}
		}
	}

	protected BNode<T>[] makeArrayFromListNode(List<BNode<T>> list) {

		int size = list.size();
		BNode<T>[] array = new BNode[size];
		for (int i = 0; i != size; i++) {
			array[i] = list.get(i);
		}
		return array;
	}

	@Override
	public int size() {
		return size(root);
	}

	protected int size(BNode<T> node) {
		int result = node.getElements().size();

		if (result != 0) {
			for (BNode<T> child : node.getChildren()) {
				result += size(child);
			}
		}

		return result;
	}

	@Override
	public BNodePosition<T> search(T element) {
		BNodePosition<T> result = null;
		if (element != null) {
			result = search(root, element);
		}
		return result;
	}

	protected BNodePosition<T> search(BNode<T> node, T key) {
		BNodePosition<T> result = new BNodePosition<>();
		int i = 0;
		int sizeElementsNode = node.getElements().size();

		if (sizeElementsNode != 0) {
			while (i < sizeElementsNode && node.getElementAt(i).compareTo(key) < 0) {
				i++;
			}

			if (i < sizeElementsNode && key.equals(node.getElementAt(i))) {
				result = new BNodePosition<>(node, i);
			}
		}

		return result;
	}

	public void insert(T element) {
		if (element != null) {
			insert(root, element);
		}
	}

	private void insert(BNode<T> node, T value) {
		int index = 0;
		int sizeElements = node.getElements().size();

		while (index < sizeElements && value.compareTo(node.getElementAt(index)) > 0) {
			index++;
		}

		if (!node.isLeaf()) {
			if (node.isEmpty()) {
				node.addElement(value);
			} else {
				try{
					BNode<T> aux = node.getChildren().get(index);
					insert(aux, value);
				}catch(IndexOutOfBoundsException e){
					
				}				
			}

		} else {
			node.addElement(value);
			while (node.size() > node.getMaxKeys()) {
				node = splitReturn(node);
				if (node.getParent() == null) {
					root = node;
				}
			}
		}
	}

	private void split(BNode<T> node) {
	};

	private BNode<T> splitReturn(BNode<T> node) {
		int size = node.size();
		int half = size / 2;
		BNode<T> left = splitInterval(0, half, node);
		BNode<T> right = splitInterval(half, size, node);

		BNode<T> parent = node.getParent();
		if (parent == null) {
			parent = new BNode<T>(order);
			parent.getChildren().add(node);
		}

		int i = parent.indexOfChild(node);
		parent.removeChild(node);

		left.setParent(parent);
		right.setParent(parent);
		parent.addChild(i, left);
		parent.addChild(i + 1, right);
		node = right;
		promote(node);
		return parent;
	}

	private BNode<T> splitInterval(int init, int end, BNode<T> node) {
		BNode<T> result = new BNode<>(order);

		for (int i = init; i != end; i++) {
			result.addElement(node.getElementAt(i));
		}
		int size = node.getChildren().size();
		for (int i = init; i != init + Math.min(end, size); i++) {
			result.addChild(i - init, node.getChildren().get(i));
		}

		return result;
	}

	private void promote(BNode<T> node) {
		T element = node.getElements().removeFirst();
		node.getParent().addElement(element);
		if(node.isEmpty()){
			node.getParent().removeChild(node);
		}
	}

	// NAO PRECISA IMPLEMENTAR OS METODOS ABAIXO
	@Override
	public BNode<T> maximum(BNode<T> node) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public BNode<T> minimum(BNode<T> node) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public void remove(T element) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

}
