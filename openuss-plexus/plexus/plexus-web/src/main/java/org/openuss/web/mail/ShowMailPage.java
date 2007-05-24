package org.openuss.web.mail;


import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.mailinglist.MailInfo;
import org.openuss.mailinglist.MailingStatus;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.web.Constants;

@Bean(name = "views$secured$mailinglist$showmail", scope = Scope.REQUEST)
@View
public class ShowMailPage extends AbstractMailingListPage{
	@Prerender
	public void prerender() throws Exception {
		if (mail.getId() == null) setMail(null);
		if (mail==null){
			addError(i18n("mailinglist_mailaccess_impossible"));
			redirect(Constants.MAILINGLIST_MAIN);
			return;
		}			
		if (mail.getId()!=null){
			MailInfo mi = new MailInfo(); 
			mi.setId(mail.getId());
			mail = getEnrollmentMailingListService().getMail(mi);
			if (mail==null){
				addError(i18n("mailinglist_mailaccess_impossible"));
				redirect(Constants.MAILINGLIST_MAIN);
				return;
			}
			
			if (mail.getStatus()==MailingStatus.DELETED){
				addError(i18n("mailinglist_mailaccess_impossible"));
				redirect(Constants.MAILINGLIST_MAIN);
				return;
			}
			setSessionBean(Constants.MAILINGLIST_MAIL, mail);
		}
	}	
}
