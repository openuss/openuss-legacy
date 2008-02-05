// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.wiki;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.openuss.lecture.Course;
import org.openuss.security.User;

/**
 * @see org.openuss.wiki.WikiService
 */
public class WikiServiceImpl
    extends org.openuss.wiki.WikiServiceBase
{

    /**
     * @see org.openuss.wiki.WikiService#createWikiSite(org.openuss.wiki.WikiSiteInfo)
     */
    protected void handleCreateWikiSite(org.openuss.wiki.WikiSiteInfo wikiSiteInfo)
        throws java.lang.Exception
    {
    	Validate.notNull(wikiSiteInfo, "Parameter wikiSiteInfo cannot be null.");
		Validate.notNull(wikiSiteInfo.getCourseId(), "getCourseId cannot be null.");
		
		// Transform VO to entity
		WikiSite wikiSiteEntity = this.getWikiSiteDao().wikiSiteInfoToEntity(wikiSiteInfo);
		Validate.notNull(wikiSiteEntity, "Cannot transform wikiSiteInfo to entity.");

		// Add wiki to course
		Course course = this.getCourseDao().load(wikiSiteInfo.getCourseId());
		course.getWikiPages().add(wikiSiteEntity);
		wikiSiteEntity.setCourse(course);
		
		// Save Entity
		this.getWikiSiteDao().create(wikiSiteEntity);
		Validate.notNull(wikiSiteEntity, "Id of wikiSite cannot be null.");
		
		wikiSiteInfo.setId(wikiSiteEntity.getId());

		// add object identity to security
		getSecurityService().createObjectIdentity(wikiSiteEntity, wikiSiteEntity.getCourse());
		
		this.getCourseDao().update(course);

		// Set Security
	//FIXME: don't know what this does:	this.getSecurityService().createObjectIdentity(workspaceEntity, workspaceEntity.getCourseType());
		
//		updateAccessTypePermission(workspaceEntity);
    }

    /**
     * @see org.openuss.wiki.WikiService#removeWikiSite(java.lang.Long)
     */
    protected void handleRemoveWikiSite(java.lang.Long wikiSiteId)
        throws java.lang.Exception
    {
        // @todo implement protected void handleRemoveWikiSite(java.lang.Long wikiSiteId)
        throw new java.lang.UnsupportedOperationException("org.openuss.wiki.WikiService.handleRemoveWikiSite(java.lang.Long wikiSiteId) Not implemented!");
    }

    /**
     * @see org.openuss.wiki.WikiService#updateWikiSite(java.lang.Long)
     */
    protected void handleUpdateWikiSite(java.lang.Long wikiSiteId)
        throws java.lang.Exception
    {
        // @todo implement protected void handleUpdateWikiSite(java.lang.Long wikiSiteId)
        throw new java.lang.UnsupportedOperationException("org.openuss.wiki.WikiService.handleUpdateWikiSite(java.lang.Long wikiSiteId) Not implemented!");
    }

    /**
     * @see org.openuss.wiki.WikiService#createWikiSiteVersion(org.openuss.wiki.WikiSiteVersionInfo)
     */
    protected void handleCreateWikiSiteVersion(org.openuss.wiki.WikiSiteVersionInfo wikiSiteVersionInfo)
        throws java.lang.Exception
    {
    	Validate.notNull(wikiSiteVersionInfo, "Parameter wikiSiteVersionInfo cannot be null.");
		Validate.notNull(wikiSiteVersionInfo.getWikiSiteId(), "getWikiSiteId cannot be null.");
		
		// Transform VO to entity
		WikiSiteVersion wikiSiteVersionEntity = this.getWikiSiteVersionDao().wikiSiteVersionInfoToEntity(wikiSiteVersionInfo);
		Validate.notNull(wikiSiteVersionEntity, "Cannot transform wikiSiteInfo to entity.");

		// Add wiki to course
		WikiSite site = this.getWikiSiteDao().load(wikiSiteVersionInfo.getWikiSiteId());
		site.getWikiPageVersions().add(wikiSiteVersionEntity);
		wikiSiteVersionEntity.setWikiSite(site);
		
		// Add user to version
		User author = this.getUserDao().load(wikiSiteVersionInfo.getUserId());
		wikiSiteVersionEntity.setAuthor(author);
		
		// Save Entity
		this.getWikiSiteVersionDao().create(wikiSiteVersionEntity);
		Validate.notNull(wikiSiteVersionEntity, "Id of wikiSiteVersion cannot be null.");
		
		wikiSiteVersionInfo.setId(wikiSiteVersionEntity.getId());

		// add object identity to security
		getSecurityService().createObjectIdentity(wikiSiteVersionEntity, wikiSiteVersionEntity.getWikiSite());
		
		this.getWikiSiteDao().update(site);

		// Set Security
	//FIXME: don't know what this does:	this.getSecurityService().createObjectIdentity(workspaceEntity, workspaceEntity.getCourseType());
		
//		updateAccessTypePermission(workspaceEntity);
    }

    /**
     * @see org.openuss.wiki.WikiService#removeWikiSiteVersion(java.lang.Long)
     */
    protected void handleRemoveWikiSiteVersion(java.lang.Long wikiSiteVersionId)
        throws java.lang.Exception
    {
        // @todo implement protected void handleRemoveWikiSiteVersion(java.lang.Long wikiSiteVersionId)
        throw new java.lang.UnsupportedOperationException("org.openuss.wiki.WikiService.handleRemoveWikiSiteVersion(java.lang.Long wikiSiteVersionId) Not implemented!");
    }

    /**
     * @see org.openuss.wiki.WikiService#getWikiSite(java.lang.Long)
     */
    protected org.openuss.wiki.WikiSiteInfo handleGetWikiSite(java.lang.Long wikiSiteId)
        throws java.lang.Exception
    {
    	Validate.notNull(wikiSiteId, "Parameter wikiSiteId must not be null!");
		return (WikiSiteInfo)getWikiSiteDao().load(WikiSiteDao.TRANSFORM_WIKISITEINFO, wikiSiteId);
    }

    /**
     * @see org.openuss.wiki.WikiService#getWikiSiteVersion(java.lang.Long)
     */
    protected org.openuss.wiki.WikiSiteVersionInfo handleGetWikiSiteVersion(java.lang.Long wikiSiteVersionId)
        throws java.lang.Exception
    {
    	Validate.notNull(wikiSiteVersionId, "Parameter wikiSiteVersionId must not be null!");
		return (WikiSiteVersionInfo)getWikiSiteVersionDao().load(WikiSiteVersionDao.TRANSFORM_WIKISITEVERSIONINFO, wikiSiteVersionId);
    }

    /**
     * @see org.openuss.wiki.WikiService#getNewestWikiSiteVersion(java.lang.Long)
     */
    protected org.openuss.wiki.WikiSiteVersionInfo handleGetNewestWikiSiteVersion(java.lang.Long wikiSiteId)
        throws java.lang.Exception
    {
    	Validate.notNull(wikiSiteId, "Parameter wikiSiteId must not be null!");
    	WikiSite site = getWikiSiteDao().load(wikiSiteId);
    	Validate.notNull(site, "No wikiSite found for wikiSiteId:" + wikiSiteId);
    	
    	String query = "from org.openuss.wiki.WikiSiteVersion as f where f.wikiSite = :wikiSite order by f.creationDate desc";
    	List<WikiSiteVersionInfo> list = getWikiSiteVersionDao().findByWikiSite(WikiSiteVersionDao.TRANSFORM_WIKISITEVERSIONINFO, query, site);
    	if (list.isEmpty()) {
    		return null;
    	} else {
    		return list.get(0);
    	}
    }

    /**
     * @see org.openuss.wiki.WikiService#findWikiSiteVersionsByWikiSite(java.lang.Long)
     */
    protected java.util.List handleFindWikiSiteVersionsByWikiSite(java.lang.Long wikiSiteId)
        throws java.lang.Exception
    {
        // @todo implement protected java.util.List handleFindWikiSiteVersionsByWikiSite(java.lang.Long wikiSiteId)
        return null;
    }

    /**
     * @see org.openuss.wiki.WikiService#findWikiSitesByCourse(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	protected java.util.List handleFindWikiSitesByCourse(java.lang.Long courseId)
        throws java.lang.Exception {
    	Validate.notNull(courseId, "Parameter courseId must not be null!");
		Course course = getCourseDao().load(courseId);
		Validate.notNull(course, "No course found for courseId:" + courseId);
		
		List<WikiSiteInfo> result = getWikiSiteDao().findByCourse(WikiSiteDao.TRANSFORM_WIKISITEINFO, course);
		return result;
    }

	@Override
	protected WikiSiteInfo handleFindWikiSiteByCourseAndName(Long courseId,
			String siteName) throws Exception {
		Validate.notNull(courseId, "Parameter courseId must not be null!");
		Course course = getCourseDao().load(courseId);
		Validate.notNull(course, "No course found for courseId:" + courseId);
		
		return (WikiSiteInfo)getWikiSiteDao().findByCourseAndName(WikiSiteDao.TRANSFORM_WIKISITEINFO, course, siteName);
	}

	@Override
	protected void handleUpdateWikiSite(WikiSiteInfo wikiSiteInfo)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}