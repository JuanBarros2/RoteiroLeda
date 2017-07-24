package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionQuadraticProbing;

public class HashtableOpenAddressQuadraticProbingImpl<T extends Storable> extends AbstractHashtableOpenAddress<T> {

   public HashtableOpenAddressQuadraticProbingImpl(int size, HashFunctionClosedAddressMethod method, int c1, int c2) {
      super(size);
      hashFunction = new HashFunctionQuadraticProbing<T>(size, method, c1, c2);
      this.initiateInternalTable(size);
   }

   @Override
   public void insert(T element) {
      if (element != null) {
         int i = 0;
         int capacity = capacity();

         if (isFull()) {
            throw new HashtableOverflowException();
         }

         do {
            int hashNumber = getHash(i, element);
            if (table[hashNumber] == null || table[hashNumber].equals(deletedElement)) {
               table[hashNumber] = element;
               elements++;
               break;
            } else if (table[hashNumber].equals(element)) {
               break;
            } else {
               COLLISIONS++;
               i++;
            }
         } while (i < capacity);

      }
   }

   @Override
   public void remove(T element) {
      if (element != null) {
         int i = 0;
         int colissions = 0;

         do {
            int hashNumber = getHash(i, element);

            if (table[hashNumber] == null) {
               break;
            } else if (table[hashNumber].equals(element)) {
               table[hashNumber] = deletedElement;
               elements--;
               COLLISIONS -= colissions;
            } else {
               colissions++;
               i++;
            }
         } while (i < capacity());
      }
   }

   @Override
   public T search(T element) {
      T aux = null;

      if (element != null) {
         int index = indexOf(element);

         if (index != -1) {
            aux = (T) table[index];
         }
      }
      return aux;
   }

   @Override
   public int indexOf(T element) {
      int result = -1;

      if (element != null) {
         int i = 0;
         do {
            int hashNumber = getHash(i, element);

            if (table[hashNumber] == null) {
               break;
            } else if (table[hashNumber].equals(element)) {
               result = hashNumber;
               break;
            } else {
               i++;
            }
         } while (i < capacity());
      }

      return result;
   }

   public int getHash(int probe, T element) {
      return ((HashFunctionQuadraticProbing) hashFunction).hash(element, probe);
   }

}
