package se.iths;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClientGet {

	public static void main(String[] args) {

		final String uri = "http://localhost:5050/web/cats.jpg";

		ClientGet client = new ClientGet();
		client.get(uri);


	}

	public void get(String uri) {

		HttpClient client = HttpClient.newHttpClient();

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uri))
				.build();

		client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
				.thenApply(HttpResponse :: body)
				.thenAccept(System.out :: println)
				.join();


	}


}
