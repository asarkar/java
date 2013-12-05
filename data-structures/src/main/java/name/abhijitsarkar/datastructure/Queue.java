package name.abhijitsarkar.datastructure;

public interface Queue<E> {
	public E dequeue();

	public boolean enqueue(E element);
	
	public boolean isEmpty();
}
