package se.iths.plugin;

import se.iths.spi.IOhandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileIMPL implements IOhandler {

	@Override
	public byte[] urlHandler(String requestPath) {
		var filePath = getFilePath(requestPath);
		byte[] file = new byte[0];

		try {
			file = Files.readAllBytes(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return file;
	}

	private Path getFilePath(String requestPath) {
		String root = System.getProperty("user.home")

				+ File.separator + "Documents"
				+ File.separator + "Webservices"
				+ File.separator;

		if ("/".equals(requestPath)) {
			requestPath = "index.html";
		}

		return Paths.get(root, requestPath);
	}
}
