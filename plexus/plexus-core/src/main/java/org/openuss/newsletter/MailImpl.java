// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.newsletter;

import org.apache.commons.lang.StringUtils;

/**
 * @see org.openuss.newsletter.Mail
 */
public class MailImpl extends MailBase implements Mail
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -7966428695284584773L;
    
    @Override
    public void setSubject(String subject) {
    	super.setSubject(StringUtils.abbreviate(subject, 250));
    }

}