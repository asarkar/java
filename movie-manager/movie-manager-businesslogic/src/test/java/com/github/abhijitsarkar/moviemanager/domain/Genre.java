package com.github.abhijitsarkar.moviemanager.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "genre")
@XmlEnum
public enum Genre {
	@XmlEnumValue(value = "Action and Adventure")
	ACTION_AND_ADVENTURE("Action and Adventure"),
	@XmlEnumValue(value = "Animation")
	ANIMATION("Animation"),
	@XmlEnumValue(value = "Comedy")
	COMDEDY("Comedy"),
	@XmlEnumValue(value = "Documentary")
	DOCUMENTARY("Documentary"),
	@XmlEnumValue(value = "Drama")
	DRAMA("Drama"),
	@XmlEnumValue(value = "Horror")
	HORROR("Horror"),
	@XmlEnumValue(value = "Romance")
	ROMANCE("Romance"),
	@XmlEnumValue(value = "R-Rated Mainstream Movies")
	R_RATED_MAINSTREAM_MOVIES("R-Rated Mainstream Movies"),
	@XmlEnumValue(value = "Sci-Fi")
	SCI_FI("Sci-Fi"),
	@XmlEnumValue(value = "Thriller")
	THRILLER("Thriller"),
	@XmlEnumValue(value = "X-Rated")
	X_RATED("X-Rated"),
	@XmlEnumValue(value = "Unknown")
	UNKNOWN("Unknown");

	Genre(String name) {
		this.name = name;
	}

	private final String name;

	@Override
	public String toString() {
		return name;
	}

	public static final Genre getGenreByName(String name) {
		for (Genre genre : Genre.values()) {
			if (name.equalsIgnoreCase(genre.toString())) {
				return genre;
			}
		}

		throw new IllegalArgumentException("No genre found with name: " + name);
	}
}
