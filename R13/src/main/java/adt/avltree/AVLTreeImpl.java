package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

/**
 * 
 * Performs consistency validations within a AVL Tree instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements AVLTree<T> {

	protected int calculateBalance(BSTNode<T> node) {
		int balance = 0;

		if (node.getData() != null) {
			balance = height((BSTNode<T>) node.getLeft()) - height((BSTNode<T>) node.getRight());
		}

		return balance;
	}

	protected void rebalance(BSTNode<T> node) {
		int balance = calculateBalance(node);

		// LEFT
		if (balance > 1) {
			// LL
			int balanceSon = calculateBalance((BSTNode<T>) node.getLeft());

			if (balanceSon <= -1) {
				Util.leftRotation((BSTNode<T>) node.getLeft());
			}

			if (node == root) {
				root = (BSTNode<T>) node.getLeft();
			}
			Util.rightRotation(node);

		} // RIGHT
		else if (balance < -1) {
			int balanceSon = calculateBalance((BSTNode<T>) node.getRight());
			// RL
			if (balanceSon >= 1) {
				Util.rightRotation((BSTNode<T>) node.getRight());
			}
			
			if (node == root) {
				root = (BSTNode<T>) node.getRight();
			}
			Util.leftRotation(node);
		}
	}

	// AUXILIARY
	protected void rebalanceUp(BSTNode<T> node) {
		BSTNode<T> aux = (BSTNode<T>) node.getParent();

		if (aux != null) {
			rebalance(aux);
			rebalanceUp(aux);
		}
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
			if (element.compareTo(node.getData()) > 0) {
				insert(element, (BSTNode<T>) node.getRight());
			} else if (element.compareTo(node.getData()) < 0) {
				insert(element, (BSTNode<T>) node.getLeft());
			}
			rebalance(node);
		}
	}

	@Override
	public void remove(BSTNode<T> node) {
		if (node.isLeaf()) {
			node.setData(null);
			node.setLeft(null);
			node.setRight(null);
			rebalanceUp(node);
		} else if (!node.getLeft().isEmpty() && !node.getRight().isEmpty()) {
			BSTNode<T> sucessor = sucessorRecursivo(node);
			node.setData(sucessor.getData());
			remove(sucessor);
			rebalanceUp(node);
		} else {
			removeOneLeaf(node);
			rebalanceUp(node);
		}
	}

}
