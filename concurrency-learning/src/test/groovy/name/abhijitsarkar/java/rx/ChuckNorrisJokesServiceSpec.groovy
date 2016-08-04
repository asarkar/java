package name.abhijitsarkar.java.rx

import name.abhijitsarkar.java.domain.Jokes
import name.abhijitsarkar.java.domain.RetryDelayStrategy
import name.abhijitsarkar.java.domain.RetryWithDelay
import name.abhijitsarkar.java.repository.ChuckNorrisJokesClient
import name.abhijitsarkar.java.repository.ChuckNorrisJokesStubClient
import spock.lang.Specification

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
/**
 * @author Abhijit Sarkar
 */
class ChuckNorrisJokesServiceSpec extends Specification {
    Map<String, List<String>> threads

    def setup() {
        threads = Mock(Map)
    }

    def "succeeds on 1st attempt"() {
        setup:
        CountDownLatch latch = new CountDownLatch(2)
        RetryWithDelay retryWithDelay = RetryWithDelay.builder()
                .retryDelayStrategy(RetryDelayStrategy.RETRY_COUNT)
                .build()
        ChuckNorrisJokesClient client = new ChuckNorrisJokesStubClient()
        ChuckNorrisJokesService service = ChuckNorrisJokesService.builder()
                .client(client)
                .latch(latch)
                .threads(threads)
                .retryWithDelay(retryWithDelay)
                .build()

        when:
        service.setRandomJokes(3)
        latch.await(2, TimeUnit.SECONDS)

        Jokes jokes = service.jokes.get()

        then:
        jokes.status == 'success'
        jokes.count() == 3

        1 * threads.merge('getRandomJokes', *_)
        1 * threads.merge('fromCallable', *_)
        0 * threads.merge('retryWhen', *_)
        1 * threads.merge('onNext', *_)
        0 * threads.merge('onError', *_)
    }

    def "attempts to retry 3 times but succeeds on 2nd hence doesn't retry 3rd time"() {
        setup:
        ChuckNorrisJokesStubClient client = Spy(ChuckNorrisJokesStubClient)
        client.getRandomJokes(_) >>> {
            println("Exception 1")
            throw new RuntimeException("Test")
        } >> {
            println("Exception 2")
            throw new RuntimeException("Test")
        } >> {
            println("Real thing")
            callRealMethod()
        }

        CountDownLatch latch = new CountDownLatch(4)
        RetryWithDelay retryWithDelay = RetryWithDelay.builder()
                .retryDelayStrategy(RetryDelayStrategy.RETRY_COUNT)
                .maxRetries(3)
                .build()
        ChuckNorrisJokesService service = ChuckNorrisJokesService.builder()
                .client(client)
                .latch(latch)
                .threads(threads)
                .retryWithDelay(retryWithDelay)
                .build()

        when:
        service.setRandomJokes(3)
        latch.await(5, TimeUnit.SECONDS)

        Jokes jokes = service.jokes.get()

        then:
        jokes.status == 'success'
        jokes.count() == 3

        1 * threads.merge('getRandomJokes', *_)
        3 * threads.merge('fromCallable', *_)
        1 * threads.merge('onNext', *_)
        0 * threads.merge('onError', *_)
    }

    def "succeeds on 3rd retry"() {
        setup:
        ChuckNorrisJokesStubClient client = Spy(ChuckNorrisJokesStubClient)
        client.getRandomJokes(_) >>> {
            println("Exception 1")
            throw new RuntimeException("Test")
        } >> {
            println("Exception 2")
            throw new RuntimeException("Test")
        } >> {
            println("Exception 3")
            throw new RuntimeException("Test")
        } >> {
            println("Real thing")
            callRealMethod()
        }

        Map<String, List<String>> threads = Mock(Map)
        CountDownLatch latch = new CountDownLatch(5)
        RetryWithDelay retryWithDelay = RetryWithDelay.builder()
                .retryDelayStrategy(RetryDelayStrategy.RETRY_COUNT)
                .maxRetries(3)
                .build()
        ChuckNorrisJokesService service = ChuckNorrisJokesService.builder()
                .client(client)
                .latch(latch)
                .threads(threads)
                .retryWithDelay(retryWithDelay)
                .build()

        when:
        service.setRandomJokes(3)
        latch.await(10, TimeUnit.SECONDS)

        Jokes jokes = service.jokes.get()

        then:
        jokes.status == 'success'
        jokes.count() == 3

        1 * threads.merge('getRandomJokes', *_)
        4 * threads.merge('fromCallable', *_)
        1 * threads.merge('onNext', *_)
        0 * threads.merge('onError', *_)
    }

    def "fails after 1 retry"() {
        setup:
        ChuckNorrisJokesStubClient client = Spy(ChuckNorrisJokesStubClient)
        client.getRandomJokes(_) >>> {
            println("Exception 1")
            throw new RuntimeException("Test")
        } >> {
            println("Exception 2")
            throw new RuntimeException("Test")
        }

        Map<String, List<String>> threads = Mock(Map)
        CountDownLatch latch = new CountDownLatch(3)
        RetryWithDelay retryWithDelay = RetryWithDelay.builder()
                .retryDelayStrategy(RetryDelayStrategy.RETRY_COUNT)
                .maxRetries(1)
                .build()
        ChuckNorrisJokesService service = ChuckNorrisJokesService.builder()
                .client(client)
                .latch(latch)
                .threads(threads)
                .retryWithDelay(retryWithDelay)
                .build()

        when:
        service.setRandomJokes(3)
        latch.await(2, TimeUnit.SECONDS)

        Jokes jokes = service.jokes.get()

        then:
        jokes.status == 'unknown'
        jokes.count() == 0

        1 * threads.merge('getRandomJokes', *_)
        2 * threads.merge('fromCallable', *_)
        0 * threads.merge('onNext', *_)
        1 * threads.merge('onError', *_)
    }
}
