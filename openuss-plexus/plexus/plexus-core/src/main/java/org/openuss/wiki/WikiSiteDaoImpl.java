package org.openuss.wiki;

/**
 * @author  Projektseminar WS 07/08, Team Collaboration
 * @see org.openuss.wiki.WikiSite
 * 
 */
public class WikiSiteDaoImpl extends WikiSiteDaoBase {

	/**
     * @see WikiSiteDao#toWikiSiteInfo(WikiSite, WikiSiteInfo)
     */
    public void toWikiSiteInfo(WikiSite sourceEntity, WikiSiteInfo targetVO) {
        super.toWikiSiteInfo(sourceEntity, targetVO);
        
        targetVO.setId(null);
        targetVO.setWikiSiteId(sourceEntity.getId());
    }

    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private WikiSite loadWikiSiteFromWikiSiteInfo(WikiSiteInfo wikiSiteInfo) {
        if (wikiSiteInfo.getId() == null) {
        	return new WikiSiteImpl();
        } else {
        	return this.load(wikiSiteInfo.getId());
        }
    }

    
    /**
     * @see WikiSiteDao#wikiSiteInfoToEntity(WikiSiteInfo)
     */
    public WikiSite wikiSiteInfoToEntity(WikiSiteInfo wikiSiteInfo) {
        WikiSite entity = this.loadWikiSiteFromWikiSiteInfo(wikiSiteInfo);
        this.wikiSiteInfoToEntity(wikiSiteInfo, entity, true);
        return entity;
    }


    /**
     * @see WikiSiteDao#wikiSiteInfoToEntity(WikiSiteInfo, WikiSite)
     */
    public void wikiSiteInfoToEntity(WikiSiteInfo sourceVO, WikiSite targetEntity, boolean copyIfNull) {
        super.wikiSiteInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}