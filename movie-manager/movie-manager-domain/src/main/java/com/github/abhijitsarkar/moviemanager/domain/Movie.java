package com.github.abhijitsarkar.moviemanager.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "movie", propOrder = { "name", "year", "genre", "filesize",
		"fileExtension", "parent" })
public class Movie implements Serializable {

	private static final long serialVersionUID = 8153939276597793852L;

	public static final short UNKNOWN_RELEASE_YEAR = -1;

	private String name;
	private short year;
	private Genre genre;
	private long filesize;
	private String fileExtension;
	private String parent;

	public Movie() {

	}

	public Movie(String name, short year, String fileExtension) {
		this(name, year, Genre.UNKNOWN, 0L, fileExtension);
	}

	public Movie(String name, short year, Genre genre, long filesize,
			String fileExtension) {
		this.name = name;
		this.year = year;
		this.genre = genre;
		this.filesize = filesize;
		this.fileExtension = fileExtension;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getYear() {
		return year;
	}

	public void setYear(short year) {
		this.year = year;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public long getFilesize() {
		return filesize;
	}

	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "[name = " + name + ", year = " + year + ", genre = " + genre
				+ ", parent = " + parent + "]";
	}

	@Override
	public boolean equals(Object obj) {
		Movie m = null;
		boolean result = false;

		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Movie)) {
			return false;
		}
		m = (Movie) obj;

		result = ((name == m.name) || (name != null && name.equals(m.name)));
		result &= (year == m.year);
		result &= ((genre == m.genre) || (genre != null && genre
				.equals(m.genre)));

		return result;
	}

	@Override
	public int hashCode() {
		int result = 17;
		int c = 0;

		c = ((name == null) ? 0 : name.hashCode());
		result = 37 * result + c;

		c = (int) year;
		result = 37 * result + c;

		c = ((genre == null) ? 0 : genre.hashCode());
		result = 37 * result + c;

		return result;
	}
}
