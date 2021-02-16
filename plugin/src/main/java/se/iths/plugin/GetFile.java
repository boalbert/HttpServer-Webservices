package se.iths.plugin;

import se.iths.routing.Route;
import se.iths.spi.IoHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Route(url = "/file")
public class GetFile implements IoHandler {

	@Override
	public byte[] urlHandler(String requestPath, String requestBody, String requestMethod) {

		var filePath = getFilePath(requestPath);
		byte[] file = new byte[0];

		try {
			if (Files.exists(filePath)) {
				file = Files.readAllBytes(filePath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return file;
	}

	private Path getFilePath(String requestPath) {
		String root = System.getProperty("user.home")

				+ java.io.File.separator + "Documents"
				+ java.io.File.separator + "Webservices"
				+ java.io.File.separator;

		if ("/".equals(requestPath)) {
			requestPath = "index.html";
		}

		return Paths.get(root, requestPath);
	}
}
