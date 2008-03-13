// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.wiki;

/**
 * @author  Projektseminar WS 07/08, Team Collaboration
 * @see org.openuss.wiki.WikiSite
 * 
 */
public class WikiSiteDaoImpl extends org.openuss.wiki.WikiSiteDaoBase {
    /**
     * @see org.openuss.wiki.WikiSiteDao#toWikiSiteInfo(org.openuss.wiki.WikiSite, org.openuss.wiki.WikiSiteInfo)
     */
    public void toWikiSiteInfo(org.openuss.wiki.WikiSite sourceEntity, org.openuss.wiki.WikiSiteInfo targetVO) {
        super.toWikiSiteInfo(sourceEntity, targetVO);
        
        targetVO.setId(null);
        targetVO.setWikiSiteId(sourceEntity.getId());
    }

    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.wiki.WikiSite loadWikiSiteFromWikiSiteInfo(org.openuss.wiki.WikiSiteInfo wikiSiteInfo) {
        if (wikiSiteInfo.getId() == null) {
        	return org.openuss.wiki.WikiSite.Factory.newInstance();
        } else {
        	return this.load(wikiSiteInfo.getId());
        }
    }

    
    /**
     * @see org.openuss.wiki.WikiSiteDao#wikiSiteInfoToEntity(org.openuss.wiki.WikiSiteInfo)
     */
    public org.openuss.wiki.WikiSite wikiSiteInfoToEntity(org.openuss.wiki.WikiSiteInfo wikiSiteInfo) {
        org.openuss.wiki.WikiSite entity = this.loadWikiSiteFromWikiSiteInfo(wikiSiteInfo);
        this.wikiSiteInfoToEntity(wikiSiteInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.wiki.WikiSiteDao#wikiSiteInfoToEntity(org.openuss.wiki.WikiSiteInfo, org.openuss.wiki.WikiSite)
     */
    public void wikiSiteInfoToEntity(org.openuss.wiki.WikiSiteInfo sourceVO, org.openuss.wiki.WikiSite targetEntity, boolean copyIfNull) {
        super.wikiSiteInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}