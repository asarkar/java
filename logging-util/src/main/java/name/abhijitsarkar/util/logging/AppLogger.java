package name.abhijitsarkar.util.logging;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class AppLogger {
	private static String CONFIG_FILE_PATH = "/log4j.properties";

	static {
		try {
			PropertyConfigurator.configure(AppLogger.class
					.getResource(CONFIG_FILE_PATH));
		} catch (Exception fnfe) {
			BasicConfigurator.configure();
		}
	}

	public static Logger getInstance(Class<?> clazz) {
		return Logger.getLogger(clazz);
	}
}
