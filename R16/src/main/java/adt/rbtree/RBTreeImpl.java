package adt.rbtree;

import java.util.LinkedList;
import java.util.List;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;
import adt.rbtree.RBNode.Colour;

public class RBTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements RBTree<T> {

	public RBTreeImpl() {
		this.root = new RBNode<T>();
	}

	protected int blackHeight() {
		return blackHeight((RBNode<T>) root);
	}

	protected int blackHeight(RBNode<T> node) {
		int result = 0;

		if (!node.isEmpty()) {
			if (node.getColour() == Colour.BLACK) {
				result++;
			}

			result += blackHeight((RBNode<T>) node.getLeft());
		}

		return result;
	}

	protected boolean verifyProperties() {
		boolean resp = verifyNodesColour() && verifyNILNodeColour() && verifyRootColour() && verifyChildrenOfRedNodes()
				&& verifyBlackHeight();

		return resp;
	}

	/**
	 * The colour of each node of a RB tree is black or red. This is guaranteed
	 * by the type Colour.
	 */
	private boolean verifyNodesColour() {
		return true; // already implemented
	}

	/**
	 * The colour of the root must be black.
	 */
	private boolean verifyRootColour() {
		return ((RBNode<T>) root).getColour() == Colour.BLACK; // already
																// implemented
	}

	/**
	 * This is guaranteed by the constructor.
	 */
	private boolean verifyNILNodeColour() {
		return true; // already implemented
	}

	/**
	 * Verifies the property for all RED nodes: the children of a red node must
	 * be BLACK.
	 */
	private boolean verifyChildrenOfRedNodes() {
		return verifyChildrenOfRedNodes((RBNode<T>) root);
	}

	private boolean verifyChildrenOfRedNodes(RBNode<T> node) {
		boolean result = true;

		if (!node.isEmpty()) {
			RBNode<T> left = (RBNode<T>) node.getLeft();
			RBNode<T> right = (RBNode<T>) node.getRight();
			if (node.getColour() == Colour.RED) {
				result = left.getColour() == Colour.BLACK && right.getColour() == Colour.BLACK;
			}

			if (result) {
				result = verifyChildrenOfRedNodes((RBNode<T>) node.getLeft())
						&& verifyChildrenOfRedNodes((RBNode<T>) node.getRight());
			}
		}

		return result;
	}

	/**
	 * Verifies the black-height property from the root. The method blackHeight
	 * returns an exception if the black heights are different.
	 */
	private boolean verifyBlackHeight() {
		int blackHeight = blackHeight();
		boolean result = verifyBlackHeight((RBNode<T>) root, 0, blackHeight);
		if (!result){
			throw new RuntimeException();
		}
		return result;
	}

	private boolean verifyBlackHeight(RBNode<T> node, int height, int blackheight) {
		boolean result = true;

		if (!node.isEmpty()) {
			RBNode<T> left = (RBNode<T>) node.getLeft();
			RBNode<T> right = (RBNode<T>) node.getRight();
			if (node.getColour() == Colour.BLACK) {
				height++;
			}

			result = verifyBlackHeight((RBNode<T>) node.getLeft(), height, blackheight)
					&& verifyBlackHeight((RBNode<T>) node.getRight(), height, blackheight);

		} else {
			result = height == blackheight;
		}

		return result;
	}

	@Override
	public void insert(T value) {
		if (value != null) {
			RBNode<T> node = insert(value, (RBNode<T>) root);
			if (node != null) {
				node.setColour(Colour.RED);
				fixUpCase1(node);
			}

		}
	}

	protected RBNode<T> insert(T element, RBNode<T> node) {
		if (node.isEmpty()) {
			RBNode<T> nilLeft = new RBNode<T>();
			nilLeft.setParent(node);
			RBNode<T> nilRight = new RBNode<T>();
			nilRight.setParent(node);

			node.setData(element);
			node.setLeft(nilLeft);
			node.setRight(nilRight);

		} else if (!node.getData().equals(element)) {
			if (element.compareTo(node.getData()) > 0) {
				node = insert(element, (RBNode<T>) node.getRight());
			} else if (element.compareTo(node.getData()) < 0) {
				node = insert(element, (RBNode<T>) node.getLeft());
			} else {
				node = null;
			}
		}

		return node;
	}

