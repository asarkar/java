package name.abhijitsarkar.codinginterview.algorithm;

import static name.abhijitsarkar.codinginterview.algorithm.OperatorToken.Associativity.LEFT;
import static name.abhijitsarkar.codinginterview.algorithm.ParenthesisToken.LEFT_PARENTHESIS;
import static name.abhijitsarkar.codinginterview.algorithm.ParenthesisToken.RIGHT_PARENTHESIS;

import java.util.ArrayDeque;
import java.util.Deque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* http://en.wikipedia.org/wiki/Shunting-yard_algorithm */
public class ShuntingYard implements TokenHandler {
	public static final Logger LOGGER = LoggerFactory.getLogger(ShuntingYard.class);

	private final StringBuilder output = new StringBuilder();
	private final Deque<OperatorToken> operatorStack = new ArrayDeque<OperatorToken>();

	public String getOutput() {
		while (!operatorStack.isEmpty()) {
			appendToOutput(operatorStack.pop(), output);
		}

		final String finalOutput = output.toString().trim();

		LOGGER.info("Final output: {}", finalOutput);

		return finalOutput;
	}

	public void handleToken(final WhitespaceToken w) {
		return;
	}

	public void handleToken(final LetterOrDigitToken l) {
		appendToOutput(l, output);
	}

	public void handleToken(final ParenthesisToken p) {
		if (p.getSymbol().equals(RIGHT_PARENTHESIS)) {
			/* Note that the LEFT_PARENTHESIS is popped but discarded */
			for (OperatorToken op = operatorStack.pop(); !op.getSymbol().equals(LEFT_PARENTHESIS); op = operatorStack
					.pop()) {
				appendToOutput(op, output);
			}
		} else {
			operatorStack.push(p);
		}
	}

	public void handleToken(final OperatorToken op1) {
		if (!operatorStack.isEmpty()) {
			final OperatorToken op2 = operatorStack.peek();

			/*
			 * If op2 has higher priority or if op1 is of equal priority and left associative, pop op2 and append to
			 * output
			 */
			if (op2.isOfHigherPrecendence(op1) || (!op1.isOfHigherPrecendence(op2) && op1.getAssociativity() == LEFT)) {
				appendToOutput(operatorStack.pop(), output);
			}
		}

		operatorStack.push(op1);
	}

	private void appendToOutput(Token token, StringBuilder output) {
		output.append(" ").append(token.getSymbol());

		LOGGER.debug("Output: {}", output.toString());
	}
}
