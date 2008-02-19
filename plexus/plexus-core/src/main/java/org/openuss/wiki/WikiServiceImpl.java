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

	@Override
	protected void handleDeleteWikiSite(Long wikiSiteId) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleDeleteWikiSiteVersion(Long wikiSiteVersionId)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected WikiSiteContentInfo handleFindWikiSiteContentByDomainObjectAndName(
			Long domainId, String siteName) throws Exception {
		Validate.notNull(domainId, "Parameter domainId must not be null!");
		Validate.notNull(siteName, "Parameter siteName must not be null!");
		
		WikiSiteInfo wikiSite = (WikiSiteInfo) getWikiSiteDao().findByDomainIdAndName(WikiSiteDao.TRANSFORM_WIKISITEINFO, domainId, siteName);
		return handleGetNewestWikiSiteContent(wikiSite.getId());
	}

	@Override
	protected List handleFindWikiSiteVersionsByWikiSite(Long wikiSiteId)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List handleFindWikiSitesByDomainObject(Long domainId)
			throws Exception {
		Validate.notNull(domainId, "Parameter domainId must not be null!");
		
		List<WikiSiteInfo> result = getWikiSiteDao().findByDomainId(WikiSiteDao.TRANSFORM_WIKISITEINFO, domainId);
		return result;
	}

	@Override
	protected WikiSiteContentInfo handleGetNewestWikiSiteContent(Long wikiSiteId)
			throws Exception {
		Validate.notNull(wikiSiteId, "Parameter wikiSiteId must not be null!");
    	WikiSite site = getWikiSiteDao().load(wikiSiteId);
    	Validate.notNull(site, "No wikiSite found for wikiSiteId:" + wikiSiteId);
    	
    	String query = "from org.openuss.wiki.WikiSiteVersion as f where f.wikiSite = :wikiSite order by f.creationDate desc";
    	List<WikiSiteInfo> list = getWikiSiteVersionDao().findByWikiSite(WikiSiteVersionDao.TRANSFORM_WIKISITEINFO, query, site);
    	if (list.isEmpty()) {
    		return null;
    	} else {
    		WikiSiteInfo wikiSite = list.get(0);
    		return (WikiSiteContentInfo) getWikiSiteVersionDao().load(WikiSiteVersionDao.TRANSFORM_WIKISITECONTENTINFO, wikiSite.getId());
    	}
	}

	@Override
	protected WikiSiteInfo handleGetWikiSite(Long wikiSiteId) throws Exception {
		Validate.notNull(wikiSiteId, "Parameter wikiSiteId must not be null!");
		return (WikiSiteInfo)getWikiSiteDao().load(WikiSiteDao.TRANSFORM_WIKISITEINFO, wikiSiteId);
	}

	@Override
	protected WikiSiteContentInfo handleGetWikiSiteContent(
			Long wikiSiteVersionId) throws Exception {
		Validate.notNull(wikiSiteVersionId, "Parameter wikiSiteId must not be null!");
		return (WikiSiteContentInfo)getWikiSiteVersionDao().load(WikiSiteVersionDao.TRANSFORM_WIKISITECONTENTINFO, wikiSiteVersionId);
	}

	/** Creates or updates a wikisite.
	 * 
	 */
	@Override
	protected void handleSaveWikiSite(WikiSiteContentInfo wikiSiteContentInfo)
			throws Exception {
		Validate.notNull(wikiSiteContentInfo, "Parameter wikiSiteContentInfo cannot be null.");
		Validate.notNull(wikiSiteContentInfo.getDomainId(), "getDomainId cannot be null.");
		
		if (wikiSiteContentInfo.getId() != null) {
			// update
			
			WikiSiteVersion wikiSiteVersion = getWikiSiteVersionDao().wikiSiteContentInfoToEntity(wikiSiteContentInfo);
			getWikiSiteVersionDao().update(wikiSiteVersion);
			
		} else {
			// create
			
			// find/create WikiSite
			WikiSite wikiSite = null;
			if (wikiSiteContentInfo.getId() != null) {
				wikiSite = getWikiSiteDao().load(wikiSiteContentInfo.getId());
				Validate.notNull(wikiSite, "Cannot find wikiSite for id: " + wikiSiteContentInfo.getId());
			} else {
				wikiSite = this.getWikiSiteDao().wikiSiteInfoToEntity(wikiSiteContentInfo);
				Validate.notNull(wikiSite, "Cannot transform wikiSiteInfo to entity.");
				
				// Save Entity
				this.getWikiSiteDao().create(wikiSite);
				Validate.notNull(wikiSite, "Id of wikiSite cannot be null.");
			}
			
			WikiSiteVersion wikiSiteVersion = getWikiSiteVersionDao().wikiSiteContentInfoToEntity(wikiSiteContentInfo);
			getWikiSiteVersionDao().create(wikiSiteVersion);
			
			wikiSiteContentInfo.setId(wikiSite.getId());
	
			// add object identity to security
			//getSecurityService().createObjectIdentity(wikiSiteEntity, wikiSiteEntity.getCourse());

		    // Set Security
	  
			//FIXME: don't know what this does:	this.getSecurityService().createObjectIdentity(workspaceEntity, workspaceEntity.getCourseType());
		}
	}

}