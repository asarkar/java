package name.abhijitsarkar.learning.hadoop.io;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.MapFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.CompressionInputStream;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.util.ReflectionUtils;
import org.apache.log4j.Logger;

/**
 * This is an Utility class for the IO work
 * 
 */
public class IOUtils {
    private static final Logger logger = Logger.getLogger(IOUtils.class);

    /**
     * 
     * @param key
     *            The key to be looked up
     * @param dirURI
     *            The MapFile directory
     * @param conf
     *            Job configuration
     * @return The values corresponding to the key, if found; Null otherwise
     */
    /*
     * For this method to be truly generic, caller should pass in the key and
     * value classes
     */
    public static Writable findInMapFile(final Text key, URI dirURI,
            final Configuration conf) {
        MapFile.Reader reader = null;
        Writable value = null;
        logger.debug("MapFile URI: " + dirURI);

        try {
            final Path inputPath = new Path(dirURI);
            final FileSystem fs = inputPath.getFileSystem(conf);
            reader = new MapFile.Reader(fs, dirURI.toString(), conf);

            value = reader.get(key, new Text());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            closeStreams(reader);
        }

        logger.debug("Returning value: " + value + ", for key: " + key);

        return value;

    }

    /**
     * 
     * @param inputURI
     *            The file from which MapFile is created
     * @param conf
     *            Job configuration
     * @return MapFile URI
     * @throws Exception
     *             If fails to create the MapFile
     */
    @SuppressWarnings("resource")
    public static URI createMapFile(URI inputURI, final Configuration conf)
            throws Exception {

        logger.debug("Attempting to create MapFile from input URI: " + inputURI);

        inputURI = uncompressFile(inputURI, conf);

        final Path inputPath = new Path(inputURI);
        final FileSystem fs = inputPath.getFileSystem(conf);

        final InputStream data = new FileInputStream(inputURI.getPath());
        final BufferedReader reader = new BufferedReader(new InputStreamReader(
                data));

        String line = null;
        final URI mapFileURI = new Path(inputPath.getParent(), "map_file")
                .toUri();

        logger.debug("MapFile URI: " + mapFileURI);

        /*
         * For this method to be truly generic, caller should pass in the key
         * and value classes
         */
        final MapFile.Writer writer = new MapFile.Writer(conf, fs,
                mapFileURI.toString(), Text.class, Text.class);

        String[] tokens = null;
        String key = null;
        String value = null;

        while ((line = reader.readLine()) != null) {
            tokens = line.split("\\s", 2);

            /*
             * For this method to be truly generic, caller should pass in a
             * token parser
             */
            if (tokens == null || tokens.length < 2) {
                logger.error("Don't know how to parse line: " + line);
                throw new RuntimeException("Don't know how to parse line: "
                        + line);
            }

            key = tokens[0];
            value = tokens[1];

            writer.append(new Text(key), new Text(value));
        }

        closeStreams(writer, reader, data);

        return mapFileURI;
    }

    /**
     * 
     * @param uncompressedURI
     *            The file to be archived
     * @param codecName
     *            The codec to be used for archiving
     * @param conf
     *            Job configuration
     * @return The archive URI
     * @throws Exception
     *             If fails to create the archive
     */
    public static URI compressFile(final URI uncompressedURI,
            final String codecName, final Configuration conf) throws Exception {
        /*
         * Hadoop 1.1.2 does not have a CompressionCodecFactory#getCodecByName
         * method. Instantiating GzipCodec by using new operator throws NPE
         * (probably Hadoop bug).
         */
        final CompressionCodec codec = getCodecByClassName(
                GzipCodec.class.getName(), conf);
        final Path uncompressedPath = new Path(uncompressedURI);

        String archiveName = addExtension(uncompressedPath.getName(),
                codec.getDefaultExtension(), true);

        final Path archivePath = new Path(uncompressedPath.getParent(),
                archiveName);

        logger.debug("uncompressedURI: " + uncompressedURI);
        logger.debug("archiveURI: " + archivePath.toString());

        final OutputStream outputStream = new FileOutputStream(archivePath
                .toUri().getPath());
        final InputStream inputStream = new FileInputStream(
                uncompressedURI.getPath());
        final CompressionOutputStream out = codec
                .createOutputStream(outputStream);
        org.apache.hadoop.io.IOUtils.copyBytes(inputStream, out, conf, false);
        out.finish();

        closeStreams(inputStream, outputStream, out);

        return archivePath.toUri();
    }

