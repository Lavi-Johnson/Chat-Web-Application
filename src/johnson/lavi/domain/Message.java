package johnson.lavi.domain;

public class Message {
	private String text;
	private User user;
	
	public Message(){
	}
	
	//== Getters and Setters ==//

	public String getText() {
		return text;
	}

	public User getUser() {
		return user;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
