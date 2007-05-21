package org.openuss.web.mail;

import org.apache.shale.tiger.managed.Property;
import org.openuss.enrollment.mailinglist.EnrollmentMailingListService;
import org.openuss.mailinglist.MailDetail;
import org.openuss.mailinglist.MailingListInfo;
import org.openuss.web.enrollment.AbstractEnrollmentPage;
import org.openuss.web.Constants;
public class AbstractMailingListPage extends AbstractEnrollmentPage{

	@Property(value = "#{enrollmentMailingListService}")
	protected EnrollmentMailingListService enrollmentMailingListService;
	
	@Property(value = "#{" + Constants.MAILINGLIST_MAIL + "}")
	protected MailDetail mail;
	
	@Property(value = "#{" + Constants.MAILINGLIST_MAILINGLIST + "}")
	protected MailingListInfo mailingList;

	public EnrollmentMailingListService getEnrollmentMailingListService() {
		return enrollmentMailingListService;
	}

	public void setEnrollmentMailingListService(
			EnrollmentMailingListService enrollmentMailingListService) {
		this.enrollmentMailingListService = enrollmentMailingListService;
	}

	public MailDetail getMail() {
		return mail;
	}

	public void setMail(MailDetail mail) {
		this.mail = mail;
	}

	public MailingListInfo getMailingList() {
		return mailingList;
	}

	public void setMailingList(MailingListInfo mailingList) {
		this.mailingList = mailingList;
	}
	

}