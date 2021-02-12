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
			// Create a new httpRequest Model
			// Parse the request and return a new requestModel
			InputStream inputStream = socket.getInputStream();
			HttpRequest httpRequest = new ParseRequest().constructRequest(inputStream);

			//gets url from requestModel;
			String url = httpRequest.getRequestPath();

			Map<String, IOhandler> routes = new HashMap<>();

			// Om den inte ska nå DatabaseIMPL() så skickar vi den till filehandler (som tar 404) eller
			// skapar en 404 plugin med felmeddelande
			//links the URL to an interface

			routes.put("/create", new DatabaseIMPL()); //need for new Interface which adds contact to database
			routes.put("/postcontact", new PostDatabaseIMPL());
			routes.put("/", new FileIMPL());

			if (httpRequest.getRequestPath().contains("/findcontact/")) {
				routes.put(httpRequest.getRequestPath(), new DatabaseIMPL()); //need for new Interface which adds contact to database
			} else if (httpRequest.getRequestPath().contains(".")) {
				routes.put(httpRequest.getRequestPath(), new FileIMPL());
			}


			//TODO Refactor!
			ResponseHandler responseHandler = new ResponseHandler();
			responseHandler.sendResponse(pluginHandler(url, routes, httpRequest.getRequestBody(), httpRequest.getRequestMethod()), socket, httpRequest);

			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//TODO Create a new class for this
	private static byte[] pluginHandler(String url, Map<String, IOhandler> routes, String requestBody, String requestMethod) throws IOException {
		//runs method urlHandler based on url as input
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
