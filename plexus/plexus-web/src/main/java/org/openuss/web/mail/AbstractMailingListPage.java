package org.openuss.web.mail;

import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.view.Prerender;
import org.openuss.course.mailinglist.CourseMailingListService;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.mailinglist.MailDetail;
import org.openuss.mailinglist.MailingListInfo;
import org.openuss.system.SystemProperties;
import org.openuss.web.course.AbstractCoursePage;
import org.openuss.web.Constants;
import org.openuss.web.PageLinks;
public class AbstractMailingListPage extends AbstractCoursePage{

	@Property(value = "#{courseMailingListService}")
	protected CourseMailingListService courseMailingListService;
	
	@Property(value = "#{" + Constants.MAILINGLIST_MAIL + "}")
	protected MailDetail mail;
	
	@Property(value = "#{" + Constants.MAILINGLIST_MAILINGLIST + "}")
	protected MailingListInfo mailingList;

	
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		addMailingListCrumb();
	}	
	
	private void addMailingListCrumb(){
		BreadCrumb mailingListCrumb = new BreadCrumb();
		mailingListCrumb.setHint(i18n("course_command_mailinglist"));
		mailingListCrumb.setName(i18n("course_command_mailinglist"));
		mailingListCrumb.setLink(getSystemService().getProperty(SystemProperties.OPENUSS_SERVER_URL).getValue()+PageLinks.COURSE_MAILINGLIST+"?course="+course.getId());
		crumbs.add(mailingListCrumb);
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