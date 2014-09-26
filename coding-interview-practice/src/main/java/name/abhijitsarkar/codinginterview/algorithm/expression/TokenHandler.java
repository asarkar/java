package name.abhijitsarkar.codinginterview.algorithm.expression;


public interface TokenHandler {
	
	public String getOutput();

	public void handleToken(final WhitespaceToken w);

	public void handleToken(final LetterOrDigitToken l);

	public void handleToken(final ParenthesisToken p);

	public void handleToken(final OperatorToken op);
}
