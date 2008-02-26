package openuss.spring.resources;

public class User {
	private String password;
	private String username;
	
	public User() {
		System.out.println("User: Creating user.");
	}
	
	public String getPassword() { 
		return password; 
	}
	
	public void setPassword(String password) { 
		this.password = password; 
	}
	
	public String getUsername() { 
		return username; 
	}
	
	public void setUsername(String username) { 
		this.username = username; 
	}
}
