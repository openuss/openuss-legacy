// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.buddylist;

import java.util.ArrayList;

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
    	ArrayList messages = new ArrayList();
    	messages.addAll(user.getSentMessages());
    	return messages;
    }

    /**
     * @see org.openuss.buddylist.InternalMessageService#setRead(org.openuss.buddylist.InternalMessageInfo)
     */
    protected void handleSetRead(org.openuss.buddylist.InternalMessageInfo message)
        throws java.lang.Exception
    {
        getInternalMessageDao().load(message.getId()).setMessageReadByRecipient(true);
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
    protected void handleDeleteInternalMessage(org.openuss.buddylist.InternalMessageInfo message, boolean deleteAtSender)
        throws java.lang.Exception
    {
        // @todo implement protected void handleDeleteInternalMessage(org.openuss.buddylist.InternalMessageInfo message)
        throw new java.lang.UnsupportedOperationException("org.openuss.buddylist.InternalMessageService.handleDeleteInternalMessage(org.openuss.buddylist.InternalMessageInfo message) Not implemented!");
    }

    /**
     * @see org.openuss.buddylist.InternalMessageService#sendInternalMessage(org.openuss.buddylist.InternalMessageInfo)
     */
    protected void handleSendInternalMessage(org.openuss.buddylist.InternalMessageInfo message)
        throws java.lang.Exception
    {
    	InternalMessage internalMessage = getInternalMessageDao().create(message.getContent(), message.getMessageDate(), message.isMessageRead(), getUserDao().load(message.getRecipient().getId()), getUserDao().load(message.getSender().getId()), message.getSubject());
    	internalMessage.getSender().getSentMessages().add(internalMessage);
        internalMessage.getRecipient().getReceivedMessages().add(internalMessage);
    }

}