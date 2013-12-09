package name.abhijitsarkar.codinginterview.datastructure.linkedlists;

import name.abhijitsarkar.codinginterview.datastructure.LinkedList;
import name.abhijitsarkar.codinginterview.datastructure.impl.LinkedListImpl;

import org.junit.Assert;
import org.junit.Test;

public class PracticeQuestionsTest {
	@Test
	public void testRemoveDupes() {
		LinkedList<Integer> linkedList = new LinkedListImpl<Integer>();
		linkedList.add(1);
		linkedList.add(2);
		linkedList.add(1);

		PracticeQuestions.removeDupes(linkedList);

		Assert.assertEquals(2, linkedList.size());
		Assert.assertEquals(Integer.valueOf(1), linkedList.remove());
		Assert.assertEquals(Integer.valueOf(2), linkedList.remove());
		Assert.assertEquals(0, linkedList.size());
	}

	@Test
	public void testPartition() {
		LinkedList<Integer> linkedList = new LinkedListImpl<Integer>();
		linkedList.add(2);
		linkedList.add(4);
		linkedList.add(5);
		linkedList.add(1);

		PracticeQuestions.partition(linkedList, 4);

		Assert.assertEquals(4, linkedList.size());
		Assert.assertEquals(Integer.valueOf(2), linkedList.get(0));
		Assert.assertEquals(Integer.valueOf(1), linkedList.get(1));
		Assert.assertEquals(Integer.valueOf(4), linkedList.get(2));
		Assert.assertEquals(Integer.valueOf(5), linkedList.get(3));
	}

	@Test
	public void testIsPalindrome() {
		LinkedList<Character> linkedList = new LinkedListImpl<Character>();
		linkedList.add('c');
		linkedList.add('i');
		linkedList.add('v');
		linkedList.add('i');
		linkedList.add('c');

		Assert.assertTrue(PracticeQuestions.isPalindrome(linkedList));

		linkedList = new LinkedListImpl<Character>();
		linkedList.add('a');
		linkedList.add('n');
		linkedList.add('n');
		linkedList.add('a');

		Assert.assertTrue(PracticeQuestions.isPalindrome(linkedList));

		linkedList = new LinkedListImpl<Character>();
		linkedList.add('a');
		linkedList.add('n');
		linkedList.add('m');
		linkedList.add('a');

		Assert.assertFalse(PracticeQuestions.isPalindrome(linkedList));
	}
}
