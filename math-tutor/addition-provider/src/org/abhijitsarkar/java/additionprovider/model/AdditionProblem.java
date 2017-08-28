package org.abhijitsarkar.java.additionprovider.model;

import org.abhijitsarkar.java.mathtutor.model.Problem;

/**
 * @author Abhijit Sarkar
 */
public class AdditionProblem extends Problem {
    public AdditionProblem(int leftOperand, int rightOperand) {
        super(leftOperand, rightOperand, "+");
    }

    @Override
    public int getResult() {
        return getLeftOperand() + getRightOperand();
    }
}
