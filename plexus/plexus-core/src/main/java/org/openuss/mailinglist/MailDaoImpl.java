// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.mailinglist;
/**
 * @see org.openuss.mailinglist.Mail
 */
public class MailDaoImpl
    extends org.openuss.mailinglist.MailDaoBase
{
    /**
     * @see org.openuss.mailinglist.MailDao#loadMailInfos(org.openuss.mailinglist.MailingList, org.openuss.security.User, org.openuss.mailinglist.MailingStatus)
     */
    protected java.util.List handleLoadMailInfos(org.openuss.mailinglist.MailingList mailingList, org.openuss.security.User user, org.openuss.mailinglist.MailingStatus status)
    {
        // @todo implement public java.util.List handleLoadMailInfos(org.openuss.mailinglist.MailingList mailingList, org.openuss.security.User user, org.openuss.mailinglist.MailingStatus status)
        return null;
}

    /**
     * @see org.openuss.mailinglist.MailDao#toMailInfo(org.openuss.mailinglist.Mail, org.openuss.mailinglist.MailInfo)
     */
    public void toMailInfo(
        org.openuss.mailinglist.Mail sourceEntity,
        org.openuss.mailinglist.MailInfo targetVO)
    {
        // @todo verify behavior of toMailInfo
        super.toMailInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.mailinglist.MailDao#toMailInfo(org.openuss.mailinglist.Mail)
     */
    public org.openuss.mailinglist.MailInfo toMailInfo(final org.openuss.mailinglist.Mail entity)
    {
        // @todo verify behavior of toMailInfo
        return super.toMailInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.mailinglist.Mail loadMailFromMailInfo(org.openuss.mailinglist.MailInfo mailInfo)
    {
        // @todo implement loadMailFromMailInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.mailinglist.loadMailFromMailInfo(org.openuss.mailinglist.MailInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.mailinglist.Mail mail = this.load(mailInfo.getId());
        if (mail == null)
        {
            mail = org.openuss.mailinglist.Mail.Factory.newInstance();
        }
        return mail;
        */
    }

    
    /**
     * @see org.openuss.mailinglist.MailDao#mailInfoToEntity(org.openuss.mailinglist.MailInfo)
     */
    public org.openuss.mailinglist.Mail mailInfoToEntity(org.openuss.mailinglist.MailInfo mailInfo)
    {
        // @todo verify behavior of mailInfoToEntity
        org.openuss.mailinglist.Mail entity = this.loadMailFromMailInfo(mailInfo);
        this.mailInfoToEntity(mailInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.mailinglist.MailDao#mailInfoToEntity(org.openuss.mailinglist.MailInfo, org.openuss.mailinglist.Mail)
     */
    public void mailInfoToEntity(
        org.openuss.mailinglist.MailInfo sourceVO,
        org.openuss.mailinglist.Mail targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of mailInfoToEntity
        super.mailInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

    /**
     * @see org.openuss.mailinglist.MailDao#toMailDetail(org.openuss.mailinglist.Mail, org.openuss.mailinglist.MailDetail)
     */
    public void toMailDetail(
        org.openuss.mailinglist.Mail sourceEntity,
        org.openuss.mailinglist.MailDetail targetVO)
    {
        // @todo verify behavior of toMailDetail
        super.toMailDetail(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.mailinglist.MailDao#toMailDetail(org.openuss.mailinglist.Mail)
     */
    public org.openuss.mailinglist.MailDetail toMailDetail(final org.openuss.mailinglist.Mail entity)
    {
        // @todo verify behavior of toMailDetail
        return super.toMailDetail(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.mailinglist.Mail loadMailFromMailDetail(org.openuss.mailinglist.MailDetail mailDetail)
    {
        // @todo implement loadMailFromMailDetail
        throw new java.lang.UnsupportedOperationException("org.openuss.mailinglist.loadMailFromMailDetail(org.openuss.mailinglist.MailDetail) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.mailinglist.Mail mail = this.load(mailDetail.getId());
        if (mail == null)
        {
            mail = org.openuss.mailinglist.Mail.Factory.newInstance();
        }
        return mail;
        */
    }

    
    /**
     * @see org.openuss.mailinglist.MailDao#mailDetailToEntity(org.openuss.mailinglist.MailDetail)
     */
    public org.openuss.mailinglist.Mail mailDetailToEntity(org.openuss.mailinglist.MailDetail mailDetail)
    {
        // @todo verify behavior of mailDetailToEntity
        org.openuss.mailinglist.Mail entity = this.loadMailFromMailDetail(mailDetail);
        this.mailDetailToEntity(mailDetail, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.mailinglist.MailDao#mailDetailToEntity(org.openuss.mailinglist.MailDetail, org.openuss.mailinglist.Mail)
     */
    public void mailDetailToEntity(
        org.openuss.mailinglist.MailDetail sourceVO,
        org.openuss.mailinglist.Mail targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of mailDetailToEntity
        super.mailDetailToEntity(sourceVO, targetEntity, copyIfNull);
    }

}