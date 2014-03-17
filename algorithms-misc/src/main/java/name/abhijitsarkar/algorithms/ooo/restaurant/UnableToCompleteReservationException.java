package name.abhijitsarkar.algorithms.ooo.restaurant;

public class UnableToCompleteReservationException extends RuntimeException {

	private static final long serialVersionUID = -6401437036919802147L;

	public UnableToCompleteReservationException() {
		super();
	}

	public UnableToCompleteReservationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnableToCompleteReservationException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnableToCompleteReservationException(String message) {
		super(message);
	}

	public UnableToCompleteReservationException(Throwable cause) {
		super(cause);
	}

}
