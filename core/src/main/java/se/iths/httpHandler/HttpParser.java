package se.iths.httpHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.iths.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class HttpParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

	//0:GET /images/cat.jpg HTTP/1.1
	//1:Host: localhost:8080
	//2:User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:85.0) Gecko/20100101 Firefox/85.0
	//3:Content-Type: text/json

	public HttpRequestModel constructRequest(Socket socket) throws IOException {

		HttpRequestModel httpRequest = new HttpRequestModel();

		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(socket.getInputStream())); // Hämtar reader via socket

		String[] requestLine = getRequestLine(bufferedReader); // Läser in hela requesten

		String method = requestLine[0]; // Hämtar GET
		String path = requestLine[1]; // hämtar /images/cat.jpg

		httpRequest.setMethod(method);
		httpRequest.setRequestPath(path);

		LOGGER.info(" > -- Received request -- ");
		LOGGER.info(" > Client address: " + socket.getInetAddress());
		LOGGER.info(" > Method: " + httpRequest.getMethod());
		LOGGER.info(" > Request path: " + httpRequest.getRequestPath());

		return httpRequest;
	}

	private String[] getRequestLine(BufferedReader bufferedReader) throws IOException {

		String request = bufferedReader.readLine(); // Läser in hela requesten i en lång sträng

		request = request + "\r\n";

		String[] requestLines = request.split("\r\n");
		String[] requestLine = requestLines[0].split(" ");
		return requestLine;
	}
}

