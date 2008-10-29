package org.openuss.paperSubmission;

import java.util.Date;

/**
 * the valueobject contains informations about the PaperSubmissions
 * 
 * @author Projektseminar WS 07/08, Team Collaboration
 */
public class PaperSubmissionInfo implements java.io.Serializable, org.openuss.foundation.DomainObject {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 4466234841506106960L;

	public PaperSubmissionInfo() {
		this.id = null;
		this.deliverDate = null;
		this.examId = null;
		this.userId = null;
		this.submissionStatus = null;
		this.firstName = null;
		this.lastName = null;
		this.displayName = null;
	}

	public PaperSubmissionInfo(Long id, Date deliverDate, Long examId, Long userId,
			org.openuss.paperSubmission.SubmissionStatus submissionStatus, String firstName, String lastName,
			String displayName) {
		this.id = id;
		this.deliverDate = deliverDate;
		this.examId = examId;
		this.userId = userId;
		this.submissionStatus = submissionStatus;
		this.firstName = firstName;
		this.lastName = lastName;
		this.displayName = displayName;
	}

	public PaperSubmissionInfo(Long id, Date deliverDate, Long examId, Long userId,
			org.openuss.paperSubmission.SubmissionStatus submissionStatus, String firstName, String lastName,
			String displayName, String comment) {
		this.id = id;
		this.deliverDate = deliverDate;
		this.examId = examId;
		this.userId = userId;
		this.submissionStatus = submissionStatus;
		this.firstName = firstName;
		this.lastName = lastName;
		this.displayName = displayName;
		this.comment = comment;
	}

	/**
	 * Copies constructor from other PaperSubmissionInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public PaperSubmissionInfo(PaperSubmissionInfo otherBean) {
		this(otherBean.getId(), otherBean.getDeliverDate(), otherBean.getExamId(), otherBean.getUserId(), otherBean
				.getSubmissionStatus(), otherBean.getFirstName(), otherBean.getLastName(), otherBean.getDisplayName(),
				otherBean.getComment());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(PaperSubmissionInfo otherBean) {
		this.setId(otherBean.getId());
		this.setDeliverDate(otherBean.getDeliverDate());
		this.setExamId(otherBean.getExamId());
		this.setUserId(otherBean.getUserId());
		this.setSubmissionStatus(otherBean.getSubmissionStatus());
		this.setFirstName(otherBean.getFirstName());
		this.setLastName(otherBean.getLastName());
		this.setDisplayName(otherBean.getDisplayName());
		this.setComment(otherBean.getComment());
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

	private Date deliverDate;

	/**
     * 
     */
	public Date getDeliverDate() {
		return this.deliverDate;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	private Long examId;

	/**
     * 
     */
	public Long getExamId() {
		return this.examId;
	}

	public void setExamId(Long examId) {
		this.examId = examId;
	}

	private Long userId;

	/**
     * 
     */
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	private org.openuss.paperSubmission.SubmissionStatus submissionStatus;

	/**
     * 
     */
	public org.openuss.paperSubmission.SubmissionStatus getSubmissionStatus() {
		return this.submissionStatus;
	}

	public void setSubmissionStatus(org.openuss.paperSubmission.SubmissionStatus submissionStatus) {
		this.submissionStatus = submissionStatus;
	}

	private String firstName;

	/**
     * 
     */
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	private String lastName;

	/**
     * 
     */
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	private String displayName;

	/**
     * 
     */
	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	private String comment;

	/**
     * 
     */
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Returns <code>true</code> if the argument is an PaperSubmissionInfo
	 * instance and all identifiers for this object equal the identifiers of the
	 * argument object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof PaperSubmissionInfo)) {
			return false;
		}
		final PaperSubmissionInfo that = (PaperSubmissionInfo) object;
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