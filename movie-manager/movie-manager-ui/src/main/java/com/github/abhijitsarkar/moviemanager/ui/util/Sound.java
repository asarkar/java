/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.abhijitsarkar.moviemanager.ui.util;

/**
 * 
 * @author abhijit
 */
public enum Sound {

	SUCCESS("success2.wav"), ERROR("error1.wav");

	Sound(String path) {
		this(path, "/sounds");
	}

	Sound(String path, String root) {
		this.path = root + "/" + path;
	}

	private final String path;

	@Override
	public String toString() {
		return path;
	}

	public static final Sound getSoundByPath(String path) {
		for (Sound sound : Sound.values()) {
			if (path.equalsIgnoreCase(sound.toString())) {
				return sound;
			}
		}

		throw new IllegalArgumentException("No audio file found on path: "
				+ path);
	}
}
