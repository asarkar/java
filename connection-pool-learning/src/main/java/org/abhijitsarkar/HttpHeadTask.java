package org.abhijitsarkar;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * @author Abhijit Sarkar
 */
@Slf4j
public class HttpHeadTask implements Callable<String> {
    private final CloseableHttpClient httpClient;
    private final HttpHead httpHead;
    private final HttpContext context;

    public HttpHeadTask(CloseableHttpClient httpClient, String uri) {
        this.httpClient = httpClient;
        httpHead = new HttpHead(uri);
        context = HttpClientContext.create();
    }

    @Override
    public String call() {
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpHead, context);
            response.getEntity();

            log.info("Successfully received response from: {}.", httpHead.getURI().getHost());

            return httpHead.getURI().getHost();
        } catch (IOException ex) {
            log.error("Something bad happened while getting from: {}.", ex, httpHead.getURI().getHost());

            return "";
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error("Failed to close response: {}.", e, httpHead.getURI().getHost());
                }
            }
        }
    }
}
