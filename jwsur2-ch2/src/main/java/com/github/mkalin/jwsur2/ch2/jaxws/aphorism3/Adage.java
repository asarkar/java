package com.github.mkalin.jwsur2.ch2.jaxws.aphorism3;

public class Adage {
	private String words;
	private int wordCount;
	private int id;

	public Adage() {
	}

	// overrides
	@Override
	public String toString() {
		return String.format("%2d: ", id) + words + " -- " + wordCount
				+ " words";
	}

	// properties
	public void setWords(String words) {
		this.words = words;
		this.wordCount = words.trim().split("\\s+").length;
	}

	public String getWords() {
		return this.words;
	}

	public void setWordCount(int wordCount) {
	}

	public int getWordCount() {
		return this.wordCount;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}
}