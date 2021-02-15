package se.iths.httpHandler;

import se.iths.model.HttpRequest;
import se.iths.routing.Adress;
import se.iths.spi.IoHandler;

import java.io.InputStream;
import java.net.Socket;
import java.util.ServiceLoader;

public class ConnectionHandler {

	public static void handleConnection(Socket socket) {
		try {

			InputStream inputStream = socket.getInputStream();
			HttpRequest httpRequest = new ParseRequest().constructRequest(inputStream);

			byte[] content = routing(httpRequest);

			ResponseHandler responseHandler = new ResponseHandler();
			responseHandler.handleResponse(content, socket, httpRequest);

			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static byte[] routing(HttpRequest httpRequest) {

		String body = null;
		if (httpRequest.getRequestMethod().equals("POST")) {
			body = httpRequest.getRequestBody();
		}

		byte[] content = null;

		ServiceLoader<IoHandler> loader = ServiceLoader.load(IoHandler.class);
		for (IoHandler handler : loader) {

			if (httpRequest.getRequestPath().contains(".")) {
				if (handler.getClass().getAnnotation(Adress.class).value().equals("/file")) {
					System.out.println("Inside /file route");
					return handler.urlHandler(httpRequest.getRequestPath(), httpRequest.getRequestBody(), httpRequest.getRequestMethod()); // (String requestPath, String requestBody, String requestMethod)
				}


			} else if (httpRequest.getRequestPath().contains("/findcontact")) {
				if (handler.getClass().getAnnotation(Adress.class).value().equals("/database")) {
					System.out.println("Inside /database route");
					return handler.urlHandler(httpRequest.getRequestPath(), httpRequest.getRequestBody(), httpRequest.getRequestMethod()); // (String requestPath, String requestBody, String requestMethod)
				}


			} else if (httpRequest.getRequestPath().contains("create")) {
				if (handler.getClass().getAnnotation(Adress.class).value().equals("/database")) {
					System.out.println("Inside /database route");
					return handler.urlHandler(httpRequest.getRequestPath(), httpRequest.getRequestBody(), httpRequest.getRequestMethod()); // (String requestPath, String requestBody, String requestMethod)
				}

				// Går ej in i POST
			} else if (httpRequest.getRequestPath().contains("/postcontact")) {
				if (handler.getClass().getAnnotation(Adress.class).value().equals("/postdatabase")) {
					System.out.println("Inside /postdatabase route");
					return handler.urlHandler(httpRequest.getRequestPath(), body, httpRequest.getRequestMethod()); // (String requestPath, String requestBody, String requestMethod)
				}
			}
		}
		return content;
	}
}
