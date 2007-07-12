// OpenUSS - Open Source University Support System
//
// Attention: Generated code! Do not modify by hand!
// Generated by: HibernateEntityImpl.vsl in in openuss/tools/andromda/templates.
//
package org.openuss.messaging;

import org.apache.commons.lang.StringUtils;

/**
 * @see org.openuss.messaging.Recipient
 * @author ingo dueppe
 */
public class RecipientImpl extends RecipientBase implements Recipient {

	private static final long serialVersionUID = -3940109635404398657L;
	
	public RecipientImpl() {
		setState(SendState.TOSEND);
	}

	public boolean hasSmsNotification() {
		return StringUtils.isNotBlank(getSms());
	}

}