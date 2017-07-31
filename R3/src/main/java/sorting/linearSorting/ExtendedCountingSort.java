package sorting.linearSorting;

import java.util.Arrays;

import sorting.AbstractSorting;

/**
 * Classe que implementa do Counting Sort vista em sala. Desta vez este
 * algoritmo deve satisfazer os seguitnes requisitos: - Alocar o tamanho minimo
 * possivel para o array de contadores (C) - Ser capaz de ordenar arrays
 * contendo numeros negativos
 */
public class ExtendedCountingSort extends AbstractSorting<Integer> {
	
	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {
		if (array != null && array.length > 1 && leftIndex < rightIndex && rightIndex < array.length) {
			int maior = retornaMaior(array, leftIndex, rightIndex);
			int menor = retornaMenor(array, leftIndex, rightIndex);
			int tamanho = maior - menor + 1;
			Integer[] aux = new Integer[tamanho];

			for (int i = 0; i != tamanho; i++) {
				aux[i] = 0;
			}

			for (int i = leftIndex; i <= rightIndex; i++) {
				aux[array[i] - menor]++;
			}

			for (int i = 1; i != tamanho; i++) {
				aux[i] += aux[i - 1];
			}

			Integer[] result = Arrays.copyOf(array, array.length);

			for (int i = leftIndex; i <= rightIndex; i++) {
				int index = array[i] - menor;
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

	private int retornaMenor(Integer[] array, int left, int right) {
		int menor = array[left];
		for (int i = left; i <= right; i++) {
			if (array[i] < menor) {
				menor = array[i];
			}
		}
		return menor;
	}

}
