package name.abhijitsarkar.java.java8impatient.miscellaneous;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class PracticeQuestionsCh8BenchmarkRunner {
    /*
     * Sample cmd line invocation : java -jar target/benchmarks.jar -wi 5 -i 5
     * -f 1 (we requested 5 warmup/measurement iterations, single fork)
     */
    public static void main(String[] args) throws RunnerException {
	final Options opts = new OptionsBuilder()
		.include(PracticeQuestionsCh8Benchmark.class.getSimpleName())
		.warmupIterations(5).measurementIterations(5).forks(1).build();

	new Runner(opts).run();
    }
}
