package org.abhijitsarkar;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Abhijit Sarkar
 */
@Slf4j
class HttpHeadTaskTest {
    private PoolingHttpClientConnectionManager connMgr;
    private final RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(10000)
            .setConnectTimeout(2000)
            .build();

    @BeforeEach
    public void before() {
        connMgr = new PoolingHttpClientConnectionManager();
    }

    @AfterEach
    public void after() {
        connMgr.close();
    }

    @Test
    @DisplayName("Keep alive doesn't prevent a connection to be used for a different route")
    public void test1() throws IOException, InterruptedException {
        connMgr.setDefaultMaxPerRoute(5);
        connMgr.setMaxTotal(5);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connMgr)
                .setDefaultRequestConfig(requestConfig)
                .setKeepAliveStrategy((response, context) -> 10l)
                .build();

        StepVerifier.create(Flux.range(1, 5)
                .parallel(10)
                .runOn(Schedulers.parallel())
                .map(i -> "https://www.google.com")
                .flatMap(uri -> Mono.fromCallable(new HttpHeadTask(httpClient, uri))))
//                .recordWith(ArrayList::new)
                // Fails with Expected size:<5> but was:<1>
                // https://github.com/reactor/reactor-core/issues/598
//                .consumeRecordedWith(results -> assertThat(results).hasSize(5))
                .consumeNextWith(verifier)
                .consumeNextWith(verifier)
                .consumeNextWith(verifier)
                .consumeNextWith(verifier)
                .consumeNextWith(verifier)
                .verifyComplete();

        HttpHeadTask headFromYahooTask = new HttpHeadTask(httpClient, "https://www.yahoo.com");

        assertThat(headFromYahooTask.call()).isEqualTo("www.yahoo.com");

        httpClient.close();
    }

    private final Consumer<String> verifier = uri -> assertThat(uri).isEqualTo("www.google.com");
}