package adt.stack;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.RecursiveDoubleLinkedListImpl;

public class StackRecursiveDoubleLinkedListImpl<T> implements Stack<T> {

	protected DoubleLinkedList<T> top;
	protected int size;

	public StackRecursiveDoubleLinkedListImpl(int size) {
		this.size = size;
		this.top = new RecursiveDoubleLinkedListImpl<T>();
	}

	@Override
	public void push(T element) throws StackOverflowException {
		if (isFull()) {
			throw new StackOverflowException();
		} else {
			top.insert(element);
		}
	}

	@Override
	public T pop() throws StackUnderflowException {
		if (top.isEmpty()) {
			throw new StackUnderflowException();
		} else {
			T aux = top();
			top.removeLast();
			return aux;
		}
	}

	@Override
	public T top() {
		T[] aux = top.toArray();
		if (aux.length > 0) {
			return aux[aux.length - 1];
		}
		return null;
	}

	@Override
	public boolean isEmpty() {
		return top.isEmpty();
	}

	@Override
	public boolean isFull() {
		return size == top.size();
	}

}
