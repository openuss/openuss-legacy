// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.discussion;
/**
 * @see org.openuss.discussion.Forum
 */
public class ForumDaoImpl
    extends org.openuss.discussion.ForumDaoBase
{
    /**
     * @see org.openuss.discussion.ForumDao#toForumInfo(org.openuss.discussion.Forum, org.openuss.discussion.ForumInfo)
     */
    public void toForumInfo(
        org.openuss.discussion.Forum sourceEntity,
        org.openuss.discussion.ForumInfo targetVO)
    {
        // @todo verify behavior of toForumInfo
        super.toForumInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.discussion.ForumDao#toForumInfo(org.openuss.discussion.Forum)
     */
    public org.openuss.discussion.ForumInfo toForumInfo(final org.openuss.discussion.Forum entity)
    {
        // @todo verify behavior of toForumInfo
        return super.toForumInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.discussion.Forum loadForumFromForumInfo(org.openuss.discussion.ForumInfo forumInfo)
    {
        // @todo implement loadForumFromForumInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.discussion.loadForumFromForumInfo(org.openuss.discussion.ForumInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.discussion.Forum forum = this.load(forumInfo.getId());
        if (forum == null)
        {
            forum = org.openuss.discussion.Forum.Factory.newInstance();
        }
        return forum;
        */
    }

    
    /**
     * @see org.openuss.discussion.ForumDao#forumInfoToEntity(org.openuss.discussion.ForumInfo)
     */
    public org.openuss.discussion.Forum forumInfoToEntity(org.openuss.discussion.ForumInfo forumInfo)
    {
        // @todo verify behavior of forumInfoToEntity
        org.openuss.discussion.Forum entity = this.loadForumFromForumInfo(forumInfo);
        this.forumInfoToEntity(forumInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.discussion.ForumDao#forumInfoToEntity(org.openuss.discussion.ForumInfo, org.openuss.discussion.Forum)
     */
    public void forumInfoToEntity(
        org.openuss.discussion.ForumInfo sourceVO,
        org.openuss.discussion.Forum targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of forumInfoToEntity
        super.forumInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}