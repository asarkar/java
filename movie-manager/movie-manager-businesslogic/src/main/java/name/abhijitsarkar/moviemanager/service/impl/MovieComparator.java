package name.abhijitsarkar.moviemanager.service.impl;

import java.util.Comparator;

import name.abhijitsarkar.moviemanager.domain.Movie;

public final class MovieComparator<E> implements Comparator<E> {
	private static final int EQUAL = 0;

	@Override
	public int compare(E e1, E e2) {
		if (!(e1 instanceof Movie)) {
			throw new IllegalArgumentException("Invalid type parameter e1: "
					+ e1.getClass().getName() + ". Only "
					+ Movie.class.getName() + " accepted.");
		}

		if (!(e2 instanceof Movie)) {
			throw new IllegalArgumentException("Invalid type parameter e2: "
					+ e2.getClass().getName() + ". Only "
					+ Movie.class.getName() + " accepted.");
		}

		Movie m1 = (Movie) e1;
		Movie m2 = (Movie) e2;

		String firstMovieName = m1.getName();
		String secondMovieName = m2.getName();
		String firstMovieGenre = m1.getGenre();
		String secondMovieGenre = m2.getGenre();
		short firstMovieYear = m1.getYear();
		short secondMovieYear = m2.getYear();

		if (firstMovieName.equals(secondMovieName)) {
			if (firstMovieGenre.equals(secondMovieGenre)) {
				if (firstMovieYear == secondMovieYear) {
					return EQUAL;
				} else {
					return firstMovieYear - secondMovieYear;
				}
			} else {
				return firstMovieGenre.compareTo(secondMovieGenre);
			}
		} else {
			return firstMovieName.compareTo(secondMovieName);
		}
	}
}
