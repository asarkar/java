package com.github.abhijitsarkar.moviemanager.delegate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.github.abhijitsarkar.moviemanager.domain.Genre;
import com.github.abhijitsarkar.moviemanager.domain.GenreSummary;
import com.github.abhijitsarkar.moviemanager.domain.Movie;
import com.github.abhijitsarkar.moviemanager.domain.Summary;
import com.github.abhijitsarkar.moviemanager.service.MovieService;
import com.github.abhijitsarkar.moviemanager.service.impl.MovieServiceImpl;
import com.github.abhijitsarkar.moviemanager.util.logging.MovieManagerLogger;

public class MovieManager {

	private MovieService movieService;
	private static String inputDirPath;
	private static File outputFile;
	final static NumberFormat formatter = NumberFormat.getInstance();
	static Logger logger = MovieManagerLogger.getInstance();
	public static final String EMPTY = "";

	public MovieManager() {
		this.movieService = new MovieServiceImpl();
	}

	public MovieManager(File inputDir, File outputFile)
			throws FileNotFoundException {
		if (outputFile != null) {
			validateFile(inputDir, outputFile);
		} else {
			validateFile(inputDir);
		}

		this.movieService = new MovieServiceImpl();
		if (inputDir != null) {
			MovieManager.inputDirPath = inputDir.getAbsolutePath();
		}

		MovieManager.outputFile = outputFile;

		if (MovieManager.outputFile != null && MovieManager.outputFile.exists()) {
			MovieManager.outputFile.delete();
		}
	}

	public static void validateFile(File... files) throws FileNotFoundException {
		FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter(
				"Microsoft Excel", "xls", "xlsx");

		for (File file : files) {
			if (file == null) {
				logger.error("File not found.");
				throw new FileNotFoundException("File not found.");
			} else if (!file.isDirectory() && !extensionFilter.accept(file)) {
				logger.error("File extension is not acceptable: "
						+ file.getAbsolutePath());
				throw new IllegalArgumentException(
						"File extension is not acceptable.");
			} else if (file.isDirectory()
					&& (!file.canRead() || !file.canExecute())) {
				logger.error("Directory not found or is not readable: "
						+ file.getAbsolutePath());
				throw new IllegalArgumentException(
						"Directory not found or is not readable.");
			}
		}
	}

	public static GenreSummary getSummaryByGenre(Genre genre) throws Exception {
		GenreSummary genreSummary = null;

		MovieManager movieManager = new MovieManager();
		genreSummary = movieManager.movieService.getSummaryByGenre(
				movieManager.movieService.filterMovieSetByGenre(
						movieManager.movieService.getMovieSet(inputDirPath),
						genre), genre);
		logger.info("Genre summary: " + genreSummary);

		return genreSummary;
	}

	public static Summary getSummary() throws Exception {
		new MovieManager();

		Summary summary = getSummary(getMovieSetGroupedByGenre());
		List<GenreSummary> genreSummaryList = summary.getGenreSummary();

		logger.info("Movie count: " + summary.getMovieTitleCount());
		logger.info("Soap count: " + summary.getSoapTitleCount());

		for (GenreSummary genreSummary : genreSummaryList) {
			logger.info("Genre summary: " + genreSummary);
		}

		return summary;
	}

	public static Map<Genre, SortedSet<Movie>> getMovieSetGroupedByGenre()
			throws Exception {
		MovieManager movieManager = new MovieManager();
		SortedSet<Movie> movieSet = movieManager.movieService
				.getMovieSet(inputDirPath);

		return movieManager.movieService.groupMovieSetByGenre(movieSet);
	}

