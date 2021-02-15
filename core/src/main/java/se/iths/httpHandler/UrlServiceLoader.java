package se.iths.httpHandler;

import se.iths.routing.Route;
import se.iths.spi.IoHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class UrlServiceLoader {

	private final static Map<String, IoHandler> routes = getRoutes();

	private static Map<String, IoHandler> getRoutes() {

		Map<String, IoHandler> routes = new HashMap<>();

		ServiceLoader<IoHandler> loader = ServiceLoader.load(IoHandler.class);

		for (var handler : loader) {
			routes.put(handler.getClass().getAnnotation(Route.class).url(), handler);
		}

		return routes;
	}

	public static IoHandler findWhatImplementationToUse(String requestPath) {

		if (requestPath.contains(".")) {
			return routes.get("/file");

		} else if (requestPath.contains("/getcontact")) {
			return routes.get("/getcontact");

		} else if(requestPath.contains("/getjson")) {
			return routes.get("/getcontact");

		} else if(requestPath.contains("/insertcontactviaget")) {
			return routes.get("/insertcontactviaget");

		} else if(requestPath.contains("/postcontact")) {
			return routes.get("/postcontact");
		}

		return routes.get("/file");
	}
}
