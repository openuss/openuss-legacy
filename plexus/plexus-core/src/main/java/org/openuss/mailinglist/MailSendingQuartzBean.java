package org.openuss.mailinglist;

import java.io.Serializable;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.openuss.framework.mail.MailEngine;
import org.openuss.messaging.MessageService;
import org.openuss.security.SecurityService;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailSendingQuartzBean implements Serializable{

	private static final long serialVersionUID = 6988073826486603295L;

	private static final Logger logger = Logger.getLogger(MailSendingQuartzBean.class);
	
	private transient MailEngine mailEngine;
	private transient MessageService messageService;
	private transient MimeMessageHelper mimeMessageHelper;
	
	private transient SecurityService securityService;
	
	public void send() throws MessagingException{
//		ArrayList<MessageJob> jobs = (ArrayList<MessageJob>) messageService.getJobs();		
//		ArrayList<MailToSend> mails;
//		Iterator jobIterator = jobs.iterator();
//		Iterator mailIterator;
//		MailingJob mj;
//		MailToSend mts;
//		while (jobIterator.hasNext()){
//			mj = (MailingJob)jobIterator.next();			
//			mails = (ArrayList<MailToSend>)messageService.getMailsToSendByMailingJob(mj.getId());
//			mailIterator = mails.iterator();
//			while(mailIterator.hasNext()){
//				mts = (MailToSend)mailIterator.next();
//				sendMail(mts);
//				messageService.deleteMailToSend(mts.getId()); //only needed, if mailingjobs are splitted
//			}
//			// deletion of mailingJobs only if no more mailsToSend -> due to possible splitting of jobs in future
//			if (messageService.getMailsToSendByMailingJob(mj.getId()).size()==0) messageService.deleteMailingJob(mj.getId());
//		}
//		logger.debug("Mailsending job was finished successfully.");
	}

//	private void sendMail(MailToSend mts) throws MessagingException{
//		mimeMessageHelper.setTo(mts.getEmail());					
//		mimeMessageHelper.setSubject(mts.getSubject());					
//		Template template = messageService.getTemplateByMailToSend(mts);		
//		
//		List modelList = messageService.getTemplateModelsByTemplate(template.getId());
//			
//		Iterator i = modelList.iterator();
//		Map map = new HashMap();
//		TemplateModel tm;
//		while (i.hasNext()){
//			tm = (TemplateModel)i.next();
//			map.put(tm.getModelName(), tm.getModelValue());
//		}
//		String locale = "";
//		try{
//			UserImpl recipient = (UserImpl) securityService.getUserByEmail(mts.getEmail());
//			//TODO check how else to get the user-locale instead of using getPreferences
//			locale = recipient.getPreferences().getLocale();
//		}
//		catch (Exception e){
//			// fails, because emailadresses are not unique in testing db
//			locale = "de";
//		}
//		mailEngine.sendMessage(mimeMessageHelper.getMimeMessage(), template.getTemplate(), map, locale);		
//	}
	
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

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
}