package se.iths.httpHandler;

public class HttpRequestModel extends HttpParser {

	private String method; // GET, POST, HEAD
	private String requestPath; // "/" "/web/cat.jpg"
	private String requestBody;

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public HttpRequestModel() {
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
