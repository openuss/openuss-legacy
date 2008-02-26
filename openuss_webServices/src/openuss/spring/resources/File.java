package openuss.spring.resources;

public class File {
	private String name;
	private boolean isFolder;
	
	public void setName(String name) {
		this.name = name;	
	}
	
	public String getName() {
		return name;	
	}
	
	public void setIsFolder(boolean isFolder) {
		this.isFolder = isFolder;	
	}

	public boolean getIsFolder() {
		return isFolder;	
	}
}
