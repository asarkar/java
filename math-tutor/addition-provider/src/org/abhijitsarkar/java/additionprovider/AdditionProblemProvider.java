package org.abhijitsarkar.java.additionprovider;

import org.abhijitsarkar.java.additionprovider.model.AdditionProblem;
import org.abhijitsarkar.java.mathtutor.model.Problem;
import org.abhijitsarkar.java.mathtutor.spi.ProblemProvider;

import java.util.Random;

/**
 * @author Abhijit Sarkar
 */
public class AdditionProblemProvider implements ProblemProvider {
    private final Random random = new Random();

    @Override
    public Problem getProblem() {
        return new AdditionProblem(random.nextInt(10), random.nextInt(10));
    }
}
