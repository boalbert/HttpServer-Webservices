package se.iths.httpHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.iths.model.HttpRequest;
import se.iths.model.HttpResponse;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URLConnection;

public class ResponseHandler {

	public void handleResponse(byte[] content, Socket socket, HttpRequest httpRequest) throws Exception {
		var bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());

		HttpResponse httpResponse = createHttpResponse(content, httpRequest);

		sendHttpResponse(httpResponse, bufferedOutputStream);

		socket.close();
	}

	private void sendHttpResponse(HttpResponse httpResponse, BufferedOutputStream bufferedOutputStream) throws IOException {

		bufferedOutputStream.write(("HTTP/1.1 " + httpResponse.getStatus() + "\r\n").getBytes());
		bufferedOutputStream.write(("Content-Type: " + httpResponse.getContentType() + "\r\n").getBytes());
		bufferedOutputStream.write(("Content-Length : " + httpResponse.getContentLength()).getBytes());
		bufferedOutputStream.write(("\r\n\r\n").getBytes());
		bufferedOutputStream.write(httpResponse.getContent());
		bufferedOutputStream.write(("\r\n\r\n").getBytes());
		bufferedOutputStream.flush();
		bufferedOutputStream.close();

	}

	private HttpResponse createHttpResponse(byte[] content, HttpRequest httpRequest) {
		HttpResponse httpResponse = new HttpResponse();
		httpResponse.setStatus("200 OK");
		httpResponse.setContentType(guessContentTypeFromUrl(httpRequest.getRequestPath()));
		httpResponse.setContentLength(content.length);
		httpResponse.setContent(content);

		return httpResponse;
	}

	private String guessContentTypeFromUrl(String requestPath) {

		String mimeType = URLConnection.guessContentTypeFromName(requestPath);

		if (mimeType == null) {
			mimeType = "text/html";
		}
		return mimeType;
	}
}