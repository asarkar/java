package org.abhijitsarkar.java.mathtutor.model;

/**
 * @author Abhijit Sarkar
 */
public abstract class Problem {
    private final int leftOperand;
    private final int rightOperand;
    private final String operation;

    protected Problem(int leftOperand, int rightOperand, String operation) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.operation = operation;
    }

    public abstract int getResult();

    protected int getLeftOperand() {
        return leftOperand;
    }

    protected int getRightOperand() {
        return rightOperand;
    }

    @Override
    public String toString() {
        return String.format("What is %d %s %d?", leftOperand, operation, rightOperand);
    }
}