package adt.skipList;

public class SkipListImpl<T> implements SkipList<T> {

	protected SkipListNode<T> root;
	protected SkipListNode<T> NIL;

	protected int maxHeight;

	protected double PROBABILITY = 0.5;

	public SkipListImpl(int maxHeight) {
		this.maxHeight = maxHeight;
		root = new SkipListNode(Integer.MIN_VALUE, maxHeight, null);
		NIL = new SkipListNode(Integer.MAX_VALUE, maxHeight, null);
		connectRootToNil();
	}

	/**
	 * Faz a ligacao inicial entre os apontadores forward do ROOT e o NIL Caso
	 * esteja-se usando o level do ROOT igual ao maxLevel esse metodo deve
	 * conectar todos os forward. Senao o ROOT eh inicializado com level=1 e o
	 * metodo deve conectar apenas o forward[0].
	 */
	private void connectRootToNil() {
		for (int i = 0; i < maxHeight; i++) {
			root.forward[i] = NIL;
		}
	}

	@Override
	public void insert(int key, T newValue, int height) {
		if (height >= 0 && height <= maxHeight && newValue != null && key < Integer.MAX_VALUE
				&& key > Integer.MIN_VALUE) {
			SkipListNode<T>[] updateNodes = new SkipListNode[maxHeight];
			SkipListNode<T> aux = root;
			for (int i = maxHeight - 1; i >= 0; i--) {
				while (aux.getForward(i).getKey() < key) {
					aux = aux.getForward(i);
				}
				updateNodes[i] = aux;
			}

			aux = aux.getForward(0);

			if (aux.getKey() == key) {
				aux.setValue(newValue);
			} else {
				aux = new SkipListNode<T>(key, height, newValue);
				for (int i = 0; i != height; i++) {
					aux.forward[i] = updateNodes[i].forward[i];
					updateNodes[i].forward[i] = aux;
				}
			}

		}
	}

	@Override
	public void remove(int key) {
		if (key < Integer.MAX_VALUE && key > Integer.MIN_VALUE) {
			SkipListNode<T>[] updateNodes = new SkipListNode[maxHeight];

			SkipListNode<T> aux = root;
			for (int i = maxHeight - 1; i >= 0; i--) {
				while (aux.getForward(i).getKey() < key) {
					aux = aux.getForward(i);
				}
				updateNodes[i] = aux;
			}

			aux = aux.getForward(0);

			if (aux.getKey() == key) {
				int i = 0;
				while (i < aux.forward.length) {
					if (updateNodes[i].forward[i] != aux) {
						break;
					}
					updateNodes[i].forward[i] = aux.forward[i];
					i++;
				}
			}

		}

	}

	@Override
	public int height() {
		int result = maxHeight - 1;

		while (result >= 0 && root.getForward(result) == NIL) {
			result--;
		}

		return result + 1;
	}

	@Override
	public SkipListNode<T> search(int key) {
		SkipListNode<T> result = null;

		SkipListNode<T> aux = root;
		for (int i = maxHeight - 1; i >= 0; i--) {
			while (aux.getForward(i).getKey() < key) {
				aux = aux.getForward(i);
			}
		}

		aux = aux.getForward(0);
		if (aux.getKey() == key) {
			result = aux;
		}

		return result;
	}

	@Override
	public int size() {
		int result = 0;
		SkipListNode<T> aux = root.getForward(0);

		while (aux != NIL) {
			result++;
			aux = aux.getForward(0);
		}

		return result;
	}

	@Override
	public SkipListNode<T>[] toArray() {
		SkipListNode<T>[] array = new SkipListNode[size() + 2];

		SkipListNode<T> aux = root;

		for (int i = 0; i != array.length; i++) {
			array[i] = aux;
			aux = aux.getForward(0);
		}

		return array;
	}

	public void changeHeight(int key, int height) {
		if (height >= 0 && height <= maxHeight && key < Integer.MAX_VALUE && key > Integer.MIN_VALUE) {
			SkipListNode<T>[] updateNodes = new SkipListNode[maxHeight];

			SkipListNode<T> aux = root;
			for (int i = maxHeight - 1; i >= 0; i--) {
				while (aux.getForward(i).getKey() < key) {
					aux = aux.getForward(i);
				}
				updateNodes[i] = aux;
			}

			aux = aux.getForward(0);

			if (aux.getKey() == key && height != aux.getForward().length) {
				SkipListNode<T>[] forwards = aux.getForward();
				
				if (height > forwards.length) {
					aux.forward = new SkipListNode[height];
					for (int i = 0; i != height; i++) {
						if (updateNodes[i].forward[i] != aux) {
							aux.forward[i] = updateNodes[i].forward[i];
							updateNodes[i].forward[i] = aux;
						} else{
							aux.forward[i] = forwards[i];
						}
					}
				} else {
					int lastHeight = forwards.length;
					aux.forward = new SkipListNode[height];
					for (int i = 0; i != lastHeight; i++) {
						if (i >= height) {
							updateNodes[i].forward[i] = forwards[i];
						} else{
							aux.forward[i] = forwards[i];
						}
					}
				}
			}

		}
	}

	public SkipListNode<T>[] toArrayLevel() {
		SkipListNode<T>[] array = new SkipListNode[size()];

		int index = 0;
		for (int i = maxHeight - 1; i >= 0; i--) {
			SkipListNode<T> aux = root.getForward(i);
			while (aux != NIL) {
				if (aux.getForward().length == i + 1) {
					array[index] = aux;
					index++;
				}

				aux = aux.getForward(i);
			}
		}

		return array;
	}

}
