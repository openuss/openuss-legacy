package org.openuss.web.mail;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.mailinglist.MailDetail;
import org.openuss.mailinglist.MailInfo;
import org.openuss.mailinglist.MailingStatus;
import org.openuss.security.User;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.web.Constants;

/**
 * 
 * @author Ingo Dueppe
 *
 */
@Bean(name = "views$secured$mailinglist$mailinglist$newmail", scope = Scope.REQUEST)
@View
public class MailingListNewMailPage extends AbstractMailingListPage{
	
	@Property(value= "#{"+Constants.USER+"}")
	private User user;
	
	@Property(value = "#{"+Constants.MAILINGLIST_MAIL+"}")
	private MailDetail mail;
	
	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		if (mail==null){
			addError(i18n("mailinglist_mailaccess_impossible"));
			redirect(Constants.MAILINGLIST_MAIN);
			return;
		}			
		if (mail.getId() == null) {
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
			if (!AcegiUtils.hasPermission(mail, new Integer[] { LectureAclEntry.ASSIST })){
				addError(i18n("mailinglist_mailaccess_noright"));
				redirect(Constants.MAILINGLIST_MAIN);
				return;
			}				
		}
	}	

	public String saveDraft(){
		getEnrollmentMailingListService().updateMail(enrollmentInfo, mail);
		return Constants.MAILINGLIST_MAIN;
	}
	
	public String send(){
		getEnrollmentMailingListService().sendMail(enrollmentInfo, mail);
		return Constants.MAILINGLIST_MAIN;
	}
	
	public String sendDraft(){
		addMessage(i18n("mailinglist_draft_send_message", getUser().getEmail()));
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