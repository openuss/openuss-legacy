// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.discussion;

import java.util.List;

/**
 * @see org.openuss.discussion.Topic
 */
public class TopicDaoImpl extends org.openuss.discussion.TopicDaoBase {
	/**
	 * @see org.openuss.discussion.TopicDao#toTopicInfo(org.openuss.discussion.Topic,
	 *      org.openuss.discussion.TopicInfo)
	 */
	public void toTopicInfo(Topic sourceEntity, TopicInfo targetVO) {
		super.toTopicInfo(sourceEntity, targetVO);
		if (sourceEntity.getForum() != null || sourceEntity.getId() != null)
			targetVO.setForumId(sourceEntity.getForum().getId());
		if (sourceEntity.getFirst() != null) {
			if (sourceEntity.getFirst().getTitle() != null)
				targetVO.setTitle(sourceEntity.getFirst().getTitle());
			if (sourceEntity.getFirst().getCreated() != null)
				targetVO.setCreated(sourceEntity.getFirst().getCreated());
			if (sourceEntity.getFirst().getSubmitterName() != null)
				targetVO.setSubmitter(sourceEntity.getFirst().getSubmitterName());
		}
		if (sourceEntity.getLastPostDate() != null)
			targetVO.setLastPost(sourceEntity.getLastPostDate());
		if (sourceEntity.getLastPostSubmitterName() != null)
			targetVO.setLastPostSubmitterName(sourceEntity.getLastPostSubmitterName());
		if (sourceEntity.getAnswerCount() != null)
			targetVO.setAnswerCount(sourceEntity.getAnswerCount());
	}

	/**
	 * @see org.openuss.discussion.TopicDao#toTopicInfo(org.openuss.discussion.Topic)
	 */
	public TopicInfo toTopicInfo(final Topic entity) {
		final TopicInfo target = new TopicInfo();
		this.toTopicInfo(entity, target);
		return target;
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private Topic loadTopicFromTopicInfo(TopicInfo topicInfo) {
		if (topicInfo.getId() == null) {
			return Topic.Factory.newInstance();
		}
		return this.load(topicInfo.getId());
	}

	/**
	 * @see org.openuss.discussion.TopicDao#topicInfoToEntity(org.openuss.discussion.TopicInfo)
	 */
	public Topic topicInfoToEntity(TopicInfo topicInfo) {
		org.openuss.discussion.Topic entity = this.loadTopicFromTopicInfo(topicInfo);
		this.topicInfoToEntity(topicInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.discussion.TopicDao#topicInfoToEntity(org.openuss.discussion.TopicInfo,
	 *      org.openuss.discussion.Topic)
	 */
	public void topicInfoToEntity(TopicInfo sourceVO, Topic targetEntity, boolean copyIfNull) {
		super.topicInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

	@Override
	protected List handleLoadTopicsWithViewState(Forum forum) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}