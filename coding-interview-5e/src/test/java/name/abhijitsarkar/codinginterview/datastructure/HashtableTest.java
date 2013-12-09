package name.abhijitsarkar.codinginterview.datastructure;

import org.junit.Assert;
import org.junit.Test;

public class HashtableTest {
	private Hashtable<Integer, Integer> hashtable;

	public HashtableTest() {
		hashtable = new Hashtable<Integer, Integer>();

		hashtable.put(1, 2);
		hashtable.put(2, 3);
	}

	@Test
	public void testHashtable() {
		Assert.assertEquals(2, hashtable.size());

		Assert.assertEquals(Integer.valueOf(3), hashtable.get(2));
		Assert.assertEquals(Integer.valueOf(2), hashtable.get(1));
		hashtable.put(2, 5);
		Assert.assertEquals(Integer.valueOf(5), hashtable.get(2));
		Assert.assertEquals(null, hashtable.get(10));
	}
}
