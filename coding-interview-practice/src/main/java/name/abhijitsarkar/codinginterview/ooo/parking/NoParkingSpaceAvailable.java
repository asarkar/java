package name.abhijitsarkar.codinginterview.ooo.parking;

public class NoParkingSpaceAvailable extends RuntimeException {

	private static final long serialVersionUID = -4996451440663296342L;

	public NoParkingSpaceAvailable() {
		super();
	}

	public NoParkingSpaceAvailable(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoParkingSpaceAvailable(String message, Throwable cause) {
		super(message, cause);
	}

	public NoParkingSpaceAvailable(String message) {
		super(message);
	}

	public NoParkingSpaceAvailable(Throwable cause) {
		super(cause);
	}
}
