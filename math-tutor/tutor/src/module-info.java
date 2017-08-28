/**
 * @author Abhijit Sarkar
 */
module org.abhijitsarkar.java.mathtutor {
    // can't use 'to' because we don't know which modules are going to implement ProblemProvider
    exports org.abhijitsarkar.java.mathtutor.spi;
    exports org.abhijitsarkar.java.mathtutor.model;

    // tells the JVM to look for implementations of ProblemProvider
    uses org.abhijitsarkar.java.mathtutor.spi.ProblemProvider;
}