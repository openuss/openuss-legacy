// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.wiki;

/**
 * @author  Projektseminar WS 07/08, Team Collaboration
 * @see org.openuss.wiki.WikiSiteVersion
 * 
 */
public class WikiSiteVersionDaoImpl  extends org.openuss.wiki.WikiSiteVersionDaoBase {
	
    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#toWikiSiteContentInfo(org.openuss.wiki.WikiSiteVersion, org.openuss.wiki.WikiSiteContentInfo)
     */
    public void toWikiSiteContentInfo(org.openuss.wiki.WikiSiteVersion sourceEntity, org.openuss.wiki.WikiSiteContentInfo targetVO) {
    	toWikiSiteInfo(sourceEntity, targetVO);
    	targetVO.setText(sourceEntity.getText());
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#toWikiSiteContentInfo(org.openuss.wiki.WikiSiteVersion)
     */
    public org.openuss.wiki.WikiSiteContentInfo toWikiSiteContentInfo(final org.openuss.wiki.WikiSiteVersion entity) {
    	final org.openuss.wiki.WikiSiteContentInfo target = new org.openuss.wiki.WikiSiteContentInfo();
        this.toWikiSiteContentInfo(entity, target);
        return target;
    }

    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.wiki.WikiSiteVersion loadWikiSiteVersionFromWikiSiteContentInfo(org.openuss.wiki.WikiSiteContentInfo wikiSiteContentInfo) {
        if (wikiSiteContentInfo.getId() != null) {
        	return this.load(wikiSiteContentInfo.getId());
        } else {
        	return new WikiSiteVersionImpl();
        }
    }
    
    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#wikiSiteContentInfoToEntity(org.openuss.wiki.WikiSiteContentInfo)
     */
    public org.openuss.wiki.WikiSiteVersion wikiSiteContentInfoToEntity(org.openuss.wiki.WikiSiteContentInfo wikiSiteContentInfo) {
        org.openuss.wiki.WikiSiteVersion entity = this.loadWikiSiteVersionFromWikiSiteContentInfo(wikiSiteContentInfo);
        this.wikiSiteContentInfoToEntity(wikiSiteContentInfo, entity, true);
        return entity;
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#wikiSiteContentInfoToEntity(org.openuss.wiki.WikiSiteContentInfo, org.openuss.wiki.WikiSiteVersion)
     */
    public void wikiSiteContentInfoToEntity(org.openuss.wiki.WikiSiteContentInfo sourceVO, org.openuss.wiki.WikiSiteVersion targetEntity, boolean copyIfNull) {
    	wikiSiteInfoToEntity(sourceVO, targetEntity, copyIfNull);
    	
    	if (copyIfNull || sourceVO.getText() != null) {
    		targetEntity.setText(sourceVO.getText());
        }
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#toWikiSiteInfo(org.openuss.wiki.WikiSiteVersion, org.openuss.wiki.WikiSiteInfo)
     */
    public void toWikiSiteInfo(org.openuss.wiki.WikiSiteVersion sourceEntity, org.openuss.wiki.WikiSiteInfo targetVO) {
    	targetVO.setId(sourceEntity.getId());
    	targetVO.setCreationDate(sourceEntity.getCreationDate());
    	targetVO.setNote(sourceEntity.getNote());
    	
    	targetVO.setWikiSiteId(sourceEntity.getWikiSite().getId());
    	targetVO.setName(sourceEntity.getWikiSite().getName());
    	targetVO.setDeleted(sourceEntity.getWikiSite().getDeleted());
    	targetVO.setReadOnly(sourceEntity.getWikiSite().getReadOnly());
    	targetVO.setDomainId(sourceEntity.getWikiSite().getDomainId());
    	
    	targetVO.setAuthorId(sourceEntity.getAuthor().getId());
    	targetVO.setAuthorName(sourceEntity.getAuthor().getDisplayName());
    	
    	targetVO.setStable(sourceEntity.getStable());
    }

    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#toWikiSiteInfo(org.openuss.wiki.WikiSiteVersion)
     */
    public org.openuss.wiki.WikiSiteInfo toWikiSiteInfo(final org.openuss.wiki.WikiSiteVersion entity) {
    	final org.openuss.wiki.WikiSiteInfo target = new org.openuss.wiki.WikiSiteInfo();
        this.toWikiSiteInfo(entity, target);
        return target;
    }

    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.wiki.WikiSiteVersion loadWikiSiteVersionFromWikiSiteInfo(org.openuss.wiki.WikiSiteInfo wikiSiteInfo) {
    	if (wikiSiteInfo.getId() != null) {
    		return this.load(wikiSiteInfo.getId());
    	} else {
    		return new WikiSiteVersionImpl();
        }
    }

    
    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#wikiSiteInfoToEntity(org.openuss.wiki.WikiSiteInfo)
     */
    public org.openuss.wiki.WikiSiteVersion wikiSiteInfoToEntity(org.openuss.wiki.WikiSiteInfo wikiSiteInfo) {
        org.openuss.wiki.WikiSiteVersion entity = this.loadWikiSiteVersionFromWikiSiteInfo(wikiSiteInfo);
        this.wikiSiteInfoToEntity(wikiSiteInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.wiki.WikiSiteVersionDao#wikiSiteInfoToEntity(org.openuss.wiki.WikiSiteInfo, org.openuss.wiki.WikiSiteVersion)
     */
    public void wikiSiteInfoToEntity(org.openuss.wiki.WikiSiteInfo sourceVO, org.openuss.wiki.WikiSiteVersion targetEntity, boolean copyIfNull) {
    	if (copyIfNull || sourceVO.getCreationDate() != null) {
    		targetEntity.setCreationDate(sourceVO.getCreationDate());
        }
        
    	if (copyIfNull || sourceVO.getNote() != null) {
        	targetEntity.setNote(sourceVO.getNote());
        }
    	
        if (copyIfNull || sourceVO.isStable()) {
            targetEntity.setStable(sourceVO.isStable());
        }
    }

}