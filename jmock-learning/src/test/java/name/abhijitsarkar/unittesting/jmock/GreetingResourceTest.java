package name.abhijitsarkar.unittesting.jmock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import name.abhijitsarkar.unittesting.jmock.GreetingResource;
import name.abhijitsarkar.unittesting.jmock.RESTUtil;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class GreetingResourceTest {
	private static final String TARGET = "/pages/hello.jsp";

	private final Mockery context;
	private final RESTUtil restUtil;
	private final HttpServletRequest request;
	private final HttpServletResponse response;

	private final GreetingResource resource;

	public GreetingResourceTest() {
		context = new JUnit4Mockery() {
			{
				// Needed to mock concrete classes
				setImposteriser(ClassImposteriser.INSTANCE);
			}
		};

		restUtil = context.mock(RESTUtil.class);
		request = context.mock(HttpServletRequest.class);
		response = context.mock(HttpServletResponse.class);

		resource = new GreetingResource(restUtil);
	}

	@Test
	public void testSayHello() {
		final String name = "Abhijit";

		context.checking(new Expectations() {
			{
				oneOf(restUtil)
						.forward(request, response, TARGET, "name", name);
			}
		});

		resource.sayHello(name, request, response);
	}
}
