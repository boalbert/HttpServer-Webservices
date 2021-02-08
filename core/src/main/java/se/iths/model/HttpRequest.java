package se.iths.model;

public class HttpRequest {

	private String method;
	private String requestPath;
	private String requestBody;

	public HttpRequest() {
	}

	public HttpRequest(String method, String requestPath) {
		this.method = method;
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
				"method='" + method + '\'' +
				", requestPath='" + requestPath + '\'' +
				'}';
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getRequestPath() {
		return requestPath;
	}

	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}
}

