// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.mail;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 * @see org.openuss.mail.MailService
 */
public class MailServiceImpl
    extends org.openuss.mail.MailServiceBase
{
	private static final Logger logger = Logger.getLogger(MailServiceImpl.class);
	
	@Override
	protected void handleSendMails(String subject, String templateName, Map model, String eMail) throws Exception {
		logger.debug("MailService sendMails started");	
		//adding mail to db
		MailingJob mailingJob = new MailingJobImpl(); 
		mailingJob.setSenderName("System");
		mailingJob.setSendingTime(new Timestamp(System.currentTimeMillis()));
		mailingJob.setStatus(MailingStatus.PLANNED);
		getMailingJobDao().create(mailingJob);
		
		MailToSend mailToSend = new MailToSendImpl();
		mailToSend.setEmail(eMail);
		mailToSend.setSubject(subject);
		mailToSend.setTemplate(templateName);
		mailToSend.setJob(mailingJob);
		getMailToSendDao().create(mailToSend);

		convertMapToTemplateModels(model, mailToSend);		
		
	}

	private void convertMapToTemplateModels(Map model, MailToSend mailToSend) {
		TemplateModel templateModel;		
		Set keySet = model.keySet();		
		Iterator i = keySet.iterator();
		String key;
		while (i.hasNext()){
			//TODO use for other datatypes?		
			templateModel = new TemplateModelImpl();
			key = (String)i.next();
			templateModel.setModelName(key); 
			templateModel.setModelValue((String) model.get(key));
			templateModel.setMail(mailToSend);
			getTemplateModelDao().create(templateModel);		
		}
		
	}

	@Override
	protected List handleGetJobs() throws Exception {		 	 
 		ArrayList<MailingJob> list = (ArrayList<MailingJob>)getMailingJobDao().findJobs();			 
		Iterator iter = list.iterator();
		MailingJob mj;
		while (iter.hasNext()){
			 mj = (MailingJob)iter.next();
			 if (mj.getSendingTime().after(new Timestamp(System.currentTimeMillis()))) list.remove(mj);
		}
		return list;						
	}

	@Override
	protected List handleGetMailsToSendByMailingJob(Long mailingJobId) throws Exception {
		return getMailToSendDao().findByJob(mailingJobId);
	}

	@Override
	protected List handleGetTemplateModulsByMailToSend(Long mailToSendId) throws Exception {
		return getTemplateModelDao().findByMail(mailToSendId);
	}

	@Override
	protected void handleDeleteMailToSend(Long id) throws Exception {
		List l = getTemplateModelDao().findByMail(id);
		Iterator i = l.iterator();
		while (i.hasNext()){
			getTemplateModelDao().remove(((TemplateModel)i.next()).getId());
		}
		getMailToSendDao().remove(id);
	}

	@Override
	protected void handleDeleteMailingJob(Long id) throws Exception {
		List l = getMailToSendDao().findByJob(id);
		Iterator i = l.iterator();
		while (i.hasNext()){
			handleDeleteMailToSend(((MailToSend)i.next()).getId());			
		}
		getMailingJobDao().remove(id);
	}

	
}