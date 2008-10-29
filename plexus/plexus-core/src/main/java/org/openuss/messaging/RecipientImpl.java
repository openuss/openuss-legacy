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

	@Override
	public void setEmail(String email) {
		super.setEmail(StringUtils.abbreviate(email, 250));
	}

	@Override
	public void setSms(String sms) {
		super.setSms(StringUtils.abbreviate(sms, 250));
	}

}