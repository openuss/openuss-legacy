package org.openuss.services.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Course")
public class CourseBean {

	private Long id;

	@XmlElement(required = true)
	@XmlSchemaType(name = "token")
	private String name;

	@XmlSchemaType(name = "token")
	private String shortcut;

	private String description;

	@XmlElement(required = true)
	private Long instituteId;

	@XmlElement(required = true)
	private Date startDate;
	private Date endDate;

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

	public String getShortcut() {
		return shortcut;
	}

	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(Long instituteId) {
		this.instituteId = instituteId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date firstDate) {
		this.startDate = firstDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date lastDate) {
		this.endDate = lastDate;
	}

}
