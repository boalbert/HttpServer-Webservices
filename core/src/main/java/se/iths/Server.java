package se.iths;

import se.iths.httpHandler.ConnectionHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {

	public static void main(String[] args) {

		ExecutorService executorService = Executors.newCachedThreadPool();

		try {

			var serverSocket = new ServerSocket(5050);

			while (true) {
				var socket = serverSocket.accept();

				executorService.execute(() -> ConnectionHandler.handleConnection(socket));

			}

		} catch (IOException e) {
			e.printStackTrace();

		}
	}
}
