package se.iths.model;

public class HttpResponse {

	private String status;
	private String contentType;
	private int contentLength;
	private byte[] content;

	public HttpResponse() {
	}

	public HttpResponse(String status, String contentType, int contentLength, byte[] content) {
		this.status = status;
		this.contentType = contentType;
		this.contentLength = contentLength;
		this.content = content;
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
