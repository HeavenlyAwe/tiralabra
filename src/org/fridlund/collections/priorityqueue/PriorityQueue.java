/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.collections.priorityqueue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Queue;

/**
 * Implemented with a binary heap in array form.
 *
 *
 * poll Time: O(1) peek Time: O(1)
 *
 * add Time: O(log(n)) remove Time: O(n log(n))
 *
 * @author Christoffer
 */
public class PriorityQueue<T> implements Collection<T>, Queue<T> {

    private Comparator comparator;
    private Object[] queue = new Object[10];
    private int size = 0;

    //=====================================================================
    /*
     * CONSTRUCTOR
     */
    //=====================================================================
    /**
     * Needs a comparator for the sorting.
     *
     * @param <T>
     * @param comparator
     */
    public <T> PriorityQueue(Comparator comparator) {
        this.comparator = comparator;
    }

    //=====================================================================
    /*
     * HELPER METHODS
     */
    //=====================================================================
    /**
     * Helper method for removing the element at "index" in the array.
     *
     * Overwrites the element at "index" with the last element. Then it swaps
     * the element with the child with either higher or lower value, depending
     * on the comparator.
     *
     * @param index
     */
    private void removeAndHeapify(int index) {
        queue[index] = queue[size - 1];
        removeLast();
        heapifyMax(index);
    }

    /**
     * Helper method for swapping the parent with correct child, based on
     * comparator value.
     *
     * @param index
     */
    private void heapifyMax(int index) {

        int left = getLeft(index);
        int right = getRight(index);
        int greatest;

        if (left < size && comparator.compare((T) queue[left], (T) queue[index]) > 0) {
            greatest = left;
        } else {
            greatest = index;
        }

        if (right < size && comparator.compare((T) queue[right], (T) queue[greatest]) > 0) {
            greatest = right;
        }

        if (greatest != index) {
            swap(index, greatest);
            heapifyMax(greatest);
        }
    }

    /**
     * Helper method for swapping two elements in the array
     *
     * @param i1
     * @param i2
     */
    private void swap(int i1, int i2) {
        T temp = (T) queue[i1];
        queue[i1] = queue[i2];
        queue[i2] = temp;
    }

    /**
     * Helper method for removing the last element in the array.
     *
     * It doesn't physically remove the element, it just making the size
     * smaller, which limits the structure from reaching the last element.
     *
     * This doesn't free up any memory, but it will make the removal much faster
     * than overwriting unwanted elements.
     */
    private void removeLast() {
        size = size - 1;
    }

    /**
     * Helper method for accessing the parent from the element at "index"
     *
     * @param index
     * @return index to parent
     */
    private int getParent(int index) {
        return (index - 1) / 2;
    }

    /**
     * Helper method for accessing the left child of the element at "index"
     *
     * @param index
     * @return index to left child
     */
    private int getLeft(int index) {
        return index * 2 + 1;
    }

    /**
     * Helper method for accessing the right child of the element at "index"
     *
     * @param index
     * @return index of the right child
     */
    private int getRight(int index) {
        return index * 2 + 2;
    }

    //=====================================================================
    /*
     * OVERRIDDEN METHODS
     */
    //=====================================================================
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (queue[i] == o) {
                return true;
            }
        }
        return false;
    }

    /**
     * Uses the Utils.Arrays.copyOf, to copy the correct amount of data, from
     * the array into the returned Object array.
     *
     * @return
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(queue, size);
    }

    /**
     * I'm not sure if this method works correctly, haven't tried it yet!
     *
     * @param <T>
     * @param a
     * @return
     */
    @Override
    public <T> T[] toArray(T[] a) {
        for (int i = 0; i < size; i++) {
            a[i] = (T) queue[i];
        }
        return a;
    }

    /**
     * Adds a new element to the heap. The element gets added at the last
     * element, then swapped upwards, by comparing it to the parents. Depending
     * on the comparator given to the constructor, the order of the values are
     * inverted.
     *
     * If the size of the array is too small, it'll double the size of it.
     *
     * @param e
     * @return True if everything worked.
     */
    @Override
    public boolean add(T e) {
        size = size + 1;

        /*
         * Resizing the array if the size is too small.
         */
        if (queue.length <= size) {
            queue = Arrays.copyOf(queue, size * 2);
        }
        int index = size - 1;

        /*
         * Goes from the last element up through the parents. Depending on the way
         * your comparator is implemented the method would either choose the closest
         * or the furthest node from the goal node.
         */
        while (index > 0) {
            int comp = comparator.compare(e, (T) queue[getParent(index)]);
            if (comp > 0) {
                // swap the parent down to the child we came from
                queue[index] = queue[getParent(index)];
                index = getParent(index);
            } else {
                break;
            }
        }
        // the highest reached parent, gets overwritten with our new value
        queue[index] = e;
        return true;
    }

    /**
     * Removes the selected element from the heap structure.
     *
     * First it checks that the object is present. Then it removes it from the
     * heap and rebuilds it in the correct order.
     *
     * @param o
     * @return
     */
    @Override
    public boolean remove(Object o) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (queue[i].equals(o)) {
                index = i;
            }
        }

        if (index >= 0) {
            removeAndHeapify(index);
        }

        return index >= 0;
    }

    @Override
    public void clear() {
        size = 0;
    }

    /**
     * Returns the root element of the heap, without removing it from the
     * structure.
     *
     * @return
     */
    @Override
    public T peek() {
        return (T) queue[0];
    }

    /**
     * Removes and returns the root element.
     *
     * @return The root of the heap
     */
    @Override
    public T poll() {
        T head = (T) queue[0];

        removeAndHeapify(0);

        return head;
    }

    //=====================================================================
    /*
     * NOT YET IMPLEMENTED METHODS
     */
    //=====================================================================
    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean offer(T e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public T remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public T element() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
