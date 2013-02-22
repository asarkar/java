package com.github.abhijitsarkar.moviemanager.domain;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "movie", propOrder = { "genreSummary", "movieTitleCount",
		"soapTitleCount", "sumOfSizeOfAllTitles" })
public class Summary implements Serializable {
	private static final long serialVersionUID = -698532393234780406L;

	private List<GenreSummary> genreSummary;
	private int movieTitleCount;
	private int soapTitleCount;
	private long sumOfSizeOfAllTitles;

	public List<GenreSummary> getGenreSummary() {
		return genreSummary;
	}

	public void setGenreSummary(List<GenreSummary> genreSummary) {
		this.genreSummary = genreSummary;
	}

	public int getMovieTitleCount() {
		return movieTitleCount;
	}

	public void setMovieTitleCount(int movieTitleCount) {
		this.movieTitleCount = movieTitleCount;
	}

	public int getSoapTitleCount() {
		return soapTitleCount;
	}

	public void setSoapTitleCount(int soapTitleCount) {
		this.soapTitleCount = soapTitleCount;
	}

	public long getSumOfSizeOfAllTitles() {
		return sumOfSizeOfAllTitles;
	}

	public void setSumOfSizeOfAllTitles(long sumOfSizeOfAllTitles) {
		this.sumOfSizeOfAllTitles = sumOfSizeOfAllTitles;
	}

	@Override
	public boolean equals(Object obj) {
		Summary s = null;

		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Summary)) {
			return false;
		}

		s = (Summary) obj;

		return ((genreSummary == s.genreSummary) || (genreSummary != null && genreSummary
				.equals(s.genreSummary)));
	}

	@Override
	public int hashCode() {
		int result = 17;
		int c = 0;

		c = ((genreSummary == null) ? 0 : genreSummary.hashCode());
		result = 37 * result + c;

		return result;
	}

	@Override
	public String toString() {
		return genreSummary.toString();
	}
}
