// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.discussion;
/**
 * @see org.openuss.discussion.Post
 */
public class PostDaoImpl
    extends org.openuss.discussion.PostDaoBase
{
    /**
     * @see org.openuss.discussion.PostDao#toPostInfo(org.openuss.discussion.Post, org.openuss.discussion.PostInfo)
     */
    public void toPostInfo(
        org.openuss.discussion.Post sourceEntity,
        org.openuss.discussion.PostInfo targetVO)
    {
        // @todo verify behavior of toPostInfo
        super.toPostInfo(sourceEntity, targetVO);
        // WARNING! No conversion for targetVO.formula (can't convert sourceEntity.getFormula():org.openuss.discussion.Formula to java.lang.String
        // WARNING! No conversion for targetVO.editor (can't convert sourceEntity.getEditor():org.openuss.security.User to java.lang.String
        // WARNING! No conversion for targetVO.submitter (can't convert sourceEntity.getSubmitter():org.openuss.security.User to java.lang.String
    }


    /**
     * @see org.openuss.discussion.PostDao#toPostInfo(org.openuss.discussion.Post)
     */
    public org.openuss.discussion.PostInfo toPostInfo(final org.openuss.discussion.Post entity)
    {
        // @todo verify behavior of toPostInfo
        return super.toPostInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.discussion.Post loadPostFromPostInfo(org.openuss.discussion.PostInfo postInfo)
    {
        // @todo implement loadPostFromPostInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.discussion.loadPostFromPostInfo(org.openuss.discussion.PostInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.discussion.Post post = this.load(postInfo.getId());
        if (post == null)
        {
            post = org.openuss.discussion.Post.Factory.newInstance();
        }
        return post;
        */
    }

    
    /**
     * @see org.openuss.discussion.PostDao#postInfoToEntity(org.openuss.discussion.PostInfo)
     */
    public org.openuss.discussion.Post postInfoToEntity(org.openuss.discussion.PostInfo postInfo)
    {
        // @todo verify behavior of postInfoToEntity
        org.openuss.discussion.Post entity = this.loadPostFromPostInfo(postInfo);
        this.postInfoToEntity(postInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.discussion.PostDao#postInfoToEntity(org.openuss.discussion.PostInfo, org.openuss.discussion.Post)
     */
    public void postInfoToEntity(
        org.openuss.discussion.PostInfo sourceVO,
        org.openuss.discussion.Post targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of postInfoToEntity
        super.postInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}