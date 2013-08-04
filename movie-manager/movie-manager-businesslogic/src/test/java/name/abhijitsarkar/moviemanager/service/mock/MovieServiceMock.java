package name.abhijitsarkar.moviemanager.service.mock;

import static name.abhijitsarkar.moviemanager.domain.Genre.ACTION_AND_ADVENTURE;
import static name.abhijitsarkar.moviemanager.domain.Genre.HORROR;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import name.abhijitsarkar.moviemanager.domain.GenreSummary;
import name.abhijitsarkar.moviemanager.domain.Movie;
import name.abhijitsarkar.moviemanager.domain.Summary;
import name.abhijitsarkar.moviemanager.service.MovieService;
import name.abhijitsarkar.moviemanager.service.impl.MovieComparator;
import name.abhijitsarkar.moviemanager.service.impl.MovieServiceImpl;

public class MovieServiceMock implements MovieService {
	private MovieService movieServiceImpl;

	public MovieServiceMock() {
		movieServiceImpl = new MovieServiceImpl();
	}

	@Override
	public Set<Movie> getMovieSet(String path) throws IOException {
		if (path != null) {
			return movieServiceImpl.getMovieSet(path);
		}

		SortedSet<Movie> movieSet = new TreeSet<Movie>(
				new MovieComparator<Movie>());
		Movie movie = null;

		movie = new Movie("3-10 To Yuma", (short) 2007,
				ACTION_AND_ADVENTURE.toString(), 2250491151L, ".mkv");
		movie.setParent(null);

		movieSet.add(movie);

		movie = new Movie("Body of Lies", (short) 2008,
				ACTION_AND_ADVENTURE.toString(), 2233486674L, ".mkv");
		movie.setParent(null);

		movieSet.add(movie);

		movie = new Movie("Casino Royal", (short) 2006,
				ACTION_AND_ADVENTURE.toString(), 1571095307L, ".mkv");
		movie.setParent("James Bond Series");

		movieSet.add(movie);

		movie = new Movie("Die Another Day", (short) 2002,
				ACTION_AND_ADVENTURE.toString(), 1535037921L, ".mkv");
		movie.setParent("James Bond Series");

		movieSet.add(movie);

		movie = new Movie("Sphere", (short) 1998,
				ACTION_AND_ADVENTURE.toString(), 2154415449L, ".mkv");
		movie.setParent(null);

		movieSet.add(movie);

		movie = new Movie("I Saw The Devil", (short) 2010, HORROR.toString(),
				2839413237L, ".mkv");
		movie.setParent("I Saw The Devil (2010)");

		movieSet.add(movie);

		movie = new Movie("Inferno", (short) 1980, HORROR.toString(),
				2216523351L, ".mkv");
		movie.setParent("The Three Mothers Trilogy");

		movieSet.add(movie);

		movie = new Movie("Mother of Tears", (short) 2007, HORROR.toString(),
				1972573064L, ".mkv");
		movie.setParent("The Three Mothers Trilogy");

		movieSet.add(movie);

		movie = new Movie("The Orphanage", (short) 2007, HORROR.toString(),
				2359279619L, ".mkv");
		movie.setParent("The Orphanage (2007)");

		movieSet.add(movie);

		movie = new Movie("The Orphanage 2", (short) 2007, HORROR.toString(),
				2359279619L, ".mkv");
		movie.setParent("The Orphanage (2007)");

		movieSet.add(movie);

		movie = new Movie("The Orphanage 3", (short) 2009, HORROR.toString(),
				2359279619L, ".mkv");
		movie.setParent("The Orphanage (2009)");

		movieSet.add(movie);

		return movieSet;
	}

	@Override
	public Set<Movie> filterMovieSetByGenre(Set<Movie> movieSet, String genre) {
		return movieServiceImpl.filterMovieSetByGenre(movieSet, genre);
	}

	@Override
	public Map<String, Set<Movie>> groupMovieSetByGenre(Set<Movie> movieSet) {
		return movieServiceImpl.groupMovieSetByGenre(movieSet);
	}

	@Override
	public Summary getSummary(Map<String, Set<Movie>> movieSetSortedByGenre) {
		return movieServiceImpl.getSummary(movieSetSortedByGenre);
	}

	@Override
	public GenreSummary getSummaryByGenre(Set<Movie> movieSetFilteredByGenre,
			String genre) {
		return movieServiceImpl.getSummaryByGenre(movieSetFilteredByGenre,
				genre);
	}

	@Override
	public String getMovieDirectory() {
		return movieServiceImpl.getMovieDirectory();
	}
}
