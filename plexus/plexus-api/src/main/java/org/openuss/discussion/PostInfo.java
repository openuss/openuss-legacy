package org.openuss.discussion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.openuss.foundation.DomainObject;

/**
 * @author Ingo Dueppe
 */
public class PostInfo implements Serializable, DomainObject {

	private static final long serialVersionUID = -7275024728151180579L;

	public PostInfo() {
		this.id = null;
		this.title = null;
		this.text = null;
		this.created = null;
		this.lastModification = null;
		this.formula = null;
		this.isEdited = false;
		this.editor = null;
		this.submitter = null;
		this.submitterId = null;
		this.submitterPicture = null;
		this.ip = null;
		this.userIsSubmitter = false;
		this.topicId = null;
	}

	public PostInfo(Long id, String title, String text, Date created, Date lastModification, String formula,
			boolean isEdited, String editor, String submitter, Long submitterId, Long submitterPicture, String ip,
			boolean userIsSubmitter, Long topicId) {
		this.id = id;
		this.title = title;
		this.text = text;
		this.created = created;
		this.lastModification = lastModification;
		this.formula = formula;
		this.isEdited = isEdited;
		this.editor = editor;
		this.submitter = submitter;
		this.submitterId = submitterId;
		this.submitterPicture = submitterPicture;
		this.ip = ip;
		this.userIsSubmitter = userIsSubmitter;
		this.topicId = topicId;
	}

	public PostInfo(Long id, String title, String text, Date created, Date lastModification, String formula,
			boolean isEdited, String editor, String submitter, Long submitterId, Long submitterPicture, String ip,
			boolean userIsSubmitter, Long topicId, List<org.openuss.documents.FileInfo> attachments) {
		this.id = id;
		this.title = title;
		this.text = text;
		this.created = created;
		this.lastModification = lastModification;
		this.formula = formula;
		this.isEdited = isEdited;
		this.editor = editor;
		this.submitter = submitter;
		this.submitterId = submitterId;
		this.submitterPicture = submitterPicture;
		this.ip = ip;
		this.userIsSubmitter = userIsSubmitter;
		this.topicId = topicId;
		this.attachments = attachments;
	}

	/**
	 * Copies constructor from other PostInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public PostInfo(PostInfo otherBean) {
		this(otherBean.getId(), otherBean.getTitle(), otherBean.getText(), otherBean.getCreated(), otherBean
				.getLastModification(), otherBean.getFormula(), otherBean.isIsEdited(), otherBean.getEditor(),
				otherBean.getSubmitter(), otherBean.getSubmitterId(), otherBean.getSubmitterPicture(), otherBean
						.getIp(), otherBean.isUserIsSubmitter(), otherBean.getTopicId(), otherBean.getAttachments());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(PostInfo otherBean) {
		this.setId(otherBean.getId());
		this.setTitle(otherBean.getTitle());
		this.setText(otherBean.getText());
		this.setCreated(otherBean.getCreated());
		this.setLastModification(otherBean.getLastModification());
		this.setFormula(otherBean.getFormula());
		this.setIsEdited(otherBean.isIsEdited());
		this.setEditor(otherBean.getEditor());
		this.setSubmitter(otherBean.getSubmitter());
		this.setSubmitterId(otherBean.getSubmitterId());
		this.setSubmitterPicture(otherBean.getSubmitterPicture());
		this.setIp(otherBean.getIp());
		this.setUserIsSubmitter(otherBean.isUserIsSubmitter());
		this.setTopicId(otherBean.getTopicId());
		this.setAttachments(otherBean.getAttachments());
	}

	private Long id;

	/**
     * 
     */
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private String title;

	/**
     * 
     */
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private String text;

	/**
     * 
     */
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	private Date created;

	/**
     * 
     */
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	private Date lastModification;

	/**
     * 
     */
	public Date getLastModification() {
		return this.lastModification;
	}

	public void setLastModification(Date lastModification) {
		this.lastModification = lastModification;
	}

	private String formula;

	/**
     * 
     */
	public String getFormula() {
		return this.formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	private boolean isEdited;

	/**
     * 
     */
	public boolean isIsEdited() {
		return this.isEdited;
	}

	public void setIsEdited(boolean isEdited) {
		this.isEdited = isEdited;
	}

	private String editor;

	/**
     * 
     */
	public String getEditor() {
		return this.editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	private String submitter;

	/**
     * 
     */
	public String getSubmitter() {
		return this.submitter;
	}

	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}

	private Long submitterId;

	/**
     * 
     */
	public Long getSubmitterId() {
		return this.submitterId;
	}

	public void setSubmitterId(Long submitterId) {
		this.submitterId = submitterId;
	}

	private Long submitterPicture;

	/**
     * 
     */
	public Long getSubmitterPicture() {
		return this.submitterPicture;
	}

	public void setSubmitterPicture(Long submitterPicture) {
		this.submitterPicture = submitterPicture;
	}

	private String ip;

	/**
     * 
     */
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	private boolean userIsSubmitter;

	/**
     * 
     */
	public boolean isUserIsSubmitter() {
		return this.userIsSubmitter;
	}

	public void setUserIsSubmitter(boolean userIsSubmitter) {
		this.userIsSubmitter = userIsSubmitter;
	}

	private Long topicId;

	/**
     * 
     */
	public Long getTopicId() {
		return this.topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	private List<org.openuss.documents.FileInfo> attachments;

	/**
	 * Get the attachments
	 * 
	 */
	public List<org.openuss.documents.FileInfo> getAttachments() {
		return this.attachments;
	}

	/**
	 * Sets the attachments
	 */
	public void setAttachments(List<org.openuss.documents.FileInfo> attachments) {
		this.attachments = attachments;
	}

	/**
	 * Returns <code>true</code> if the argument is an PostInfo instance and all
	 * identifiers for this object equal the identifiers of the argument object.
	 * Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof PostInfo)) {
			return false;
		}
		final PostInfo that = (PostInfo) object;
		if (this.id == null || that.getId() == null || !this.id.equals(that.getId())) {
			return false;
		}

		return true;
	}

	/**
	 * Returns a hash code based on this entity's identifiers.
	 */
	public int hashCode() {
		int hashCode = 0;
		hashCode = 29 * hashCode + (id == null ? 0 : id.hashCode());

		return hashCode;
	}

}