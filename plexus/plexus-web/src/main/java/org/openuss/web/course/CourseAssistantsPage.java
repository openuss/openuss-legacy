package org.openuss.web.course;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.lecture.InstituteMember;
import org.openuss.lecture.InstituteSecurity;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.security.UserComparator;
import org.openuss.web.Constants;

@Bean(name = "views$secured$course$courseassistants", scope = Scope.REQUEST)
@View
public class CourseAssistantsPage extends AbstractCoursePage {
	private static final Logger logger = Logger.getLogger(CourseAssistantsPage.class);

	private AssistantsDataProvider data = new AssistantsDataProvider();

	@Property(value = "#{securityService}")
	protected SecurityService securityService;

	private Long userId;

	List<CourseMemberInfo> assistants;
	Set<Long> assistantsUserIds;
	List<SelectItem> instituteMembers;
	
	private DataPage<CourseMemberInfo> page;


	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("course_command_options_assistants"));
		crumb.setHint(i18n("course_command_options_assistants"));
		crumbs.add(crumb);
		setSessionBean(Constants.BREADCRUMBS, crumbs);
	}	
	
	private class AssistantsDataProvider extends AbstractPagedTable<CourseMemberInfo> {
		
		private static final long serialVersionUID = -5342817757466323535L;

		@Override
		public DataPage<CourseMemberInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				logger.debug("fetching course assistant list");
				List<CourseMemberInfo> assistants = getAssistants();
				page = new DataPage<CourseMemberInfo>(assistants.size(), 0, assistants);
			}
			return page;
		}
	}

	private List<CourseMemberInfo> getAssistants() {
		if (assistants == null) {
			assistants = courseService.getAssistants(courseInfo);
		}
		return assistants;
	}

	private Set<Long> getAssistantsUserIdMap() {
		if (assistantsUserIds == null) {
			assistantsUserIds = new HashSet<Long>();
			for (CourseMemberInfo assistant : getAssistants()) {
				assistantsUserIds.add(assistant.getUserId());
			}
		}
		return assistantsUserIds;
	}

	public String showProfile() {
		CourseMemberInfo memberInfo = data.getRowData();
		User user = User.Factory.newInstance();
		user.setId(memberInfo.getUserId());
		setSessionBean("showuser", user);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}

	public String delete() {
		logger.debug("course member deleted");
		CourseMemberInfo assistant = data.getRowData();
		courseService.removeMember(assistant.getId());
		addMessage(i18n("message_course_removed_assistant", assistant.getUsername()));
		resetCachedData();
		return Constants.SUCCESS;
	}

	public String addAssistant() {
		logger.debug("course assistant aspirant added");
		User user = User.Factory.newInstance();
		user.setId(userId);
		courseService.addAssistant(courseInfo, user);
		addMessage(i18n("message_course_add_assistant"));
		resetCachedData();
		return Constants.SUCCESS;
	}

	private void resetCachedData() {
		assistants = null;
		instituteMembers = null;
		assistantsUserIds = null;
		page = null;
	}

	public Collection<SelectItem> getInstituteMemberList() {
		if (instituteMembers == null) {
			InstituteSecurity instituteSecurity = lectureService.getInstituteSecurity(course.getInstitute().getId());
			List<InstituteMember> members = instituteSecurity.getMembers();
			final Set<Long> userIds = getAssistantsUserIdMap();
			CollectionUtils.filter(members, new Predicate() {
				public boolean evaluate(Object object) {
					InstituteMember member = (InstituteMember) object;
					return !userIds.contains(member.getId());
				}
			});
			List<User> membersUser = new ArrayList<User>();
			for(InstituteMember member : members) {
				membersUser.add(getSecurityService().getUserByName(member.getUsername()));
			}
			UserComparator userComparator = new UserComparator();
			Collections.sort(membersUser, userComparator);
			instituteMembers = new ArrayList<SelectItem>();
			for(User member : membersUser) {
				instituteMembers.add(new SelectItem(member.getId(), member.getTitle()+" "+member.getLastName()+" "+member.getFirstName()));
			}

			
		}
		return instituteMembers;
	}

	public String save() {
		logger.debug("Course assistants page - saved");
		return Constants.SUCCESS;
	}

	public void changedAssistant(ValueChangeEvent event) {
		logger.debug("changed course assistants");
	}

	public AssistantsDataProvider getData() {
		return data;
	}

	public void setData(AssistantsDataProvider data) {
		this.data = data;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

}