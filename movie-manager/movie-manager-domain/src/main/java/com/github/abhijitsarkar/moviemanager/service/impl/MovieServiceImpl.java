package com.github.abhijitsarkar.moviemanager.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.github.abhijitsarkar.moviemanager.domain.Genre;
import com.github.abhijitsarkar.moviemanager.domain.GenreSummary;
import com.github.abhijitsarkar.moviemanager.domain.Movie;
import com.github.abhijitsarkar.moviemanager.domain.MovieFileFormat;
import com.github.abhijitsarkar.moviemanager.domain.Summary;
import com.github.abhijitsarkar.moviemanager.service.MovieService;
import com.github.abhijitsarkar.moviemanager.util.logging.MovieManagerLogger;

public class MovieServiceImpl implements MovieService {
	/*
	 * The following regex matches file names with release year in parentheses,
	 * something like Titanic (1997).mkv Each part of the regex is explained
	 * further:
	 * 
	 * ([-',!\\[\\]\\.\\w\\s]++) -> Matches one or more occurrences of any
	 * alphabet, number or the following special characters in the movie name:
	 * dash (-), apostrophe ('), comma (,), exclamation sign (!), square braces
	 * ([]), full stop (.)
	 * 
	 * (?:\\((\\d{4})\\)) -> Matches 4 digit release year within parentheses.
	 * 
	 * (.++) -> Matches one or more occurrences of any character.
	 */
	private static final String MOVIE_NAME_WITH_RELEASE_YEAR_REGEX = "([-',!\\[\\]\\.\\w\\s]++)(?:\\((\\d{4})\\))?+(.++)";
	private static final Pattern pattern = Pattern
			.compile(MOVIE_NAME_WITH_RELEASE_YEAR_REGEX);
	private final static Map<String, Genre> genreMap;
	private static Logger logger = MovieManagerLogger.getInstance();

	private Matcher matcher = null;
	private Genre currentGenre = null;
	private String movieDirectory = null;

	static {
		genreMap = new HashMap<String, Genre>();

		for (Genre genre : Genre.values()) {
			genreMap.put(genre.toString(), genre);
		}
	}

	public MovieServiceImpl() {
		currentGenre = Genre.UNKNOWN;
	}

	@Override
	public SortedSet<Movie> getMovieSet(String path) throws IOException {
		File f = new File(path);

		if (!f.isAbsolute()) {
			throw new IllegalArgumentException(f.getCanonicalPath()
					+ " is not an absolute path.");
		}

		movieDirectory = path;

		SortedSet<Movie> movieSet = new TreeSet<Movie>(
				new MovieComparator<Movie>());

		addToMovieSet(f, movieSet);

		return movieSet;
	}

	public String getMovieDirectory() {
		return this.movieDirectory;
	}

