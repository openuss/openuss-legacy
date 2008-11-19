package org.openuss.lecture;

/**
 * An course is a specific courseType that take place within a defined period of
 * time (like a specific semester) by one institute.
 */
public abstract class CourseBase implements Course, java.io.Serializable {

	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -685954586268523283L;

	private java.lang.Long id;

	/**
	 * @see org.openuss.lecture.Course#getId()
	 */
	public java.lang.Long getId() {
		return this.id;
	}

	/**
	 * @see org.openuss.lecture.Course#setId(java.lang.Long id)
	 */
	public void setId(java.lang.Long id) {
		this.id = id;
	}

	private java.lang.String shortcut;

	/**
	 * @see org.openuss.lecture.Course#getShortcut()
	 */
	public java.lang.String getShortcut() {
		return this.shortcut;
	}

	/**
	 * @see org.openuss.lecture.Course#setShortcut(java.lang.String shortcut)
	 */
	public void setShortcut(java.lang.String shortcut) {
		this.shortcut = shortcut;
	}

	private org.openuss.lecture.AccessType accessType = org.openuss.lecture.AccessType.CLOSED;

	/**
	 * @see org.openuss.lecture.Course#getAccessType()
	 */
	public org.openuss.lecture.AccessType getAccessType() {
		return this.accessType;
	}

	/**
	 * @see org.openuss.lecture.Course#setAccessType(org.openuss.lecture.AccessType
	 *      accessType)
	 */
	public void setAccessType(org.openuss.lecture.AccessType accessType) {
		this.accessType = accessType;
	}

	private java.lang.String password;

	/**
	 * @see org.openuss.lecture.Course#getPassword()
	 */
	public java.lang.String getPassword() {
		return this.password;
	}

	/**
	 * @see org.openuss.lecture.Course#setPassword(java.lang.String password)
	 */
	public void setPassword(java.lang.String password) {
		this.password = password;
	}

	private java.lang.Boolean documents = java.lang.Boolean.valueOf(true);

	/**
	 * @see org.openuss.lecture.Course#getDocuments()
	 */
	public java.lang.Boolean getDocuments() {
		return this.documents;
	}

	/**
	 * @see org.openuss.lecture.Course#setDocuments(java.lang.Boolean documents)
	 */
	public void setDocuments(java.lang.Boolean documents) {
		this.documents = documents;
	}

	private java.lang.Boolean discussion = java.lang.Boolean.valueOf(true);

	/**
	 * @see org.openuss.lecture.Course#getDiscussion()
	 */
	public java.lang.Boolean getDiscussion() {
		return this.discussion;
	}

	/**
	 * @see org.openuss.lecture.Course#setDiscussion(java.lang.Boolean
	 *      discussion)
	 */
	public void setDiscussion(java.lang.Boolean discussion) {
		this.discussion = discussion;
	}

	private java.lang.Boolean newsletter = java.lang.Boolean.valueOf(true);

	/**
	 * @see org.openuss.lecture.Course#getNewsletter()
	 */
	public java.lang.Boolean getNewsletter() {
		return this.newsletter;
	}

	/**
	 * @see org.openuss.lecture.Course#setNewsletter(java.lang.Boolean
	 *      newsletter)
	 */
	public void setNewsletter(java.lang.Boolean newsletter) {
		this.newsletter = newsletter;
	}

	private java.lang.Boolean chat = java.lang.Boolean.valueOf(false);

	/**
	 * @see org.openuss.lecture.Course#getChat()
	 */
	public java.lang.Boolean getChat() {
		return this.chat;
	}

	/**
	 * @see org.openuss.lecture.Course#setChat(java.lang.Boolean chat)
	 */
	public void setChat(java.lang.Boolean chat) {
		this.chat = chat;
	}

	private java.lang.Boolean freestylelearning = java.lang.Boolean.valueOf(false);

	/**
	 * @see org.openuss.lecture.Course#getFreestylelearning()
	 */
	public java.lang.Boolean getFreestylelearning() {
		return this.freestylelearning;
	}

