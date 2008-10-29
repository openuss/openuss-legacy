package org.openuss.discussion;

import java.util.Date;

import org.openuss.foundation.DomainObject;

/**
 * @author Ingo Dueppe
 */
public interface Post extends DomainObject {

	public String getTitle();

	public void setTitle(String title);

	public String getText();

	public void setText(String text);

	public Date getCreated();

	public void setCreated(Date created);

	public Date getLastModification();

	public void setLastModification(Date lastModification);

	public Long getId();

	public void setId(Long id);

	public String getIp();

	public void setIp(String ip);

	public org.openuss.security.User getSubmitter();

	public void setSubmitter(org.openuss.security.User submitter);

	public org.openuss.discussion.Topic getTopic();

	public void setTopic(org.openuss.discussion.Topic topic);

	public org.openuss.discussion.Formula getFormula();

	public void setFormula(org.openuss.discussion.Formula formula);

	public org.openuss.security.User getEditor();

	public void setEditor(org.openuss.security.User editor);

	public String getSubmitterName();

	public String getEditorName();

	public boolean isEdited();

	public String getFormulaString();

	public void setFormulaString(String formula);

	public Long getSubmitterPicture();

}