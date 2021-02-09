package se.iths.model;

public class HttpRequest {

	private String requestMethod;
	private String requestPath;
	private String requestBody;

	public HttpRequest() {
	}

	public HttpRequest(String requestMethod, String requestPath) {
		this.requestMethod = requestMethod;
		this.requestPath = requestPath;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	@Override
	public String toString() {
		return "HttpRequest{" +
				"method='" + requestMethod + '\'' +
				", requestPath='" + requestPath + '\'' +
				'}';
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getRequestPath() {
		return requestPath;
	}

	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}
}

