package multipleClientChat;

public class Chat implements java.io.Serializable {
	private String name;
	private String chat;

	public Chat(String chat) {
		this.chat = chat;
	}

	public Chat(String name, String chat) {
		this.name = name;
		this.chat = chat;
	}

	public void setName(String name) {
		this.name =name;
	}

	public String getName() {
		return name;
	}

	public void setChat(String chat) {
		this.chat = chat;
	}

	public String getChat() {
		return chat;
	}
}