	/**
	 * @see org.openuss.lecture.Course#setFreestylelearning(java.lang.Boolean
	 *      freestylelearning)
	 */
	public void setFreestylelearning(java.lang.Boolean freestylelearning) {
		this.freestylelearning = freestylelearning;
	}

	private java.lang.Boolean braincontest = java.lang.Boolean.valueOf(false);

	/**
	 * @see org.openuss.lecture.Course#getBraincontest()
	 */
	public java.lang.Boolean getBraincontest() {
		return this.braincontest;
	}

	/**
	 * @see org.openuss.lecture.Course#setBraincontest(java.lang.Boolean
	 *      braincontest)
	 */
	public void setBraincontest(java.lang.Boolean braincontest) {
		this.braincontest = braincontest;
	}

	private java.lang.Boolean collaboration = java.lang.Boolean.valueOf(false);

	/**
	 * @see org.openuss.lecture.Course#getCollaboration()
	 */
	public java.lang.Boolean getCollaboration() {
		return this.collaboration;
	}

	/**
	 * @see org.openuss.lecture.Course#setCollaboration(java.lang.Boolean
	 *      collaboration)
	 */
	public void setCollaboration(java.lang.Boolean collaboration) {
		this.collaboration = collaboration;
	}

	private java.lang.Boolean papersubmission = java.lang.Boolean.valueOf(false);

	/**
	 * @see org.openuss.lecture.Course#getPapersubmission()
	 */
	public java.lang.Boolean getPapersubmission() {
		return this.papersubmission;
	}

	/**
	 * @see org.openuss.lecture.Course#setPapersubmission(java.lang.Boolean
	 *      papersubmission)
	 */
	public void setPapersubmission(java.lang.Boolean papersubmission) {
		this.papersubmission = papersubmission;
	}

	private java.lang.Boolean wiki = java.lang.Boolean.valueOf(false);

	/**
	 * @see org.openuss.lecture.Course#getWiki()
	 */
	public java.lang.Boolean getWiki() {
		return this.wiki;
	}

	/**
	 * @see org.openuss.lecture.Course#setWiki(java.lang.Boolean wiki)
	 */
	public void setWiki(java.lang.Boolean wiki) {
		this.wiki = wiki;
	}

	private java.lang.String description;

	/**
	 * @see org.openuss.lecture.Course#getDescription()
	 */
	public java.lang.String getDescription() {
		return this.description;
	}

	/**
	 * @see org.openuss.lecture.Course#setDescription(java.lang.String
	 *      description)
	 */
	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	private boolean enabled = true;

	/**
	 * @see org.openuss.lecture.Course#isEnabled()
	 */
	public boolean isEnabled() {
		return this.enabled;
	}

	/**
	 * @see org.openuss.lecture.Course#setEnabled(boolean enabled)
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	private org.openuss.lecture.CourseType courseType;

	/**
     * 
     */
	public org.openuss.lecture.CourseType getCourseType() {
		return this.courseType;
	}

	public void setCourseType(org.openuss.lecture.CourseType courseType) {
		this.courseType = courseType;
	}

	private org.openuss.lecture.Period period;

	/**
     * 
     */
	public org.openuss.lecture.Period getPeriod() {
		return this.period;
	}

	public void setPeriod(org.openuss.lecture.Period period) {
		this.period = period;
	}

	private java.util.Set<org.openuss.security.Group> groups = new java.util.HashSet<org.openuss.security.Group>();

	/**
     * 
     */
	public java.util.Set<org.openuss.security.Group> getGroups() {
		return this.groups;
	}

	public void setGroups(java.util.Set<org.openuss.security.Group> groups) {
		this.groups = groups;
	}

	/**
     * 
     */
	public abstract java.lang.Boolean isPasswordCorrect(java.lang.String password);

	/**
     * 
     */
	public abstract java.lang.String getName();

	/**
     * 
     */
	public abstract java.lang.Boolean isActive();

	/**
	 * Returns <code>true</code> if the argument is an Course instance and all
	 * identifiers for this entity equal the identifiers of the argument entity.
	 * Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof Course)) {
			return false;
		}
		final Course that = (Course) object;
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