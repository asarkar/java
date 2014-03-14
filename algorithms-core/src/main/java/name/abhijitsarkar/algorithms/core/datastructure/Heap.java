/*******************************************************************************
 * Copyright (c) 2014, the original author or authors.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * A copy of the GNU General Public License accompanies this software, 
 * and is also available at http://www.gnu.org/licenses.
 *******************************************************************************/
package name.abhijitsarkar.algorithms.core.datastructure;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * @author Abhijit Sarkar
 */
public abstract class Heap<E extends Comparable<? super E>> {
	protected int size;
	protected int capacity;
	protected E[] heap;

	private final int defaultsize = 10;

	public Heap(final E[] arr) {
		capacity = Math.max(defaultsize, arr.length);
		size = arr.length;
		heap = Arrays.copyOf(arr, capacity);

		/*
		 * Start from middle of the array and work towards the start. Starting from the end is a waste because the child
		 * indices for elements greater than the middle are going to be outside the limits of the array and heapify
		 * would just fall through without making any changes.
		 */
		for (int i = (size - 1) / 2; i >= 0; --i) {
			heapify(i);
		}
	}

	public final void heapify(final int index) {
		int i = index;

		final int l = 2 * i + 1;
		final int r = 2 * i + 2;

		if (l < size && isHeapPropertyViolated(l, i)) {
			i = l;
		}
		if (r < size && isHeapPropertyViolated(r, i)) {
			i = r;
		}

		/*
		 * The assumption is that all subtrees above 'i' do not violate the heap property. Since we swap the parent and
		 * the child, the subtree rooted at parent now satisfies the heap property. We recursively heapify the subtree
		 * rooted at the child to make them satisfy the heap property too.
		 */
		if (i != index) {
			swap(i, index);
			heapify(i);
		}
	}

	public final E root() {
		return isEmpty() ? null : heap[0];
	}

	public final E delete() {
		if (!isEmpty()) {
			E root = heap[0];
			heap[0] = heap[--size];
			heap[size] = null;
			heapify(0);

			return root;
		}
		throw new NoSuchElementException("Cannot delete from an empty heap.");
	}

	public E leftChild(final int index) {
		validateIndex(index);

		final int l = 2 * index + 1;

		return l < size ? heap[l] : null;
	}

	public E rightChild(final int index) {
		validateIndex(index);

		final int r = 2 * index + 2;

		return r < size ? heap[r] : null;
	}

	public E decreaseKey(final int index, final E newKey) {
		return updateKey(index, newKey);
	}

	public E increaseKey(final int index, final E newKey) {
		return updateKey(index, newKey);
	}

	private E updateKey(int index, E newKey) {
		validateIndex(index);

		E oldKey = heap[index];

		heap[index] = newKey;
		heapify(index);

		return oldKey;
	}

	protected void validateIndex(final int index) {
		if (index < 0 || index >= capacity) {
			throw new ArrayIndexOutOfBoundsException("Index " + index + " is outside the valid range [0, " + capacity
					+ ").");
		}
	}

	public final int size() {
		return size;
	}

	public final boolean isEmpty() {
		return size == 0;
	}

	public final E insert(final E element) {
		/* This may be changed by dynamically growing the heap. */
		if (size == capacity) {
			throw new ArrayIndexOutOfBoundsException(
					"Heap is at full capacity. Cannot insert until an element is deleted.");
		}

		heap[size++] = element;

		int index = size - 1, parentIndex = (index - 1) / 2;

		while (parentIndex >= 0 && isHeapPropertyViolated(index, parentIndex)) {
			swap(index, parentIndex);

			index = parentIndex;
			parentIndex = (index - 1) / 2;
		}

		return element;
	}

	protected abstract boolean isHeapPropertyViolated(final int index, final int parentIndex);

	protected void swap(final int i, final int j) {
		E temp = heap[i];
		heap[i] = heap[j];
		heap[j] = temp;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + Arrays.toString(heap);
	}
}
