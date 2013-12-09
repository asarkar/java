package name.abhijitsarkar.codinginterview.datastructure;

import java.util.Collection;
import java.util.NoSuchElementException;

import name.abhijitsarkar.codinginterview.datastructure.impl.LinkedListNode;

public interface LinkedList<E> {
	/**
	 * Adds element e at the end.
	 * 
	 * @param e
	 *            Element to add.
	 * @return true if successful, false otherwise.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             If index is less than 0 or greater than size.
	 */
	public boolean add(E e);

	/**
	 * Adds element e at the specified index.
	 * 
	 * @param index
	 *            Index to add element at.
	 * @param e
	 *            Element to add.
	 * @return true if successful, false otherwise.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             If index is less than 0 or greater than size.
	 */
	public boolean add(int index, E e);

	/**
	 * Adds all elements in the collection at the end.
	 * 
	 * @param c
	 *            Collection whose elements are to be added to this list.
	 * @return true if successful, false otherwise.
	 */

	public boolean addAll(Collection<E> c);

	/**
	 * Removes the first element of the list.
	 * 
	 * @return The element removed.
	 * 
	 * @throws NoSuchElementException
	 *             is the list is empty.
	 * @throws IndexOutOfBoundsException
	 *             If index is less than 0 or greater than size.
	 */
	public E remove();

	/**
	 * Removes the element from the list as specified by the index.
	 * 
	 * @param index
	 *            Index to remove element from.
	 * @return The element removed.
	 * 
	 * @throws NoSuchElementException
	 *             is the list is empty.
	 * @throws IndexOutOfBoundsException
	 *             If index is less than 0 or greater than size.
	 */
	public E remove(int index);

	/**
	 * Returns but doesn't remove the first element of the list.
	 * 
	 * @return The first element.
	 * 
	 * @throws NoSuchElementException
	 *             is the list is empty.
	 */
	public E peek();

	/**
	 * 
	 * @return Number of elements in the list.
	 */
	public int size();

	/**
	 * Reverse the list in place.
	 */
	public void reverse();

	/**
	 * Returns the head node of the list. The head is a sentinel and not a valid
	 * element of the list.
	 * 
	 * @return Head node of the list.
	 */
	public LinkedListNode<E> head();

	/**
	 * Returns the tail node of the list. The tail is a sentinel and not a valid
	 * element of the list.
	 * 
	 * @return Tail node of the list.
	 */
	public LinkedListNode<E> tail();

	/**
	 * Returns but doesn't remove the element from the list as specified by the
	 * index.
	 * 
	 * @param index
	 *            Index to get element from.
	 * @return The element.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             If index is less than 0 or greater than size.
	 */
	public E get(int index);

	/**
	 * Replaces the element at index with the one provided.
	 * 
	 * @param index
	 *            Index to set the element to.
	 * @param element
	 *            Replacement element.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             If index is less than 0 or greater than size.
	 */
	public void set(int index, E element);

	/**
	 * Returns the first index of the element provided.
	 * 
	 * @param element
	 *            The one to find the index for.
	 * @return First index of the element.
	 * 
	 * @throws NoSuchElementException
	 *             is the list is empty.
	 */
	public int indexOf(E element);

	/**
	 * Returns the last index of the element provided.
	 * 
	 * @param element
	 *            The one to find the index for.
	 * @return last index of the element.
	 * 
	 * @throws NoSuchElementException
	 *             is the list is empty.
	 */
	public int lastIndexOf(E element);
}
