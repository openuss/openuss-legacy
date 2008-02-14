// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.internalMessage;
/**
 * @see org.openuss.internalMessage.MessageStatus
 */
public class MessageStatusDaoImpl
    extends org.openuss.internalMessage.MessageStatusDaoBase
{
    /**
     * @see org.openuss.internalMessage.MessageStatusDao#toInternalMessageRecipientsInfo(org.openuss.internalMessage.MessageStatus, org.openuss.internalMessage.InternalMessageRecipientsInfo)
     */
    public void toInternalMessageRecipientsInfo(
        org.openuss.internalMessage.MessageStatus sourceEntity,
        org.openuss.internalMessage.InternalMessageRecipientsInfo targetVO)
    {
        // @todo verify behavior of toInternalMessageRecipientsInfo
        super.toInternalMessageRecipientsInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.internalMessage.MessageStatusDao#toInternalMessageRecipientsInfo(org.openuss.internalMessage.MessageStatus)
     */
    public org.openuss.internalMessage.InternalMessageRecipientsInfo toInternalMessageRecipientsInfo(final org.openuss.internalMessage.MessageStatus entity)
    {
        // @todo verify behavior of toInternalMessageRecipientsInfo
        return super.toInternalMessageRecipientsInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.internalMessage.MessageStatus loadMessageStatusFromInternalMessageRecipientsInfo(org.openuss.internalMessage.InternalMessageRecipientsInfo internalMessageRecipientsInfo)
    {
        // @todo implement loadMessageStatusFromInternalMessageRecipientsInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.internalMessage.loadMessageStatusFromInternalMessageRecipientsInfo(org.openuss.internalMessage.InternalMessageRecipientsInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.internalMessage.MessageStatus messageStatus = this.load(internalMessageRecipientsInfo.getId());
        if (messageStatus == null)
        {
            messageStatus = org.openuss.internalMessage.MessageStatus.Factory.newInstance();
        }
        return messageStatus;
        */
    }

    
    /**
     * @see org.openuss.internalMessage.MessageStatusDao#internalMessageRecipientsInfoToEntity(org.openuss.internalMessage.InternalMessageRecipientsInfo)
     */
    public org.openuss.internalMessage.MessageStatus internalMessageRecipientsInfoToEntity(org.openuss.internalMessage.InternalMessageRecipientsInfo internalMessageRecipientsInfo)
    {
        // @todo verify behavior of internalMessageRecipientsInfoToEntity
        org.openuss.internalMessage.MessageStatus entity = this.loadMessageStatusFromInternalMessageRecipientsInfo(internalMessageRecipientsInfo);
        this.internalMessageRecipientsInfoToEntity(internalMessageRecipientsInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.internalMessage.MessageStatusDao#internalMessageRecipientsInfoToEntity(org.openuss.internalMessage.InternalMessageRecipientsInfo, org.openuss.internalMessage.MessageStatus)
     */
    public void internalMessageRecipientsInfoToEntity(
        org.openuss.internalMessage.InternalMessageRecipientsInfo sourceVO,
        org.openuss.internalMessage.MessageStatus targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of internalMessageRecipientsInfoToEntity
        super.internalMessageRecipientsInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}