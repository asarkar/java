package name.abhijitsarkar.moviemanager.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "genreSummary", propOrder = { "genre", "movieTitleCount",
		"soapTitleCount", "sumOfSizeOfAllTitlesInThisGenre" })
public class GenreSummary implements Serializable {
	private static final long serialVersionUID = 7752847629385617137L;

	private String genre;
	private int movieTitleCount;
	private int soapTitleCount;
	private long sumOfSizeOfAllTitlesInThisGenre;

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
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

	public long getSumOfSizeOfAllTitlesInThisGenre() {
		return sumOfSizeOfAllTitlesInThisGenre;
	}

	public void setSumOfSizeOfAllTitlesInThisGenre(
			long sumOfSizeOfAllTitlesInThisGenre) {
		this.sumOfSizeOfAllTitlesInThisGenre = sumOfSizeOfAllTitlesInThisGenre;
	}

	@Override
	public boolean equals(Object obj) {
		GenreSummary gs = null;
		boolean result = false;

		if (obj == this) {
			return true;
		}
		if (!(obj instanceof GenreSummary)) {
			return false;
		}
		gs = (GenreSummary) obj;

		result = (genre != null && genre.equals(gs.genre));
		result &= (movieTitleCount == gs.movieTitleCount);
		result &= (soapTitleCount == gs.soapTitleCount);

		return result;
	}

	@Override
	public int hashCode() {
		int result = 17;
		int c = 0;

		c = ((genre == null) ? 0 : genre.hashCode());
		result = 37 * result + c;

		c = (int) (movieTitleCount);
		result = 37 * result + c;

		c = (int) (soapTitleCount);
		result = 37 * result + c;

		return result;
	}

	@Override
	public String toString() {
		return "[genre = " + genre + ", movieTitleCount = " + movieTitleCount
				+ ", soapTitleCount = " + soapTitleCount + "]";
	}
}
