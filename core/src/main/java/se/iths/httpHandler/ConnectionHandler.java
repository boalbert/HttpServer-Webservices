package se.iths.httpHandler;

import se.iths.model.HttpRequest;
//import se.iths.plugin.DatebaseImpl;
//import se.iths.plugin.FileImpl;
//import se.iths.plugin.PostDatebaseImpl;
import se.iths.spi.IoHandler;

import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ConnectionHandler {
	
	public static void handleConnection(Socket socket) {
		try {

			Map<String, String> routing = new HashMap<>();
			routing.p

			InputStream inputStream = socket.getInputStream();
			HttpRequest httpRequest = new ParseRequest().constructRequest(inputStream);

			HashMap <String, IoHandler> routes = setURLRoutes(httpRequest);
			PluginHandler pluginHandler = new PluginHandler(routes, httpRequest);

			byte[] content = pluginHandler.URLHandler(httpRequest);

			ResponseHandler responseHandler = new ResponseHandler();
			responseHandler.handleResponse(content,socket, httpRequest);

			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	private static HashMap <String, IoHandler> setURLRoutes(HttpRequest httpRequest) {
//
//		Map<String, IoHandler> routes = new HashMap<>();
//
//		routes.put("/create", new DatebaseImpl());
//		routes.put("/postcontact", new PostDatebaseImpl());
//		routes.put("/", new FileImpl());
//
//		if (httpRequest.getRequestPath().contains("/findcontact/")) {
//			routes.put(httpRequest.getRequestPath(), new DatebaseImpl());
//		} else if (httpRequest.getRequestPath().contains(".")) {
//			routes.put(httpRequest.getRequestPath(), new FileImpl());
//		}
//		return (HashMap<String, IoHandler>) routes;
//	}
}
