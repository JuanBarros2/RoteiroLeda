package adt.linkedList;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	protected SingleLinkedListNode<T> head;

	public SingleLinkedListImpl() {
		this.head = new SingleLinkedListNode<T>();
	}

	@Override
	public boolean isEmpty() {
		return head.isNIL();
	}

	@Override
	public int size() {
		int count = 0;

		SingleLinkedListNode<T> aux = head;
		while (!aux.isNIL()) {
			count++;
			aux = aux.next;
		}

		return count;
	}

	@Override
	public T search(T element) {

		SingleLinkedListNode<T> aux = head;

		if (element != null) {
			while (!aux.isNIL() && !aux.getData().equals(element)) {
				aux = aux.getNext();
			}

		}

		return aux.getData();
	}

	@Override
	public void insert(T element) {
		if (element != null){
			SingleLinkedListNode<T> aux = head;

			while (!aux.isNIL()) {
				aux = aux.getNext();
			}

			aux.setData(element);
			aux.setNext(new SingleLinkedListNode<>());
		}
		
	}

	@Override
	public void remove(T element) {

		SingleLinkedListNode<T> aux = head;
		
		if(element != null){
			while (!aux.isNIL() && !aux.getData().equals(element)) {
				aux = aux.getNext();
			}

			if (!aux.isNIL()) {
				while (!aux.isNIL()) {
					aux.setData(aux.getNext().getData());
					aux.setNext(aux.getNext().getNext());
				}
			}
		}

		
	}

	@Override
	public T[] toArray() {
		int size = this.size();
		T[] auxArray = ((T[]) new Object[size]);

		SingleLinkedListNode<T> aux = head;

		for (int i = 0; i < size; i++) {
			auxArray[i] = aux.getData();
			aux = aux.getNext();
		}

		return auxArray;
	}

	public SingleLinkedListNode<T> getHead() {
		return head;
	}

	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}

}
