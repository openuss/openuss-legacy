package org.openuss.paperSubmission;

import java.util.Date;
import java.util.List;

/**
 * the valueobject contains informations about the Exams
 * 
 * @author Projektseminar WS 07/08, Team Collaboration
 */
public class ExamInfo implements java.io.Serializable, org.openuss.foundation.DomainObject {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 8630068172245143833L;

	public ExamInfo() {
		this.id = null;
		this.domainId = null;
		this.name = null;
		this.deadline = null;
		this.description = null;
	}

	public ExamInfo(Long id, Long domainId, String name, Date deadline, String description) {
		this.id = id;
		this.domainId = domainId;
		this.name = name;
		this.deadline = deadline;
		this.description = description;
	}

	public ExamInfo(Long id, Long domainId, String name, Date deadline, String description,
			List<org.openuss.documents.FileInfo> attachments) {
		this.id = id;
		this.domainId = domainId;
		this.name = name;
		this.deadline = deadline;
		this.description = description;
		this.attachments = attachments;
	}

	/**
	 * Copies constructor from other ExamInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public ExamInfo(ExamInfo otherBean) {
		this(otherBean.getId(), otherBean.getDomainId(), otherBean.getName(), otherBean.getDeadline(), otherBean
				.getDescription(), otherBean.getAttachments());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(ExamInfo otherBean) {
		this.setId(otherBean.getId());
		this.setDomainId(otherBean.getDomainId());
		this.setName(otherBean.getName());
		this.setDeadline(otherBean.getDeadline());
		this.setDescription(otherBean.getDescription());
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

	private Long domainId;

	/**
     * 
     */
	public Long getDomainId() {
		return this.domainId;
	}

	public void setDomainId(Long domainId) {
		this.domainId = domainId;
	}

	private String name;

	/**
     * 
     */
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private Date deadline;

	/**
     * 
     */
	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	private String description;

	/**
     * 
     */
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	 * Returns <code>true</code> if the argument is an ExamInfo instance and all
	 * identifiers for this object equal the identifiers of the argument object.
	 * Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof ExamInfo)) {
			return false;
		}
		final ExamInfo that = (ExamInfo) object;
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