package name.abhijitsarkar.codinginterview.algorithm;

public class ArithmeticOperatorToken extends OperatorToken {

	public static final Character PLUS = Character.valueOf('+');
	public static final Character MINUS = Character.valueOf('-');
	public static final Character MULTIPLICATION = Character.valueOf('*');
	public static final Character DIVISION = Character.valueOf('/');
	public static final Character REMAINDER = Character.valueOf('%');

	public ArithmeticOperatorToken(Character symbol, Associativity associativity, int precedence) {
		super(symbol, associativity, precedence);
	}

	public static boolean isArithmeticOperator(Character symbol) {
		return symbol.equals(PLUS) || symbol.equals(MINUS) || symbol.equals(MULTIPLICATION) || symbol.equals(DIVISION)
				|| symbol.equals(REMAINDER);
	}
	
	@Override
	public void visit(TokenHandler handler) {
		handler.handleToken(this);
	}
}
