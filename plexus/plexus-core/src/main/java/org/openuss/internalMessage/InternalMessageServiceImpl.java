// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.internalMessage;

import java.util.*;

import org.openuss.security.User;

/**
 * @see org.openuss.internalMessage.InternalMessageService
 */
public class InternalMessageServiceImpl
    extends org.openuss.internalMessage.InternalMessageServiceBase
{

    /**
     * @see org.openuss.internalMessage.InternalMessageService#getAllSentInternalMessages()
     */
    protected java.util.List handleGetAllSentInternalMessages()
        throws java.lang.Exception
    {
        User user = getSecurityService().getCurrentUser();
        InternalMessageCenter imCenter = getInternalMessageCenterDao().findByUser(user);
        if(imCenter==null){
        	imCenter = getInternalMessageCenterDao().create(user);
        }
        Set<InternalMessage> sentMessagesSet = imCenter.getSentInternalMessage();
        LinkedList sentMessages = new LinkedList();
        for(InternalMessage im : sentMessagesSet){
        	sentMessages.add(getInternalMessageDao().toInternalMessageInfo(im));
        }
        return sentMessages;
    }

    /**
     * @see org.openuss.internalMessage.InternalMessageService#setRead(org.openuss.internalMessage.InternalMessageInfo)
     */
    protected void handleSetRead(org.openuss.internalMessage.InternalMessageInfo message)
        throws java.lang.Exception
    {
        // @todo implement protected void handleSetRead(org.openuss.internalMessage.InternalMessageInfo message)
        throw new java.lang.UnsupportedOperationException("org.openuss.internalMessage.InternalMessageService.handleSetRead(org.openuss.internalMessage.InternalMessageInfo message) Not implemented!");
    }

    /**
     * @see org.openuss.internalMessage.InternalMessageService#getAllReceivedInternalMessages()
     */
    protected java.util.List handleGetAllReceivedInternalMessages()
        throws java.lang.Exception
    {
    	User user = getSecurityService().getCurrentUser();
        InternalMessageCenter imCenter = getInternalMessageCenterDao().findByUser(user);
        if(imCenter==null){
        	imCenter = getInternalMessageCenterDao().create(user);
        }
        Set<MessageStatus> recMessagesSet = imCenter.getReceivedMessages();
        LinkedList recMessages = new LinkedList();
        for(MessageStatus im : recMessagesSet){
        	if(!im.isDeleted()){
        		recMessages.add(getInternalMessageDao().toInternalMessageInfo(im.getInternalMessage()));
        	}
        }
        return recMessages;
    }

    /**
     * @see org.openuss.internalMessage.InternalMessageService#deleteInternalMessage(org.openuss.internalMessage.InternalMessageInfo)
     */
    protected void handleDeleteInternalMessage(org.openuss.internalMessage.InternalMessageInfo messageInfo)
        throws java.lang.Exception
    {
        // @todo implement protected void handleDeleteInternalMessage(org.openuss.internalMessage.InternalMessageInfo messageInfo)
        throw new java.lang.UnsupportedOperationException("org.openuss.internalMessage.InternalMessageService.handleDeleteInternalMessage(org.openuss.internalMessage.InternalMessageInfo messageInfo) Not implemented!");
    }

    /**
     * @see org.openuss.internalMessage.InternalMessageService#sendInternalMessage(org.openuss.internalMessage.InternalMessageInfo)
     */
    protected void handleSendInternalMessage(org.openuss.internalMessage.InternalMessageInfo messageInfo)
        throws java.lang.Exception
    {
    	InternalMessage message = InternalMessage.Factory.newInstance();
    	message.setContent(messageInfo.getContent());
    	message.setSubject(messageInfo.getSubject());
    	message.setMessageDate(messageInfo.getMessageDate());
    	InternalMessageCenter sender = getInternalMessageCenterDao().findByUser(getUserDao().load(messageInfo.getSenderId()));
    	message.setSender(sender);
    	sender.getSentInternalMessage().add(message);
    	for(InternalMessageRecipientsInfo rec : messageInfo.getInternalMessageRecipientsInfos()){
    		MessageStatus messageStatus = MessageStatus.Factory.newInstance();
    		messageStatus.setDeleted(false);
    		messageStatus.setMessageRead(false);
    		messageStatus.setInternalMessage(message);
    		messageStatus.setRecipient(getInternalMessageCenterDao().findByUser(getUserDao().load(rec.getRecipientId())));
    		message.getRecipients().add(messageStatus);
    	}
    	getInternalMessageDao().create(message);
    }

}