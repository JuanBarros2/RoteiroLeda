package adt.avltree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import adt.bst.BSTNode;
import adt.bt.Util;

public class AVLCountAndFillImpl<T extends Comparable<T>> extends AVLTreeImpl<T> implements AVLCountAndFill<T> {

	private int LLcounter;
	private int LRcounter;
	private int RRcounter;
	private int RLcounter;

	public AVLCountAndFillImpl() {
		LLcounter = 0;
		LRcounter = 0;
		RRcounter = 0;
		RLcounter = 0;
	}

	@Override
	public int LLcount() {
		return LLcounter;
	}

	@Override
	public int LRcount() {
		return LRcounter;
	}

	public boolean isBalanced(){
		return (Math.abs(calculateBalance(root)) <= 1);
	}
	
	@Override
 	public int RRcount() {
		return RRcounter;
	}

	@Override
	public int RLcount() {
		return RLcounter;
	}

	@Override
	public void fillWithoutRebalance(T[] array) {
		if (array != null){
			Arrays.sort(array);
			T[] sortTree = this.order();
			resetTree();
			T[] mergedTree = Util.makeArrayOfComparable(array.length + sortTree.length);
			System.arraycopy(array, 0, mergedTree, 0, array.length);
			System.arraycopy(array, 0, mergedTree, array.length, sortTree.length);
			quickInsert(mergedTree, 0, mergedTree.length - 1);
		}
	}

	public void quickInsert(T[] array, int leftIndex, int rightIndex) {
		if (array != null && leftIndex < rightIndex && rightIndex - leftIndex > 1 && rightIndex < array.length && array.length > 0) {
			int pivo = (leftIndex + rightIndex) / 2;
			int leftPivo = (pivo + leftIndex) / 2;
			int rightPivo = (pivo + rightIndex) / 2;
			insertWithoutBalance(array[pivo], root);
			insertWithoutBalance(array[leftPivo], root);
			insertWithoutBalance(array[rightPivo], root);

			quickInsert(array, leftIndex, pivo);
			quickInsert(array, pivo, rightIndex);
		}

	}
	

	protected void insertWithoutBalance(T element, BSTNode<T> node) {
		if (node.isEmpty()) {
			node.setData(element);

			BSTNode<T> nilLeft = (BSTNode<T>) new BSTNode.Builder<T>().parent(node).build();
			BSTNode<T> nilRight = (BSTNode<T>) new BSTNode.Builder<T>().parent(node).build();
			node.setLeft(nilLeft);
			node.setRight(nilRight);

		} else if (!node.getData().equals(element)) {
			if (element.compareTo(node.getData()) > 0) {
				insertWithoutBalance(element, (BSTNode<T>) node.getRight());
			} else if (element.compareTo(node.getData()) < 0) {
				insertWithoutBalance(element, (BSTNode<T>) node.getLeft());
			}

		}
	}

	@Override
	protected void rebalance(BSTNode<T> node) {
		int balance = calculateBalance(node);

		// LEFT
		if (balance > 1) {
			// LL
			int balanceSon = calculateBalance((BSTNode<T>) node.getLeft());

			if (balanceSon <= -1) {
				Util.leftRotation((BSTNode<T>) node.getLeft());
				LRcounter++;
			} else {
				LLcounter++;
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
				RLcounter++;
			} else {
				RRcounter++;
			}

			if (node == root) {
				root = (BSTNode<T>) node.getRight();
			}
			Util.leftRotation(node);
		}
	}

	private void resetTree() {
		root = new BSTNode<T>();
		LLcounter = 0;
		LRcounter = 0;
		RRcounter = 0;
		RLcounter = 0;
	}
}
