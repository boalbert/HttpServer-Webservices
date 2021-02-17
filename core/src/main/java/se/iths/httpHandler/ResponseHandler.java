package se.iths.httpHandler;

import se.iths.model.HttpRequest;
import se.iths.model.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class ResponseHandler {

	/**
	 * Constructs our httpResponse and send it out to the client via class methods.
	 *
	 * @param content received from our plugins (file or database query)
	 */
	public void handleResponse(byte[] content, Socket socket, HttpRequest httpRequest) throws Exception {
		var output = new PrintStream(socket.getOutputStream());

		HttpResponse httpResponse = createHttpResponse(content, httpRequest);

		sendHttpResponse(httpResponse, output);
	}

	/**
	 * Populates our httpResponse object.
	 * 
	 * @param content     from our IoHandlers. If it is 0 bytes we return a 404 httpResponse object.
	 * @param httpRequest with requested information.
	 * @return populated httpResponse.
	 */
	private HttpResponse createHttpResponse(byte[] content, HttpRequest httpRequest) throws IOException {

		if (content.length > 0) {
			return new HttpResponse(httpRequest.getRequestMethod(), "200 OK", findContentType(httpRequest.getRequestPath()), content);
		} else {
			File file404 = new File("core/src/main/resources/404.html");
			content = Files.readAllBytes(Path.of(file404.getAbsolutePath()));
			return new HttpResponse(httpRequest.getRequestMethod(), "404", "text/html", content);
		}
	}

	/**
	 * Sends the response to client.
	 *
	 * @param httpResponse populated with all data needed for response.
	 * @param output       (Printstream) used for sending the requested data to client; closes after sending data.
	 */
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

	/**
	 * If the url points to a database action , the content type is set to Json.
	 * Otherwise we check mimetype of the file.
	 *
	 * @param requestPath to the file or database path
	 * @return mimetype/content-type of the file / path
	 */
	private String findContentType(String requestPath) throws IOException {

		if (requestPath.contains("/contacts") || requestPath.contains("/postcontact") || requestPath.contains("/insertcontactviaget")) {
			return "application/json";
		} else {
			if ("/".equals(requestPath)) {
				requestPath = "index.html";
			}
			Path path = Path.of(requestPath);
			return Files.probeContentType(path);
		}
	}
}