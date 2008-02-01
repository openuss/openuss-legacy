package org.openuss.web.papersubmission;


public class SubmissionFileInfo implements  java.io.Serializable, org.openuss.foundation.DomainObject {

	private static final long serialVersionUID = 466217808440537745L;

	private Long id;
	
	private String name;
	private String extension;
	private Long size;
	private String creationDate;
	private String changeDate;
	
	public SubmissionFileInfo() {
		super();
	}
	public SubmissionFileInfo(Long id, String name, String extension, Long size,
			String creationDate, String changeDate) {
		super();
		this.id = id;
		this.name = name;
		this.size = size;
		this.creationDate = creationDate;
		this.changeDate = changeDate;
		this.extension = extension;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	
	public String getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}
	
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}

}
