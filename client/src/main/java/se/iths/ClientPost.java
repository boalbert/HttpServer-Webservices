package se.iths;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClientPost {

	public static void main(String[] args) throws IOException, InterruptedException {

		final String uri = "http://localhost:5050/";
		String data = "123456789";

		post(uri,data);



	}

	private static void post(String uri, String data) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uri))
				.POST(HttpRequest.BodyPublishers.ofString(data))
				.build();

		HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.discarding());
		System.out.println(response.statusCode());

	}
}
