package kgs.bot;

public class YIData {
	private String user_key;
	private String type;
	private String content;
	public String getUser_key() {
		return user_key;
	}
	public void setUser_key(String user_key) {
		this.user_key = user_key;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "YIData [user_key=" + user_key + ", type=" + type + ", content=" + content + "]";
	}
	
}
