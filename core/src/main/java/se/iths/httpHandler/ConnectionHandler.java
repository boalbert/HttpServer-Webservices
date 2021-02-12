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

			HashMap <String,IOhandler> routes = setURLRoutes(httpRequest);
			PluginHandler pluginHandler = new PluginHandler(routes,httpRequest);

			byte [] content = pluginHandler.URLHandler(httpRequest);

			ResponseHandler responseHandler = new ResponseHandler();
			responseHandler.handleResponse(content,socket, httpRequest);

			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static HashMap <String, IOhandler> setURLRoutes(HttpRequest httpRequest) {

		Map<String, IOhandler> routes = new HashMap<>();

		routes.put("/create", new DatabaseIMPL());
		routes.put("/postcontact", new PostDatabaseIMPL());
		routes.put("/", new FileIMPL());

		if (httpRequest.getRequestPath().contains("/findcontact/")) {
			routes.put(httpRequest.getRequestPath(), new DatabaseIMPL());
		} else if (httpRequest.getRequestPath().contains(".")) {
			routes.put(httpRequest.getRequestPath(), new FileIMPL());
		}
		return (HashMap<String, IOhandler>) routes;
	}

}
