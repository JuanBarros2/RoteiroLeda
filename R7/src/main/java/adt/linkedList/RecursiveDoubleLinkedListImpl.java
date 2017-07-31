package adt.linkedList;

public class RecursiveDoubleLinkedListImpl<T> extends RecursiveSingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	protected RecursiveDoubleLinkedListImpl<T> previous;

	public RecursiveDoubleLinkedListImpl() {
	}

	public RecursiveDoubleLinkedListImpl(T data, RecursiveSingleLinkedListImpl<T> next,
			RecursiveDoubleLinkedListImpl<T> previous) {
		super(data, next);
		this.previous = previous;
	}

	@Override
	public void insertFirst(T element) {
		if (isEmpty()) {
			setData(element);
			setNext(new RecursiveDoubleLinkedListImpl<T>());
			setPrevious(new RecursiveDoubleLinkedListImpl<>());
		} else {
			RecursiveDoubleLinkedListImpl<T> aux = new RecursiveDoubleLinkedListImpl<>();
			aux.setData(getData());
			aux.setNext(getNext());
			((RecursiveDoubleLinkedListImpl<T>) aux.getNext()).setPrevious(aux);

			aux.setPrevious(this);

			setData(element);
			setNext(aux);
		}
	}

	@Override
	public void insert(T element) {
		if (isEmpty()) {
			setData(element);
			setNext(new RecursiveDoubleLinkedListImpl<T>());
			((RecursiveDoubleLinkedListImpl<T>) getNext()).setPrevious(this);
			if (getPrevious() == null) {
				setPrevious(new RecursiveDoubleLinkedListImpl<T>());
			}
		} else {
			getNext().insert(element);
		}
	}

	@Override
	public void remove(T element) {
		if (!isEmpty()) {
			if (element.equals(getData())) {
				setData(getNext().getData());
				setNext(getNext().getNext());
				if (isEmpty()){
					setPrevious(null);
				}
			} else {
				((RecursiveDoubleLinkedListImpl) getNext()).remove(element);
			}
		}
	}

	@Override
	public void removeFirst() {
		if (!isEmpty()) {
			setData(getNext().getData());
			setNext(getNext().getNext());

			if (getNext() != null) {
				((RecursiveDoubleLinkedListImpl<T>) getNext()).setPrevious(this);
			} else {
				setPrevious(null);
			}
		}
	}

	@Override
	public void removeLast() {
		if (!isEmpty()) {
			if (getNext().isEmpty()) {
				setData(null);
				setNext(null);
				if (getPrevious().isEmpty()) {
					setPrevious(null);
				}
			} else {
				((RecursiveDoubleLinkedListImpl) getNext()).removeLast();
			}
		}
	}

	public RecursiveDoubleLinkedListImpl<T> getPrevious() {
		return previous;
	}

	public void setPrevious(RecursiveDoubleLinkedListImpl<T> previous) {
		this.previous = previous;
	}

}
