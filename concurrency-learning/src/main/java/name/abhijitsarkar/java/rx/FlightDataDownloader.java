package name.abhijitsarkar.java.rx;

import lombok.val;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Instant;

import static java.time.temporal.ChronoUnit.SECONDS;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

/**
 * @author Abhijit Sarkar
 */
public class FlightDataDownloader {
    private static final int MEGABYTES = 1024 * 1024;

    public static void main(String... args) throws IOException, URISyntaxException {
        if (args.length < 2) {
            return;
        }

        FlightDataDownloader flightDataDownloader = new FlightDataDownloader();
        flightDataDownloader.extract(flightDataDownloader.download(new URL(args[0]), new File(args[1])));
    }

    public File download(URL url, File out) throws URISyntaxException, IOException {
        if (!out.exists()) {
            out.mkdirs();
        } else if (out.isFile() || !out.canRead() || !out.canExecute()) {
            System.err.println(String.format("%s is not a directory or no rx permissions.", out.getAbsolutePath()));
        }

        Client client = ClientBuilder.newBuilder().build();
        WebTarget webTarget = client.target(url.toURI());

        String file = url.getFile();
        File outfile = new File(out, file.substring(file.lastIndexOf('/') + 1));
        outfile.delete();

        System.out.println("Beginning download...");

        Instant start = Instant.now();

        InputStream is = webTarget.request()
                .accept(APPLICATION_JSON_TYPE)
                .<InputStream>get(InputStream.class);

        FileOutputStream fos = new FileOutputStream(outfile);

        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = is.read(buffer)) != -1) {
            fos.write(buffer, 0, bytesRead);
        }

        val timeTaken = SECONDS.between(start, Instant.now());

        fos.close();
        is.close();
        client.close();

        System.out.println(String.format("Successfully downloaded: %s to: %s.", url, outfile.getAbsolutePath()));
        System.out.println(String.format("File size: %d MB.", outfile.length() / MEGABYTES));
        System.out.println(String.format("Time taken: %d seconds.", timeTaken));

        return outfile;
    }

    public String extract(File in) throws IOException {
        if (!in.isFile() || !in.canRead()) {
            System.err.println(String.format("%s is not a file or no read permission.", in.getAbsolutePath()));
        }

        String parentDir = in.getParent();
        String filename = in.getName();
        String extractedFilename = filename.substring(0, filename.lastIndexOf('.'));

        FileInputStream fin = new FileInputStream(in);
        BufferedInputStream bin = new BufferedInputStream(fin);
        File outfile = new File(parentDir, extractedFilename);
        FileOutputStream out = new FileOutputStream(outfile);
        BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(bin);

        byte[] buffer = new byte[4096];

        System.out.println("Beginning extraction...");

        Instant start = Instant.now();

        int bytesRead;
        while ((bytesRead = bzIn.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }

        out.close();
        bzIn.close();

        in.delete();

        val timeTaken = SECONDS.between(start, Instant.now());

        System.out.println(String.format("Successfully extracted: %s to: %s.",
                in.getAbsolutePath(), outfile.getAbsolutePath()));
        System.out.println(String.format("File size: %d MB.", outfile.length() / MEGABYTES));
        System.out.println(String.format("Time taken: %d seconds.", timeTaken));

        return outfile.getAbsolutePath();
    }
}
