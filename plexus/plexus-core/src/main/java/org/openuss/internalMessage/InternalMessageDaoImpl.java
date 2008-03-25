// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.internalMessage;
/**
 * @see org.openuss.internalMessage.InternalMessage
 */
public class InternalMessageDaoImpl
    extends org.openuss.internalMessage.InternalMessageDaoBase
{
    /**
     * @see org.openuss.internalMessage.InternalMessageDao#toInternalMessageInfo(org.openuss.internalMessage.InternalMessage, org.openuss.internalMessage.InternalMessageInfo)
     */
    public void toInternalMessageInfo(
        org.openuss.internalMessage.InternalMessage sourceEntity,
        org.openuss.internalMessage.InternalMessageInfo targetVO)
    {
        // @todo verify behavior of toInternalMessageInfo
        super.toInternalMessageInfo(sourceEntity, targetVO);
        targetVO.setSenderId(sourceEntity.getSender().getUser().getId());
        targetVO.setSenderImageId(sourceEntity.getSender().getUser().getImageId());
        targetVO.setSenderDisplayName(sourceEntity.getSender().getUser().getDisplayName());
    }


    /**
     * @see org.openuss.internalMessage.InternalMessageDao#toInternalMessageInfo(org.openuss.internalMessage.InternalMessage)
     */
    public org.openuss.internalMessage.InternalMessageInfo toInternalMessageInfo(final org.openuss.internalMessage.InternalMessage entity)
    {
        // @todo verify behavior of toInternalMessageInfo
    	InternalMessageInfo messageInfo = super.toInternalMessageInfo(entity);
    	messageInfo.setSenderDisplayName(entity.getSender().getUser().getDisplayName());
    	messageInfo.setSenderId(entity.getSender().getUser().getId());
        messageInfo.setSenderImageId(entity.getSender().getUser().getImageId());
        return messageInfo;
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.internalMessage.InternalMessage loadInternalMessageFromInternalMessageInfo(org.openuss.internalMessage.InternalMessageInfo internalMessageInfo)
    {
        org.openuss.internalMessage.InternalMessage internalMessage = this.load(internalMessageInfo.getId());
        if (internalMessage == null)
        {
            internalMessage = org.openuss.internalMessage.InternalMessage.Factory.newInstance();
        }
        return internalMessage;
    }

    
    /**
     * @see org.openuss.internalMessage.InternalMessageDao#internalMessageInfoToEntity(org.openuss.internalMessage.InternalMessageInfo)
     */
    public org.openuss.internalMessage.InternalMessage internalMessageInfoToEntity(org.openuss.internalMessage.InternalMessageInfo internalMessageInfo)
    {
        // @todo verify behavior of internalMessageInfoToEntity
        org.openuss.internalMessage.InternalMessage entity = this.loadInternalMessageFromInternalMessageInfo(internalMessageInfo);
        this.internalMessageInfoToEntity(internalMessageInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.internalMessage.InternalMessageDao#internalMessageInfoToEntity(org.openuss.internalMessage.InternalMessageInfo, org.openuss.internalMessage.InternalMessage)
     */
    public void internalMessageInfoToEntity(
        org.openuss.internalMessage.InternalMessageInfo sourceVO,
        org.openuss.internalMessage.InternalMessage targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of internalMessageInfoToEntity
        super.internalMessageInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}