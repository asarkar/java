/*******************************************************************************
 * Copyright (c) 2014, the original author or authors.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * A copy of the GNU General Public License accompanies this software, 
 * and is also available at http://www.gnu.org/licenses.
 *******************************************************************************/
package name.abhijitsarkar.hadoop.io;

import static name.abhijitsarkar.hadoop.io.IOUtils.addExtension;
import static name.abhijitsarkar.hadoop.io.IOUtils.compressFile;
import static name.abhijitsarkar.hadoop.io.IOUtils.createMapFile;
import static name.abhijitsarkar.hadoop.io.IOUtils.findInMapFile;
import static name.abhijitsarkar.hadoop.io.IOUtils.removeExtension;
import static name.abhijitsarkar.hadoop.io.IOUtils.uncompressFile;

import java.io.File;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Abhijit Sarkar
 */
public class IOUtilsTest {
	private static URI dirURI = null;
	private static Configuration conf = null;

	@BeforeClass
	public static void oneTimeSetUp() throws Exception {
		conf = new Configuration();
		dirURI = createMapFile(IOUtilsTest.class.getResource("/apat.txt").toURI(), conf);
	}

	@Test
	public void testKeyFound() {
		final String key = "3858243";
		final String expectedValue = "1975 5485 1973 \"FR\" \"\" 445095 3 12 2 6 63 7 2 0.8571 0 0.2778 6 8.1429 0 0 0 0";
		final Writable actualValue = findInMapFile(new Text(key), dirURI, conf);
		Assert.assertNotNull("Value for key: " + key + " shouldn't be null", actualValue);
		Assert.assertEquals("Wrong value retirned for key: " + key, expectedValue, actualValue.toString());
	}

	@Test
	public void testKeyNotFound() {
		final String key = "1";
		final Writable actualValue = findInMapFile(new Text(key), dirURI, conf);
		Assert.assertNull("Value for key: " + key + " should be null", actualValue);
	}

	@Test
	public void testCompressUncompressFile() {
		try {
			URI archiveURI = compressFile(getClass().getResource("/apat.txt").toURI(), "gzip", new Configuration());

			System.out.println("archiveURI: " + archiveURI);

			Assert.assertTrue("Can't read archive: " + archiveURI, new File(archiveURI).canRead());

			URI uncompressedURI = uncompressFile(new URI(archiveURI.getPath()), conf);
			Assert.assertTrue("Can't read uncompressed file: " + uncompressedURI,
					new File(uncompressedURI.getPath()).canRead());
		} catch (Exception e) {
			e.printStackTrace();

			Assert.fail();
		}
	}

	@Test
	public void testAddExtension() {
		Assert.assertEquals("Wrong extension", "/a/b/c.txt.log", addExtension("/a/b/c.txt", ".log", false));
		Assert.assertEquals("Wrong extension", "/a/b/c.log", addExtension("/a/b/c.txt", ".log", true));
		Assert.assertEquals("Wrong extension", ".log", addExtension(".txt", ".log", true));
		Assert.assertEquals("Wrong extension", ".txt.log", addExtension(".txt", ".log", false));
		Assert.assertEquals("Wrong extension", ".", addExtension(".", ".txt", true));
		Assert.assertEquals("Wrong extension", ".", addExtension(".", ".txt", false));
		Assert.assertEquals("Wrong extension", "a.txt", addExtension("a.", ".txt", true));
	}

	@Test
	public void testRemoveExtension() {
		Assert.assertEquals("Wrong extension", "/a/b/c.txt", removeExtension("/a/b/c.txt.log"));
		Assert.assertEquals("Wrong extension", "/a/b/c", removeExtension("/a/b/c.log"));
		Assert.assertEquals("Wrong extension", "", removeExtension(".txt"));
		Assert.assertEquals("Wrong extension", ".", removeExtension("."));
		Assert.assertEquals("Wrong extension", "a", removeExtension("a.txt"));
	}

	@AfterClass
	public static void cleanUpMyMess() {
		new File(dirURI).delete();
	}
}
