/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fridlund.collections.arraylist;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Christoffer
 */
public class ArrayList<T> implements Collection<T>, List<T> {

    private Object[] list;
    private int size;

    public ArrayList() {
        this(10);
    }

    public ArrayList(int size) {
        this.list = new Object[size];
        this.size = 0;
    }

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
        for (int i = 0; i < list.length; i++) {
            if (list[i] == null) {
                return false;
            }

            if (list[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object[] toArray() {
        return list;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean add(T e) {
        if (size >= list.length) {
            list = Arrays.copyOf(list, size * 2);
        }
        list[size] = e;
        size++;

        return true;
    }

    @Override
    public boolean remove(Object o) {

        boolean found = false;

        for (int i = 0; i < list.length - 1; i++) {
            if (list[i] == null) {
                return false;
            }
            if (list[i].equals(o)) {
                found = true;
                size--;
            }
            if (found) {
                list[i] = list[i + 1];
            }
        }

        if (list[list.length - 1].equals(o)) {
            found = true;
            size--;
        }
        return found;
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
    public void clear() {
        size = 0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public T get(int index) {
        if (index < list.length && index >= 0) {
            return (T) list[index];
        }
        return null;
    }

    @Override
    public T set(int index, T element) {
        if (index < list.length && index >= 0) {
            list[index] = element;
            return element;
        } else {
            throw new IndexOutOfBoundsException();

        }
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}