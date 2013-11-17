package name.abhijitsarkar.longestprefix;

import name.abhijitsarkar.longestprefix.Trie;

import org.junit.Assert;
import org.junit.Test;

public class TrieTest {
	private Trie trie = null;

	@Test
	public void testLongestPrefix1() {
		trie = new Trie(new String[] { "abc", "abcd", "abcdde" });
		Assert.assertEquals("abcd", trie.longestPrefix("abcdd"));
	}

	@Test
	public void testLongestPrefix2() {
		trie = new Trie(new String[] { "are", "area", "base", "cat", "cater",
				"basement" });
		Assert.assertEquals("cater", trie.longestPrefix("caterer"));
		Assert.assertEquals("basement", trie.longestPrefix("basement"));
		Assert.assertEquals("are", trie.longestPrefix("are"));
		Assert.assertEquals("are", trie.longestPrefix("arex"));
		Assert.assertEquals("base", trie.longestPrefix("basemexz"));
		Assert.assertNull(trie.longestPrefix("xyz"));
		Assert.assertNull(trie.longestPrefix(""));
		Assert.assertNull(trie.longestPrefix(null));
		Assert.assertNull(trie.longestPrefix(" "));
	}
}
