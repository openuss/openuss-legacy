package org.openuss.lecture;

import java.util.Set;

/**
 * An course is a specific courseType that take place within a defined period of
 * time (like a specific semester) by one institute.
 * 
 * @author Ingo Düppe
 */
public interface Course extends org.openuss.foundation.DomainObject {

	public Long getId();

	public void setId(Long id);

	public String getShortcut();

	public void setShortcut(String shortcut);

	/**
	 * Defines the access type of this course.
	 * 
	 * @See org.openuss.lecture.AccessType
	 */
	public org.openuss.lecture.AccessType getAccessType();

	public void setAccessType(org.openuss.lecture.AccessType accessType);

	public String getPassword();

	public void setPassword(String password);

	public Boolean getDocuments();

	public void setDocuments(Boolean documents);

	public Boolean getDiscussion();

	public void setDiscussion(Boolean discussion);

	public Boolean getNewsletter();

	public void setNewsletter(Boolean newsletter);

	public Boolean getChat();

	public void setChat(Boolean chat);

	public Boolean getFreestylelearning();

	public void setFreestylelearning(Boolean freestylelearning);

	public Boolean getBraincontest();

	public void setBraincontest(Boolean braincontest);

	public Boolean getCollaboration();

	public void setCollaboration(Boolean collaboration);

	public Boolean getPapersubmission();

	public void setPapersubmission(Boolean papersubmission);

	public Boolean getWiki();

	public void setWiki(Boolean wiki);

	public String getDescription();

	public void setDescription(String description);

	public boolean isEnabled();

	public void setEnabled(boolean enabled);

	public org.openuss.lecture.CourseType getCourseType();

	public void setCourseType(org.openuss.lecture.CourseType courseType);

	public org.openuss.lecture.Period getPeriod();

	public void setPeriod(org.openuss.lecture.Period period);

	public Set<org.openuss.security.Group> getGroups();

	public void setGroups(Set<org.openuss.security.Group> groups);

	public abstract Boolean isPasswordCorrect(String password);

	public abstract String getName();

	public abstract Boolean isActive();

}