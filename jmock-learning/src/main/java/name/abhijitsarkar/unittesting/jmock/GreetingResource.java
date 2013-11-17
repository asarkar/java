package name.abhijitsarkar.unittesting.jmock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

@Path("/hello")
public class GreetingResource {
	private static final String TARGET = "/pages/hello.jsp";
	private final RESTUtil restUtil;

	public GreetingResource() {
		this(new RESTUtil());
	}

	public GreetingResource(RESTUtil restUtil) {
		this.restUtil = restUtil;
	}

	@POST
	public void sayHello(@FormParam("name") String name,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		restUtil.forward(request, response, TARGET, "name", name);
	}
}
