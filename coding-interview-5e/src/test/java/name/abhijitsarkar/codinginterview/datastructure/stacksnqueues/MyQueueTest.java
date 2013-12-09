package name.abhijitsarkar.codinginterview.datastructure.stacksnqueues;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MyQueueTest {
	private MyQueue<Integer> queue = null;

	@Before
	public void setUp() {
		queue = new MyQueue<Integer>();

		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);
	}

	@Test
	public void testQueue() {
		Assert.assertFalse(queue.isEmpty());

		Assert.assertEquals(Integer.valueOf(1), queue.dequeue());
		Assert.assertEquals(Integer.valueOf(2), queue.dequeue());

		queue.enqueue(4);

		Assert.assertEquals(Integer.valueOf(3), queue.dequeue());
		Assert.assertEquals(Integer.valueOf(4), queue.dequeue());

		Assert.assertTrue(queue.isEmpty());
	}
}
