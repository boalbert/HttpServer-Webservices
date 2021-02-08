package se.iths.plugin;

import se.iths.spi.IOhandler;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileIMPL implements IOhandler {

	public static byte[] readFromFile(File file) {
		byte[] content = new byte[0];
		System.out.println("Does file exists: " + file.exists());
		if (file.exists() && file.canRead()) {
			try (FileInputStream fileInputStream = new FileInputStream(file)) {
				content = new byte[(int) file.length()];
				int count = fileInputStream.read(content);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}

	@Override
	public String urlHandler(String requestPath, Socket socket) throws IOException {

        var output = new BufferedOutputStream(socket.getOutputStream());

		var filePath = getFilePath(requestPath);

		byte[] file = Files.readAllBytes(filePath);

		var contentType = findContentType(filePath);

		output.write(("HTTP/1.1 200 OK" + "\r\n").getBytes());
		output.write(("Content-Type: " + contentType + "\r\n").getBytes());
		output.write(("Content-Length: " + file.length).getBytes());
		output.write(("\r\n\r\n").getBytes());
		output.write(file);
        output.write(("\r\n").getBytes());
		output.flush();
		output.close();
		socket.close();

		return null;
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

	private String findContentType(Path filepath) throws IOException {
		return Files.probeContentType(filepath);
	}
}
