package org.openuss.web.mail;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.course.mailinglist.CourseMailingListService;
import org.openuss.mailinglist.MailDetail;
import org.openuss.mailinglist.MailInfo;
import org.openuss.mailinglist.MailingListInfo;
import org.openuss.mailinglist.MailingStatus;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 * 
 * @author Ingo Dueppe
 *
 */
@Bean(name = "views$secured$mailinglist$showmail", scope = Scope.REQUEST)
@View
public class ShowMailPage extends BasePage{
	
	@Property(value = "#{courseMailingListService}")
	protected CourseMailingListService courseMailingListService;
	
	@Property(value = "#{" + Constants.MAILINGLIST_MAIL + "}")
	protected MailDetail mail;
	
	@Property(value = "#{" + Constants.MAILINGLIST_MAILINGLIST + "}")
	protected MailingListInfo mailingList;

	
	@Prerender
	public void prerender() throws Exception {
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
			mail = getCourseMailingListService().getMail(mi);
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


	public CourseMailingListService getCourseMailingListService() {
		return courseMailingListService;
	}


	public void setCourseMailingListService(
			CourseMailingListService courseMailingListService) {
		this.courseMailingListService = courseMailingListService;
	}


	public MailDetail getMail() {
		return mail;
	}


	public void setMail(MailDetail mail) {
		this.mail = mail;
	}


	public MailingListInfo getMailingList() {
		return mailingList;
	}


	public void setMailingList(MailingListInfo mailingList) {
		this.mailingList = mailingList;
	}	
}
