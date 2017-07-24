package adt.heap;

import java.util.Arrays;
import java.util.Comparator;

import util.Util;

/**
 * O comportamento de qualquer heap Ã© definido pelo heapify. Neste caso o
 * heapify dessa heap deve comparar os elementos e colocar o maior sempre no
 * topo. Ou seja, admitindo um comparador normal (responde corretamente 3 > 2),
 * essa heap deixa os elementos maiores no topo. Essa comparaÃ§Ã£o nÃ£o Ã© feita
 * diretamente com os elementos armazenados, mas sim usando um comparator. Dessa
 * forma, dependendo do comparator, a heap pode funcionar como uma max-heap ou
 * min-heap.
 */
public class HeapImpl<T extends Comparable<T>> implements Heap<T> {

   protected T[] heap;
   protected int index = -1;
   /**
    * O comparador Ã© utilizado para fazer as comparaÃ§Ãµes da heap. O ideal Ã©
    * mudar apenas o comparator e mandar reordenar a heap usando esse
    * comparator. Assim os metodos da heap nÃ£o precisam saber se vai funcionar
    * como max-heap ou min-heap.
    */
   protected Comparator<T> comparator;

   private static final int INITIAL_SIZE = 20;
   private static final int INCREASING_FACTOR = 10;

   /**
    * Construtor da classe. Note que de inicio a heap funciona como uma
    * min-heap.
    */
   @SuppressWarnings("unchecked")
   public HeapImpl(Comparator<T> comparator) {
      this.heap = (T[]) (new Comparable[INITIAL_SIZE]);
      this.comparator = comparator;
   }

   // /////////////////// METODOS IMPLEMENTADOS
   private int parent(int i) {
      return (i - 1) / 2;
   }

   /**
    * Deve retornar o indice que representa o filho a esquerda do elemento
    * indexado pela posicao i no vetor
    */
   private int left(int i) {
      return (i * 2 + 1);
   }

   /**
    * Deve retornar o indice que representa o filho a direita do elemento
    * indexado pela posicao i no vetor
    */
   private int right(int i) {
      return (i * 2 + 1) + 1;
   }

   @Override
   public boolean isEmpty() {
      return (index == -1);
   }

   @Override
   public T[] toArray() {
      @SuppressWarnings("unchecked")
      T[] resp = Util.makeArrayOfComparable(index + 1);
      for (int i = 0; i <= index; i++) {
         resp[i] = this.heap[i];
      }
      return resp;
   }

   // ///////////// METODOS A IMPLEMENTAR
   /**
    * Valida o invariante de uma heap a partir de determinada posicao, que pode
    * ser a raiz da heap ou de uma sub-heap. O heapify deve colocar os maiores
    * (comparados usando o comparator) elementos na parte de cima da heap.
    */
   private void heapify(int position) {
      if (position >= 0 && position <= index) {
         int leftElement = left(position);
         int rightElement = right(position);
         int largest = position;

         if (leftElement <= index && heap[leftElement] != null
               && comparator.compare(heap[leftElement], heap[largest]) > 0) {
            largest = leftElement;
         }
         if (rightElement <= index && heap[rightElement] != null
               && comparator.compare(heap[rightElement], heap[largest]) > 0) {
            largest = rightElement;
         }

         if (largest != position) {
            Util.swap(heap, largest, position);
            heapify(largest);
         }
      }

   }

   @Override
   public void insert(T element) {
      if (index == heap.length - 1) {
         heap = Arrays.copyOf(heap, heap.length + INCREASING_FACTOR);
      }

      if (element != null) {
         index++;
         heap[index] = element;
         int aux = index;

         while (aux > 0 && comparator.compare(heap[parent(aux)], element) < 0) {
            Util.swap(heap, aux, parent(aux));
            aux = parent(aux);
         }
      }
   }

   @Override
   public void buildHeap(T[] array) {
      if (array != null) {
         heap = array;
         index = array.length - 1;

         for (int i = array.length / 2; i >= 0; i--) {
            heapify(i);
         }
      }
   }

   @Override
   public T extractRootElement() {
      T aux = rootElement();

      if (index >= 0) {
         aux = heap[0];
         heap[0] = heap[index];
         heap[index] = null;
         index--;

         heapify(0);
      }

      return aux;
   }

   @Override
   public T rootElement() {
      T aux = null;

      if (heap[0] != null) {
         aux = heap[0];
      }

      return aux;
   }

   @Override
	public T[] heapsort(T[] array) {
		T[] aux = (T[]) new Comparable[]{};

		if (array != null) {
			Comparator<T> comparatorSort = (ob1, ob2) -> ob2.compareTo(ob1);
			Comparator<T> comparatorTemp = getComparator();
			setComparator(comparatorSort);
			buildHeap(array);
			
			aux = (T[]) (new Comparable[size()]);
			
			for (int index = 0; index < aux.length; index++) {
				aux[index] = extractRootElement();
			}
			
			heap = (T[]) (new Comparable[INITIAL_SIZE]);
			setComparator(comparatorTemp);
		}

		return aux;
	}

   @Override
   public int size() {
      return index + 1;
   }

   public Comparator<T> getComparator() {
      return comparator;
   }

   public void setComparator(Comparator<T> comparator) {
      this.comparator = comparator;
   }

   public T[] getHeap() {
      return heap;
   }

}
