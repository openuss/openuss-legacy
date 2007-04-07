// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.mail;
/**
 * @see org.openuss.mail.MailingList
 */
public class MailingListDaoImpl
    extends org.openuss.mail.MailingListDaoBase
{
    /**
     * @see org.openuss.mail.MailingListDao#toMailingListInfo(org.openuss.mail.MailingList, org.openuss.mail.MailingListInfo)
     */
    public void toMailingListInfo(
        org.openuss.mail.MailingList sourceEntity,
        org.openuss.mail.MailingListInfo targetVO)
    {
        super.toMailingListInfo(sourceEntity, targetVO);
        // WARNING! No conversion for targetVO.recipients (can't convert sourceEntity.getRecipients():org.openuss.security.User to org.openuss.mail.RecipientInfo
    }


    /**
     * @see org.openuss.mail.MailingListDao#toMailingListInfo(org.openuss.mail.MailingList)
     */
    public org.openuss.mail.MailingListInfo toMailingListInfo(final org.openuss.mail.MailingList entity)
    {
        return super.toMailingListInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.mail.MailingList loadMailingListFromMailingListInfo(org.openuss.mail.MailingListInfo mailingListInfo)
    {
        // @todo implement loadMailingListFromMailingListInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.mail.loadMailingListFromMailingListInfo(org.openuss.mail.MailingListInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.mail.MailingList mailingList = this.load(mailingListInfo.getId());
        if (mailingList == null)
        {
            mailingList = org.openuss.mail.MailingList.Factory.newInstance();
        }
        return mailingList;
        */
    }

    
    /**
     * @see org.openuss.mail.MailingListDao#mailingListInfoToEntity(org.openuss.mail.MailingListInfo)
     */
    public org.openuss.mail.MailingList mailingListInfoToEntity(org.openuss.mail.MailingListInfo mailingListInfo)
    {
        // @todo verify behavior of mailingListInfoToEntity
        org.openuss.mail.MailingList entity = this.loadMailingListFromMailingListInfo(mailingListInfo);
        this.mailingListInfoToEntity(mailingListInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.mail.MailingListDao#mailingListInfoToEntity(org.openuss.mail.MailingListInfo, org.openuss.mail.MailingList)
     */
    public void mailingListInfoToEntity(
        org.openuss.mail.MailingListInfo sourceVO,
        org.openuss.mail.MailingList targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of mailingListInfoToEntity
        super.mailingListInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}