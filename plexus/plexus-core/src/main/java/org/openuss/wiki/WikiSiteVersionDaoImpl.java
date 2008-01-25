// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.wiki;
/**
 * @see org.openuss.wiki.WikiSiteVersion
 */
public class WikiSiteVersionDaoImpl
    extends org.openuss.wiki.WikiSiteVersionDaoBase
{
    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#toWikiSiteVersionInfo(org.openuss.wiki.WikiSiteVersion, org.openuss.wiki.WikiSiteVersionInfo)
     */
    public void toWikiSiteVersionInfo(
        org.openuss.wiki.WikiSiteVersion sourceEntity,
        org.openuss.wiki.WikiSiteVersionInfo targetVO)
    {
        // @todo verify behavior of toWikiSiteVersionInfo
        super.toWikiSiteVersionInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#toWikiSiteVersionInfo(org.openuss.wiki.WikiSiteVersion)
     */
    public org.openuss.wiki.WikiSiteVersionInfo toWikiSiteVersionInfo(final org.openuss.wiki.WikiSiteVersion entity)
    {
        // @todo verify behavior of toWikiSiteVersionInfo
        return super.toWikiSiteVersionInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.wiki.WikiSiteVersion loadWikiSiteVersionFromWikiSiteVersionInfo(org.openuss.wiki.WikiSiteVersionInfo wikiSiteVersionInfo)
    {
    	WikiSiteVersion wikiSiteVersion = null;
    	if (wikiSiteVersionInfo.getId() == null) {
    		wikiSiteVersion = org.openuss.wiki.WikiSiteVersion.Factory.newInstance();
    	} else {
    		wikiSiteVersion = this.load(wikiSiteVersionInfo.getId());
        }
        return wikiSiteVersion;
    }

    
    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#wikiSiteVersionInfoToEntity(org.openuss.wiki.WikiSiteVersionInfo)
     */
    public org.openuss.wiki.WikiSiteVersion wikiSiteVersionInfoToEntity(org.openuss.wiki.WikiSiteVersionInfo wikiSiteVersionInfo)
    {
        // @todo verify behavior of wikiSiteVersionInfoToEntity
        org.openuss.wiki.WikiSiteVersion entity = this.loadWikiSiteVersionFromWikiSiteVersionInfo(wikiSiteVersionInfo);
        this.wikiSiteVersionInfoToEntity(wikiSiteVersionInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#wikiSiteVersionInfoToEntity(org.openuss.wiki.WikiSiteVersionInfo, org.openuss.wiki.WikiSiteVersion)
     */
    public void wikiSiteVersionInfoToEntity(
        org.openuss.wiki.WikiSiteVersionInfo sourceVO,
        org.openuss.wiki.WikiSiteVersion targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of wikiSiteVersionInfoToEntity
        super.wikiSiteVersionInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}