package se.iths.httpHandler;

import se.iths.model.HttpRequest;
import se.iths.spi.IoHandler;

import java.io.InputStream;
import java.net.Socket;


public class ConnectionHandler {

	public static void handleConnection(Socket socket) {

		ResponseHandler responseHandler = new ResponseHandler();

		try (InputStream inputStream = socket.getInputStream()) {

			HttpRequest httpRequest = new ParseRequest().constructRequest(inputStream);

			// Get handler
			IoHandler handler = UrlServiceLoader.findWhatImplementationToUse(httpRequest.getRequestPath());

			// Get content back from handler
			byte[] content = handler.urlHandler(httpRequest.getRequestPath(), httpRequest.getRequestBody(), httpRequest.getRequestMethod());

			responseHandler.handleResponse(content, socket, httpRequest);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
