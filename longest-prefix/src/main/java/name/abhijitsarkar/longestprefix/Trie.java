package name.abhijitsarkar.longestprefix;

import java.util.ArrayList;
import java.util.List;

/* This class implements a Trie Data Structure 
 * 
 * Complexity Analysis: O(N), where N is the number of words
 * 
 */
public class Trie {
	private final Node root = new Node('\u0000');

	/*
	 * Complexity Analysis: O(N * M), where N is the number of words and M is
	 * the number of characters in the corresponding word. For a very large N,
	 * even if each word is the longest in the English dictionary, it becomes a
	 * constant. Thus for a very large N, we can say that the complexity is
	 * O(N).
	 */
	public Trie(String[] words) {
		if (words != null && words.length != 0) {
			int len = 0;
			Node node = null;
			Node child = null;
			char ch = '\u0000';

			/*
			 * For each word, start at the root and keep matching each character
			 * with child nodes.
			 */
			for (String word : words) {
				node = root;
				len = word.length();

				for (int idx = 0; idx < len; idx++) {
					ch = word.charAt(idx);

					/* Treat repeated characters specially */
					if (idx > 0 && word.charAt(idx - 1) == ch) {
						child = new Node(ch);
						node.addChild(child);
						node = child;
					}
					/* No repeated characters */
					else if (node.hasChild(ch)) {
						node = node.getChild(ch);
					} else {
						child = new Node(ch);
						node.addChild(child);
						node = child;
					}
				}
				node.isEndOfWord = true;
			}
		}
	}

	/*
	 * Complexity Analysis: O(K), where K is the number of number of characters
	 * in the input word. Taking an average over K, it runs in constant time and
	 * becomes O(1)
	 */
	public String longestPrefix(String inputWord) {
		if (inputWord == null || inputWord.isEmpty()) {
			return null;
		}

		Node node = root;
		Node child = null;
		char ch = '\u0000';
		final int len = inputWord.length();
		StringBuilder runningPrefix = new StringBuilder();
		String longestPrefix = null;

		for (int idx = 0; idx < len; idx++) {
			ch = inputWord.charAt(idx);
			child = node.getChild(ch);

			/* Store the longest prefix found so far */
			if (node.isEndOfWord) {
				longestPrefix = runningPrefix.toString();
			}

			if (child != null) {
				runningPrefix.append(ch);
				node = child;
			} else {
				break;
			}
		}

		/*
		 * In case the loop finished before checking the last character;
		 * possible because the loop starts from root node and hence is always a
		 * step behind
		 */
		if (node.isEndOfWord && node.getChild(ch) == null
				&& ch == node.value) {
			longestPrefix = runningPrefix.toString();
		}

		return longestPrefix;
	}

	private class Node {
		private char value;
		private List<Node> children = new ArrayList<Node>();
		private boolean isEndOfWord = false;

		private Node(char value) {
			this.value = value;
		}

		private Node getChild(char ch) {
			int idx = children.indexOf(new Node(ch));

			return idx >= 0 ? children.get(idx) : null;
		}

		private boolean hasChild(char ch) {
			return getChild(ch) != null;
		}

		private void addChild(Node child) {
			children.add(child);
		}

		@Override
		public boolean equals(Object obj) {
			return (obj instanceof Node) && value == ((Node) obj).value;
		}

		@Override
		public int hashCode() {
			/* Joshua Bloch's recipe */
			return 31 * 17 + (int) value;
		}

		@Override
		public String toString() {
			return Character.toString(value);
		}
	}
}
