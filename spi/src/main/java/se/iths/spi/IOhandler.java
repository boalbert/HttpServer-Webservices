package se.iths.spi;

public interface IOhandler {

	byte[] urlHandler(String requestPath, String requestBody, String requestMethod);

}
