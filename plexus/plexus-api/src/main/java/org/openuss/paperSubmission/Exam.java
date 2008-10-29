package org.openuss.paperSubmission;

import java.util.Collection;
import java.util.Date;

/**
 * Exams can have many paperSubmissions and are attached to a course.
 * The have a name, a deadline and a description
 * 
 * @author Projektseminar WS 07/08, Team Collaboration
 */
public interface Exam extends org.openuss.foundation.DomainObject {

	public Long getId();

	public void setId(Long id);

	public Long getDomainId();

	public void setDomainId(Long domainId);

	public String getName();

	public void setName(String name);

	public Date getDeadline();

	public void setDeadline(Date deadline);

	public String getDescription();

	public void setDescription(String description);

	public Collection<PaperSubmission> getPapersubmissions();

	public void setPapersubmissions(Collection<PaperSubmission> papersubmissions);

}