	@Override
	public RBNode<T>[] rbPreOrder() {
		LinkedList<RBNode<T>> list = listAllRBInPreOrder((RBNode<T>) root, new LinkedList<RBNode<T>>());

		return makeArrayFromListNode(list);
	}

	protected RBNode<T>[] makeArrayFromListNode(List<RBNode<T>> list) {

		int size = list.size();
		RBNode<T>[] array = new RBNode[size];
		for (int i = 0; i != size; i++) {
			array[i] = list.get(i);
		}
		return array;
	}

	private LinkedList<RBNode<T>> listAllRBInPreOrder(RBNode<T> node, LinkedList<RBNode<T>> list) {
		if (!node.isEmpty()) {
			list.add(node);
			listAllRBInPreOrder((RBNode<T>) node.getLeft(), list);
			listAllRBInPreOrder((RBNode<T>) node.getRight(), list);
		}

		return list;
	}

	// FIXUP methods
	protected void fixUpCase1(RBNode<T> node) {
		if (node.equals(root)) {
			node.setColour(Colour.BLACK);
		} else {
			fixUpCase2(node);
		}
	}

	protected void fixUpCase2(RBNode<T> node) {
		if (((RBNode<T>) node.getParent()).getColour() == Colour.BLACK) {

		} else {
			fixUpCase3(node);
		}
	}

	protected void fixUpCase3(RBNode<T> node) {
		RBNode<T> uncle = getUncle(node);
		if (uncle.getColour() == Colour.RED) {
			((RBNode<T>) node.getParent()).setColour(Colour.BLACK);
			uncle.setColour(Colour.BLACK);
			getGrandfather(node).setColour(Colour.RED);

			fixUpCase1(getGrandfather(node));
		} else {
			fixUpCase4(node);
		}
	}

	protected void fixUpCase4(RBNode<T> node) {
		RBNode<T> next = node;
		if (isRightSon(node) && isLeftSon((BSTNode<T>) node.getParent())) {
			Util.leftRotation((BSTNode<T>) node.getParent());
			next = (RBNode<T>) node.getLeft();
		} else if (isLeftSon(node) && isRightSon((BSTNode<T>) node.getParent())) {
			Util.rightRotation((BSTNode<T>) node.getParent());
			next = (RBNode<T>) node.getRight();
		}

		fixUpCase5(next);
	}

	protected void fixUpCase5(RBNode<T> node) {
		((RBNode<T>) node.getParent()).setColour(Colour.BLACK);
		RBNode<T> grand = getGrandfather(node);

		grand.setColour(Colour.RED);

		if (isLeftSon(node)) {
			Util.rightRotation(grand);
		} else {
			Util.leftRotation(grand);
		}

		resetRoot();
	}
	
	private void resetRoot(){
		if (root.getParent() != null){
			root = (BSTNode<T>) root.getParent();
			resetRoot();
		}
	}
	

	private RBNode<T> getUncle(RBNode<T> node) {
		RBNode<T> grandFatherNode = getGrandfather(node);
		RBNode<T> uncle = (RBNode<T>) grandFatherNode.getLeft();

		if (uncle.equals(node.getParent())) {
			uncle = (RBNode<T>) grandFatherNode.getRight();
		}

		return uncle;
	}

	private RBNode<T> getGrandfather(RBNode<T> node) {
		return (RBNode<T>) node.getParent().getParent();
	}

	private boolean isLeftSon(BSTNode<T> node) {
		boolean result = false;

		if (node.getParent() != null) {
			result = node.getParent().getLeft().equals(node);
		}
		return result;
	}

	private boolean isRightSon(BSTNode<T> node) {
		boolean result = false;

		if (node.getParent() != null) {
			result = node.getParent().getRight().equals(node);
		}
		return result;
	}

}
