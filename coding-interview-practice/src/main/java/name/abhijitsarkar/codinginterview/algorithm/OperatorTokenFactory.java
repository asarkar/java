package name.abhijitsarkar.codinginterview.algorithm;

import static name.abhijitsarkar.codinginterview.algorithm.ArithmeticOperatorToken.DIVISION;
import static name.abhijitsarkar.codinginterview.algorithm.ArithmeticOperatorToken.MINUS;
import static name.abhijitsarkar.codinginterview.algorithm.ArithmeticOperatorToken.MULTIPLICATION;
import static name.abhijitsarkar.codinginterview.algorithm.ArithmeticOperatorToken.PLUS;
import static name.abhijitsarkar.codinginterview.algorithm.ArithmeticOperatorToken.REMAINDER;
import static name.abhijitsarkar.codinginterview.algorithm.OperatorToken.Associativity.LEFT;
import static name.abhijitsarkar.codinginterview.algorithm.OperatorToken.Associativity.NOT_APPLICABLE;

public class OperatorTokenFactory {

	public static ArithmeticOperatorToken getArithmeticOperatorTokenInstance(Character symbol) {
		if (symbol.equals(PLUS) || symbol.equals(MINUS)) {
			return new ArithmeticOperatorToken(symbol, LEFT, 10);
		} else if (symbol.equals(MULTIPLICATION) || symbol.equals(DIVISION) || symbol.equals(REMAINDER)) {
			return new ArithmeticOperatorToken(symbol, LEFT, 11);
		}

		throw new IllegalArgumentException("Unknown arithmetic operator: " + symbol);
	}

	public static ParenthesisToken getParenthesisTokenInstance(Character symbol) {
		return new ParenthesisToken(symbol, NOT_APPLICABLE, Integer.MIN_VALUE);
	}
}
