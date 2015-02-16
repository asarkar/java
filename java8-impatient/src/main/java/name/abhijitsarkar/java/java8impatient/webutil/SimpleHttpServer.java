package name.abhijitsarkar.java.java8impatient.webutil;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class SimpleHttpServer {
    public static final int PORT = 8000;
    public static final String USERNAME = "ch8";
    public static final String PASSWORD = "q11";

    public static HttpServer newInstance() {
	/*
	 * The 2nd arg to 'create' is the backlog, meaning the maximum number of
	 * queued incoming connections to allow on the listening socket. 0 lets
	 * the system pick a value for us.
	 */
	try {
	    final HttpServer server = HttpServer.create(new InetSocketAddress(
		    PORT), 0);

	    server.createContext("/info", new InfoHandler());

	    /* Protected URL. */
	    server.createContext("/get", new GetHandler()).setAuthenticator(
		    new BasicAuthenticator("get") {
			@Override
			public boolean checkCredentials(final String user,
				final String pwd) {
			    return user.equals(USERNAME)
				    && pwd.equals(PASSWORD);
			}
		    });

	    /* Create a default executor. */
	    server.setExecutor(null);

	    return server;
	} catch (IOException e) {
	    throw new UncheckedIOException(e);
	}
    }

    /* http://localhost:<PORT>/info */
    static class InfoHandler implements HttpHandler {
	public void handle(final HttpExchange httpExchange) throws IOException {
	    final String response = "Use /get to authenticate";
	    writeResponse(httpExchange, response);
	}
    }

    /* http://localhost:<PORT>/get */
    static class GetHandler implements HttpHandler {
	public void handle(final HttpExchange httpExchange) throws IOException {
	    writeResponse(httpExchange, "Hello "
		    + httpExchange.getPrincipal().getUsername());
	}
    }

    private static void writeResponse(final HttpExchange httpExchange,
	    final String response) throws IOException {
	httpExchange.sendResponseHeaders(200, response.length());
	httpExchange.getResponseHeaders().add("Content-Type", "text/plain");

	try (final OutputStream os = httpExchange.getResponseBody()) {
	    os.write(response.getBytes(UTF_8));
	}
    }
}