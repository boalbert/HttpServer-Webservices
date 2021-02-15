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

		var bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));

		var httpRequest = new HttpRequest();

		StringBuilder head = getRequestHeader(bufferedReader);
		String[] requestRows = readRequestHeader(head);

		String[] firstRow = getFirstRowInHeader(requestRows);

		if (firstRow[0].equals("POST")) {
			int contentLength = getContentLength(requestRows);
			String requestBody = getRequestBody(bufferedReader, contentLength);
			httpRequest.setRequestBody(requestBody);
		}

		String requestMethod = firstRow[0];
		String requestPath = firstRow[1];

		httpRequest.setRequestMethod(requestMethod);
		httpRequest.setRequestPath(requestPath);

		return httpRequest;
	}

	
	private StringBuilder getRequestHeader(BufferedReader bufferedReader) throws IOException {

		StringBuilder builder = new StringBuilder();
		String line;

		// Break när vi har content length == hela contentlength
		do {
			line = bufferedReader.readLine();
			if (line.equals("")) {
				break;
			}
			builder.append(line).append("\r\n");

		} while (true);

		return builder;
	}

	private String[] readRequestHeader(StringBuilder builder) {

		// Splitting the request into individual lines
		String wholeRequest = builder.toString();

		// Splitting them where there is a linebreak, "\r\n"
		String[] requestRows = wholeRequest.split("\r\n");

		return requestRows;

		// Splitting the first row in three parts, split where there is a whitespace, " "
		// "[GET, /path, HTTP/1.1]

	}

	private String[] getFirstRowInHeader(String[] header) {

		String[] firstrow = header[0].split(" ");

		return firstrow;
	}

	private int getContentLength(String[] requestRows) {

		int contentLengthIndex = 0;
		for (int i = 0; i < requestRows.length; i++) {
			if (requestRows[i].contains("Content-Length:")) {
				contentLengthIndex = i;
			}
		}
		String contentLengthAsText = requestRows[contentLengthIndex];

		String[] lengthArray = contentLengthAsText.split(" ");

		String length = lengthArray[1];

		return Integer.parseInt(length);
	}

	private String getRequestBody(BufferedReader bufferedReader, int length) throws IOException {

		char[] buffer = new char[length];

		bufferedReader.read(buffer, 0, buffer.length);

		String postData = "";

		postData = new String(buffer, 0, buffer.length);

		return postData;
	}
}