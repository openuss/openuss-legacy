// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.discussion;

/**
 * @see org.openuss.discussion.Post
 */
public class PostDaoImpl extends PostDaoBase {
	/**
	 * @see org.openuss.discussion.PostDao#toPostInfo(org.openuss.discussion.Post,
	 *      org.openuss.discussion.PostInfo)
	 */
	public void toPostInfo(Post sourceEntity, PostInfo targetVO) {
		super.toPostInfo(sourceEntity, targetVO);
		targetVO.setFormula(sourceEntity.getFormulaString());
		targetVO.setEditor(sourceEntity.getEditorName());
		targetVO.setSubmitter(sourceEntity.getSubmitterName());
		targetVO.setIsEdited(sourceEntity.isEdited());
		targetVO.setSubmitterId(sourceEntity.getSubmitter().getId());
		targetVO.setSubmitterPicture(sourceEntity.getSubmitterPicture());
		targetVO.setTopicId(sourceEntity.getTopic().getId());
	}

	/**
	 * @see org.openuss.discussion.PostDao#toPostInfo(org.openuss.discussion.Post)
	 */
	public PostInfo toPostInfo(final Post entity) {
		PostInfo pi = new PostInfo();
		toPostInfo(entity, pi);
		return pi;
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private Post loadPostFromPostInfo(PostInfo postInfo) {
		if (postInfo.getId() == null)
			return Post.Factory.newInstance();
		return this.load(postInfo.getId());
	}

	/**
	 * @see org.openuss.discussion.PostDao#postInfoToEntity(org.openuss.discussion.PostInfo)
	 */
	public Post postInfoToEntity(PostInfo postInfo) {
		Post entity = this.loadPostFromPostInfo(postInfo);
		if (entity == null) {
			entity = Post.Factory.newInstance();
		}
		this.postInfoToEntity(postInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.discussion.PostDao#postInfoToEntity(org.openuss.discussion.PostInfo,
	 *      org.openuss.discussion.Post)
	 */
	public void postInfoToEntity(PostInfo sourceVO, Post targetEntity,
			boolean copyIfNull) {
		super.postInfoToEntity(sourceVO, targetEntity, copyIfNull);
		if (copyIfNull || sourceVO.getFormula() != null) {
			if (sourceVO.getFormula() != null) {
				targetEntity.setFormula(Formula.Factory.newInstance(sourceVO.getFormula()));
			}
		}

	}

}