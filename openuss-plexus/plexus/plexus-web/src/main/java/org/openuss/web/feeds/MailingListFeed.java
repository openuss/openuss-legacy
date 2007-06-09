// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.web.feeds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.openuss.enrollment.mailinglist.EnrollmentMailingListService;
import org.openuss.lecture.Enrollment;
import org.openuss.lecture.EnrollmentInfo;
import org.openuss.lecture.EnrollmentService;
import org.openuss.mailinglist.MailDetail;
import org.openuss.mailinglist.MailInfo;
import org.openuss.system.SystemProperties;
import org.openuss.system.SystemService;

public class MailingListFeed extends AbstractFeed{
	

	private transient EnrollmentService enrollmentService;
	
	private transient SystemService systemService;
	
	private transient EnrollmentMailingListService enrollmentMailingListService;
	
	public static final Logger logger = Logger.getLogger(MailingListFeed.class);

	private FeedWrapper buildFeedArray(EnrollmentInfo enrollment) {
		final List entries = new ArrayList();
		MailInfo mailInfo;
		MailDetail mailDetail;
		String link;
		FeedWrapper feedWrapper = new FeedWrapper();
		List mails = getEnrollmentMailingListService().getMails(enrollment);

		if (mails!=null&&mails.size()!=0){ 
			Collections.reverse(mails);
			Iterator i = mails.iterator();
			while (i.hasNext()) {  
				mailInfo = (MailInfo) i.next();
				link = getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+"views/public/mailinglist/showmail.faces?mail="+mailInfo.getId();
				mailDetail = getEnrollmentMailingListService().getMail(mailInfo);
				this.addEntry(entries, mailDetail.getSubject(), link, mailDetail.getSendDate(), mailDetail.getText(), enrollment.getName(), enrollment.getName());
			}
			mailDetail = getEnrollmentMailingListService().getMail((MailInfo)mails.get(0));
			feedWrapper.setLastModified(mailDetail.getSendDate());
		}	
			
		link = systemService.getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+"views/secured/mailinglist/mailinglist.faces?enrollment="+enrollment.getId();
		
		feedWrapper.setWriter(this.convertToXml(enrollment.getName(), link, enrollment.getDescription(), systemService.getProperty(SystemProperties.COPYRIGHT).getValue(), entries));
		
		return feedWrapper;		
	}	
	
    /**
     * @see org.openuss.feed.FeedService#getRssFeedForEnrollment(org.openuss.lecture.EnrollmentInfo)
     */
    public FeedWrapper getFeed(Long enrollmentId)        
    {
    	if (enrollmentId==null||enrollmentId==0) return null;
    	Enrollment e = Enrollment.Factory.newInstance();
    	e.setId(enrollmentId);    	
    	EnrollmentInfo enrollment = getEnrollmentService().getEnrollmentInfo(getEnrollmentService().getEnrollment(e));
    	if (enrollment==null) return null;

        return buildFeedArray(enrollment);
    }

	public SystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public EnrollmentService getEnrollmentService() {
		return enrollmentService;
	}

	public void setEnrollmentService(EnrollmentService enrollmentService) {
		this.enrollmentService = enrollmentService;
	}

	public EnrollmentMailingListService getEnrollmentMailingListService() {
		return enrollmentMailingListService;
	}

	public void setEnrollmentMailingListService(
			EnrollmentMailingListService enrollmentMailingListService) {
		this.enrollmentMailingListService = enrollmentMailingListService;
	}

}