package org.openuss.web.course.papersubmission;

/** FIXME: a class like this must be generated and then this must be removed! */
public class PaperInfo implements  java.io.Serializable, org.openuss.foundation.DomainObject {

	private static final long serialVersionUID = 684667056524260994L;

	private Long id;
	private Long courseId;
	
	private String name;
	private String description;
	
	public PaperInfo() {
		super();
	}
	public PaperInfo(Long id, Long courseId, String name, String description) {
		this();
		this.id = id;
		this.courseId = courseId;
		this.name = name;
		this.description = description;
	}
	
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	

	// TODO implement equals, hash
}
