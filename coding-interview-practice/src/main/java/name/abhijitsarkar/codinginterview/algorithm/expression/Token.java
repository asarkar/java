package name.abhijitsarkar.codinginterview.algorithm.expression;

public abstract class Token {
	private final Character symbol;

	public Token(final Character symbol) {
		this.symbol = symbol;
	}

	public Character getSymbol() {
		return symbol;
	}

	public abstract void visit(TokenHandler handler);

	@Override
	public String toString() {
		final String className = this.getClass().getSimpleName();

		return className + "[symbol=" + symbol + "]";
	}
}
