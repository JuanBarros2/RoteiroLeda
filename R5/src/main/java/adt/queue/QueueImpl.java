package adt.queue;

public class QueueImpl<T> implements Queue<T> {

   private T[] array;
   private int tail;

   @SuppressWarnings("unchecked")
   public QueueImpl(int size) {
      array = (T[]) new Object[size];
      tail = -1;
   }

   @Override
   public T head() {
      if (isEmpty()) {
         return null;
      }
      return array[0];
   }

   @Override
   public boolean isEmpty() {
      return (tail == -1);
   }

   @Override
   public boolean isFull() {
      return (tail == array.length - 1);
   }

   private void shiftLeft() {
      int index = 0;
      T anterior = null;

      while (index < tail) {
         array[index] = array[index + 1];
         index++;
      }
      ;
      array[index] = null;
   }

   @Override
   public void enqueue(T element) throws QueueOverflowException {
      if (isFull()) {
         throw new QueueOverflowException();
      }
      tail++;
      array[tail] = element;
   }

   @Override
   public T dequeue() throws QueueUnderflowException {
      if (isEmpty()) {
         throw new QueueUnderflowException();
      }
      T aux = array[0];
      shiftLeft();
      tail--;
      return aux;
   }

}
