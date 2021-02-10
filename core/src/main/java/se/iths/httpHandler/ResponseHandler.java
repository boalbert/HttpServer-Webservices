package se.iths.httpHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.iths.model.HttpRequest;

import java.io.BufferedOutputStream;
import java.net.Socket;
import java.net.URLConnection;

public class ResponseHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseHandler.class);

	public void sendResponse(byte[] content, Socket socket, HttpRequest httpRequest) throws Exception {

		String contentType = guessContentTypeFromUrl(httpRequest.getRequestPath());

		var output = new BufferedOutputStream(socket.getOutputStream());

		// TODO Generate the output-message via a method?
		output.write(("HTTP/1.1 200 OK" + "\r\n").getBytes());
		output.write(("Content-Type: " + contentType + "\r\n").getBytes());
		output.write(("Content-Length: " + content.length).getBytes());
		output.write(("\r\n\r\n").getBytes());
		output.write(content);
		output.write(("\r\n").getBytes());
		output.flush();
		output.close();
		socket.close();

		LOGGER.info(" --- Sending Response --- ");
		LOGGER.info(" > HTTP/1.1 200 OK");
		LOGGER.info(" > Content-Type: " + contentType);
		LOGGER.info(" > Content-Length: " + content.length);
		LOGGER.info(" > \r\n\r\n");
		LOGGER.info(" > 'Placeholder for content (sent as byte[])'");
		LOGGER.info(" > \r\n");
		LOGGER.info(" ------------------------");

	}
	// TODO Does not work with favicon.ico
	private String guessContentTypeFromUrl(String requestPath) {

		String mimeType = URLConnection.guessContentTypeFromName(requestPath);

		if (mimeType == null) {
			mimeType = "text/html";
		}
		return mimeType;
	}
}