	private void addToMovieSet(File dir, SortedSet<Movie> movieSet)
			throws IOException {

		if (!dir.exists() || !dir.isDirectory()) {
			throw new IllegalArgumentException(dir.getCanonicalPath()
					+ " does not exist or is not a directory.");
		}
		if (!dir.canRead()) {
			throw new IllegalArgumentException(dir.getCanonicalPath()
					+ " does not exist or is not readable.");
		}

		Movie movie = null;
		String fileName = null;
		String parent = null;
		boolean isUnique = false;

		File[] fileList = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory()
						|| isMovieFile(pathname.getName());
			}
		});

		for (File file : fileList) {
			fileName = file.getName();

			if (file.isDirectory()) {
				if (isGenre(fileName)) {
					currentGenre = genreMap.get(fileName);
				}
				addToMovieSet(file, movieSet);
			} else {
				movie = parseFilenameToGetMovie(fileName);

				movie.setGenre(currentGenre);
				movie.setFilesize(file.length());

				parent = this.getParent(file, currentGenre, null);

				if (parent != null
						&& !parent.equalsIgnoreCase(currentGenre.toString())) {
					movie.setParent(parent);
				}

				isUnique = movieSet.add(movie);

				if (!isUnique) {
					logger.warn("Found duplicate movie: " + movie);
				}
			}
		}
	}

	Movie parseFilenameToGetMovie(String fileName) {
		short year = Movie.UNKNOWN_RELEASE_YEAR;
		String movieName = null;
		/* Unicode representation of char . */
		final char fullStop = '\u002E';
		String movieFileExtension = getFileExtension(fileName);
		String lastPart = null;

		matcher = pattern.matcher(fileName);
		if (matcher.find() && matcher.groupCount() >= 1) {
			movieName = matcher.group(1).trim();
			if (matcher.group(2) != null) {
				year = Short.parseShort(matcher.group(2));
			}
			if (matcher.group(3) != null) {
				lastPart = matcher.group(3);

				if (!lastPart.equals(movieFileExtension)) {
					movieName += lastPart.substring(0, lastPart.length()
							- (movieFileExtension.length() + 1));
				}
			}
		} else {
			logger.debug("Found unconventional filename: " + fileName);
			movieName = fileName.substring(0, fileName.length()
					- (movieFileExtension.length() + 1));
		}

		return new Movie(movieName, year, fullStop + movieFileExtension);
	}

	private boolean isMovieFile(String fileName) {

		try {
			MovieFileFormat.valueOf(MovieFileFormat.class,
					getFileExtension(fileName).toUpperCase());
		} catch (IllegalArgumentException iae) {
			return false;
		}
		return true;
	}

	private boolean isGenre(String fileName) {
		return genreMap.get(fileName) != null ? true : false;
	}

	private String getFileExtension(String fileName) {
		/* Unicode representation of char . */
		final char fullStop = '\u002E';

		return fileName.substring(fileName.lastIndexOf(fullStop) + 1,
				fileName.length());
	}

	private String getParent(File file, Genre currentGenre,
			String immediateParent) {
		File parentFile = file.getParentFile();

		if (parentFile == null || !parentFile.isDirectory()
				|| parentFile.compareTo(new File(movieDirectory)) <= 0) {
			return immediateParent;
		}

		if (parentFile.getName().equalsIgnoreCase(currentGenre.toString())) {
			if (file.isDirectory()) {
				return file.getName();
			}
		}

		return getParent(parentFile, currentGenre, parentFile.getName());
	}

	@Override
	public SortedSet<Movie> filterMovieSetByGenre(SortedSet<Movie> movieSet,
			Genre genre) {
		SortedSet<Movie> filteredMovieListByGenre = new TreeSet<Movie>(
				new MovieComparator<Movie>());
		Iterator<Movie> it = movieSet.iterator();
		Movie movie = null;

		while (it.hasNext()) {
			movie = it.next();

			if (movie.getGenre().equals(genre)) {
				filteredMovieListByGenre.add(movie);
			}
		}

		return filteredMovieListByGenre;
	}

	@Override
	public Map<Genre, SortedSet<Movie>> groupMovieSetByGenre(
			SortedSet<Movie> movieSet) {
		Map<Genre, SortedSet<Movie>> movieSetGroupedByGenre = new HashMap<Genre, SortedSet<Movie>>();
		SortedSet<Movie> movieSetFilteredByGenre = null;

		for (Genre genre : Genre.values()) {
			movieSetFilteredByGenre = this.filterMovieSetByGenre(movieSet,
					genre);

			movieSetGroupedByGenre.put(genre, movieSetFilteredByGenre);
		}

		return (movieSetGroupedByGenre.size() > 0) ? movieSetGroupedByGenre
				: null;
	}

	@Override
	public Summary getSummary(
			Map<Genre, SortedSet<Movie>> movieSetGroupedByGenre) {
		SortedSet<Movie> movieSetFilteredByGenre = null;

		int movieTitleCount = 0;
		int soapTitleCount = 0;
		long sumOfSizeOfAllTitles = 0L;

		GenreSummary genreSummary = null;
		List<GenreSummary> genreSummaryList = new ArrayList<GenreSummary>();
		Summary summary = null;

		for (Genre genre : Genre.values()) {
			movieSetFilteredByGenre = movieSetGroupedByGenre.get(genre);

			genreSummary = getSummaryByGenre(movieSetFilteredByGenre, genre);
			sumOfSizeOfAllTitles += genreSummary
					.getSumOfSizeOfAllTitlesInThisGenre();
			movieTitleCount += genreSummary.getMovieTitleCount();
			soapTitleCount += genreSummary.getSoapTitleCount();

			genreSummaryList.add(genreSummary);
		}

		summary = new Summary();
		summary.setGenreSummary(genreSummaryList);
		summary.setMovieTitleCount(movieTitleCount);
		summary.setSoapTitleCount(soapTitleCount);
		summary.setSumOfSizeOfAllTitles(sumOfSizeOfAllTitles);

		return summary;
	}

	private boolean isParentNotASeries(String parent) {
		return (parent == null) ? false : parent.trim().matches(
				".*(?:\\((\\d{4})\\))$");
	}

	@SuppressWarnings("unused")
	@Override
	public GenreSummary getSummaryByGenre(
			SortedSet<Movie> movieSetFilteredByGenre, Genre genre) {
		Iterator<Movie> it = null;

		Movie currentMovie = null;
		String currentMovieParent = null;
		short currentMovieYear = 0;
		Movie previousMovie = null;
		String previousMovieParent = null;

		int movieTitleCount = 0;
		int soapTitleCount = 0;
		long sumOfSizeOfAllTitlesInThisGenre = 0L;

		GenreSummary genreSummary = null;

		it = movieSetFilteredByGenre.iterator();

		for (int i = 0; it.hasNext(); i++) {
			currentMovie = it.next();
			currentMovieParent = currentMovie.getParent();
			currentMovieYear = currentMovie.getYear();

			logger.debug("Current movie: " + currentMovie);

			if (currentMovieYear == Movie.UNKNOWN_RELEASE_YEAR) { // soap
				logger.debug("Current movie is a soap. "
						+ "Soap count is incremented.");

				soapTitleCount++;
			} else { // movie
				/* current movie parent is a series */
				if (!isParentNotASeries(currentMovieParent)) {
					logger.debug("Current movie parent is a series. "
							+ "Movie count is incremented.");

					movieTitleCount++;
				}
				/* previous movie parent is not a series */
				else if (!isParentNotASeries(previousMovieParent)) {
					logger.debug("Previous movie parent is a series. "
							+ "Movie count is incremented.");

					movieTitleCount++;
				}
				/* current movie parent is not equal to previous movie parent */
				else if (!(currentMovieParent.equals(previousMovieParent))) {
					logger.debug("Current movie parent is not equal to "
							+ "previous movie parent. "
							+ "Movie count is incremented.");

					movieTitleCount++;
				} else {
					logger.debug("Movie count is not incremented.");
				}
			}

			logger.debug("Movie count: " + movieTitleCount);

			previousMovie = currentMovie;
			previousMovieParent = currentMovieParent;

			sumOfSizeOfAllTitlesInThisGenre += currentMovie.getFilesize();
		}

		genreSummary = new GenreSummary();
		genreSummary.setGenre(genre);
		genreSummary.setMovieTitleCount(movieTitleCount);
		genreSummary.setSoapTitleCount(soapTitleCount);
		genreSummary
				.setSumOfSizeOfAllTitlesInThisGenre(sumOfSizeOfAllTitlesInThisGenre);

		return genreSummary;
	}
}
