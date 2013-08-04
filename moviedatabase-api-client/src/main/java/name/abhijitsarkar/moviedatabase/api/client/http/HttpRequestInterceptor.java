/* Copyright (c) 2013, the original author or authors.

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; version 3 of the License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, see http://www.gnu.org/licenses. */

package name.abhijitsarkar.moviedatabase.api.client.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * Intercepts the request and response for logging purpose. Cleaner design than
 * logging in the client code.
 * 
 * @author Abhijit Sarkar
 * 
 */
public class HttpRequestInterceptor implements ClientHttpRequestInterceptor {
	private static final Logger logger = Logger
			.getLogger(HttpRequestInterceptor.class);

	public ClientHttpResponse intercept(HttpRequest request, byte[] body,
			ClientHttpRequestExecution execution) throws IOException {
		logger.debug("Request headers:");
		logHeaders(request.getHeaders());

		logger.debug("Request URI: " + request.getURI());
		logger.debug("Request method: " + request.getMethod());

		ClientHttpResponse response = execution.execute(request, body);

		logger.debug("Response headers:");
		logHeaders(response.getHeaders());

		BufferedReader br = new BufferedReader(new InputStreamReader(
				response.getBody()));
		String line = null;
		StringBuilder buffer = new StringBuilder();

		while ((line = br.readLine()) != null)
			buffer.append(line);

		logger.debug("Server response: " + buffer);

		return response;
	}

	private void logHeaders(HttpHeaders headers) {
		Set<Map.Entry<String, List<String>>> entries = headers.entrySet();
		for (Map.Entry<String, List<String>> entry : entries) {
			logger.debug("[Key: " + entry.getKey() + ", Value: "
					+ entry.getValue() + "]");
		}
	}
}
