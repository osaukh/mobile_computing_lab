package com.ahinea.speechprocessing;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.RandomAccess;

public class RingArrayList<E> extends AbstractList<E> implements RandomAccess {

    private final List<E> ringBuffer;
    private int size;
    private int head;

    public RingArrayList(int capacity) {
        // TODO capacity 0

        ringBuffer = new ArrayList<E>((Collection<? extends E>) Collections.nCopies(capacity + 1, null)); // head index always point to empty position (+1)
        size = 0;
        head = 0;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index is greater than actual ring buffer size.");
        }

        return ringBuffer.get(getRealIndex(index));
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index is greater than actual ring buffer size.");
        }
        if (index == size()) {
            throw new IndexOutOfBoundsException(String.format(Locale.getDefault(), "The size of the list is %d " +
                    "while the index was %d. Use add(element) method to fill the list.", size(), index));
        }

        return ringBuffer.set(getRealIndex(index), element);
    }

    @Override
    public int size() {
        return size;
    }

    public int capacity() {
        return ringBuffer.size() - 1;
    }

    public void resize(int newSize) {
        // ringBuffer // TODO
    }

    @Override
    public boolean add(E element) {
        ringBuffer.set(head, element);

        if (size() < capacity()) {
            ++size;
        }
        head = ++head % ringBuffer.size();
        return true;
    }

    @Override
    public void clear() {
        size = 0;
        head = 0;
    }

    private int getRealIndex(int index) {
        return (index + head + 1) % (size() + 1);
    }

}
