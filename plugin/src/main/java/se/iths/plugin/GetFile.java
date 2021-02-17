package se.iths.plugin;

import se.iths.routing.Route;
import se.iths.spi.IoHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Route(url = "/file")
public class GetFile implements IoHandler {

	/**
	 * Used to return files. If no file is found or if it fails to open the file returns
	 * an empty byte[], which ResponseHandler interprets as 404.
	 *
	 * @return file or byte[0]
	 */
	@Override
	public byte[] urlHandler(String requestPath, String requestBody, String requestMethod) {

		var filePath = getFilePath(requestPath);

		try {
			if (Files.exists(filePath))
				return Files.readAllBytes(filePath);
			return new byte[0];
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new byte[0];
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
