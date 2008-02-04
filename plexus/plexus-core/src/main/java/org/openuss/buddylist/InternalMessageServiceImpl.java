// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.buddylist;

import java.util.ArrayList;
import java.util.Set;

import org.openuss.security.User;

/**
 * @see org.openuss.buddylist.InternalMessageService
 */
public class InternalMessageServiceImpl
    extends org.openuss.buddylist.InternalMessageServiceBase
{

    /**
     * @see org.openuss.buddylist.InternalMessageService#getAllInternalMessages(org.openuss.security.User)
     */
    protected java.util.List handleGetAllInternalMessages(org.openuss.security.User user)
        throws java.lang.Exception
    {
    	Set messageSet = user.getSentMessages();
    	ArrayList messages = new ArrayList();
    	messages.addAll(messageSet);    	
    	getInternalMessageDao().toInternalMessageInfoCollection(messages);
    	return messages;
    }

    /**
     * @see org.openuss.buddylist.InternalMessageService#setRead(org.openuss.buddylist.InternalMessageInfo)
     */
    protected void handleSetRead(org.openuss.buddylist.InternalMessageInfo message)
        throws java.lang.Exception
    {
        InternalMessage iMessage = getInternalMessageDao().load(message.getId());
        iMessage.setMessageReadByRecipient(true);
    }

    /**
     * @see org.openuss.buddylist.InternalMessageService#getAllReceivedInternalMessages(org.openuss.security.User)
     */
    protected java.util.List handleGetAllReceivedInternalMessages(org.openuss.security.User user)
        throws java.lang.Exception
    {
    	ArrayList messages = new ArrayList();
    	messages.addAll(user.getReceivedMessages());
    	return messages;
    }

    /**
     * @see org.openuss.buddylist.InternalMessageService#deleteInternalMessage(org.openuss.buddylist.InternalMessageInfo)
     */
    protected void handleDeleteInternalMessage(org.openuss.buddylist.InternalMessageInfo messageInfo, boolean deleteAtSender)
        throws java.lang.Exception
    {
    	InternalMessage message = getInternalMessageDao().load(messageInfo.getId());
    	if(deleteAtSender){
     		message.getSender().getSentMessages().remove(message);
    	} else {
    		message.getRecipient().getReceivedMessages().remove(message);
    	}
    }

    /**
     * @see org.openuss.buddylist.InternalMessageService#sendInternalMessage(org.openuss.buddylist.InternalMessageInfo)
     */
    protected void handleSendInternalMessage(org.openuss.buddylist.InternalMessageInfo message)
        throws java.lang.Exception
    {
    	InternalMessage internalMessage = getInternalMessageDao().create(message.getContent(), message.getMessageDate(), message.isMessageReadByRecipient(), getUserDao().load(message.getRecipientId()), getUserDao().load(message.getSenderId()), message.getSubject());
    	internalMessage.getSender().getSentMessages().add(internalMessage);
    	internalMessage.getRecipient().getReceivedMessages().add(internalMessage);
    }

}