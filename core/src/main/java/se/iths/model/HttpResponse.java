package se.iths.model;

public class HttpResponse {

	private String method;
	private String status;
	private String contentType;
	private int contentLength;

	public HttpResponse(String method, String status, String contentType, byte[] content) {
		this.method = method;
		this.status = "HTTP/1.1 " + status;
		this.contentType = "Content-Type: " + contentType;
		this.contentLength = content.length;
		this.content = content;
	}

	public String getMethod() {
		return method;
	}

	private byte[] content;

	public HttpResponse() {
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public int getContentLength() {
		return contentLength;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
}