	public static void createSummarySheet(Summary summary) throws Exception {
		final String SUMMARY = "Summary";
		final String MOVIE_COUNT = "Movie Count";
		final String SOAP_COUNT = "Soap Count";
		final String SIZE = "Size";
		final String GENRE_SUMMARY = "Genre Summary";

		Workbook wb = createWorkbook(outputFile);

		List<GenreSummary> genreSummaryList = summary.getGenreSummary();
		Sheet summarySheet = null;
		Row row = null;
		CellStyle headerCellStyle = createHeaderCellStyle(wb.createCellStyle(),
				wb.createFont());
		CellStyle bodyCellStyle = createBodyCellStyle(wb.createCellStyle(),
				wb.createFont());

		summarySheet = createNewSheet(wb, SUMMARY);

		row = summarySheet.createRow(0);
		summarySheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
		createCell(row, 0, headerCellStyle, SUMMARY, Cell.CELL_TYPE_STRING);

		row = summarySheet.createRow(1);
		createCell(row, 0, headerCellStyle, MOVIE_COUNT, Cell.CELL_TYPE_STRING);
		createCell(row, 1, headerCellStyle, SOAP_COUNT, Cell.CELL_TYPE_STRING);
		createCell(row, 2, headerCellStyle, SIZE, Cell.CELL_TYPE_STRING);

		row = summarySheet.createRow(2);
		createCell(row, 0, bodyCellStyle, summary.getMovieTitleCount(),
				Cell.CELL_TYPE_NUMERIC);
		createCell(row, 1, bodyCellStyle, summary.getSoapTitleCount(),
				Cell.CELL_TYPE_NUMERIC);
		createCell(row, 2, bodyCellStyle,
				formatter.format(summary.getSumOfSizeOfAllTitles()),
				Cell.CELL_TYPE_STRING);

		for (GenreSummary genreSummary : genreSummaryList) {
			row = summarySheet.createRow(summarySheet.getLastRowNum() + 2);

			summarySheet.addMergedRegion(new CellRangeAddress(row.getRowNum(),
					row.getRowNum(), 0, 2));
			createCell(row, 0, headerCellStyle, GENRE_SUMMARY + " ("
					+ genreSummary.getGenre() + ")", Cell.CELL_TYPE_STRING);

			row = summarySheet.createRow(summarySheet.getLastRowNum() + 1);
			createCell(row, 0, headerCellStyle, MOVIE_COUNT,
					Cell.CELL_TYPE_STRING);
			createCell(row, 1, headerCellStyle, SOAP_COUNT,
					Cell.CELL_TYPE_STRING);
			createCell(row, 2, headerCellStyle, SIZE, Cell.CELL_TYPE_STRING);

			row = summarySheet.createRow(summarySheet.getLastRowNum() + 1);
			createCell(row, 0, bodyCellStyle,
					genreSummary.getMovieTitleCount(), Cell.CELL_TYPE_NUMERIC);
			createCell(row, 1, bodyCellStyle, genreSummary.getSoapTitleCount(),
					Cell.CELL_TYPE_NUMERIC);
			createCell(row, 2, bodyCellStyle, formatter.format(genreSummary
					.getSumOfSizeOfAllTitlesInThisGenre()),
					Cell.CELL_TYPE_STRING);

			autoSizeColumns(summarySheet);
		}

		writeToDisk(wb);
	}

