package name.abhijitsarkar.programminginterviews.linkedlists;

import java.util.NoSuchElementException;

import name.abhijitsarkar.algorithms.core.datastructure.LinkedList;
import name.abhijitsarkar.algorithms.core.datastructure.LinkedList.LinkedListNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PracticeQuestionsCh7 {
	public static final Logger LOGGER = LoggerFactory.getLogger(PracticeQuestionsCh7.class);

	/*
	 * Q7.1: Write a function that takes L and F (linked lists), and returns the merge of L and F. Your code should use
	 * O(1) additional storage - it should reuse the nodes from the lists provided as input. The only field you can
	 * change in a node is next.
	 * 
	 * Solution: Since the lists are sorted, we could've just compared corresponding elements and copied over the
	 * smaller one to a 3rd list. However, the O(1) space requirement prohibits the use of a 3rd list; instead, we
	 * manipulate the references to move segments of the lists before or after current, which is always the latest
	 * insertion point.
	 */
	public static LinkedListNode<Integer> mergeLists(LinkedList<Integer> l, LinkedList<Integer> f) {
		final String newline = System.getProperty("line.separator");

		LinkedListNode<Integer> currentHead = l.head();
		LinkedListNode<Integer> current = currentHead.successor();
		LinkedListNode<Integer> otherHead = f.head();
		LinkedListNode<Integer> smallest = currentHead.successor();

		while (otherHead.successor().data() != null) {
			LOGGER.info("{}", newline);
			LOGGER.info("Current head: {}.", currentHead);
			LOGGER.info("Current: {}.", current);
			LOGGER.info("Other head: {}.", otherHead);
			LOGGER.info("Smallest: {}.", smallest);

			if (current.data() < otherHead.successor().data()) {
				LOGGER.info("Setting current head successor to: {}.", current.successor());
				currentHead.setSuccessor(current.successor());

				LOGGER.info("Setting current successor to: {}.", otherHead.successor());
				current.setSuccessor(otherHead.successor());

				LOGGER.info("Setting current to: {}.", otherHead.successor());
				current = otherHead.successor();

				LOGGER.info("Setting other head successor to: {}.", smallest);
				otherHead.setSuccessor(smallest);

				/* Swap heads */
				LOGGER.info("Swapping heads.");
				LinkedListNode<Integer> tempHead = currentHead;
				currentHead = otherHead;
				otherHead = tempHead;
			} else {
				LinkedListNode<Integer> predecessor = predecessor(currentHead, current);
				LOGGER.info("Predecessor to {} is {}.", current, predecessor);

				LinkedListNode<Integer> temp = otherHead.successor();

				LOGGER.info("Setting predecessor successor to: {}.", temp);
				predecessor.setSuccessor(temp);

				LOGGER.info("Setting smallest to: {}.", currentHead.successor());
				smallest = currentHead.successor();

				LOGGER.info("Setting other head successor to: {}.", temp.successor());
				otherHead.setSuccessor(temp.successor());

				LOGGER.info("Setting temp successor to: {}.", current);
				temp.setSuccessor(current);
			}
		}

		return currentHead;
	}

	private static LinkedListNode<Integer> predecessor(LinkedListNode<Integer> head, LinkedListNode<Integer> node) {
		for (LinkedListNode<Integer> runningNode = head; runningNode.successor() != null; runningNode = runningNode
				.successor()) {
			if (runningNode.successor().data() == node.data()) {
				return runningNode;
			}
		}

		/* Can't happen but the compiler is unmerciful. */
		throw new NoSuchElementException("No predecessor found for " + node);
	}
}
