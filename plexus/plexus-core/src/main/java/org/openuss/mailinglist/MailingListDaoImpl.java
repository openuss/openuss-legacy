// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.mailinglist;
/**
 * @see org.openuss.mail.MailingList
 */
public class MailingListDaoImpl extends MailingListDaoBase
{
    /**
     * @see org.openuss.mail.MailingListDao#toMailingListInfo(org.openuss.mail.MailingList, org.openuss.mail.MailingListInfo)
     */
    public void toMailingListInfo(
        MailingList sourceEntity,
        MailingListInfo targetVO)
    {
        super.toMailingListInfo(sourceEntity, targetVO);
        // WARNING! No conversion for targetVO.recipients (can't convert sourceEntity.getRecipients():org.openuss.security.User to RecipientInfo
    }


    /**
     * @see MailingListDao#toMailingListInfo(MailingList)
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
        // @todo implement loadMailingListFromMailingListInfo
        throw new java.lang.UnsupportedOperationException("loadMailingListFromMailingListInfo(MailingListInfo) not yet implemented.");

        /* A typical implementation looks like this:
        MailingList mailingList = this.load(mailingListInfo.getId());
        if (mailingList == null)
        {
            mailingList = MailingList.Factory.newInstance();
        }
        return mailingList;
        */
    }

    
    /**
     * @see MailingListDao#mailingListInfoToEntity(MailingListInfo)
     */
    public MailingList mailingListInfoToEntity(MailingListInfo mailingListInfo)
    {
        // @todo verify behavior of mailingListInfoToEntity
        MailingList entity = this.loadMailingListFromMailingListInfo(mailingListInfo);
        this.mailingListInfoToEntity(mailingListInfo, entity, true);
        return entity;
    }


    /**
     * @see MailingListDao#mailingListInfoToEntity(MailingListInfo, MailingList)
     */
    public void mailingListInfoToEntity(
        MailingListInfo sourceVO,
        MailingList targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of mailingListInfoToEntity
        super.mailingListInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}