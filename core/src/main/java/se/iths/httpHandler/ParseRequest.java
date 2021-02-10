package se.iths.httpHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.iths.model.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ParseRequest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ParseRequest.class);

	public HttpRequest constructRequest(InputStream inputStream) throws IOException {
		// Open buffer to read request
		var bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));

		// Parse the request
		String[] firstRequestRow = getFirstRequestRow(bufferedReader);

		// Set up the request object
		String requestMethod = firstRequestRow[0];
		String requestPath = firstRequestRow[1];

		var httpRequest = new HttpRequest(requestMethod, requestPath);

		return httpRequest;
	}

	private String[] getFirstRequestRow(BufferedReader bufferedReader) throws IOException {

		var requestBuilder = new StringBuilder(); // Will hold the whole request
		String row;

		// Looping through the request line-by-line, each line on a separate row. Use this to parse the body when needed
		while (!(row = bufferedReader.readLine()).isBlank()) {
			requestBuilder.append(row).append("\r\n");
			LOGGER.info(" > " + row);
		}

		// Splitting the request into individual lines
		String wholeRequest = requestBuilder.toString();

		// Splitting them where there is a linebreak, "\r\n"
		String[] requestRows = wholeRequest.split("\r\n");

		// Splitting the first row in three parts, split where there is a whitespace, " "
		// "[GET, /path, HTTP/1.1]
		return requestRows[0].split(" ");
	}
}