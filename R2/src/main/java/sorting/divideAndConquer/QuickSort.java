package sorting.divideAndConquer;

import sorting.AbstractSorting;
import util.Util;

/**
 * Quicksort is based on the divide-and-conquer paradigm. The algorithm chooses
 * a pivot element and rearranges the elements of the interval in such a way
 * that all elements lesser than the pivot go to the left part of the array and
 * all elements greater than the pivot, go to the right part of the array. Then
 * it recursively sorts the left and the right parts. Notice that if the list
 * has length == 1, it is already sorted.
 */
public class QuickSort<T extends Comparable<T>> extends AbstractSorting<T> {

   @Override
   public void sort(T[] array, int leftIndex, int rightIndex) {
      if (array != null && leftIndex < rightIndex && rightIndex < array.length && array.length > 0) {
         int pivo = rightIndex;
         int aux = leftIndex;
         for (int i = leftIndex; i != pivo; i++) {
            if (array[i].compareTo(array[pivo]) < 0) {
               Util.swap(array, aux, i);
               aux++;
            }
         }
         Util.swap(array, pivo, aux);
         sort(array, leftIndex, aux - 1);
         sort(array, aux + 1, rightIndex);

      }

   }
}
