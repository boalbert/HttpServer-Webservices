package se.iths.httpHandler;

import se.iths.model.HttpRequest;
import se.iths.plugin.DatabaseIMPL;
import se.iths.plugin.FileIMPL;
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

			routes.put("/", new FileIMPL());
			routes.put("/index.html", new FileIMPL());
			routes.put("/post.html", new FileIMPL());
			routes.put("/testpost.html", new FileIMPL());
			routes.put("/404.html", new FileIMPL());
			routes.put("/pdf/git.pdf", new FileIMPL());
			routes.put("/img/cat.png", new FileIMPL());
			routes.put("/img/dog.jpeg", new FileIMPL());
			routes.put("/text/readme.txt", new FileIMPL());
			routes.put("/contacts.html", new FileIMPL());

			routes.put("/contact", new DatabaseIMPL()); //need for new Interface which adds contact to database

			//TODO Refactor!
			ResponseHandler responseHandler = new ResponseHandler();
			responseHandler.sendResponse(pluginHandler(url, routes), socket, httpRequest);

			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//TODO Create a new class for this
	private static byte[] pluginHandler(String url, Map<String, IOhandler> routes) throws IOException {
		//runs method urlHandler based on url as input
		var handler = routes.get(url);

		byte[] file = null;

		if (handler != null) {
			file = handler.urlHandler(url);
		} else {
			handler = routes.get("/404.html"); //TODO Refactor this, added quick fix for 404 page
			url = "/404.html";

			file = handler.urlHandler(url);
		}
		return file;
	}
}