	public static void createGenreSheet(
			SortedSet<Movie> filteredMovieSetByGenre, Genre genre)
			throws Exception {
		final String MOVIE_NAME = "Movie Name";
		final String RELEASE_YEAR = "Release Year";
		final String FILESIZE = "Filesize";
		final String PARENT = "Parent";

		Workbook wb = createWorkbook(outputFile);

		Sheet worksheet = null;
		Row row = null;
		CellStyle headerCellStyle = createHeaderCellStyle(wb.createCellStyle(),
				wb.createFont());
		CellStyle bodyCellStyle = createBodyCellStyle(wb.createCellStyle(),
				wb.createFont());

		Iterator<Movie> it = null;
		Movie movie = null;

		worksheet = createNewSheet(wb, genre.toString());

		it = filteredMovieSetByGenre.iterator();

		row = worksheet.createRow(0);
		createCell(row, 0, headerCellStyle, MOVIE_NAME, Cell.CELL_TYPE_STRING);
		createCell(row, 1, headerCellStyle, RELEASE_YEAR, Cell.CELL_TYPE_STRING);
		createCell(row, 2, headerCellStyle, FILESIZE, Cell.CELL_TYPE_STRING);
		createCell(row, 3, headerCellStyle, PARENT, Cell.CELL_TYPE_STRING);

		for (int i = 0; it.hasNext(); i++) {
			row = worksheet.createRow(i + 1);
			movie = it.next();

			createCell(row, 0, bodyCellStyle,
					movie.getName() + movie.getFileExtension(),
					Cell.CELL_TYPE_STRING);
			createCell(row, 1, bodyCellStyle, movie.getYear(),
					Cell.CELL_TYPE_NUMERIC);
			createCell(row, 2, bodyCellStyle,
					formatter.format(movie.getFilesize()),
					Cell.CELL_TYPE_STRING);
			createCell(row, 3, bodyCellStyle, movie.getParent(),
					Cell.CELL_TYPE_STRING);

			Cell cell = row.getCell(1);

			if (((short) cell.getNumericCellValue()) == Movie.UNKNOWN_RELEASE_YEAR) {
				cell.setCellValue(EMPTY);
			}
		}

		autoSizeColumns(worksheet);

		writeToDisk(wb);
	}

	private static void autoSizeColumns(Sheet name) {
		int lastRowNum = name.getLastRowNum();

		for (int rowNum = 0; rowNum <= lastRowNum; rowNum++) {
			name.autoSizeColumn(rowNum);
		}
	}

	private static void writeToDisk(Workbook wb) throws Exception {
		FileOutputStream fileOut = new FileOutputStream(outputFile);

		wb.write(fileOut);

		fileOut.close();
	}

	private static Workbook createWorkbook(File opFile) throws Exception {
		Workbook wb = null;

		if (opFile.canRead()) {
			wb = WorkbookFactory.create(new FileInputStream(opFile));
		} else {
			wb = new XSSFWorkbook();
		}

		return wb;
	}

	private static Sheet createNewSheet(Workbook wb, String name) {
		int sheetIndex = wb.getSheetIndex(name);

		if (sheetIndex >= 0) {
			wb.removeSheetAt(sheetIndex);
		}

		return wb.createSheet(name);
	}

	private static CellStyle createHeaderCellStyle(CellStyle headerCellStyle,
			Font font) {
		/* border */
		headerCellStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
		headerCellStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
		headerCellStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);

		/* pattern */
		headerCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		/* foreground color */
		headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());

		/* alignment */
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		/* font */
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerCellStyle.setFont(font);

		/* wrap */
		headerCellStyle.setWrapText(true);

		return headerCellStyle;
	}

	private static CellStyle createBodyCellStyle(CellStyle bodyCellStyle,
			Font font) {
		/* border */
		bodyCellStyle.setBorderLeft(CellStyle.BORDER_DASHED);
		bodyCellStyle.setBorderTop(CellStyle.BORDER_DASHED);
		bodyCellStyle.setBorderRight(CellStyle.BORDER_DASHED);
		bodyCellStyle.setBorderBottom(CellStyle.BORDER_DASHED);

		/* alignment */
		bodyCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		bodyCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		/* font */
		bodyCellStyle.setFont(font);

		/* wrap */
		bodyCellStyle.setWrapText(true);

		return bodyCellStyle;
	}

	private static void createCell(Row row, int column, CellStyle cellStyle,
			Object cellValue, int cellType) {
		Cell cell = row.createCell(column);
		cell.setCellStyle(cellStyle);

		switch (cellType) {
		case Cell.CELL_TYPE_NUMERIC:
			if (cellValue != null) {
				cell.setCellValue(Long.parseLong(cellValue.toString()));
			}
			break;
		case Cell.CELL_TYPE_STRING:
		default:
			if (cellValue != null) {
				cell.setCellValue(cellValue.toString());
			}
			break;
		}
	}

	public static Summary getSummary(
			Map<Genre, SortedSet<Movie>> movieSetGroupedByGenre) {
		return new MovieManager().movieService
				.getSummary(movieSetGroupedByGenre);
	}
}
