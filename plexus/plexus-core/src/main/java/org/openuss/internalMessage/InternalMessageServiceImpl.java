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
        LinkedList sentMessages = new LinkedList();
        if(imCenter==null){
        	imCenter = getInternalMessageCenterDao().create(user);
        	return sentMessages;
        }
        Set<InternalMessage> sentMessagesSet = imCenter.getSentInternalMessage();
        for(InternalMessage im : sentMessagesSet){
        	if(!im.getDeletedAtSender()){
        		InternalMessageInfo imInfo = getInternalMessageDao().toInternalMessageInfo(im);
        		List recipients = new LinkedList();
        		recipients.addAll(im.getRecipients());
        		getMessageStatusDao().toInternalMessageRecipientsInfoCollection(recipients);
        		imInfo.setInternalMessageRecipientsInfos(recipients);
        		sentMessages.add(imInfo);
        	}
        }
        return sentMessages;
    }

    /**
     * @see org.openuss.internalMessage.InternalMessageService#setRead(org.openuss.internalMessage.InternalMessageInfo)
     */
    protected void handleSetRead(org.openuss.internalMessage.InternalMessageInfo messageInfo)
        throws java.lang.Exception
    {
    	User user = getSecurityService().getCurrentUser();
        InternalMessageCenter imCenter = getInternalMessageCenterDao().findByUser(user);
        InternalMessage message = getInternalMessageDao().load(messageInfo.getId());
        System.out.println("markiere als gelesen: " + message.getContent());
        MessageStatus messageStatus = null;
        for(MessageStatus messageStatusCandidate : message.getRecipients()){
        	if(messageStatusCandidate.getRecipient() == imCenter){
        		messageStatus = messageStatusCandidate;
        		break;
        	}
        }
        if(messageStatus==null)
        	throw new Exception("Message not found");
        messageStatus.setMessageRead(true);
        getMessageStatusDao().update(messageStatus);
        getInternalMessageDao().update(message);
    }

    /**
     * @see org.openuss.internalMessage.InternalMessageService#getAllReceivedInternalMessages()
     */
    protected java.util.List handleGetAllReceivedInternalMessages()
        throws java.lang.Exception
    {
    	User user = getSecurityService().getCurrentUser();
        InternalMessageCenter imCenter = getInternalMessageCenterDao().findByUser(user);
        LinkedList<InternalMessageInfo> recMessages = new LinkedList();
        if(imCenter==null){
        	imCenter = getInternalMessageCenterDao().create(user);
        	return recMessages;
        }
        Set<MessageStatus> recMessagesSet = imCenter.getReceivedMessages();
        for(MessageStatus im : recMessagesSet){
        	if(!im.isDeleted()){
        		InternalMessageInfo imInfo = getInternalMessageDao().toInternalMessageInfo(im.getInternalMessage());
        		List recipients = new LinkedList();
        		recipients.addAll(im.getInternalMessage().getRecipients());
        		getMessageStatusDao().toInternalMessageRecipientsInfoCollection(recipients);
        		imInfo.setInternalMessageRecipientsInfos(recipients);
        		recMessages.add(imInfo);
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
    	User user = getSecurityService().getCurrentUser();
    	if(messageInfo.getSenderId() == user.getId()){
    		// User is sender
            InternalMessage message = getInternalMessageDao().load(messageInfo.getId());
            message.setDeletedAtSender(true);
    	} else {
    		// user is recipient
            InternalMessageCenter imCenter = getInternalMessageCenterDao().findByUser(user);
            InternalMessage message = getInternalMessageDao().load(messageInfo.getId());
            MessageStatus messageStatus = null;
            for(MessageStatus messageStatusCandidate : message.getRecipients()){
            	if(messageStatusCandidate.getRecipient() == imCenter){
            		messageStatus = messageStatusCandidate;
            		break;
            	}
            }
            if(messageStatus==null)
            	throw new Exception("Message not found");
            messageStatus.setDeleted(true);
    	}
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
		if (messageInfo.getMessageDate() == null)
			message.setMessageDate(new Date());
		else
			message.setMessageDate(messageInfo.getMessageDate());
		InternalMessageCenter sender = getInternalMessageCenterDao()
				.findByUser(getUserDao().load(messageInfo.getSenderId()));
		if(sender == null){
			sender = getInternalMessageCenterDao().findByUser(getSecurityService().getCurrentUser());
		}
		if(sender == null){
			sender = getInternalMessageCenterDao().create(getSecurityService().getCurrentUser());
		}
		message.setSender(sender);
		sender.getSentInternalMessage().add(message);
    	getInternalMessageDao().create(message);
    	for(InternalMessageRecipientsInfo rec : messageInfo.getInternalMessageRecipientsInfos()){
    		MessageStatus messageStatus = MessageStatus.Factory.newInstance();
    		messageStatus.setDeleted(false);
    		messageStatus.setMessageRead(false);
    		messageStatus.setInternalMessage(message);
    		InternalMessageCenter imCenter = getInternalMessageCenterDao().findByUser(getUserDao().load(rec.getRecipientId()));
    		if(imCenter == null){
    			imCenter = getInternalMessageCenterDao().create(getUserDao().load(rec.getRecipientId()));
    		}
    		messageStatus.setRecipient(imCenter);
    		imCenter.getReceivedMessages().add(messageStatus);
    		message.getRecipients().add(messageStatus);
    		getInternalMessageCenterDao().update(imCenter);
    		getMessageStatusDao().create(messageStatus);
    	}
    	getInternalMessageDao().update(message);
    }

	@Override
	protected int handleGetNumberOfUnreadMessages() throws Exception {
		List<InternalMessageInfo> list = this.getAllReceivedInternalMessages();
		int unreadMessages = 0;
		for(InternalMessageInfo imInfo : list){
			for(InternalMessageRecipientsInfo imrecInfo : imInfo.getInternalMessageRecipientsInfos()){
				if(imrecInfo.getRecipientId().equals(getSecurityService().getCurrentUser().getId())){
					if(!imrecInfo.isRead()){
						unreadMessages++;
					}
				}
			}
		}
		return unreadMessages;
	}

}