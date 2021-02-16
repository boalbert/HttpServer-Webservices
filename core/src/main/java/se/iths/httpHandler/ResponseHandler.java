package se.iths.httpHandler;

import se.iths.model.HttpRequest;
import se.iths.model.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

public class ResponseHandler {

	public void handleResponse(byte[] content, Socket socket, HttpRequest httpRequest) throws Exception {
		var output = new PrintStream(socket.getOutputStream());

		HttpResponse httpResponse = createHttpResponse(content, httpRequest);

		sendHttpResponse(httpResponse, output);

	}

	private HttpResponse createHttpResponse(byte[] content, HttpRequest httpRequest) throws IOException {

		if (content.length > 0) {
			return new HttpResponse(httpRequest.getRequestMethod(), "200 OK", guessContentTypeFromUrl(httpRequest.getRequestPath()), content.length, content);
		} else {
			File file404 = new File("core/src/main/resources/404.html");
			content = Files.readAllBytes(Path.of(file404.getAbsolutePath()));
			return new HttpResponse(httpRequest.getRequestMethod(), "404", guessContentTypeFromUrl(httpRequest.getRequestPath()), content.length, content);
		}
	}

	private void sendHttpResponse(HttpResponse httpResponse, PrintStream output) throws IOException {
		output.println(httpResponse.getStatus());
		output.println("Content-Length: " + httpResponse.getContentLength());
		output.println(httpResponse.getContentType());
		output.println("");
		if (!httpResponse.getMethod().equals("HEAD")) {
			output.write(httpResponse.getContent());
		}
		output.flush();
		output.close();
	}

	private String guessContentTypeFromUrl(String requestPath) {

		String mimeType = URLConnection.guessContentTypeFromName(requestPath);

		if (requestPath.contains("getcontact")) {
			mimeType = "application/json";
		}

		if (requestPath.contains("postcontact")) {
			mimeType = "application/json";
		}

		if (requestPath.contains("insertcontactviaget")) {
			mimeType = "application/json";
		}

		if (mimeType == null) {
			mimeType = "text/html";
		}

		return mimeType;
	}
}