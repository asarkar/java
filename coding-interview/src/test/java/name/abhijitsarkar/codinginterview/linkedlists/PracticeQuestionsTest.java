package name.abhijitsarkar.codinginterview.linkedlists;

import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

public class PracticeQuestionsTest {
	@Test
	public void testRemoveDupes() {
		LinkedList<Integer> linkedList = new LinkedList<Integer>();
		linkedList.add(1);
		linkedList.add(2);
		linkedList.add(1);

		PracticeQuestions.removeDupes(linkedList);

		Assert.assertEquals(2, linkedList.size());
		Assert.assertEquals(0, linkedList.indexOf(1));
		Assert.assertEquals(1, linkedList.indexOf(2));
	}

	@Test
	public void testRemoveDupesWithoutUsingBuffer() {
		LinkedList<Integer> linkedList = new LinkedList<Integer>();
		linkedList.add(1);
		linkedList.add(2);
		linkedList.add(1);

		PracticeQuestions.removeDupesWithoutUsingBuffer(linkedList);

		Assert.assertEquals(2, linkedList.size());
		Assert.assertEquals(0, linkedList.indexOf(1));
		Assert.assertEquals(1, linkedList.indexOf(2));
	}

	@Test
	public void testPartition() {
		LinkedList<Integer> linkedList = new LinkedList<Integer>();
		linkedList.add(2);
		linkedList.add(4);
		linkedList.add(5);
		linkedList.add(1);

		PracticeQuestions.partition(linkedList, 2);

		Assert.assertEquals(4, linkedList.size());
		Assert.assertEquals(Integer.valueOf(1), linkedList.get(0));
		Assert.assertEquals(Integer.valueOf(2), linkedList.get(1));
		Assert.assertEquals(Integer.valueOf(5), linkedList.get(2));
		Assert.assertEquals(Integer.valueOf(4), linkedList.get(3));
	}

	@Test
	public void testIsPalindrome() {
		LinkedList<Character> linkedList = new LinkedList<Character>();
		linkedList.add('c');
		linkedList.add('i');
		linkedList.add('v');
		linkedList.add('i');
		linkedList.add('c');

		Assert.assertTrue(PracticeQuestions.isPalindrome(linkedList));

		linkedList = new LinkedList<Character>();
		linkedList.add('a');
		linkedList.add('n');
		linkedList.add('n');
		linkedList.add('a');

		Assert.assertTrue(PracticeQuestions.isPalindrome(linkedList));

		linkedList = new LinkedList<Character>();
		linkedList.add('a');
		linkedList.add('n');
		linkedList.add('m');
		linkedList.add('a');

		Assert.assertFalse(PracticeQuestions.isPalindrome(linkedList));
	}
}
