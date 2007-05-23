package org.openuss.web.mail;

import org.acegisecurity.acl.AclEntry;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.mailinglist.MailDetail;
import org.openuss.mailinglist.MailInfo;
import org.openuss.mailinglist.MailingStatus;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.web.Constants;

@Bean(name = "views$secured$mailinglist$mailinglistnewmailpage", scope = Scope.REQUEST)
@View
public class MailingListNewMailPage extends AbstractMailPage{
	
	@Property(value= "#{"+Constants.USER+"}")
	private User user;
	
	@Property(value = "#{"+Constants.MAILINGLIST_MAIL+"}")
	private MailDetail mail;
	
	@SuppressWarnings("unchecked")
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
			if (!AcegiUtils.hasPermission(mail, new Integer[] { LectureAclEntry.ASSIST })){
				addError(i18n("mailinglist_mailaccess_noright"));
				redirect(Constants.MAILINGLIST_MAIN);				
			}				
		}
	}	

	public String saveDraft(){
		getEnrollmentMailingListService().updateMail(mail);
		return Constants.MAILINGLIST_MAIN;
	}
	
	public String send(){
		getEnrollmentMailingListService().sendMail(enrollmentInfo, mail);
		return Constants.MAILINGLIST_MAIN;
	}
	
	public String sendDraft(){
		getEnrollmentMailingListService().sendPreview(enrollmentInfo, mail);
		return Constants.SUCCESS;	
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public MailDetail getMail() {
		return mail;
	}


	public void setMail(MailDetail mail) {
		this.mail = mail;
	}

}