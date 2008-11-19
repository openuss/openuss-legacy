// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.newsletter;
/**
 * @see org.openuss.newsletter.Newsletter
 */
public class NewsletterDaoImpl
    extends NewsletterDaoBase
{
    /**
     * @see org.openuss.newsletter.NewsletterDao#toNewsletterInfo(org.openuss.newsletter.Newsletter, org.openuss.newsletter.NewsletterInfo)
     */
    public void toNewsletterInfo(
        Newsletter sourceEntity,
        NewsletterInfo targetVO)
    {
        super.toNewsletterInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.newsletter.NewsletterDao#toNewsletterInfo(org.openuss.newsletter.Newsletter)
     */
    public NewsletterInfo toNewsletterInfo(final Newsletter entity)
    {
        return super.toNewsletterInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private Newsletter loadNewsletterFromNewsletterInfo(NewsletterInfo newsletterInfo)
    {
        Newsletter newsletter = this.load(newsletterInfo.getId());
        if (newsletter == null)
        {
            newsletter = Newsletter.Factory.newInstance();            
        }
        return newsletter;
    }

    
    /**
     * @see org.openuss.newsletter.NewsletterDao#newsletterInfoToEntity(org.openuss.newsletter.NewsletterInfo)
     */
    public Newsletter newsletterInfoToEntity(NewsletterInfo newsletterInfo)
    {
        Newsletter entity = this.loadNewsletterFromNewsletterInfo(newsletterInfo);
        this.newsletterInfoToEntity(newsletterInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.newsletter.NewsletterDao#newsletterInfoToEntity(org.openuss.newsletter.NewsletterInfo, org.openuss.newsletter.Newsletter)
     */
    public void newsletterInfoToEntity(
        NewsletterInfo sourceVO,
        Newsletter targetEntity,
        boolean copyIfNull)
    {
        super.newsletterInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}