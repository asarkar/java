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

/**
 * @author Abhijit Sarkar
 */
public class MaxHeap<E extends Comparable<? super E>> extends Heap<E> {
	public MaxHeap(final E[] arr) {
		super(arr);
	}

	@Override
	protected boolean isHeapPropertyViolated(final int index, final int parentIndex) {
		return heap[parentIndex] != null && heap[parentIndex].compareTo(heap[index]) < 0;
	}
}
