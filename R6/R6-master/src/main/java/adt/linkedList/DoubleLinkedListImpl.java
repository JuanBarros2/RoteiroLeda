package adt.linkedList;

public class DoubleLinkedListImpl<T> extends SingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	protected DoubleLinkedListNode<T> last;

	@Override
	public void insertFirst(T element) {
		if (element != null) {

			DoubleLinkedListNode<T> newNode = new DoubleLinkedListNode<T>(element, (DoubleLinkedListNode<T>) head,
					new DoubleLinkedListNode<T>());

			((DoubleLinkedListNode<T>) head).setPrevious(newNode);
			this.setHead(newNode);

			if (last.isNIL()) {
				setLast(newNode);
			} else if (last.getPrevious() == null) {
				last.setPrevious(newNode);
			}

		}
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			DoubleLinkedListNode<T> aux = new DoubleLinkedListNode<T>(element, new DoubleLinkedListNode<T>(), last);

			if (isEmpty()) {
				head = aux;
				last = aux;
			} else {
				last.setNext(aux);
				last = aux;
			}
		}

	}

	@Override
	public void remove(T element) {
		if (element != null) {
			if (element.equals(last.getData())) {
				removeLast();
			} else if (element.equals(head.getData())) {
				removeFirst();
			} else {

				DoubleLinkedListNode<T> aux = (DoubleLinkedListNode<T>) head;

				while (!aux.isNIL() && !aux.getData().equals(element)) {
					aux = (DoubleLinkedListNode<T>) aux.getNext();
				}

				if (!aux.isNIL()) {
					aux.getPrevious().setNext(aux.getNext());
					((DoubleLinkedListNode<T>) aux.getNext()).setPrevious(aux.getPrevious());
				}

			}
		}
	}

	@Override
	public void removeFirst() {
		if (!isEmpty()) {

			if (size() == 1) {
				last = new DoubleLinkedListNode<T>();
				head = last;
			} else {
				DoubleLinkedListNode<T> elem = (DoubleLinkedListNode<T>) head.getNext();
				elem.setPrevious(new DoubleLinkedListNode<T>());
				this.setHead(elem);
			}

		}
	}

	@Override
	public void removeLast() {
		if (!isEmpty()) {

			if (size() == 1) {
				last = new DoubleLinkedListNode<T>();
				head = last;
			} else {
				last.getPrevious().setNext(new DoubleLinkedListNode<T>());
				last = last.getPrevious();
			}

		}
	}

	public DoubleLinkedListNode<T> getLast() {
		return last;
	}

	public void setLast(DoubleLinkedListNode<T> last) {
		this.last = last;
	}

}
