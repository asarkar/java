package name.abhijitsarkar.unittesting.jmockit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import name.abhijitsarkar.unittesting.jmockit.MyFilter;

import org.junit.Assert;
import org.junit.Test;

public class MyFilterTest {
	HttpServletRequest mockRequest = new MockUp<HttpServletRequest>() {
		private List<String> hdrValues = new ArrayList<String>();

		@Mock
		public Enumeration<String> getHeaders(String name) {
			if ("X-Forwarded-For".equals(name)) {
				hdrValues.add("0.0.0.0");
				hdrValues.add("127.0.0.1");
			}

			return Collections.enumeration(hdrValues);
		}
	}.getMockInstance();

	@Test
	public void testDoSomething() {
		Map<String, String> actual = Deencapsulation.invoke(new MyFilter(),
				"doSomething", mockRequest);

		Assert.assertEquals("Wrong return value",
				actual.get("X-Forwarded-For"), "0.0.0.0;127.0.0.1");
	}

	@Test
	// This test shows the power of JMockit by testing invocations to
	// private methods but ideally it should be avoided
	public void testDoFilter(@Mocked final ServletResponse response,
			@Mocked final FilterChain chain) throws IOException,
			ServletException {
		// Uses dynamic partial mocking; since call to doSomething()
		// and chain.doFilter() are recorded, those method will be mocked. No
		// calls to doFilter() are recorded, so it's not mocked and actually
		// invokes doSomething() and chain.doFilter() during the test.

		new Expectations(MyFilter.class) {
			{
				invoke(new MyFilter(), "doSomething", mockRequest);
				chain.doFilter(mockRequest, response);
			}
		};

		new MyFilter().doFilter(mockRequest, response, chain);
	}
}
