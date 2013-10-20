package com.github.mkalin.jwsur2.ch2.jaxrs1.adages;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "adage")
public class Adage {
	protected String words;
	protected int wordCount;

	public Adage() {
	}

	// overrides
	@Override
	public String toString() {
		return words + " -- " + wordCount + " words";
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
}
