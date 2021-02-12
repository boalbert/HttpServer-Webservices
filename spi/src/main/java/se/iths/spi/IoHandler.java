package se.iths.spi;

public interface IoHandler {

	byte[] urlHandler(String requestPath, String requestBody, String requestMethod);

}
