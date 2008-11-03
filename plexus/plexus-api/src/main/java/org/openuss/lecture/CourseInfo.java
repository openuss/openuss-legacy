package org.openuss.lecture;

/**
 * 
 */
public class CourseInfo implements java.io.Serializable, org.openuss.foundation.NamedDomainObject {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 4436736148187870273L;

	public CourseInfo() {
		this.id = null;
		this.periodId = null;
		this.periodName = null;
		this.courseTypeId = null;
		this.courseTypeDescription = null;
		this.name = null;
		this.shortcut = null;
		this.description = null;
		this.password = null;
		this.accessType = null;
		this.braincontest = false;
		this.freestylelearning = false;
		this.chat = false;
		this.newsletter = false;
		this.discussion = false;
		this.collaboration = false;
		this.papersubmission = false;
		this.wiki = false;
		this.documents = false;
		this.enabled = false;
		this.instituteId = null;
		this.instituteName = null;
	}

	public CourseInfo(Long id, Long periodId, String periodName, Long courseTypeId, String courseTypeDescription,
			String name, String shortcut, String description, String password,
			org.openuss.lecture.AccessType accessType, boolean braincontest, boolean freestylelearning, boolean chat,
			boolean newsletter, boolean discussion, boolean collaboration, boolean papersubmission, boolean wiki,
			boolean documents, boolean enabled, Long instituteId, String instituteName) {
		this.id = id;
		this.periodId = periodId;
		this.periodName = periodName;
		this.courseTypeId = courseTypeId;
		this.courseTypeDescription = courseTypeDescription;
		this.name = name;
		this.shortcut = shortcut;
		this.description = description;
		this.password = password;
		this.accessType = accessType;
		this.braincontest = braincontest;
		this.freestylelearning = freestylelearning;
		this.chat = chat;
		this.newsletter = newsletter;
		this.discussion = discussion;
		this.collaboration = collaboration;
		this.papersubmission = papersubmission;
		this.wiki = wiki;
		this.documents = documents;
		this.enabled = enabled;
		this.instituteId = instituteId;
		this.instituteName = instituteName;
	}

	/**
	 * Copies constructor from other CourseInfo
	 * 
	 * @param otherBean
	 *            , cannot be <code>null</code>
	 * @throws NullPointerException
	 *             if the argument is <code>null</code>
	 */
	public CourseInfo(CourseInfo otherBean) {
		this(otherBean.getId(), otherBean.getPeriodId(), otherBean.getPeriodName(), otherBean.getCourseTypeId(),
				otherBean.getCourseTypeDescription(), otherBean.getName(), otherBean.getShortcut(), otherBean
						.getDescription(), otherBean.getPassword(), otherBean.getAccessType(), otherBean
						.isBraincontest(), otherBean.isFreestylelearning(), otherBean.isChat(), otherBean
						.isNewsletter(), otherBean.isDiscussion(), otherBean.isCollaboration(), otherBean
						.isPapersubmission(), otherBean.isWiki(), otherBean.isDocuments(), otherBean.isEnabled(),
				otherBean.getInstituteId(), otherBean.getInstituteName());
	}

	/**
	 * Copies all properties from the argument value object into this value
	 * object.
	 */
	public void copy(CourseInfo otherBean) {
		this.setId(otherBean.getId());
		this.setPeriodId(otherBean.getPeriodId());
		this.setPeriodName(otherBean.getPeriodName());
		this.setCourseTypeId(otherBean.getCourseTypeId());
		this.setCourseTypeDescription(otherBean.getCourseTypeDescription());
		this.setName(otherBean.getName());
		this.setShortcut(otherBean.getShortcut());
		this.setDescription(otherBean.getDescription());
		this.setPassword(otherBean.getPassword());
		this.setAccessType(otherBean.getAccessType());
		this.setBraincontest(otherBean.isBraincontest());
		this.setFreestylelearning(otherBean.isFreestylelearning());
		this.setChat(otherBean.isChat());
		this.setNewsletter(otherBean.isNewsletter());
		this.setDiscussion(otherBean.isDiscussion());
		this.setCollaboration(otherBean.isCollaboration());
		this.setPapersubmission(otherBean.isPapersubmission());
		this.setWiki(otherBean.isWiki());
		this.setDocuments(otherBean.isDocuments());
		this.setEnabled(otherBean.isEnabled());
		this.setInstituteId(otherBean.getInstituteId());
		this.setInstituteName(otherBean.getInstituteName());
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

	private Long periodId;

	/**
     * 
     */
	public Long getPeriodId() {
		return this.periodId;
	}

	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	private String periodName;

	/**
     * 
     */
	public String getPeriodName() {
		return this.periodName;
	}

	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}

