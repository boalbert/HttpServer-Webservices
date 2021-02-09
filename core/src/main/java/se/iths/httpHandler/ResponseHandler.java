package se.iths.httpHandler;

import se.iths.model.HttpRequest;

import java.io.BufferedOutputStream;
import java.net.Socket;
import java.net.URLConnection;

public class ResponseHandler {

	public void sendResponse(byte[] content, Socket socket, HttpRequest httpRequest) throws Exception {

		String contentType = guessContentTypeFromUrl(httpRequest.getRequestPath());

		var output = new BufferedOutputStream(socket.getOutputStream());

		output.write(("HTTP/1.1 200 OK" + "\r\n").getBytes());
		output.write(("Content-Type: " + contentType + "\r\n").getBytes());
		output.write(("Content-Length: " + content.length).getBytes());
		output.write(("\r\n\r\n").getBytes());
		output.write(content);
		output.write(("\r\n").getBytes());
		output.flush();
		output.close();
		socket.close();
	}

	private String guessContentTypeFromUrl(String requestPath) {

		String mimeType = URLConnection.guessContentTypeFromName(requestPath);
		if (mimeType == null) {
			mimeType = "text/html";
		}
		return mimeType;
	}
}