package name.abhijitsarkar.datastructure;

import java.util.Collection;

public interface LinkedList<E> {
	public boolean add(E e);

	public boolean add(int index, E e);
	
	public boolean addAll(Collection<E> c);
	
	public E remove();

	public E remove(int index);
	
	public E peek();

	public int size();
	
	public void reverse();
}
