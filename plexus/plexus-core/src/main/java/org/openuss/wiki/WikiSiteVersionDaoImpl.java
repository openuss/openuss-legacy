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
     * @see org.openuss.wiki.WikiSiteVersionDao#toWikiSiteContentInfo(org.openuss.wiki.WikiSiteVersion, org.openuss.wiki.WikiSiteContentInfo)
     */
    public void toWikiSiteContentInfo(
        org.openuss.wiki.WikiSiteVersion sourceEntity,
        org.openuss.wiki.WikiSiteContentInfo targetVO)
    {
        // @todo verify behavior of toWikiSiteContentInfo
        super.toWikiSiteContentInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#toWikiSiteContentInfo(org.openuss.wiki.WikiSiteVersion)
     */
    public org.openuss.wiki.WikiSiteContentInfo toWikiSiteContentInfo(final org.openuss.wiki.WikiSiteVersion entity)
    {
        // @todo verify behavior of toWikiSiteContentInfo
        return super.toWikiSiteContentInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.wiki.WikiSiteVersion loadWikiSiteVersionFromWikiSiteContentInfo(org.openuss.wiki.WikiSiteContentInfo wikiSiteContentInfo)
    {
        // @todo implement loadWikiSiteVersionFromWikiSiteContentInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.wiki.loadWikiSiteVersionFromWikiSiteContentInfo(org.openuss.wiki.WikiSiteContentInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.wiki.WikiSiteVersion wikiSiteVersion = this.load(wikiSiteContentInfo.getId());
        if (wikiSiteVersion == null)
        {
            wikiSiteVersion = org.openuss.wiki.WikiSiteVersion.Factory.newInstance();
        }
        return wikiSiteVersion;
        */
    }

    
    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#wikiSiteContentInfoToEntity(org.openuss.wiki.WikiSiteContentInfo)
     */
    public org.openuss.wiki.WikiSiteVersion wikiSiteContentInfoToEntity(org.openuss.wiki.WikiSiteContentInfo wikiSiteContentInfo)
    {
        // @todo verify behavior of wikiSiteContentInfoToEntity
        org.openuss.wiki.WikiSiteVersion entity = this.loadWikiSiteVersionFromWikiSiteContentInfo(wikiSiteContentInfo);
        this.wikiSiteContentInfoToEntity(wikiSiteContentInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#wikiSiteContentInfoToEntity(org.openuss.wiki.WikiSiteContentInfo, org.openuss.wiki.WikiSiteVersion)
     */
    public void wikiSiteContentInfoToEntity(
        org.openuss.wiki.WikiSiteContentInfo sourceVO,
        org.openuss.wiki.WikiSiteVersion targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of wikiSiteContentInfoToEntity
        super.wikiSiteContentInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#toWikiSiteInfo(org.openuss.wiki.WikiSiteVersion, org.openuss.wiki.WikiSiteInfo)
     */
    public void toWikiSiteInfo(
        org.openuss.wiki.WikiSiteVersion sourceEntity,
        org.openuss.wiki.WikiSiteInfo targetVO)
    {
        // @todo verify behavior of toWikiSiteInfo
        super.toWikiSiteInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#toWikiSiteInfo(org.openuss.wiki.WikiSiteVersion)
     */
    public org.openuss.wiki.WikiSiteInfo toWikiSiteInfo(final org.openuss.wiki.WikiSiteVersion entity)
    {
        // @todo verify behavior of toWikiSiteInfo
        return super.toWikiSiteInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.wiki.WikiSiteVersion loadWikiSiteVersionFromWikiSiteInfo(org.openuss.wiki.WikiSiteInfo wikiSiteInfo)
    {
        // @todo implement loadWikiSiteVersionFromWikiSiteInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.wiki.loadWikiSiteVersionFromWikiSiteInfo(org.openuss.wiki.WikiSiteInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.wiki.WikiSiteVersion wikiSiteVersion = this.load(wikiSiteInfo.getId());
        if (wikiSiteVersion == null)
        {
            wikiSiteVersion = org.openuss.wiki.WikiSiteVersion.Factory.newInstance();
        }
        return wikiSiteVersion;
        */
    }

    
    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#wikiSiteInfoToEntity(org.openuss.wiki.WikiSiteInfo)
     */
    public org.openuss.wiki.WikiSiteVersion wikiSiteInfoToEntity(org.openuss.wiki.WikiSiteInfo wikiSiteInfo)
    {
        // @todo verify behavior of wikiSiteInfoToEntity
        org.openuss.wiki.WikiSiteVersion entity = this.loadWikiSiteVersionFromWikiSiteInfo(wikiSiteInfo);
        this.wikiSiteInfoToEntity(wikiSiteInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#wikiSiteInfoToEntity(org.openuss.wiki.WikiSiteInfo, org.openuss.wiki.WikiSiteVersion)
     */
    public void wikiSiteInfoToEntity(
        org.openuss.wiki.WikiSiteInfo sourceVO,
        org.openuss.wiki.WikiSiteVersion targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of wikiSiteInfoToEntity
        super.wikiSiteInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}