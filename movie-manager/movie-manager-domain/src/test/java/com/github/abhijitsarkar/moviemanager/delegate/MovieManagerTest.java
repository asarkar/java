package com.github.abhijitsarkar.moviemanager.delegate;

import static com.github.abhijitsarkar.moviemanager.domain.Genre.ACTION_AND_ADVENTURE;
import static com.github.abhijitsarkar.moviemanager.domain.Genre.HORROR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.SortedSet;

import com.github.abhijitsarkar.moviemanager.delegate.MovieManager;
import com.github.abhijitsarkar.moviemanager.domain.Genre;
import com.github.abhijitsarkar.moviemanager.domain.Movie;
import com.github.abhijitsarkar.moviemanager.domain.Summary;
import com.github.abhijitsarkar.moviemanager.service.MovieService;
import com.github.abhijitsarkar.moviemanager.service.mock.MovieServiceMock;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class MovieManagerTest {

    private static MovieService movieService = new MovieServiceMock();
    private static SortedSet<Movie> movieSet;
    private static final String OUTPUT_FILE = "MovieManagerTestOutput.xlsx";
    private static File outputFile;
    private static Map<Genre, SortedSet<Movie>> movieSetGroupedByGenre;
    private static Summary summary;

    @BeforeClass
    public static void oneTimeSetUp() throws Exception {
	outputFile = new File(OUTPUT_FILE);

	assertNotNull(outputFile);

	new MovieManager(new File("ignore.xlsx"), outputFile);
	movieSet = movieService.getMovieSet(null);

	movieSetGroupedByGenre = movieService.groupMovieSetByGenre(movieSet);
	summary = movieService.getSummary(movieSetGroupedByGenre);
    }

    @AfterClass
    public static void oneTimeTearDown() {
	movieService = null;
	movieSet = null;

	if (outputFile.exists()) {
	    outputFile.delete();
	}
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateUnreadableDirectory() throws FileNotFoundException {
	MovieManager.validateFile(new File("src/test/resources/unreadable"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateUnacceptableFileExtension()
	    throws FileNotFoundException {
	MovieManager.validateFile(new File(
		"src/test/resources/invalid_extension.txt"));
    }

    @Test
    public void testValidateGood() {
	try {
	    MovieManager.validateFile(new File("src/test/resources"));
	    MovieManager.validateFile(new File(
		    "src/test/resources/valid_extension.xlsx"));
	} catch (Exception ex) {
	    fail("Should not have been here.");
	}
    }

    @Test
    public void testCreateSummarySheet() throws Exception {
	final String SUMMARY = "Summary";
	final String MOVIE_COUNT = "Movie Count";
	final String SOAP_COUNT = "Soap Count";
	final String SIZE = "Size";

	Sheet summarySheet = null;
	Row row = null;

	MovieManager.createSummarySheet(summary);

	InputStream input = new FileInputStream(outputFile);

	Workbook wb = WorkbookFactory.create(input);

	summarySheet = wb.getSheet(SUMMARY);
	assertNotNull(summarySheet);

	row = summarySheet.getRow(0);
	assertEquals(summarySheet.getMergedRegion(0).getFirstRow(), 0);
	assertEquals(summarySheet.getMergedRegion(0).getLastRow(), 0);
	assertEquals(summarySheet.getMergedRegion(0).getFirstColumn(), 0);
	assertEquals(summarySheet.getMergedRegion(0).getLastColumn(), 2);
	assertEquals(row.getCell(0).getStringCellValue(), SUMMARY);

	row = summarySheet.getRow(1);
	assertEquals(row.getCell(0).getStringCellValue(), MOVIE_COUNT);
	assertEquals(row.getCell(1).getStringCellValue(), SOAP_COUNT);
	assertEquals(row.getCell(2).getStringCellValue(), SIZE);

	input.close();
    }

    @Test
    public void testCreateGenreSheets() throws Exception {
	Sheet genreSheet = null;
	Row row = null;

	MovieManager.createGenreSheet(movieService.filterMovieSetByGenre(
		movieSet, ACTION_AND_ADVENTURE), ACTION_AND_ADVENTURE);
	// movieSetGroupedByGenre.get(ACTION_AND_ADVENTURE),
	// ACTION_AND_ADVENTURE);

	MovieManager.createGenreSheet(
		movieService.filterMovieSetByGenre(movieSet, HORROR), HORROR);

	InputStream input = new FileInputStream(outputFile);

	Workbook wb = WorkbookFactory.create(input);

	genreSheet = wb.getSheet(ACTION_AND_ADVENTURE.toString());
	assertNotNull(genreSheet);

	row = genreSheet.getRow(1);
	assertEquals(row.getCell(0).getStringCellValue(), "3-10 To Yuma.mkv");

	row = genreSheet.getRow(3);
	assertEquals(row.getCell(0).getStringCellValue(), "Casino Royal.mkv");

	// MovieManager.createGenreSheet(movieSetGroupedByGenre.get(HORROR),
	// HORROR);

	genreSheet = wb.getSheet(HORROR.toString());
	assertNotNull(genreSheet);

	row = genreSheet.getRow(1);
	assertEquals(row.getCell(0).getStringCellValue(), "I Saw The Devil.mkv");
	assertEquals(row.getCell(3).getStringCellValue(),
		"I Saw The Devil (2010)");

	row = genreSheet.getRow(2);
	assertEquals(row.getCell(0).getStringCellValue(), "Inferno.mkv");
	assertEquals(row.getCell(3).getStringCellValue(),
		"The Three Mothers Trilogy");

	input.close();
    }
}
