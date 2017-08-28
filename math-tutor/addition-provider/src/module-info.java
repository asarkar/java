module org.abhijitsarkar.java.additionprovider {
    requires org.abhijitsarkar.java.mathtutor;

    provides org.abhijitsarkar.java.mathtutor.spi.ProblemProvider
            with org.abhijitsarkar.java.additionprovider.AdditionProblemProvider;
}