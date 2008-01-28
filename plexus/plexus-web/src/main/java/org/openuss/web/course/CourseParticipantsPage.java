package org.openuss.web.course;

import java.util.List;

import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.lecture.LectureException;
import org.openuss.security.UserInfo;
import org.openuss.security.UserInfoDetails;
import org.openuss.web.Constants;

@Bean(name = "views$secured$course$courseparticipants", scope = Scope.REQUEST)
@View
public class CourseParticipantsPage extends AbstractCoursePage {
	private static final Logger logger = Logger.getLogger(CourseParticipantsPage.class);

	private ParticipantDataProvider data = new ParticipantDataProvider();

	public String save() {
		logger.debug("Course member page - saved");
		return Constants.SUCCESS;
	}

	public void changedMember(ValueChangeEvent event) throws LectureException {
		logger.debug("changed course members");
	}

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("course_command_options_participants"));
		crumb.setHint(i18n("course_command_options_participants"));
		breadcrumbs.addCrumb(crumb);
	}
	
	private class ParticipantDataProvider extends AbstractPagedTable<CourseMemberInfo> {

		private static final long serialVersionUID = -1918372320518667092L;
		
		private DataPage<CourseMemberInfo> page;

		@Override
		public DataPage<CourseMemberInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<CourseMemberInfo> participants = courseService.getParticipants(courseInfo);
				page = new DataPage<CourseMemberInfo>(participants.size(), 0, participants);
				sort(participants);
			}
			return page;
		}
	}

	public String showProfile() {
		CourseMemberInfo participant = data.getRowData();
		UserInfoDetails user = new UserInfoDetails();
		user.setId(participant.getUserId());
		setSessionBean(Constants.SHOW_USER_PROFILE, user);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}

	public String delete() {
		logger.info("course participant deleted");
		CourseMemberInfo participant = data.getRowData();
		courseService.removeMember(participant.getId());
		addMessage(i18n("message_course_removed_participant",participant.getUsername()));
		return Constants.SUCCESS;
	}

	// ----------------------- PROPERTIES ---------------------------------

	public ParticipantDataProvider getData() {
		return data;
	}

	public void setData(ParticipantDataProvider data) {
		this.data = data;
	}

}