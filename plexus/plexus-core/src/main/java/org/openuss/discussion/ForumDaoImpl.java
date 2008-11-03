package org.openuss.discussion;

/**
 * @see org.openuss.discussion.Forum
 */
public class ForumDaoImpl extends ForumDaoBase {
	/**
	 * @see org.openuss.discussion.ForumDao#toForumInfo(org.openuss.discussion.Forum,
	 *      org.openuss.discussion.ForumInfo)
	 */
	public void toForumInfo(final Forum sourceEntity, final ForumInfo targetVO) {
		if (sourceEntity != null) {
			if (sourceEntity.getDomainIdentifier() != null) {
				targetVO.setDomainIdentifier(sourceEntity.getDomainIdentifier());
			}
			if (sourceEntity.getId() != null) {
				targetVO.setId(sourceEntity.getId());
			}
			if (sourceEntity.getName() != null) {
				targetVO.setName(sourceEntity.getName());
			}
			targetVO.setReadOnly(sourceEntity.isReadOnly());
		}
	}

	/**
	 * @see org.openuss.discussion.ForumDao#toForumInfo(org.openuss.discussion.Forum)
	 */
	public ForumInfo toForumInfo(final Forum entity) {
		ForumInfo target = new ForumInfo();
		this.toForumInfo(entity, target);
		return target;
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private Forum loadForumFromForumInfo(final ForumInfo forumInfo) {
		Forum forum = null;
		if (forumInfo.getId() != null)
			forum = this.load(forumInfo.getId());
		if (forum == null) {
			forum = new ForumImpl();
		}
		return forum;
	}

	/**
	 * @see org.openuss.discussion.ForumDao#forumInfoToEntity(org.openuss.discussion.ForumInfo)
	 */
	public Forum forumInfoToEntity(final ForumInfo forumInfo) {
		Forum entity = this.loadForumFromForumInfo(forumInfo);
		if (entity == null) {
			entity = new ForumImpl();
		}
		this.forumInfoToEntity(forumInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.discussion.ForumDao#forumInfoToEntity(org.openuss.discussion.ForumInfo,
	 *      org.openuss.discussion.Forum)
	 */
	public void forumInfoToEntity(final ForumInfo sourceVO, final Forum targetEntity, final boolean copyIfNull) {
		super.forumInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

}