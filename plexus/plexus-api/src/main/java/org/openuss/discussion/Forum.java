package org.openuss.discussion;

import java.util.Set;

/**
 * @author Ingo Dueppe
 */
public interface Forum extends org.openuss.foundation.DomainObject {

	public Long getId();

	public void setId(Long id);

	public Long getDomainIdentifier();

	public void setDomainIdentifier(Long domainIdentifier);

	public boolean isReadOnly();

	public void setReadOnly(boolean readOnly);

	public Set<org.openuss.discussion.Topic> getTopics();

	public void setTopics(Set<org.openuss.discussion.Topic> topics);

	public abstract void addTopic(org.openuss.discussion.Topic topic);

	public abstract void removeTopic(org.openuss.discussion.Topic topic);

}