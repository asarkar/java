package name.abhijitsarkar.codinginterview.algorithm.expression;

public abstract class OperatorToken extends Token {
	private final Associativity associativity;
	private final int precedence;

	public OperatorToken(final Character symbol, final Associativity associativity, final int precedence) {
		super(symbol);
		this.associativity = associativity;
		this.precedence = precedence;
	}

	public Associativity getAssociativity() {
		return associativity;
	}

	public int getPrecedence() {
		return precedence;
	}

	public boolean isOfHigherPrecendence(final OperatorToken other) {
		return this.precedence > other.precedence;
	}

	public enum Associativity {
		LEFT, RIGHT, NOT_APPLICABLE;
	}

	@Override
	public String toString() {
		final String className = this.getClass().getSimpleName();

		return className + "[symbol=" + getSymbol() + ", associativity=" + associativity + ", precedence=" + precedence
				+ "]";
	}
}