	private Long courseTypeId;

	/**
     * 
     */
	public Long getCourseTypeId() {
		return this.courseTypeId;
	}

	public void setCourseTypeId(Long courseTypeId) {
		this.courseTypeId = courseTypeId;
	}

	private String courseTypeDescription;

	/**
	 * <p>
	 * Description of course type
	 * </p>
	 */
	public String getCourseTypeDescription() {
		return this.courseTypeDescription;
	}

	public void setCourseTypeDescription(String courseTypeDescription) {
		this.courseTypeDescription = courseTypeDescription;
	}

	private String name;

	/**
	 * <p>
	 * name of the course (courseType.name)
	 * </p>
	 */
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String shortcut;

	/**
     * 
     */
	public String getShortcut() {
		return this.shortcut;
	}

	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
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

	private String password;

	/**
     * 
     */
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private org.openuss.lecture.AccessType accessType;

	/**
	 * <p>
	 * Defines the access type of this course.
	 * </p>
	 * <p>
	 * 
	 * @See org.openuss.lecture.AccessType
	 *      </p>
	 */
	public org.openuss.lecture.AccessType getAccessType() {
		return this.accessType;
	}

	public void setAccessType(org.openuss.lecture.AccessType accessType) {
		this.accessType = accessType;
	}

	private boolean braincontest;

	/**
     * 
     */
	public boolean isBraincontest() {
		return this.braincontest;
	}

	public void setBraincontest(boolean braincontest) {
		this.braincontest = braincontest;
	}

	private boolean freestylelearning;

	/**
     * 
     */
	public boolean isFreestylelearning() {
		return this.freestylelearning;
	}

	public void setFreestylelearning(boolean freestylelearning) {
		this.freestylelearning = freestylelearning;
	}

	private boolean chat;

	/**
     * 
     */
	public boolean isChat() {
		return this.chat;
	}

	public void setChat(boolean chat) {
		this.chat = chat;
	}

	private boolean newsletter;

	/**
     * 
     */
	public boolean isNewsletter() {
		return this.newsletter;
	}

	public void setNewsletter(boolean newsletter) {
		this.newsletter = newsletter;
	}

	private boolean discussion;

	/**
     * 
     */
	public boolean isDiscussion() {
		return this.discussion;
	}

	public void setDiscussion(boolean discussion) {
		this.discussion = discussion;
	}

	private boolean collaboration;

	/**
     * 
     */
	public boolean isCollaboration() {
		return this.collaboration;
	}

	public void setCollaboration(boolean collaboration) {
		this.collaboration = collaboration;
	}

	private boolean papersubmission;

	/**
     * 
     */
	public boolean isPapersubmission() {
		return this.papersubmission;
	}

	public void setPapersubmission(boolean papersubmission) {
		this.papersubmission = papersubmission;
	}

	private boolean wiki;

	/**
     * 
     */
	public boolean isWiki() {
		return this.wiki;
	}

	public void setWiki(boolean wiki) {
		this.wiki = wiki;
	}

	private boolean documents;

	/**
     * 
     */
	public boolean isDocuments() {
		return this.documents;
	}

	public void setDocuments(boolean documents) {
		this.documents = documents;
	}

	private boolean enabled = true;

	/**
     * 
     */
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	private Long instituteId;

	/**
     * 
     */
	public Long getInstituteId() {
		return this.instituteId;
	}

	public void setInstituteId(Long instituteId) {
		this.instituteId = instituteId;
	}

	private String instituteName;

	/**
     * 
     */
	public String getInstituteName() {
		return this.instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	/**
	 * Returns <code>true</code> if the argument is an CourseInfo instance and
	 * all identifiers for this object equal the identifiers of the argument
	 * object. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof CourseInfo)) {
			return false;
		}
		final CourseInfo that = (CourseInfo) object;
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