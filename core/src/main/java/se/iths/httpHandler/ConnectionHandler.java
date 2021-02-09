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

			//links the URL to an interface
			routes.put("/", new FileIMPL());
			routes.put("/index.html", new FileIMPL());
			routes.put("/404.html", new FileIMPL());
			routes.put("/pdf/git.pdf", new FileIMPL());
			routes.put("/img/cat.png", new FileIMPL());
			routes.put("/img/dog.jpeg", new FileIMPL());
			routes.put("/text/readme.txt", new FileIMPL());
			routes.put("/contacts.html", new FileIMPL());

			routes.put("/contacts/find", new DatabaseIMPL());
			routes.put("/add", new DatabaseIMPL()); //need for new Interface which adds contact to database

			URLRouter(url, routes, socket);

			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void URLRouter(String url, Map<String, IOhandler> routes, Socket socket) throws IOException {
		//runs method urlHandler based on url as input
		var handler = routes.get(url);
		if (handler != null) {
			handler.urlHandler(url, socket);
		} else {
			handler = routes.get("/404.html"); //TODO Refactor this, added quick fix for 404 page
			url = "/404.html";
			handler.urlHandler(url, socket);

		}
	}
}
