package name.abhijitsarkar.learning.webservices.security.exception;

public class NoSuchKeyException extends RuntimeException {

	private static final long serialVersionUID = 203938991134252035L;

	public NoSuchKeyException() {
		super();
	}

	public NoSuchKeyException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchKeyException(String message) {
		super(message);
	}

	public NoSuchKeyException(Throwable cause) {
		super(cause);
	}
}
