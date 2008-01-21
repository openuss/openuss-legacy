package org.openuss.web.course.papersubmission;

public class SubmissionInfo implements  java.io.Serializable, org.openuss.foundation.DomainObject {

	private static final long serialVersionUID = 466217808440537745L;
	
	private Long id;
	
	private String name;
	private String status;
	
	public SubmissionInfo() {
		super();
	}
	public SubmissionInfo(Long id, String name, String status) {
		this();
		this.id = id;
		this.name = name;
		this.status = status;
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
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}