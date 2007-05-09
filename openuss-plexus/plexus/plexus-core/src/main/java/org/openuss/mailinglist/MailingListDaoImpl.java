// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.mailinglist;
/**
 * @see org.openuss.mailinglist.MailingList
 */
public class MailingListDaoImpl
    extends MailingListDaoBase
{
    /**
     * @see org.openuss.mailinglist.MailingListDao#toMailingListInfo(org.openuss.mailinglist.MailingList, org.openuss.mailinglist.MailingListInfo)
     */
    public void toMailingListInfo(
        MailingList sourceEntity,
        MailingListInfo targetVO)
    {
        super.toMailingListInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.mailinglist.MailingListDao#toMailingListInfo(org.openuss.mailinglist.MailingList)
     */
    public MailingListInfo toMailingListInfo(final MailingList entity)
    {
        return super.toMailingListInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private MailingList loadMailingListFromMailingListInfo(MailingListInfo mailingListInfo)
    {
        MailingList mailingList = this.load(mailingListInfo.getId());
        if (mailingList == null)
        {
            mailingList = MailingList.Factory.newInstance();            
        }
        return mailingList;
    }

    
    /**
     * @see org.openuss.mailinglist.MailingListDao#mailingListInfoToEntity(org.openuss.mailinglist.MailingListInfo)
     */
    public MailingList mailingListInfoToEntity(MailingListInfo mailingListInfo)
    {
        MailingList entity = this.loadMailingListFromMailingListInfo(mailingListInfo);
        this.mailingListInfoToEntity(mailingListInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.mailinglist.MailingListDao#mailingListInfoToEntity(org.openuss.mailinglist.MailingListInfo, org.openuss.mailinglist.MailingList)
     */
    public void mailingListInfoToEntity(
        MailingListInfo sourceVO,
        MailingList targetEntity,
        boolean copyIfNull)
    {
        super.mailingListInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}