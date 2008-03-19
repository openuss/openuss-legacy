// OpenUSS - Open Source University Support System
//
// Attention: Generated code! Do not modify by hand!
// Generated by: HibernateEntityImpl.vsl in in openuss/tools/andromda/templates.
//
package org.openuss.discussion;

import org.apache.commons.lang.Validate;

/**
 * @author ingo dueppe
 * @author sebasitan roekens
 * @see org.openuss.discussion.Forum
 */
public class ForumImpl extends ForumBase implements Forum {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -2096584781367256187L;

	@Override
	public void addTopic(final Topic topic) {
		Validate.notNull(topic, "Parameter topic must not be null.");
		getTopics().add(topic);
		topic.setForum(this);
	}

	@Override
	public void removeTopic(final Topic topic) {
		Validate.notNull(topic, "Parameter topic must not be null");
		getTopics().remove(topic);
		if (topic.getForum().equals(this)) {
			topic.setForum(null);
		}
	}

}