// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.mailinglist;

/**
 * @see org.openuss.mailinglist.Mail
 */
public class MailImpl extends MailBase implements Mail
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -7966428695284584773L;

    /**
     * @see org.openuss.mailinglist.Mail#sendCount()
     */
    public int sendCount()
    {
        return this.getJob().getSend();
    }

    /**
     * @see org.openuss.mailinglist.Mail#toSendCount()
     */
    public int toSendCount()
    {
    	return this.getJob().getToSend();
    }

    /**
     * @see org.openuss.mailinglist.Mail#errorCount()
     */
    public int errorCount()
    {
    	return this.getJob().getError();
    }

}