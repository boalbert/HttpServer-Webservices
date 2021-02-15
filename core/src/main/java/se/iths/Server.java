package se.iths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.iths.httpHandler.ConnectionHandler;
import se.iths.routing.Adress;
import se.iths.spi.IoHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ServiceLoader;
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

				executorService.execute(() -> ConnectionHandler.handleConnection(socket));
			}

		} catch (IOException e) {
			LOGGER.error(" * Failed to start server: " + e);
			e.printStackTrace();

		}
	}
}
