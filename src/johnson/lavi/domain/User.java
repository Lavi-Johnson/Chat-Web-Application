package johnson.lavi.domain;

public class User {
	private String userName;
	private String hexColor;
	
	public User(){
	}
	
	public User(String userName){
		setUserName(userName);
	}
	
	public User(String userName, String hexColor){
		setUserName(userName);
		setHexColor(hexColor);
	}
	
	//== Getters and Setters ==//

	public String getUserName() {
		return userName;
	}

	public String getHexColor() {
		return hexColor;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setHexColor(String hexColor) {
		this.hexColor = hexColor;
	}
}
