package org.openuss.web.mail;

import org.apache.shale.tiger.managed.Property;
import org.openuss.enrollment.mailinglist.EnrollmentMailingListService;
import org.openuss.web.enrollment.AbstractEnrollmentPage;

public class AbstractMailingListPage extends AbstractEnrollmentPage{

	@Property(value = "#{enrollmentMailingListService}")
	protected EnrollmentMailingListService enrollmentMailingListService;

	public EnrollmentMailingListService getEnrollmentMailingListService() {
		return enrollmentMailingListService;
	}

	public void setEnrollmentMailingListService(
			EnrollmentMailingListService enrollmentMailingListService) {
		this.enrollmentMailingListService = enrollmentMailingListService;
	}
	

}