package org.abhijitsarkar.java.mathtutor.spi;

import org.abhijitsarkar.java.mathtutor.model.Problem;

/**
 * @author Abhijit Sarkar
 */
public interface ProblemProvider {
    Problem getProblem();
}
