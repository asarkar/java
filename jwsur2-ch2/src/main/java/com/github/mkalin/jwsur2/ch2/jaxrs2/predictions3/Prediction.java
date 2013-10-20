package com.github.mkalin.jwsur2.ch2.jaxrs2.predictions3;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "prediction")
public class Prediction implements Comparable<Prediction> {
	private String who; // person
	private String what; // his/her prediction
	private int id; // identifier used as lookup-key

	public Prediction() {
	}

	@Override
	public String toString() {
		return String.format("%2d: ", id) + who + " ==> " + what + "\n";
	}

	// ** properties
	public void setWho(String who) {
		this.who = who;
	}

	@XmlElement
	public String getWho() {
		return this.who;
	}

	public void setWhat(String what) {
		this.what = what;
	}

	@XmlElement
	public String getWhat() {
		return this.what;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlElement
	public int getId() {
		return this.id;
	}

	// implementation of Comparable interface
	public int compareTo(Prediction other) {
		return this.id - other.id;
	}
}
