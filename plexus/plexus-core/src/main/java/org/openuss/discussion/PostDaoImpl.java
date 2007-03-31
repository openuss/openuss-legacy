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
        super.toPostInfo(sourceEntity, targetVO);        
        targetVO.setFormula(sourceEntity.getFormulaString());
        targetVO.setEditor(sourceEntity.getEditorName());
        targetVO.setSubmitter(sourceEntity.getEditorName());
        targetVO.setIsEdited(sourceEntity.isEdited());
        targetVO.setSubmitterId(sourceEntity.getSubmitter().getId());
    }


    /**
     * @see org.openuss.discussion.PostDao#toPostInfo(org.openuss.discussion.Post)
     */
    public org.openuss.discussion.PostInfo toPostInfo(final org.openuss.discussion.Post entity)
    {
    	PostInfo pi = new PostInfo();
    	toPostInfo(entity, pi);
        return pi;
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.discussion.Post loadPostFromPostInfo(org.openuss.discussion.PostInfo postInfo)
    {
    	if (postInfo.getId()==null) return Post.Factory.newInstance(); 
        return this.load(postInfo.getId());
    }

    
    /**
     * @see org.openuss.discussion.PostDao#postInfoToEntity(org.openuss.discussion.PostInfo)
     */
    public Post postInfoToEntity(org.openuss.discussion.PostInfo postInfo)
    {
        Post entity = this.loadPostFromPostInfo(postInfo);
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
        super.postInfoToEntity(sourceVO, targetEntity, copyIfNull);
        if (copyIfNull || sourceVO.getFormula() != null)
        {
        	if (sourceVO.getFormula()!=null){
        		targetEntity.setFormula(Formula.Factory.newInstance(sourceVO.getFormula()));        		
        	}
        	else if (sourceVO.getFormula()==null){
        		targetEntity.setFormula(null);
        	}
        }

    }

}