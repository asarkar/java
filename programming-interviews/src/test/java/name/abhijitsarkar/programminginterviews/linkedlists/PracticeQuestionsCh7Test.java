package name.abhijitsarkar.programminginterviews.linkedlists;

import static name.abhijitsarkar.programminginterviews.linkedlists.PracticeQuestionsCh7.mergeLists;

import java.util.Arrays;

import name.abhijitsarkar.algorithms.core.datastructure.LinkedList;
import name.abhijitsarkar.algorithms.core.datastructure.LinkedList.LinkedListNode;

import org.junit.Assert;
import org.junit.Test;

public class PracticeQuestionsCh7Test {

	@Test
	public void testMergeLists() {
		LinkedList<Integer> l = new LinkedList<Integer>(Arrays.asList(new Integer[] { 2, 5, 7 }));

		LinkedList<Integer> f = new LinkedList<Integer>(Arrays.asList(new Integer[] { 3, 11 }));

		Integer[] merged = new Integer[] { 2, 3, 5, 7, 11 };
		LinkedListNode<Integer> runningNode = mergeLists(l, f);

		for (int i = 0; i < merged.length; i++, runningNode = runningNode.successor()) {
			Assert.assertEquals(merged[i], runningNode.successor().data());
		}

		/* Test with arrays reversed */

		l = new LinkedList<Integer>(Arrays.asList(new Integer[] { 3, 11 }));

		f = new LinkedList<Integer>(Arrays.asList(new Integer[] { 2, 5, 7 }));

		runningNode = mergeLists(l, f);

		for (int i = 0; i < merged.length; i++, runningNode = runningNode.successor()) {
			Assert.assertEquals(merged[i], runningNode.successor().data());
		}
	}
}
