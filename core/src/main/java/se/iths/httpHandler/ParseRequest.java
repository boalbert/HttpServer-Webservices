package se.iths.httpHandler;

import se.iths.model.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ParseRequest {

	/***
	 * Creates a request object by parsing the incoming request (input stream)
	 * @param inputStream (BufferedReader)
	 * @return Http request as object with requested params
	 * @throws IOException
	 */
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

		httpRequest.setRequestMethod(firstRow[0]);
		httpRequest.setRequestPath(firstRow[1]);

		return httpRequest;
	}

	/***
	 * Returns the header of Http request as Stringbuilder
	 * @param bufferedReader
	 * @return Head of Http request as Stringbuilder
	 * @throws IOException
	 */
	private static StringBuilder getRequestHeader(BufferedReader bufferedReader) throws IOException {

		StringBuilder builder = new StringBuilder();

		while (true) {

			String line = bufferedReader.readLine();
			builder.append(line).append("\r\n");
			if (line == null || line.isEmpty()) {
				break;
			}
		}

		return builder;
	}

	/***
	 * Returns the header as String array by splitting the header at linebreak
	 * @param builder
	 * @return String array
	 */
	private String[] readRequestHeader(StringBuilder builder) {

		String wholeRequest = builder.toString();

		return wholeRequest.split("\r\n");
	}

	private String[] getFirstRowInHeader(String[] header) {

		return header[0].split(" ");
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

	/***
	 * Returns the body of Http request as String
	 * @param bufferedReader
	 * @param contentlength of the body to determine how many bytes we need to read in
	 * @return body as String
	 * @throws IOException
	 */

	private String getRequestBody(BufferedReader bufferedReader, int contentlength) throws IOException {

		char[] buffer = new char[contentlength];

		bufferedReader.read(buffer, 0, buffer.length);

		return new String(buffer, 0, buffer.length);
	}
}