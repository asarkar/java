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
package name.abhijitsarkar.codinginterview.datastructure;

import name.abhijitsarkar.codinginterview.datastructure.LinkedList.LinkedListNode;

/**
 * @author Abhijit Sarkar
 */
/*
 * Q8.10: Design and implement a hash table which uses chaining (linked lists) to handle collisions.
 */
public class Hashtable<K, V> {
	private int capacity = 8;
	private int numElements = 0;

	private LinkedList<Entry<K, V>>[] buckets;

	@SuppressWarnings("unchecked")
	public Hashtable() {
		buckets = new LinkedList[capacity];
	}

	public V put(K key, V value) {
		final long hash = DJBHash(key.toString());
		int idx = computeIndex(hash);

		System.out.printf("Hash = %d, index = %d.%n", hash, idx);

		LinkedList<Entry<K, V>> bucket = createOrGetBucket(idx);

		Entry<K, V> anEntry = new Entry<K, V>(key, value);

		idx = indexOf(bucket, anEntry);

		if (idx >= 0) {
			bucket.set(idx, anEntry);
		} else {
			bucket.add(anEntry);
			numElements++;
		}

		return value;
	}

	private LinkedList<Entry<K, V>> createOrGetBucket(int index) {
		if (buckets[index] == null) {
			buckets[index] = new LinkedList<Entry<K, V>>();
		}

		return buckets[index];
	}

	private int indexOf(LinkedList<Entry<K, V>> bucket, Entry<K, V> anEntry) {
		if (bucket.size() > 0) {
			return bucket.indexOf(anEntry);
		}

		return -1;
	}

	public V get(K key) {
		final long hash = DJBHash(key.toString());
		final int idx = computeIndex(hash);

		LinkedList<Entry<K, V>> bucket = buckets[idx];

		if (bucket == null || bucket.size() == 0) {
			return null;
		}

		LinkedListNode<Entry<K, V>> node = bucket.head().getSuccessor();
		LinkedListNode<Entry<K, V>> tail = bucket.tail();

		Entry<K, V> anEntry = null;
		for (; node != tail; node = node.getSuccessor()) {
			anEntry = node.getData();

			if (anEntry.key.equals(key)) {
				return anEntry.value;
			}
		}

		return null;
	}

	public final int size() {
		return numElements;
	}

	public final float loadFactor() {
		return numElements << 3;
	}

	private int computeIndex(long hash) {
		// Compute modulo 8 with a bitmask
		return (int) (hash & 0x07);
	}

	/*
	 * http://www.partow.net/programming/hashfunctions
	 */
	private final long DJBHash(String str) {
		long hash = 5381;

		for (int i = 0; i < str.length(); i++) {
			hash = hash << 5 + hash + str.charAt(i);
		}

		return hash;
	}

	public static class Entry<K, V> {
		private K key;
		private V value;

		private Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			@SuppressWarnings("unchecked")
			Entry<K, V> other = (Entry<K, V>) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Entry [key=" + key + ", value=" + value + "]";
		}
	}
}
