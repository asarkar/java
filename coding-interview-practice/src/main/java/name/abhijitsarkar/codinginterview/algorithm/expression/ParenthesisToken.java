package name.abhijitsarkar.codinginterview.algorithm.expression;

public class ParenthesisToken extends OperatorToken {
	public static final Character LEFT_PARENTHESIS = Character.valueOf('(');
	public static final Character RIGHT_PARENTHESIS = Character.valueOf(')');

	public ParenthesisToken(Character symbol, Associativity associativity, int precedence) {
		super(symbol, associativity, precedence);
	}

	public static boolean isParenthesis(Character symbol) {
		return symbol.equals(LEFT_PARENTHESIS) || symbol.equals(RIGHT_PARENTHESIS);
	}
	
	@Override
	public void visit(TokenHandler handler) {
		handler.handleToken(this);
	}
}
