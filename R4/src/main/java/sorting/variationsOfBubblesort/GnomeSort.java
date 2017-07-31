package sorting.variationsOfBubblesort;

import sorting.AbstractSorting;
import util.Util;

/**
 * The implementation of the algorithm must be in-place!
 */
public class GnomeSort<T extends Comparable<T>> extends AbstractSorting<T> {
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (array.length > 1 && leftIndex < rightIndex && rightIndex < array.length && leftIndex >= 0) {
			for (int i = leftIndex + 1; i <= rightIndex; i++) {
				int aux = i;
				while (aux > 0 && array[aux].compareTo(array[aux - 1]) < 0) {
					Util.swap(array, aux, aux - 1);
					aux--;
				}
			}
		}
	}
}
