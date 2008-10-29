package org.openuss.braincontest;

/**
 * 
 */
public abstract class BrainContestBase implements BrainContest, java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -6214729864973555510L;

	private java.lang.Long id;

	/**
	 * @see org.openuss.braincontest.BrainContest#getId()
	 */
	public java.lang.Long getId() {
		return this.id;
	}

	/**
	 * @see org.openuss.braincontest.BrainContest#setId(java.lang.Long id)
	 */
	public void setId(java.lang.Long id) {
		this.id = id;
	}

	private java.lang.Long domainIdentifier;

	/**
	 * @see org.openuss.braincontest.BrainContest#getDomainIdentifier()
	 */
	public java.lang.Long getDomainIdentifier() {
		return this.domainIdentifier;
	}

	/**
	 * @see org.openuss.braincontest.BrainContest#setDomainIdentifier(java.lang.Long
	 *      domainIdentifier)
	 */
	public void setDomainIdentifier(java.lang.Long domainIdentifier) {
		this.domainIdentifier = domainIdentifier;
	}

	private java.util.Date releaseDate;

	/**
	 * @see org.openuss.braincontest.BrainContest#getReleaseDate()
	 */
	public java.util.Date getReleaseDate() {
		return this.releaseDate;
	}

	/**
	 * @see org.openuss.braincontest.BrainContest#setReleaseDate(java.util.Date
	 *      releaseDate)
	 */
	public void setReleaseDate(java.util.Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	private java.lang.String description;

	/**
	 * @see org.openuss.braincontest.BrainContest#getDescription()
	 */
	public java.lang.String getDescription() {
		return this.description;
	}

	/**
	 * @see org.openuss.braincontest.BrainContest#setDescription(java.lang.String
	 *      description)
	 */
	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	private java.lang.String title;

	/**
	 * @see org.openuss.braincontest.BrainContest#getTitle()
	 */
	public java.lang.String getTitle() {
		return this.title;
	}

	/**
	 * @see org.openuss.braincontest.BrainContest#setTitle(java.lang.String
	 *      title)
	 */
	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	private java.lang.Integer tries = java.lang.Integer.valueOf(0);

	/**
	 * @see org.openuss.braincontest.BrainContest#getTries()
	 */
	public java.lang.Integer getTries() {
		return this.tries;
	}

	/**
	 * @see org.openuss.braincontest.BrainContest#setTries(java.lang.Integer
	 *      tries)
	 */
	public void setTries(java.lang.Integer tries) {
		this.tries = tries;
	}

	private java.lang.String solution;

	/**
	 * @see org.openuss.braincontest.BrainContest#getSolution()
	 */
	public java.lang.String getSolution() {
		return this.solution;
	}

	/**
	 * @see org.openuss.braincontest.BrainContest#setSolution(java.lang.String
	 *      solution)
	 */
	public void setSolution(java.lang.String solution) {
		this.solution = solution;
	}

	private java.util.List<org.openuss.braincontest.Answer> answers = new java.util.ArrayList<org.openuss.braincontest.Answer>();

	/**
     * 
     */
	public java.util.List<org.openuss.braincontest.Answer> getAnswers() {
		return this.answers;
	}

	public void setAnswers(java.util.List<org.openuss.braincontest.Answer> answers) {
		this.answers = answers;
	}

	/**
     * 
     */
	public abstract boolean isReleased();

	/**
     * 
     */
	public abstract java.lang.Integer getAnswersCount();

	/**
     * 
     */
	public abstract void addAnswer(org.openuss.braincontest.Answer answer);

	/**
     * 
     */
	public abstract boolean validateAnswer(java.lang.String answer);

	/**
	 * Returns <code>true</code> if the argument is an BrainContest instance and
	 * all identifiers for this entity equal the identifiers of the argument
	 * entity. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof BrainContest)) {
			return false;
		}
		final BrainContest that = (BrainContest) object;
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