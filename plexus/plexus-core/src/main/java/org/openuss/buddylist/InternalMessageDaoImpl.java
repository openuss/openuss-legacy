// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.buddylist;
/**
 * @see org.openuss.buddylist.InternalMessage
 */
public class InternalMessageDaoImpl
    extends org.openuss.buddylist.InternalMessageDaoBase
{
    /**
     * @see org.openuss.buddylist.InternalMessageDao#toInternalMessageInfo(org.openuss.buddylist.InternalMessage, org.openuss.buddylist.InternalMessageInfo)
     */
    public void toInternalMessageInfo(
        org.openuss.buddylist.InternalMessage sourceEntity,
        org.openuss.buddylist.InternalMessageInfo targetVO)
    {
        // @todo verify behavior of toInternalMessageInfo
        super.toInternalMessageInfo(sourceEntity, targetVO);
        // WARNING! No conversion for targetVO.sender (can't convert sourceEntity.getSender():org.openuss.security.User to org.openuss.security.UserInfo
        // WARNING! No conversion for targetVO.recipient (can't convert sourceEntity.getRecipient():org.openuss.security.User to org.openuss.security.UserInfo
    }


    /**
     * @see org.openuss.buddylist.InternalMessageDao#toInternalMessageInfo(org.openuss.buddylist.InternalMessage)
     */
    public org.openuss.buddylist.InternalMessageInfo toInternalMessageInfo(final org.openuss.buddylist.InternalMessage entity)
    {
        // @todo verify behavior of toInternalMessageInfo
        return super.toInternalMessageInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.buddylist.InternalMessage loadInternalMessageFromInternalMessageInfo(org.openuss.buddylist.InternalMessageInfo internalMessageInfo)
    {
        // @todo implement loadInternalMessageFromInternalMessageInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.buddylist.loadInternalMessageFromInternalMessageInfo(org.openuss.buddylist.InternalMessageInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.buddylist.InternalMessage internalMessage = this.load(internalMessageInfo.getId());
        if (internalMessage == null)
        {
            internalMessage = org.openuss.buddylist.InternalMessage.Factory.newInstance();
        }
        return internalMessage;
        */
    }

    
    /**
     * @see org.openuss.buddylist.InternalMessageDao#internalMessageInfoToEntity(org.openuss.buddylist.InternalMessageInfo)
     */
    public org.openuss.buddylist.InternalMessage internalMessageInfoToEntity(org.openuss.buddylist.InternalMessageInfo internalMessageInfo)
    {
        // @todo verify behavior of internalMessageInfoToEntity
        org.openuss.buddylist.InternalMessage entity = this.loadInternalMessageFromInternalMessageInfo(internalMessageInfo);
        this.internalMessageInfoToEntity(internalMessageInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.buddylist.InternalMessageDao#internalMessageInfoToEntity(org.openuss.buddylist.InternalMessageInfo, org.openuss.buddylist.InternalMessage)
     */
    public void internalMessageInfoToEntity(
        org.openuss.buddylist.InternalMessageInfo sourceVO,
        org.openuss.buddylist.InternalMessage targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of internalMessageInfoToEntity
        super.internalMessageInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}