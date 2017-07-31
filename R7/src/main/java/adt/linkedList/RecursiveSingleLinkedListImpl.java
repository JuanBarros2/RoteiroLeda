package adt.linkedList;

public class RecursiveSingleLinkedListImpl<T> implements LinkedList<T> {

	protected T data;
	protected RecursiveSingleLinkedListImpl<T> next;

	public RecursiveSingleLinkedListImpl() {

	}

	public RecursiveSingleLinkedListImpl(T data, RecursiveSingleLinkedListImpl<T> next) {
		this.data = data;
		this.next = next;
	}

	@Override
	public boolean isEmpty() {
		return (getData() == null);
	}

	@Override
	public int size() {
		int result = 0;

		if (!isEmpty()) {
			result = 1 + getNext().size();
		}

		return result;
	}

	@Override
	public T search(T element) {
		T aux = null;
		if (!isEmpty()) {
			if (getData().equals(element)) {
				aux = getData();
			} else {
				aux = getNext().search(element);
			}
		}

		return aux;
	}

	@Override
	public void insert(T element) {
		if (isEmpty()) {
			setData(element);
			setNext(new RecursiveSingleLinkedListImpl<T>());
		} else {
			getNext().insert(element);
		}
	}

	@Override
	public void remove(T element) {
		if (!isEmpty()) {
			if (getData().equals(element)) {
				setData(getNext().getData());
				setNext(getNext().getNext());
			} else {
				getNext().remove(element);
			}
		}
	}

	@Override
	public T[] toArray() {
		int size = size();
		T[] aux = (T[]) new Object[size()];
		recursiveToArray(aux, this, 0);
		return aux;
	}

	private void recursiveToArray(T[] array, RecursiveSingleLinkedListImpl<T> node, int index) {
		if (!node.isEmpty()) {
			array[index] = node.getData();
			recursiveToArray(array, node.getNext(), index + 1);
		}
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public RecursiveSingleLinkedListImpl<T> getNext() {
		return next;
	}

	public void setNext(RecursiveSingleLinkedListImpl<T> next) {
		this.next = next;
	}

}
