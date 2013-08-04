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

package name.abhijitsarkar.moviedatabase.api.client.domain;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Movie instance customized for the IMDB API.
 * 
 * @author Abhijit Sarkar
 * 
 */
public class OmdbApiStyleMovie extends Movie implements Serializable {

	private static final long serialVersionUID = 8002837762507424427L;

	public OmdbApiStyleMovie() {
	}

	public OmdbApiStyleMovie(String imdbId) {
		this.imdbId = imdbId;
	}

	public OmdbApiStyleMovie(String title, short year) {
		this.title = title;
		this.year = year;
	}

	public OmdbApiStyleMovie(String title, short year, String imdbId) {
		this.title = title;
		this.year = year;
		this.imdbId = imdbId;
	}

	public String getTitle() {
		return title;
	}

	@JsonProperty(value = "Title")
	public void setTitle(String title) {
		this.title = title;
	}

	public short getYear() {
		return year;
	}

	@JsonProperty(value = "Year")
	public void setYear(short year) {
		this.year = year;
	}

	public String getGenre() {
		return genre;
	}

	@JsonProperty(value = "Genre")
	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getMpaaRating() {
		return mpaaRating;
	}

	@JsonProperty(value = "Rated")
	public void setMpaaRating(String mpaaRating) {
		this.mpaaRating = mpaaRating;
	}

	public String getRuntime() {
		return runtime;
	}

	@JsonProperty(value = "Runtime")
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getImdbRating() {
		return imdbRating;
	}

	@JsonProperty(value = "imdbRating")
	public void setImdbRating(String imdbRating) {
		this.imdbRating = imdbRating;
	}

	public String getImdbVotes() {
		return imdbVotes;
	}

	@JsonProperty(value = "imdbVotes")
	public void setImdbVotes(String imdbVotes) {
		this.imdbVotes = imdbVotes;
	}

	public String getImdbId() {
		return imdbId;
	}

	@JsonProperty(value = "imdbID")
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
		OmdbApiStyleMovie other = (OmdbApiStyleMovie) obj;
		if (imdbId == null) {
			if (other.imdbId != null)
				return false;
		} else if (!imdbId.equals(other.imdbId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OmdbApiStyleMovie [title=" + title + ", year=" + year
				+ ", genre=" + genre + ", mpaaRating=" + mpaaRating
				+ ", runtime=" + runtime + ", imdbRating=" + imdbRating
				+ ", imdbVotes=" + imdbVotes + ", imdbId=" + imdbId + "]";
	}
}
