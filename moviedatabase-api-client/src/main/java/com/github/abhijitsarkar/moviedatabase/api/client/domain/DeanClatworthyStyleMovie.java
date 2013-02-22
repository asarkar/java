/* Copyright (c) 2013, the original author or authors.

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; version 3 of the License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, see http://www.gnu.org/licenses. */

package com.github.abhijitsarkar.moviedatabase.api.client.domain;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Movie instance customized for the Dean Clatworthy API.
 * 
 * @author Abhijit Sarkar
 * 
 */
public class DeanClatworthyStyleMovie extends Movie implements Serializable {

	private static final long serialVersionUID = 8195885501166165500L;

	public DeanClatworthyStyleMovie() {
	}

	public DeanClatworthyStyleMovie(String imdbId) {
		this.imdbId = imdbId;
	}

	public DeanClatworthyStyleMovie(String title, short year) {
		this.title = title;
		this.year = year;
	}

	public DeanClatworthyStyleMovie(String title, short year, String imdbId) {
		this.title = title;
		this.year = year;
		this.imdbId = imdbId;
	}

	public String getTitle() {
		return title;
	}

	@JsonProperty(value = "title")
	public void setTitle(String title) {
		this.title = title;
	}

	public short getYear() {
		return year;
	}

	@JsonProperty(value = "year")
	public void setYear(short year) {
		this.year = year;
	}

	public String getGenre() {
		return genre;
	}

	@JsonProperty(value = "genres")
	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getMpaaRating() {
		return mpaaRating;
	}

	public void setMpaaRating(String mpaaRating) {
		this.mpaaRating = mpaaRating;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getImdbRating() {
		return imdbRating;
	}

	@JsonProperty(value = "rating")
	public void setImdbRating(String imdbRating) {
		this.imdbRating = imdbRating;
	}

	public String getImdbVotes() {
		return imdbVotes;
	}

	@JsonProperty(value = "votes")
	public void setImdbVotes(String imdbVotes) {
		this.imdbVotes = imdbVotes;
	}

	public String getImdbId() {
		return imdbId;
	}

	@JsonProperty(value = "imdbid")
	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((imdbId == null) ? 0 : imdbId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeanClatworthyStyleMovie other = (DeanClatworthyStyleMovie) obj;
		if (imdbId == null) {
			if (other.imdbId != null)
				return false;
		} else if (!imdbId.equals(other.imdbId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DeanClatworthyStyleMovie [title=" + title + ", year=" + year
				+ ", genre=" + genre + ", mpaaRating=" + mpaaRating
				+ ", runtime=" + runtime + ", imdbRating=" + imdbRating
				+ ", imdbVotes=" + imdbVotes + ", imdbId=" + imdbId + "]";
	}
}
