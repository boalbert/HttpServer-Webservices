package se.iths.httpHandler;

import se.iths.model.HttpRequest;
import se.iths.plugin.DatabaseIMPL;
import se.iths.plugin.FileIMPL;
import se.iths.plugin.PostDatabaseIMPL;
import se.iths.spi.IOhandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ConnectionHandler {
	
	public static void handleConnection(Socket socket) {
		try {

			InputStream inputStream = socket.getInputStream();
			HttpRequest httpRequest = new ParseRequest().constructRequest(inputStream);

			String url = httpRequest.getRequestPath();
			Map<String, IOhandler> routes = new HashMap<>();


			routes.put("/create", new DatabaseIMPL());
			routes.put("/postcontact", new PostDatabaseIMPL());
			routes.put("/", new FileIMPL());

			if (httpRequest.getRequestPath().contains("/findcontact/")) {
				routes.put(httpRequest.getRequestPath(), new DatabaseIMPL());
			} else if (httpRequest.getRequestPath().contains(".")) {
				routes.put(httpRequest.getRequestPath(), new FileIMPL());
			}

			PluginHandler pluginHandler = new PluginHandler(routes,httpRequest);
			byte [] content = pluginHandler.URLHandler(httpRequest);

			ResponseHandler responseHandler = new ResponseHandler();

			responseHandler.handleResponse(content,socket, httpRequest);

			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private static byte[] pluginHandler(String url, Map<String, IOhandler> routes, String requestBody, String requestMethod) throws IOException {

		var handler = routes.get(url);

		byte[] file;

		if (handler != null) {
			file = handler.urlHandler(url, requestBody, requestMethod);
		} else if (url.contains("create")) {
			handler = routes.get("/create");
			file = handler.urlHandler(url, requestBody, requestMethod);

		} else {
			handler = routes.get("/404.html"); //TODO Refactor this, added quick fix for 404 page
			url = "/404.html";

			file = handler.urlHandler(url, requestBody, requestMethod);
		}
		return file;
	}
}
