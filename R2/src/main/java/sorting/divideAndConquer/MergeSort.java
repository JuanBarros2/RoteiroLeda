package sorting.divideAndConquer;

import java.util.Arrays;

import sorting.AbstractSorting;

/**
 * Merge sort is based on the divide-and-conquer paradigm. The algorithm
 * consists of recursively dividing the unsorted list in the middle, sorting
 * each sublist, and then merging them into one single sorted list. Notice that
 * if the list has length == 1, it is already sorted.
 */
public class MergeSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (array != null && leftIndex < rightIndex && rightIndex < array.length && array.length > 1) {
			int mid = (rightIndex + leftIndex) / 2;
			sort(array, leftIndex, mid);
			sort(array, mid + 1, rightIndex);
			merge(array, leftIndex, rightIndex, mid);
			
		}
	}
	
	public void merge(T[] array, int leftIndex, int rightIndex, int mid){
		T[] auxiliar = Arrays.copyOf(array, array.length);
		
		int firstHalfIndex = leftIndex;
		int secondHalfIndex = mid + 1;
		int arrayIndex = leftIndex;
		
		while (firstHalfIndex <= mid && secondHalfIndex <= rightIndex){
			if (auxiliar[firstHalfIndex].compareTo(auxiliar[secondHalfIndex]) <= 0){
				array[arrayIndex] = auxiliar[firstHalfIndex];
				firstHalfIndex++;
			} else {
				array[arrayIndex] = auxiliar[secondHalfIndex];
				secondHalfIndex++;
				
			}
			arrayIndex++;
		}
		
		while(firstHalfIndex <= mid){
			array[arrayIndex] = auxiliar[firstHalfIndex];
			firstHalfIndex++;
			arrayIndex++;
		}
		while(secondHalfIndex <= rightIndex){
			array[arrayIndex] = auxiliar[secondHalfIndex];
			secondHalfIndex++;
			arrayIndex++;
		}
	}
}
