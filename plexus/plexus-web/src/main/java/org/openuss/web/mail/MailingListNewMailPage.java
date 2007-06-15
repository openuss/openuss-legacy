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
@Bean(name = "views$secured$mailinglist$newmail", scope = Scope.REQUEST)
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
		mailingList = getEnrollmentMailingListService().getMailingList(enrollmentInfo);
		setSessionBean(Constants.MAILINGLIST_MAILINGLIST, mailingList);

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