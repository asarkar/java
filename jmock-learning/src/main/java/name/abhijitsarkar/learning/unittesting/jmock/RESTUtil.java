package name.abhijitsarkar.learning.unittesting.jmock;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RESTUtil {

	public void forward(HttpServletRequest request,
			HttpServletResponse response, String target, String entityKey,
			Object entity) {
		request.setAttribute(entityKey, entity);

		RequestDispatcher rd = request.getRequestDispatcher(target);

		try {
			rd.forward(request, response);
		} catch (Exception e) {
			throw new RuntimeException(
					"Something went wrong trying to forward the request to "
							+ target);
		}
	}
}
