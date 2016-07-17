package name.abhijitsarkar.java.rxjava

import spock.lang.Specification

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * @author Abhijit Sarkar
 */
class ChuckNorrisJokesServiceSpec extends Specification {
    def "succeeds on 1st attempt"() {
        setup:
        CountDownLatch latch = new CountDownLatch(2)
        Map<String, List<String>> threads = Mock(Map)
        ChuckNorrisJokesService service = ChuckNorrisJokesService.builder()
                .latch(latch)
                .threads(threads)
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
        1 * threads.merge('onCompleted', *_)
    }

    def "attempts to retry 3 times but succeeds on 2nd hence doesn't retry 3rd time"() {
        setup:
        ChuckNorrisJokesRepository jokesRepository = Spy(ChuckNorrisJokesRepository)
        jokesRepository.getRandomJokes(_) >>> {
            println("Exception 1")
            throw new RuntimeException("Test")
        } >> {
            println("Exception 2")
            throw new RuntimeException("Test")
        } >> {
            println("Real thing")
            callRealMethod()
        }

        Map<String, List<String>> threads = Mock(Map)
        CountDownLatch latch = new CountDownLatch(4)
        ChuckNorrisJokesService service = ChuckNorrisJokesService.builder()
                .latch(latch)
                .numRetries(3)
                .jokesRepository(jokesRepository)
                .threads(threads)
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
        1 * threads.merge('retryWhen', *_)
        1 * threads.merge('onNext', *_)
        0 * threads.merge('onError', *_)
        1 * threads.merge('onCompleted', *_)
    }

    def "succeeds on 3rd retry"() {
        setup:
        ChuckNorrisJokesRepository jokesRepository = Spy(ChuckNorrisJokesRepository)
        jokesRepository.getRandomJokes(_) >>> {
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
        ChuckNorrisJokesService service = ChuckNorrisJokesService.builder()
                .latch(latch)
                .numRetries(3)
                .jokesRepository(jokesRepository)
                .threads(threads)
                .build()

        when:
        service.setRandomJokes(3)
        latch.await(5, TimeUnit.SECONDS)

        Jokes jokes = service.jokes.get()

        then:
        jokes.status == 'success'
        jokes.count() == 3

        1 * threads.merge('getRandomJokes', *_)
        4 * threads.merge('fromCallable', *_)
        1 * threads.merge('retryWhen', *_)
        1 * threads.merge('onNext', *_)
        0 * threads.merge('onError', *_)
        1 * threads.merge('onCompleted', *_)
    }

    def "fails after 1 retry"() {
        setup:
        ChuckNorrisJokesRepository jokesRepository = Spy(ChuckNorrisJokesRepository)
        jokesRepository.getRandomJokes(_) >>> {
            println("Exception 1")
            throw new RuntimeException("Test")
        } >> {
            println("Exception 2")
            throw new RuntimeException("Test")
        }

        Map<String, List<String>> threads = Mock(Map)
        CountDownLatch latch = new CountDownLatch(3)
        ChuckNorrisJokesService service = ChuckNorrisJokesService.builder()
                .latch(latch)
                .numRetries(1)
                .jokesRepository(jokesRepository)
                .threads(threads)
                .build()

        when:
        service.setRandomJokes(3)
        latch.await(2, TimeUnit.SECONDS)

        Jokes jokes = service.jokes.get()

        then:
        jokes.status == 'success'
        jokes.count() == 0

        1 * threads.merge('getRandomJokes', *_)
        2 * threads.merge('fromCallable', *_)
        1 * threads.merge('retryWhen', *_)
        0 * threads.merge('onNext', *_)
        0 * threads.merge('onError', *_)
        1 * threads.merge('onCompleted', *_)
    }
}
