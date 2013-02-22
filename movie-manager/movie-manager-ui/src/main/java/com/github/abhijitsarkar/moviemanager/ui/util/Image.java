/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.abhijitsarkar.moviemanager.ui.util;

/**
 * 
 * @author abhijit
 */
public enum Image {

	OPEN("open1.png"), SAVE("save1.png"), CLOSE("close1.gif"), WARNING(
			"warning1.png"), ERROR("error1.png"), SUCCESS("success1.gif"), DOIT(
			"arrowhead1.png"), SETTINGS("settings1.png"), BROWSE("open1.png"), SPINNER(
			"spinner1.gif");

	Image(String path) {
		this(path, "/images");
	}

	Image(String path, String root) {
		this.path = root + "/" + path;
	}

	private final String path;

	@Override
	public String toString() {
		return path;
	}

	public static final Image getImageByPath(String path) {
		for (Image image : Image.values()) {
			if (path.equalsIgnoreCase(image.toString())) {
				return image;
			}
		}

		throw new IllegalArgumentException("No image found on path: " + path);
	}
}
