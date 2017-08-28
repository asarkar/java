package org.abhijitsarkar.java.mathtutor;

import org.abhijitsarkar.java.mathtutor.model.Problem;
import org.abhijitsarkar.java.mathtutor.spi.ProblemProvider;

import java.util.Optional;
import java.util.Scanner;
import java.util.ServiceLoader;

/**
 * @author Abhijit Sarkar
 */
public class MathTutor {
    private static final Scanner input = new Scanner(System.in);

    public static void main(String... args) {
        new MathTutor().play();
    }

    private void play(boolean... play) {
        Optional.of(play.length > 0 && play[0])
                .filter(Boolean::booleanValue)
                .flatMap(p -> this.newProblem())
                .ifPresentOrElse(p -> {
                    System.out.println(p.toString());

                    int answer = input.nextInt();

                    String result = answer == p.getResult() ? "Correct." : "Incorrect.";
                    System.out.println(result);

                    System.out.printf("Try another? 'y' to continue, 'n' to terminate.");
                    boolean keepPlaying = "y".equalsIgnoreCase(input.next());

                    play(keepPlaying);
                }, () -> System.out.println("No problem providers found."));
    }

    private Optional<Problem> newProblem() {
        return ServiceLoader.load(ProblemProvider.class).stream()
                .findAny()
                .map(ServiceLoader.Provider::get)
                .map(ProblemProvider::getProblem);
    }
}
