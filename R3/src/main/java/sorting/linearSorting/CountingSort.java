package sorting.linearSorting;

import java.util.Arrays;

import sorting.AbstractSorting;

/**
 * Classe que implementa a estratégia de Counting Sort vista em sala. Procure
 * evitar desperdicio de memoria alocando o array de contadores com o tamanho
 * sendo o máximo inteiro presente no array a ser ordenado.
 * 
 */
public class CountingSort extends AbstractSorting<Integer> {

   @Override
   public void sort(Integer[] array, int leftIndex, int rightIndex) {
      if (array != null && array.length > 1 && leftIndex < rightIndex && rightIndex < array.length) {
         int maior = retornaMaior(array, leftIndex, rightIndex);
         Integer[] aux = new Integer[maior + 1];

         for (int i = 0; i != maior + 1; i++) {
            aux[i] = 0;
         }

         for (int i = leftIndex; i <= rightIndex; i++) {
            aux[array[i]]++;
         }

         for (int i = 1; i <= maior ; i++) {
            aux[i] += aux[i - 1];
         }

         Integer[] result = Arrays.copyOf(array, array.length);

         for (int i = leftIndex; i <= rightIndex; i++) {
            int index = array[i];
            int indexAux = aux[index];
            result[leftIndex + indexAux - 1] = array[i];
            aux[index]--;
         }

         for (int i = leftIndex; i <= rightIndex; i++) {
            array[i] = result[i];
         }
      }

   }

   private int retornaMaior(Integer[] array, int left, int right) {
      int maior = array[left];
      for (int i = left; i <= right; i++) {
         if (array[i] > maior) {
            maior = array[i];
         }
      }
      return maior;
   }

}
