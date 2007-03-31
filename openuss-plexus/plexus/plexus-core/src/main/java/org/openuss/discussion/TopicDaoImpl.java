// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.discussion;
/**
 * @see org.openuss.discussion.Topic
 */
public class TopicDaoImpl
    extends org.openuss.discussion.TopicDaoBase
{
    /**
     * @see org.openuss.discussion.TopicDao#toTopicInfo(org.openuss.discussion.Topic, org.openuss.discussion.TopicInfo)
     */
    public void toTopicInfo(
        org.openuss.discussion.Topic sourceEntity,
        org.openuss.discussion.TopicInfo targetVO)
    {
        super.toTopicInfo(sourceEntity, targetVO);
        if (sourceEntity.getForum()!=null||sourceEntity.getId()!=null)
        	targetVO.setForumId(sourceEntity.getForum().getId());
        if (sourceEntity.getFirst()!=null){
        	if (sourceEntity.getFirst().getTitle()!=null)
        		targetVO.setTitle(sourceEntity.getFirst().getTitle());
        	if (sourceEntity.getFirst().getCreated()!=null)
        		targetVO.setCreated(sourceEntity.getFirst().getCreated());
        	if (sourceEntity.getFirst().getSubmitterName()!=null)
        		targetVO.setSubmitter(sourceEntity.getFirst().getSubmitterName());        	
        }
        if (sourceEntity.getLastPostDate()!=null)
        	targetVO.setLastPost(sourceEntity.getLastPostDate());
        if (sourceEntity.getAnswerCount()!=null)
        	targetVO.setAnswerCount(sourceEntity.getAnswerCount());        
    }


    /**
     * @see org.openuss.discussion.TopicDao#toTopicInfo(org.openuss.discussion.Topic)
     */
    public org.openuss.discussion.TopicInfo toTopicInfo(final org.openuss.discussion.Topic entity)
    {
        return super.toTopicInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.discussion.Topic loadTopicFromTopicInfo(org.openuss.discussion.TopicInfo topicInfo)
    {
        if (topicInfo.getId() == null)
        {
            return org.openuss.discussion.Topic.Factory.newInstance();
        }
       	return this.load(topicInfo.getId());
    }

    
    /**
     * @see org.openuss.discussion.TopicDao#topicInfoToEntity(org.openuss.discussion.TopicInfo)
     */
    public org.openuss.discussion.Topic topicInfoToEntity(org.openuss.discussion.TopicInfo topicInfo)
    {
        org.openuss.discussion.Topic entity = this.loadTopicFromTopicInfo(topicInfo);
        this.topicInfoToEntity(topicInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.discussion.TopicDao#topicInfoToEntity(org.openuss.discussion.TopicInfo, org.openuss.discussion.Topic)
     */
    public void topicInfoToEntity(
        org.openuss.discussion.TopicInfo sourceVO,
        org.openuss.discussion.Topic targetEntity,
        boolean copyIfNull)
    {
        super.topicInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}