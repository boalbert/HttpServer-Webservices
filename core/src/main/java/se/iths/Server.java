package se.iths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.iths.httpHandler.HttpParser;
import se.iths.httpHandler.HttpRequestModel;

import java.io.IOException;
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
			HttpRequestModel httpRequest = new HttpParser().constructRequest(socket);

			// Not implemented - create a method / class for creating response
			// Other method here to send it to client
				//	HttpResponse httpResponse = new HttpResponse();
				//	httpResponse.handleResponse(httpRequest,socket);

			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


//		private static void handleConnection(Socket socket) {
//
//			try {
//				BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//				while (true) {
//					String headerLine = inputReader.readLine();
//					LOGGER.info(headerLine);
//
//					if (headerLine.isEmpty()) {
//						break;
//					}
//				}
//				var output = new PrintWriter(socket.getOutputStream());
//				String page =
//						"""
//			                  <html>
//			                  <head>
//			                      <title>Hello World!</title>
//			                  <body>
//			                  <h1>Hello</h1>
//			                  <div>
//			                  Here is out main-page.
//			                  </div>
//			                  </body>
//			                  </html>
//								""";
//
//				output.println("HTTP/1.1 200 OK");
//				output.println("Content-Length:" + page.getBytes().length);
//				output.println("Content-Type:text/html");  //application/json
//				output.println("");
//				output.print(page);
//
//				output.flush();
//				socket.close();
//
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//
//		}

}
