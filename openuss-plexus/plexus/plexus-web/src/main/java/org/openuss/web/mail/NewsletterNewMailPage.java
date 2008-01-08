package org.openuss.web.mail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.group.GroupService;
import org.openuss.group.WorkingGroupInfo;
import org.openuss.newsletter.MailDetail;
import org.openuss.security.UserInfo;
import org.openuss.web.Constants;

/**
 * 
 * @author Ingo Dueppe
 *
 */
@Bean(name = "views$secured$newsletter$newmail", scope = Scope.REQUEST)
@View
public class NewsletterNewMailPage extends AbstractNewsletterPage{
	
	@Property(value= "#{"+Constants.USER+"}")
	private UserInfo user;
	
	@Property(value = "#{"+Constants.NEWSLETTER_MAIL+"}")
	private MailDetail mail;
	
	@Property(value = "#{groupService}")
	private GroupService groupService;
	
	private Collection<SelectItem> groups;
	
	private String group;
	
	@SuppressWarnings("unchecked")
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		newsletter = getCourseNewsletterService().getNewsletter(courseInfo);
		setSessionBean(Constants.NEWSLETTER_NEWSLETTER, newsletter);
		addPageCrumb();
	}	
	
	public Collection<SelectItem> getGroups() {
		if (groups==null){
			Collection<SelectItem> groupsSelectItems = new ArrayList<SelectItem>();
			List<WorkingGroupInfo> groupInfos = getGroupService().getGroups(courseInfo.getId());
			groupsSelectItems.add(new SelectItem(courseInfo.getName(), courseInfo.getName()));
			for (WorkingGroupInfo group : groupInfos){
				groupsSelectItems.add(new SelectItem(group.getGroupName(), group.getGroupName()));
			}
			groups = groupsSelectItems;
		}
		return groups;
	}	
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("newsletter_newmail_header"));
		crumb.setHint(i18n("newsletter_newmail_header"));
		breadcrumbs.addCrumb(crumb);
	}

	public String saveDraft(){
		if (courseInfo.isGroups()){
			if (!group.equals(courseInfo.getName())){
				mail.setSubject("["+group+"] "+mail.getSubject());
			}
		}
		getCourseNewsletterService().updateMail(courseInfo, mail);
		return Constants.NEWSLETTER_MAIN;
	}
	
	public String send(){
		if (courseInfo.isGroups()){
			if (!group.equals(courseInfo.getName())){
				mail.setSubject("["+group+"] "+mail.getSubject());
			}
		}
		getCourseNewsletterService().sendMail(courseInfo, mail);
		return Constants.NEWSLETTER_MAIN;
	}
	
	public String sendDraft(){
		if (courseInfo.isGroups()){
			if (!group.equals(courseInfo.getName())){
				mail.setSubject("["+group+"] "+mail.getSubject());
			}
		}		
		addMessage(i18n("newsletter_draft_send_message", getUser().getEmail()));
		getCourseNewsletterService().sendPreview(courseInfo, mail);
		return Constants.SUCCESS;	
	}
	

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}


	public MailDetail getMail() {
		return mail;
	}


	public void setMail(MailDetail mail) {
		this.mail = mail;
	}

	public void setGroups(Collection<SelectItem> groups) {
		this.groups = groups;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}


}