package com.github.mkalin.jwsur2.ch1.predictions;

import java.io.Serializable;

public class Prediction implements Serializable {
	private static final long serialVersionUID = 8472623997854378819L;

	private String who; // person
	private String what; // his/her prediction

	public Prediction() {
	}

	public void setWho(String who) {
		this.who = who;
	}

	public String getWho() {
		return this.who;
	}

	public void setWhat(String what) {
		this.what = what;
	}

	public String getWhat() {
		return this.what;
	}
}
