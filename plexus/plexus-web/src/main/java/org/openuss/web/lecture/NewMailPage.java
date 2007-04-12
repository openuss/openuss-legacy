package org.openuss.web.lecture;

import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.apache.tools.mail.MailMessage;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;
import org.openuss.web.enrollment.AbstractEnrollmentPage;

@Bean(name = "views$secured$enrollment$newmail", scope = Scope.REQUEST)
@View
public class NewMailPage extends AbstractEnrollmentPage {
	private static final Logger logger = Logger.getLogger(NewMailPage.class);
	
	@Property(value = "#{mailingMessage}")
	MailMessage mailMessage;
	
//	@Property(value = "#{mailingJob}")
//	MailingJob mailingJob;
	
	@Preprocess
	public void preprocess() throws LectureException {
//		if (mailMessage==null){
//			mailMessage = org.openuss.mail.MailMessage.Factory.newInstance();
//			mailMessage.setMailMessage("example message");			
//		}
//		if (mailingJob==null){
//			mailingJob = org.openuss.mail.MailingJob.Factory.newInstance();
//			mailingJob.setMailTitle("MailTitle");
//			mailingJob.setSendingTime(new Timestamp(System.currentTimeMillis()));			
//		}
	}
	
	@Prerender
	public void prerender() throws LectureException {
//		if (mailMessage==null){
//			mailMessage = org.openuss.mail.MailMessage.Factory.newInstance();
//			mailMessage.setMailMessage("example message");			
//		}
//		if (mailingJob==null){
//			mailingJob = org.openuss.mail.MailingJob.Factory.newInstance();
//			mailingJob.setMailTitle("MailTitle");
//			mailingJob.setSendingTime(new Timestamp(System.currentTimeMillis()));			
//		}
	}

//	public MailingJob getMailingJob() {
//		return mailingJob;
//	}
//
//	public void setMailingJob(MailingJob mailingJob) {
//		this.mailingJob = mailingJob;
//	}

	public MailMessage getMailMessage() {
		return mailMessage;
	}

	public void setMailMessage(MailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}

	public String send() {
		logger.debug("MailingList send Item");
		return Constants.SUCCESS;
	}

	public String sendDraft(){
		logger.debug("MailingList change Item");
		return Constants.SUCCESS;
	}
	
	public String saveDraft() {
		logger.debug("MailingList delete Item");
		return Constants.SUCCESS;
	}	
		

}
