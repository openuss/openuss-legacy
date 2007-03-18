package org.openuss.web.enrollment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.EnrollmentMemberInfo;
import org.openuss.lecture.FacultyMember;
import org.openuss.lecture.FacultySecurity;
import org.openuss.security.User;
import org.openuss.web.Constants;

@Bean(name = "views$secured$enrollment$enrollmentassistants", scope = Scope.REQUEST)
@View
public class EnrollmentAssistantsPage extends AbstractEnrollmentPage {
	private static final Logger logger = Logger.getLogger(EnrollmentAssistantsPage.class);

	private AssistantsDataProvider data = new AssistantsDataProvider();

	private Long userId;

	List<EnrollmentMemberInfo> assistants;
	Set<Long> assistantsUserIds;
	List<SelectItem> facultyMembers;
	
	private DataPage<EnrollmentMemberInfo> page;

	private class AssistantsDataProvider extends AbstractPagedTable<EnrollmentMemberInfo> {
		
		@Override
		public DataPage<EnrollmentMemberInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				logger.debug("fetching enrollment assistant list");
				List<EnrollmentMemberInfo> assistants = getAssistants();
				page = new DataPage<EnrollmentMemberInfo>(assistants.size(), 0, assistants);
			}
			return page;
		}
	}

	private List<EnrollmentMemberInfo> getAssistants() {
		if (assistants == null) {
			assistants = enrollmentService.getAssistants(enrollment);
		}
		return assistants;
	}

	private Set<Long> getAssistantsUserIdMap() {
		if (assistantsUserIds == null) {
			assistantsUserIds = new HashSet<Long>();
			for (EnrollmentMemberInfo assistant : getAssistants()) {
				assistantsUserIds.add(assistant.getUserId());
			}
		}
		return assistantsUserIds;
	}

	public String showProfile() {
		EnrollmentMemberInfo memberInfo = data.getRowData();
		User user = User.Factory.newInstance();
		user.setId(memberInfo.getUserId());
		setSessionBean("showuser", user);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}

	public String delete() {
		logger.debug("enrollment member deleted");
		EnrollmentMemberInfo assistant = data.getRowData();
		enrollmentService.removeMember(assistant.getId());
		addMessage(i18n("message_enrollment_removed_assistant"), assistant.getUsername());
		resetCachedData();
		return Constants.SUCCESS;
	}

	public String addAssistant() {
		logger.debug("enrollment assistant aspirant added");
		User user = User.Factory.newInstance();
		user.setId(userId);
		enrollmentService.addAssistant(enrollment, user);
		addMessage(i18n("message_enrollment_add_assistant"));
		resetCachedData();
		return Constants.SUCCESS;
	}

	private void resetCachedData() {
		assistants = null;
		facultyMembers = null;
		assistantsUserIds = null;
		page = null;
	}

	public Collection<SelectItem> getFacultyMemberList() {
		if (facultyMembers == null) {
			FacultySecurity facultySecurity = lectureService.getFacultySecurity(enrollment.getFaculty().getId());
			List<FacultyMember> members = facultySecurity.getMembers();
			final Set<Long> userIds = getAssistantsUserIdMap();
			CollectionUtils.filter(members, new Predicate() {
				public boolean evaluate(Object object) {
					FacultyMember member = (FacultyMember) object;
					return !userIds.contains(member.getId());
				}
			});
			facultyMembers = new ArrayList<SelectItem>();
			for(FacultyMember member : members) {
				facultyMembers.add(new SelectItem(member.getId(), member.getFirstName()+ " "+member.getLastName() + "("+member.getUsername()+")"));
			}
		}
		return facultyMembers;
	}

	public String save() {
		logger.debug("Enrollment assistants page - saved");
		return Constants.SUCCESS;
	}

	public void changedAssistant(ValueChangeEvent event) {
		logger.debug("changed enrollment assistants");
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

}