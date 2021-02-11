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


		StringBuilder fetchWholeRequest = readWholeRequest(bufferedReader);
//		LOGGER.info(fetchWholeRequest.toString());

		String[] requestHeader = getRequestHeader(fetchWholeRequest);

//		String requestBody = getRequestBody(fetchWholeRequest);



		// Set up the request object
		String requestMethod = requestHeader[0];
		String requestPath = requestHeader[1];

		var httpRequest = new HttpRequest(requestMethod, requestPath);

		return httpRequest;
	}

	private String[] getRequestHeader(StringBuilder builder) {

		// Splitting the request into individual lines
		String wholeRequest = builder.toString();
		LOGGER.info("wholeRequest: " + wholeRequest);

		// Splitting them where there is a linebreak, "\r\n"
		String[] requestRows = wholeRequest.split("\r\n");

		// Splitting the first row in three parts, split where there is a whitespace, " "
		// "[GET, /path, HTTP/1.1]
		String[] firstrow = requestRows[0].split(" ");
		LOGGER.info("firstRow[0]: " + firstrow[0].toString());
		LOGGER.info("firstRow[1]: " + firstrow[1].toString());
		return firstrow;
	}

	private String getRequestBody(StringBuilder builder) {

		String wholeRequest = builder.toString();
		String[] rows = wholeRequest.split("\r\n");
		var lastRow = rows[rows.length-1];

		return lastRow;
	}

	private StringBuilder readWholeRequest(BufferedReader bufferedReader) throws IOException {

		StringBuilder builder = new StringBuilder();
		String line;

		// Break när vi har content length == hela contentlength
		do {
			line = bufferedReader.readLine();
			if(line.equals("")) {
				break;
			}
			builder.append(line).append("\r\n");

		} while(true);

		return builder;


	}
}