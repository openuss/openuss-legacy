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
		//save mailingJob
		MailingJob mailingJob = new MailingJobImpl(); 
		mailingJob.setSenderName("System");
		mailingJob.setSendingTime(new Timestamp(System.currentTimeMillis()));
		mailingJob.setMailTitle(subject);
		getMailingJobDao().create(mailingJob);
		//save used template
		Template template = Template.Factory.newInstance();
		template.setTemplate(templateName);
		getTemplateDao().create(template);
		//save template model
		convertMapToTemplateModels(model, template);		
		//save MailToSend		
		MailToSend mailToSend = new MailToSendImpl();
		mailToSend.setEmail(eMail);
		mailToSend.setSubject(subject);		
		mailToSend.setStatus(MailingStatus.PLANNED);
		mailToSend.setJob(mailingJob);
		mailToSend.setMailBody(template);
		getMailToSendDao().create(mailToSend);		
	}

	private void convertMapToTemplateModels(Map model, Template template) {
		List<TemplateModel> templates = new ArrayList<TemplateModel>();
		for (Map.Entry<String, String> entry: (Set<Map.Entry<String, String>>)model.entrySet()) {
			TemplateModel tm = TemplateModel.Factory.newInstance();
			tm.setModelName(entry.getKey());
			tm.setModelValue(entry.getValue());
			tm.setTemplate(template);
			templates.add(tm);
		}
		getTemplateModelDao().create(templates);
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
	protected void handleDeleteMailToSend(Long id) throws Exception {		
		MailToSend mts = getMailToSendDao().load(id);
		Template template = handleGetTemplateByMailToSend(mts);
		Long tId = template.getId();
		//handleDeleteTemplate(handleGetTemplateByMailToSend(getMailToSendDao().load(id)).getId());
		getMailToSendDao().remove(id);		
		handleDeleteTemplate(tId);
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

	@Override
	protected void handleDeleteTemplate(Long id) throws Exception {
		List l = handleGetTemplateModelsByTemplate(id);
		Iterator i = l.iterator();
		while (i.hasNext()){
			getTemplateModelDao().remove(((TemplateModel)i.next()).getId());
		}
		getTemplateDao().remove(id);
	}

	@Override
	protected Template handleGetTemplateByMailToSend(MailToSend mailToSend) throws Exception {		
		return getTemplateDao().findById(mailToSend.getMailBody().getId());
	}

	@Override
	protected List handleGetTemplateModelsByTemplate(Long templateId) throws Exception {
		return getTemplateModelDao().findByTemplate(templateId);
	}

	
}