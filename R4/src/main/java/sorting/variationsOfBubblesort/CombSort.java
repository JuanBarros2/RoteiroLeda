package sorting.variationsOfBubblesort;

import sorting.AbstractSorting;
import util.Util;

/**
 * The combsort algoritm.
 */
public class CombSort<T extends Comparable<T>> extends AbstractSorting<T> {
	private float fator = 1.25f;

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (array.length > 1 && leftIndex < rightIndex && rightIndex < array.length && leftIndex >= 0) {
			int gap = rightIndex - leftIndex;
			while (gap > 0) {
				gap = (int) (gap / fator);
				for(int i = leftIndex; i + gap <= rightIndex; i++){
					if (array[i].compareTo(array[i + gap]) > 0){
						Util.swap(array, i, i + gap);
					}
				}
			}
		}
	}
}
