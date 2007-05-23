package org.openuss.web.mail;

import org.apache.shale.tiger.view.Prerender;
import org.openuss.mailinglist.MailInfo;
import org.openuss.mailinglist.MailingStatus;
import org.openuss.web.Constants;

public class AbstractMailPage extends AbstractMailingListPage{
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		if (mail.getId()!=null){
			MailInfo mi = new MailInfo(); 
			mi.setId(mail.getId());
			mail = getEnrollmentMailingListService().getMail(mi);
			if (mail.getStatus()==MailingStatus.DELETED){
				addError(i18n("mailinglist_mailaccess_impossible"));
				redirect(Constants.MAILINGLIST_MAIN);
			}
			setSessionBean(Constants.MAILINGLIST_MAIL, mail);
			if (mail==null){
				addError(i18n("mailinglist_mailaccess_impossible"));
				redirect(Constants.MAILINGLIST_MAIN);
			}
		}
	}	

}
