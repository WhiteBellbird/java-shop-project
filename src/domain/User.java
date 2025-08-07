package domain;

public class User {

	private String userId;
	private String username;
	private String email;
	private String password;
	private boolean isAdmin;
	// 전화번호, 주소 등등 다른 필드는 알아서 구현하길 바람.
	
	public User(String userId, String username, String email, String password) {
		super();
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.password = password;
		this.isAdmin = false;
	}
	
	public void giveManagerAuthentication() {
		// 이미 관리자면 메서드 종료
		if(this.isAdmin) return;
		// 관리자로 승격
		this.isAdmin = true;
	}

	public String getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}


	public boolean isAdmin() {
		return isAdmin;
	}

	
	
	
	
}