    /* Finds a codec by given class name */
    private static CompressionCodec getCodecByClassName(final String className,
            final Configuration conf) throws InstantiationException,
            IllegalAccessException {
        CompressionCodec codec = null;
        List<Class<? extends CompressionCodec>> allCodecClasses = CompressionCodecFactory
                .getCodecClasses(conf);
        for (Class<? extends CompressionCodec> clazz : allCodecClasses) {
            if (clazz.getName().equals(className)) {
                codec = ReflectionUtils.newInstance(clazz, conf);
                break;
            }
        }
        return codec;
    }

    /**
     * 
     * @param filename
     *            The filename which extension is to be changed
     * @param newExtension
     *            The new extension
     * @param removeExistingExtension
     *            Keep or replace existing extension?
     * @return Filename after appending new extension
     */
    public static String addExtension(String filename,
            final String newExtension, final boolean removeExistingExtension) {
        if (filename != null && filename.length() <= 1) {
            return filename;
        }

        logger.debug("Received: " + filename);

        if (removeExistingExtension) {
            filename = removeExtension(filename);
        }

        logger.debug("Returning: " + filename + newExtension);
        return filename + newExtension;
    }

    public static String removeExtension(String filename) {
        if (filename != null && filename.length() <= 1) {
            return filename;
        }

        logger.debug("Received: " + filename);

        final int idx = filename.lastIndexOf('.');

        if (idx >= 0) {
            filename = filename.substring(0, idx);
        }

        logger.debug("Returning: " + filename);
        return filename;
    }

    /**
     * 
     * @param archiveURI
     *            The archive to be extracted
     * @param conf
     *            Job configuration
     * @return Extracted file URI
     * @throws IOException
     *             If fails to extract the archive
     */
    public static URI uncompressFile(final URI archiveURI,
            final Configuration conf) throws IOException {
        Path archivePath = new Path(archiveURI);

        final FileSystem fs = FileSystem.getLocal(conf);

        FileStatus[] statuses = new FileStatus[] { fs
                .getFileStatus(archivePath) };
        if (statuses[0].isDir()) {
            statuses = fs.listStatus(archivePath);

            logger.debug("Archive is a directory and contains "
                    + statuses.length + " element(s)");

            archivePath = statuses[0].getPath();
        }

        logger.debug("archiveURI: " + archivePath.toUri());

        final CompressionCodec codec = new CompressionCodecFactory(conf)
                .getCodec(archivePath);
        if (codec == null) {
            logger.debug("Not an archive: " + archivePath.toUri());
            return archivePath.toUri();
        }

        logger.debug("Using codec: " + codec.getClass().getName());
        final Path uncompressionPath = new Path(addExtension(archivePath
                .toUri().getPath(), ".new", true));

        logger.debug("uncompressedURI: " + uncompressionPath.toUri());

        final OutputStream outputStream = new FileOutputStream(
                uncompressionPath.toUri().getPath());

        final InputStream inputStream = new FileInputStream(archivePath.toUri()
                .getPath());
        final CompressionInputStream in = codec.createInputStream(inputStream);
        org.apache.hadoop.io.IOUtils.copyBytes(in, outputStream, conf, false);

        closeStreams(inputStream, outputStream);

        return uncompressionPath.toUri();
    }

    /* Closes multiple streams at once */
    private static void closeStreams(final Closeable... streams) {
        for (Closeable stream : streams) {
            org.apache.hadoop.io.IOUtils.closeStream(stream);
        }
    }
}
