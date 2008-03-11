// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.internalMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
        Collections.sort(sentMessages, new InternalMessageInfoComparator());
        return sentMessages;
    }

    /**
     * @see org.openuss.internalMessage.InternalMessageService#setRead(org.openuss.internalMessage.InternalMessageInfo)
     */
    protected void handleSetRead(org.openuss.internalMessage.InternalMessageInfo messageInfo)
        throws InternalMessageServiceException
    {
    	User user = getSecurityService().getCurrentUser();
        InternalMessageCenter imCenter = getInternalMessageCenterDao().findByUser(user);
        InternalMessage message = getInternalMessageDao().load(messageInfo.getId());
        MessageStatus messageStatus = null;
        for(MessageStatus messageStatusCandidate : message.getRecipients()){
        	if(messageStatusCandidate.getRecipient() == imCenter){
        		messageStatus = messageStatusCandidate;
        		break;
        	}
        }
        if(messageStatus==null){
        	throw new InternalMessageServiceException("Message not found");
        }
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
        		imInfo.setReadByCurrentUser(im.isMessageRead());
        		List recipients = new LinkedList();
        		recipients.addAll(im.getInternalMessage().getRecipients());
        		getMessageStatusDao().toInternalMessageRecipientsInfoCollection(recipients);
        		imInfo.setInternalMessageRecipientsInfos(recipients);
        		recMessages.add(imInfo);
        	}
        }
        //sort recMessages
        Collections.sort(recMessages, new InternalMessageInfoComparator());
        return recMessages;
    }
    
    /**
     * Comparator for List of InternalMessageInfo objects.
     * Sorts by message-date
     */
    private class InternalMessageInfoComparator implements java.util.Comparator<InternalMessageInfo>{

		public int compare(InternalMessageInfo o1, InternalMessageInfo o2) {
			if(o1.getMessageDate().before(o2.getMessageDate()))
				return 1;
			if(o1.getMessageDate().before(o2.getMessageDate()))
				return -1;
			return 0;
		}
    }

    /**
     * @see org.openuss.internalMessage.InternalMessageService#deleteInternalMessage(org.openuss.internalMessage.InternalMessageInfo)
     */
    protected void handleDeleteInternalMessage(org.openuss.internalMessage.InternalMessageInfo messageInfo)
        throws java.lang.Exception
    {
    	User user = getSecurityService().getCurrentUser();
        InternalMessage message = getInternalMessageDao().load(messageInfo.getId());
    	if(messageInfo.getSenderId().equals(user.getId())){
    		// User is sender
            message.setDeletedAtSender(true);
    	} else {
    		// user is recipient
            InternalMessageCenter imCenter = getInternalMessageCenterDao().findByUser(user);
            for(MessageStatus messageStatusCandidate : message.getRecipients()){
            	if(messageStatusCandidate.getRecipient() == imCenter){
            		messageStatusCandidate.setDeleted(true);
            		getMessageStatusDao().update(messageStatusCandidate);
            	}
            }
    	}
    }

    /**
     * @see org.openuss.internalMessage.InternalMessageService#sendInternalMessage(org.openuss.internalMessage.InternalMessageInfo)
     */
    protected void handleSendInternalMessage(org.openuss.internalMessage.InternalMessageInfo messageInfo)
        throws InternalMessageServiceException
    {
    	InternalMessage message = InternalMessage.Factory.newInstance();
//    	//replace here? better: Converter --> ask ingo
//    	messageInfo.setContent(StringUtils.replace(messageInfo.getContent().trim(), "\n", "<br/>"));
    	message.setContent(messageInfo.getContent());
    	message.setSubject(messageInfo.getSubject());
		if (messageInfo.getMessageDate() == null){
			message.setMessageDate(new Date());
		}
		else{
			message.setMessageDate(messageInfo.getMessageDate());
		}
		InternalMessageCenter sender = null;
		if (messageInfo.getSenderId() != null) {
			sender = getInternalMessageCenterDao().findByUser(
					getUserDao().load(messageInfo.getSenderId()));
		} else {
			sender = getInternalMessageCenterDao().findByUser(
					getSecurityService().getCurrentUser());
		}
		if (sender == null) {
			sender = getInternalMessageCenterDao().create(
					getSecurityService().getCurrentUser());
		}
		message.setSender(sender);
		sender.getSentInternalMessage().add(message);
    	getInternalMessageDao().create(message);
    	message.setRecipients(new ArrayList<MessageStatus>());
    	if(messageInfo.getInternalMessageRecipientsInfos() == null || messageInfo.getInternalMessageRecipientsInfos().size() == 0){
    		throw new InternalMessageServiceException("No Recipients found");
    	}
    	for(InternalMessageRecipientsInfo rec : messageInfo.getInternalMessageRecipientsInfos()){
    		if(rec.getRecipientId().equals(sender.getUser().getId())){
    			throw new InternalMessageServiceException("You cannot send a message to yourself");
    		}
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
	protected int handleGetNumberOfUnreadMessages() {
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