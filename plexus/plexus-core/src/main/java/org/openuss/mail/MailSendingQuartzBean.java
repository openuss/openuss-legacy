package org.openuss.mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import org.apache.log4j.Logger;
import org.openuss.framework.mail.MailEngine;
import org.openuss.security.SecurityService;
import org.openuss.security.UserImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailSendingQuartzBean{
	private static final Logger logger = Logger.getLogger(MailSendingQuartzBean.class);
	
	private MailEngine mailEngine;
	private MailService mailService;
	private MimeMessageHelper mimeMessageHelper;
	
	private SecurityService securityService;
	
	public void send() throws MessagingException{
		ArrayList<MailingJob> jobs = (ArrayList<MailingJob>) mailService.getJobs();		
		ArrayList<MailToSend> mails;
		Iterator jobIterator = jobs.iterator();
		Iterator mailIterator;
		MailingJob mj;
		MailToSend mts;
		while (jobIterator.hasNext()){
			mj = (MailingJob)jobIterator.next();			
			mails = (ArrayList<MailToSend>)mailService.getMailsToSendByMailingJob(mj.getJobId());
			mailIterator = mails.iterator();
			while(mailIterator.hasNext()){
				mts = (MailToSend)mailIterator.next();
				sendMail(mts);
				mailService.deleteMailToSend(mts.getId()); //only needed, if mailingjobs are splitted
			}
			// deletion of mailingJobs only if no more mailsToSend -> due to possible splitting of jobs in future
			if (mailService.getMailsToSendByMailingJob(mj.getJobId()).size()==0) mailService.deleteMailingJob(mj.getJobId());
		}
		logger.debug("Mailsending job was finished successfully.");
	}

	private void sendMail(MailToSend mts) throws MessagingException{
		mimeMessageHelper.setTo(mts.getEmail());					
		mimeMessageHelper.setSubject(mts.getSubject());					
		Template template = mailService.getTemplateByMailToSend(mts);		
		
		List modelList = mailService.getTemplateModelsByTemplate(template.getId());
			
		Iterator i = modelList.iterator();
		Map map = new HashMap();
		TemplateModel tm;
		while (i.hasNext()){
			tm = (TemplateModel)i.next();
			map.put(tm.getModelName(), tm.getModelValue());
		}
		String locale = "";
		try{
			UserImpl recipient = (UserImpl) securityService.getUserByEmail(mts.getEmail());
			//TODO check how else to get the user-locale instead of using getPreferences
			locale = recipient.getPreferences().getLocale();
		}
		catch (Exception e){
			// fails, because emailadresses are not unique in testing db
			locale = "de";
		}
		mailEngine.sendMessage(mimeMessageHelper.getMimeMessage(), template.getTemplate(), map, locale);		
	}
	
	public MailEngine getMailEngine() {
		return mailEngine;
	}

	public void setMailEngine(MailEngine mailEngine) {
		this.mailEngine = mailEngine;
	}

	public MimeMessageHelper getMimeMessageHelper() {
		return mimeMessageHelper;
	}

	public void setMimeMessageHelper(MimeMessageHelper mimeMessageHelper) {
		this.mimeMessageHelper = mimeMessageHelper;
	}

	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
}