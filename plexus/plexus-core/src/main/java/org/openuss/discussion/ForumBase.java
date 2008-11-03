package org.openuss.discussion;

import java.io.Serializable;

/**
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 */
public abstract class ForumBase implements Forum, Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 781295000319312230L;

	private java.lang.Long id;
	
	private String name;
	
	/**
	 * @see org.openuss.discussion.Forum#getName()
	 */
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 * @see org.openuss.discussion.Forum#setName(java.lang.String name)
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	
	/**
	 * @see org.openuss.discussion.Forum#getId()
	 */
	public java.lang.Long getId() {
		return this.id;
	}

	/**
	 * @see org.openuss.discussion.Forum#setId(java.lang.Long id)
	 */
	public void setId(java.lang.Long id) {
		this.id = id;
	}

	private java.lang.Long domainIdentifier;

	/**
	 * @see org.openuss.discussion.Forum#getDomainIdentifier()
	 */
	public java.lang.Long getDomainIdentifier() {
		return this.domainIdentifier;
	}

	/**
	 * @see org.openuss.discussion.Forum#setDomainIdentifier(java.lang.Long
	 *      domainIdentifier)
	 */
	public void setDomainIdentifier(java.lang.Long domainIdentifier) {
		this.domainIdentifier = domainIdentifier;
	}

	private boolean readOnly;

	/**
	 * @see org.openuss.discussion.Forum#isReadOnly()
	 */
	public boolean isReadOnly() {
		return this.readOnly;
	}

	/**
	 * @see org.openuss.discussion.Forum#setReadOnly(boolean readOnly)
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	private java.util.Set<org.openuss.discussion.Topic> topics = new java.util.HashSet<org.openuss.discussion.Topic>();

	/**
     * 
     */
	public java.util.Set<org.openuss.discussion.Topic> getTopics() {
		return this.topics;
	}

	public void setTopics(java.util.Set<org.openuss.discussion.Topic> topics) {
		this.topics = topics;
	}

	/**
     * 
     */
	public abstract void addTopic(org.openuss.discussion.Topic topic);

	/**
     * 
     */
	public abstract void removeTopic(org.openuss.discussion.Topic topic);

	/**
	 * Returns <code>true</code> if the argument is an Forum instance and all
	 * identifiers for this entity equal the identifiers of the argument entity.
	 * Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof Forum)) {
			return false;
		}
		final Forum that = (Forum) object;
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