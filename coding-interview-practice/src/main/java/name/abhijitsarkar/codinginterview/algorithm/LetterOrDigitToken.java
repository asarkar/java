package name.abhijitsarkar.codinginterview.algorithm;

public class LetterOrDigitToken extends Token {

	public LetterOrDigitToken(Character symbol) {
		super(symbol);
	}
	
	@Override
	public void visit(ShuntingYard handler) {
		handler.handleToken(this);
	}
}
