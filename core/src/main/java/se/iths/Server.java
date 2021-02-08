package se.iths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.iths.httpHandler.ParseRequest;
import se.iths.model.HttpRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);


	public static void main(String[] args) {

		ExecutorService executorService = Executors.newCachedThreadPool();

		try {
			LOGGER.info(" * Server started on port 5050");

			var serverSocket = new ServerSocket(5050);

			while (true) {
				var socket = serverSocket.accept();

				executorService.execute(() -> handleConnection(socket));
			}

		} catch (IOException e) {
			LOGGER.error(" * Failed to start server: " + e);
			e.printStackTrace();

		}

	}

	private static void handleConnection(Socket socket) {

		try {
			// Create a new httpRequest Model
			// Parse the request and return a new requestModel
			InputStream inputStream = socket.getInputStream();
			HttpRequest httpRequest = new ParseRequest().constructRequest(inputStream);


			// Not implemented - create a method / class for creating response
			// Other method here to send it to client

			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
