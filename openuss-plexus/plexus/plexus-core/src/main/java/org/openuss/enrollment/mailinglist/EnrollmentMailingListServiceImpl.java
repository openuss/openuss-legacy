// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.enrollment.mailinglist;

import org.openuss.lecture.EnrollmentInfo;
import org.openuss.mailinglist.MailDetail;
import org.openuss.mailinglist.MailInfo;
import org.openuss.mailinglist.MailingListInfo;
import org.openuss.security.User;

/**
 * @see org.openuss.enrollment.mailinglist.EnrollmentMailingListService
 */
public class EnrollmentMailingListServiceImpl extends EnrollmentMailingListServiceBase
{

	protected MailingListInfo handleGetMailingList(EnrollmentInfo enrollment){
		MailingListInfo mailingList = getMailingListService().getMailingList(enrollment);
		if (mailingList == null){
			addMailingList(enrollment);
			mailingList = getMailingListService().getMailingList(enrollment);
		}
		if (mailingList.getName().equals(enrollment.getName())){
			mailingList.setName(enrollment.getName());
			getMailingListService().updateMailingList(mailingList);
		}
		return mailingList;
	}
	
    /**
     * @see org.openuss.enrollment.mailinglist.EnrollmentMailingListService#sendPreview(org.openuss.lecture.EnrollmentInfo, org.openuss.mailinglist.MailDetail)
     */
    protected void handleSendPreview(EnrollmentInfo enrollment, MailDetail mail)
        throws java.lang.Exception
    {
    	MailingListInfo mailingList = getMailingList(enrollment);
    	getMailingListService().sendPreview(mailingList, mail);
    }

    /**
     * @see org.openuss.enrollment.mailinglist.EnrollmentMailingListService#getMails(org.openuss.lecture.EnrollmentInfo)
     */
    protected java.util.List handleGetMails(EnrollmentInfo enrollment)
        throws java.lang.Exception
    {
    	MailingListInfo mailingList = getMailingList(enrollment);
    	return getMailingListService().getMails(mailingList, false);
    }

    /**
     * @see org.openuss.enrollment.mailinglist.EnrollmentMailingListService#getMail(org.openuss.mailinglist.MailInfo)
     */
    protected org.openuss.mailinglist.MailDetail handleGetMail(MailInfo mail)
        throws java.lang.Exception
    {
        return getMailingListService().getMail(mail);
    }

    /**
     * @see org.openuss.enrollment.mailinglist.EnrollmentMailingListService#sendMail(org.openuss.lecture.EnrollmentInfo, org.openuss.mailinglist.MailDetail)
     */
    protected void handleSendMail(EnrollmentInfo enrollment, MailDetail mail)
        throws java.lang.Exception
    {
    	MailingListInfo mailingList = getMailingList(enrollment);
    	getMailingListService().sendMail(mailingList, mail);        
    }

    /**
     * @see org.openuss.enrollment.mailinglist.EnrollmentMailingListService#subscribe(org.openuss.lecture.EnrollmentInfo, org.openuss.security.User)
     */
    protected void handleSubscribe(EnrollmentInfo enrollment, User user)
        throws java.lang.Exception
    {
    	MailingListInfo mailingList = getMailingList(enrollment);
    	getMailingListService().subscribe(mailingList, user);
    }

    /**
     * @see org.openuss.enrollment.mailinglist.EnrollmentMailingListService#unsubscribe1(org.openuss.lecture.EnrollmentInfo, org.openuss.security.User)
     */
    protected void handleUnsubscribe(EnrollmentInfo enrollment, User user)
        throws java.lang.Exception
    {
    	MailingListInfo mailingList = getMailingList(enrollment);
    	getMailingListService().unsubscribe(mailingList, user);
    }

    /**
     * @see org.openuss.enrollment.mailinglist.EnrollmentMailingListService#unsubscribe(org.openuss.mailinglist.SubscriberInfo)
     */
    protected void handleUnsubscribe(org.openuss.mailinglist.SubscriberInfo subscriber)
        throws java.lang.Exception
    {
    	getMailingListService().unsubscribe(subscriber);
    }

    /**
     * @see org.openuss.enrollment.mailinglist.EnrollmentMailingListService#setBlockingState(org.openuss.mailinglist.SubscriberInfo)
     */
    protected void handleSetBlockingState(org.openuss.mailinglist.SubscriberInfo subscriber)
        throws java.lang.Exception
    {
    	getMailingListService().setBlockingState(subscriber);
    }

    /**
     * @see org.openuss.enrollment.mailinglist.EnrollmentMailingListService#getSubscribers(org.openuss.lecture.EnrollmentInfo)
     */
    protected java.util.List handleGetSubscribers(org.openuss.lecture.EnrollmentInfo enrollment)
        throws java.lang.Exception
    {
    	MailingListInfo mailingList = getMailingList(enrollment);
    	return getMailingListService().getSubscribers(mailingList);
    }

    /**
     * @see org.openuss.enrollment.mailinglist.EnrollmentMailingListService#saveMail(org.openuss.lecture.EnrollmentInfo, org.openuss.mailinglist.MailDetail)
     */
    protected void handleSaveMail(org.openuss.lecture.EnrollmentInfo enrollment, org.openuss.mailinglist.MailDetail mail)
        throws java.lang.Exception
    {
    	MailingListInfo mailingList = getMailingList(enrollment);
    	getMailingListService().saveMail(mailingList, mail);
    }

    /**
     * @see org.openuss.enrollment.mailinglist.EnrollmentMailingListService#deleteMail(org.openuss.lecture.EnrollmentInfo, org.openuss.mailinglist.MailDetail)
     */
    protected void handleDeleteMail(org.openuss.lecture.EnrollmentInfo enrollment, org.openuss.mailinglist.MailDetail mail)
        throws java.lang.Exception
    {
    	MailingListInfo mailingList = getMailingList(enrollment);
    	getMailingListService().deleteMail(mailingList, mail);
    }

	@Override
	protected void handleAddMailingList(EnrollmentInfo enrollment) throws Exception {
		getMailingListService().addMailingList(enrollment, enrollment.getName());
	}

	@Override
	protected void handleUpdateMailingList(MailingListInfo mailingList) throws Exception {
		getMailingListService().updateMailingList(mailingList);
	}

	@Override
	protected void handleUpdateMail(EnrollmentInfo enrollment, MailDetail mail) throws Exception {
		getMailingListService().updateMail(enrollment, mail);		
	}

	@Override
	protected String handleExportSubscribers(EnrollmentInfo enrollment) throws Exception {
		return getMailingListService().exportSubscribers(getMailingList(enrollment));
	}

}