package name.abhijitsarkar.learning.unittesting.jmock;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class RESTUtilTest {
	private static final String TARGET = "/pages/hello.jsp";

	private final Mockery context;
	private final RequestDispatcher rd;
	private final RESTUtil restUtil;
	private final HttpServletRequest request;
	private final HttpServletResponse response;

	public RESTUtilTest() {

		context = new JUnit4Mockery();

		rd = context.mock(RequestDispatcher.class);
		context.setDefaultResultForType(RequestDispatcher.class, rd);

		request = context.mock(HttpServletRequest.class);
		response = context.mock(HttpServletResponse.class);

		restUtil = new RESTUtil();
	}

	@Test
	public void testForward() throws Exception {
		final String name = "Abhijit";

		final Sequence fwd = context.sequence("fwd");

		context.checking(new Expectations() {
			{
				oneOf(request).setAttribute("name", name);
				inSequence(fwd);

				oneOf(request).getRequestDispatcher(TARGET);
				will(returnValue(rd));
				inSequence(fwd);

				oneOf(rd).forward(request, response);
				inSequence(fwd);
			}
		});

		restUtil.forward(request, response, TARGET, "name", name);
	}
}
