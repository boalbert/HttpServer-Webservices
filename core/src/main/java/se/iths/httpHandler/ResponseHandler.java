package se.iths.httpHandler;

import se.iths.model.HttpRequest;
import se.iths.model.HttpResponse;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URLConnection;

public class ResponseHandler {

	public void handleResponse(byte[] content, Socket socket, HttpRequest httpRequest) throws Exception {
		var printStream = new PrintStream(socket.getOutputStream());

		HttpResponse httpResponse = createHttpResponse(content, httpRequest);

		sendHttpResponse(httpResponse, printStream);

		socket.close();
	}

	private void sendHttpResponse(HttpResponse httpResponse, PrintStream output) throws IOException {
		output.println(httpResponse.getStatus());
		output.println("Content-Length: " + httpResponse.getContentLength());
		output.println(httpResponse.getContentType());
		output.println("");
		if(!httpResponse.getMethod().equals("HEAD")) {
			output.write(httpResponse.getContent());
		}
		output.flush();
		output.close();
	}

	private HttpResponse createHttpResponse(byte[] content, HttpRequest httpRequest) {
		return new HttpResponse(httpRequest.getRequestMethod(), "200 OK", guessContentTypeFromUrl(httpRequest.getRequestPath()),content.length, content);
	}

	private String guessContentTypeFromUrl(String requestPath) {

		String mimeType = URLConnection.guessContentTypeFromName(requestPath);

		if(requestPath.contains("json")) {
			mimeType = "application/json";
		}

		if (mimeType == null) {
			mimeType = "text/html";
		}

		return mimeType;
	}
}