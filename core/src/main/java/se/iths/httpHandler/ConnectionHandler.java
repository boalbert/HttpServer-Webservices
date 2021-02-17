package se.iths.httpHandler;

import se.iths.model.HttpRequest;
import se.iths.spi.IoHandler;

import java.io.InputStream;
import java.net.Socket;


public class ConnectionHandler {


	public static void handleConnection(Socket socket) {

		ResponseHandler responseHandler = new ResponseHandler();

		try (InputStream inputStream = socket.getInputStream()) {

			// Populate the client request
			HttpRequest httpRequest = new ParseRequest().constructRequest(inputStream);

			// Get implementation of IoHandler based on requested URL
			IoHandler handler = UrlServiceLoader.findRoute(httpRequest.getRequestPath());

			// Return content from chosen IoHandler implementation based on request
			byte[] content = handler.urlHandler(httpRequest.getRequestPath(), httpRequest.getRequestBody(), httpRequest.getRequestMethod());

			// Send content to client
			responseHandler.handleResponse(content, socket, httpRequest);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
