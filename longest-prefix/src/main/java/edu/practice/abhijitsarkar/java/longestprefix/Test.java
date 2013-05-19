package edu.practice.abhijitsarkar.java.longestprefix;

public class Test {
	private Trie trie = null;

	public void setup(String[] prefixWords) {
		trie = new Trie(prefixWords);
	}

	public String findLongestPrefix(String theInputWord) {
		return trie.longestPrefix(theInputWord);
	}
}
