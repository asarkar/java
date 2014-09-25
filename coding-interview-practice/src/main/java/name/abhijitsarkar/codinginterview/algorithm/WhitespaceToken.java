package name.abhijitsarkar.codinginterview.algorithm;

public class WhitespaceToken extends Token {

	public WhitespaceToken(Character symbol) {
		super(symbol);
	}

	@Override
	public void visit(TokenHandler handler) {
		handler.handleToken(this);
	}
}
