package synapticloop.h2zero.validator.bean;

public class Message {
	private String type;
	private String content;

	public Message(String type, String content) {
		this.type = type;
		this.content = content;
	}

	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	public String getContent() { return content; }
	public void setContent(String message) { this.content = message; }
}
