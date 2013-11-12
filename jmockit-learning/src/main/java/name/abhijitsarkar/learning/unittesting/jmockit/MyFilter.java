package name.abhijitsarkar.learning.unittesting.jmockit;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class MyFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		doSomething((HttpServletRequest) request);

		chain.doFilter(request, response);
	}

	private Map<String, String> doSomething(HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<String> hdrValues = Collections.list(request
				.getHeaders("X-Forwarded-For"));

		StringBuilder buffer = new StringBuilder();

		for (String aHeaderValue : hdrValues) {
			buffer.append(aHeaderValue).append(";");
		}

		buffer.deleteCharAt(buffer.length() - 1);

		Map<String, String> hdrMap = new HashMap<String, String>();
		hdrMap.put("X-Forwarded-For", buffer.toString());

		return hdrMap;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}
}
