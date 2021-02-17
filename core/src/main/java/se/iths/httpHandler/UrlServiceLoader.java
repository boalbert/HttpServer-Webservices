package se.iths.httpHandler;

import se.iths.routing.Route;
import se.iths.spi.IoHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class UrlServiceLoader {

	private final static Map<String, IoHandler> routes = getRoutes();

	/***
	 * Method that loads all plugins (IOHandler), gets their URL route from their annotation at runtime
	 * and saves the information (route, name of IOHandler) in a hashmap
 	 * @return hashmap
	 */
	private static Map<String, IoHandler> getRoutes() {

		Map<String, IoHandler> routes = new HashMap<>();

		ServiceLoader<IoHandler> loader = ServiceLoader.load(IoHandler.class);

		for (var handler : loader) {
			routes.put(handler.getClass().getAnnotation(Route.class).url(), handler);
		}

		return routes;
	}

	/***
	 * Gets the designated IOHandler from the hashmap based on the URL from the Http request;
	 * If URL doesnt match a IOHandler, we call the IOHandler for files as default.
	 * @param requestPath from the HttpRequest object
	 * @return designated handler for a specific request
	 */
	public static IoHandler findWhatImplementationToUse(String requestPath) {

		if (requestPath.contains("/contacts")) {
			return routes.get("/contacts");

		} else if (requestPath.contains("/insertcontactviaget")) {
			return routes.get("/insertcontactviaget");

		} else if (requestPath.contains("/postcontact")) {
			return routes.get("/postcontact");

		} else {
			return routes.get("/file");

		}
	}
}
