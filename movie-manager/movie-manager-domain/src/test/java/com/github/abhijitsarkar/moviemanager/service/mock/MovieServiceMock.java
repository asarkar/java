package com.github.abhijitsarkar.moviemanager.service.mock;

import static com.github.abhijitsarkar.moviemanager.domain.Genre.ACTION_AND_ADVENTURE;
import static com.github.abhijitsarkar.moviemanager.domain.Genre.HORROR;

import java.io.IOException;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import com.github.abhijitsarkar.moviemanager.domain.Genre;
import com.github.abhijitsarkar.moviemanager.domain.GenreSummary;
import com.github.abhijitsarkar.moviemanager.domain.Movie;
import com.github.abhijitsarkar.moviemanager.domain.Summary;
import com.github.abhijitsarkar.moviemanager.service.MovieService;
import com.github.abhijitsarkar.moviemanager.service.impl.MovieComparator;
import com.github.abhijitsarkar.moviemanager.service.impl.MovieServiceImpl;

public class MovieServiceMock implements MovieService {
	private MovieService movieServiceImpl;

	public MovieServiceMock() {
		movieServiceImpl = new MovieServiceImpl();
	}

	@Override
	public SortedSet<Movie> getMovieSet(String path) throws IOException {
		if (path != null) {
			return movieServiceImpl.getMovieSet(path);
		}

		SortedSet<Movie> movieSet = new TreeSet<Movie>(
				new MovieComparator<Movie>());
		Movie movie = null;

		movie = new Movie("3-10 To Yuma", (short) 2007, ACTION_AND_ADVENTURE,
				2250491151L, ".mkv");
		movie.setParent(null);

		movieSet.add(movie);

		movie = new Movie("Body of Lies", (short) 2008, ACTION_AND_ADVENTURE,
				2233486674L, ".mkv");
		movie.setParent(null);

		movieSet.add(movie);

		movie = new Movie("Casino Royal", (short) 2006, ACTION_AND_ADVENTURE,
				1571095307L, ".mkv");
		movie.setParent("James Bond Series");

		movieSet.add(movie);

		movie = new Movie("Die Another Day", (short) 2002,
				ACTION_AND_ADVENTURE, 1535037921L, ".mkv");
		movie.setParent("James Bond Series");

		movieSet.add(movie);

		movie = new Movie("Sphere", (short) 1998, ACTION_AND_ADVENTURE,
				2154415449L, ".mkv");
		movie.setParent(null);

		movieSet.add(movie);

		movie = new Movie("I Saw The Devil", (short) 2010, HORROR, 2839413237L,
				".mkv");
		movie.setParent("I Saw The Devil (2010)");

		movieSet.add(movie);

		movie = new Movie("Inferno", (short) 1980, HORROR, 2216523351L, ".mkv");
		movie.setParent("The Three Mothers Trilogy");

		movieSet.add(movie);

		movie = new Movie("Mother of Tears", (short) 2007, HORROR, 1972573064L,
				".mkv");
		movie.setParent("The Three Mothers Trilogy");

		movieSet.add(movie);

		movie = new Movie("The Orphanage", (short) 2007, HORROR, 2359279619L,
				".mkv");
		movie.setParent("The Orphanage (2007)");

		movieSet.add(movie);

		movie = new Movie("The Orphanage 2", (short) 2007, HORROR, 2359279619L,
				".mkv");
		movie.setParent("The Orphanage (2007)");

		movieSet.add(movie);

		movie = new Movie("The Orphanage 3", (short) 2009, HORROR, 2359279619L,
				".mkv");
		movie.setParent("The Orphanage (2009)");

		movieSet.add(movie);

		return movieSet;
	}

	@Override
	public SortedSet<Movie> filterMovieSetByGenre(SortedSet<Movie> movieSet,
			Genre genre) {
		return movieServiceImpl.filterMovieSetByGenre(movieSet, genre);
	}

	@Override
	public Map<Genre, SortedSet<Movie>> groupMovieSetByGenre(
			SortedSet<Movie> movieSet) {
		return movieServiceImpl.groupMovieSetByGenre(movieSet);
	}

	@Override
	public Summary getSummary(Map<Genre, SortedSet<Movie>> movieSetSortedByGenre) {
		return movieServiceImpl.getSummary(movieSetSortedByGenre);
	}

	@Override
	public GenreSummary getSummaryByGenre(
			SortedSet<Movie> movieSetFilteredByGenre, Genre genre) {
		return movieServiceImpl.getSummaryByGenre(movieSetFilteredByGenre,
				genre);
	}

	@Override
	public String getMovieDirectory() {
		return movieServiceImpl.getMovieDirectory();
	}
}
