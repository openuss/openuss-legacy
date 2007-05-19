package org.openuss.web.enrollment;

import java.util.List;

import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.EnrollmentMemberInfo;
import org.openuss.lecture.LectureException;
import org.openuss.security.User;
import org.openuss.web.Constants;

@Bean(name = "views$secured$enrollment$enrollmentparticipants", scope = Scope.REQUEST)
@View
public class EnrollmentParticipantsPage extends AbstractEnrollmentPage {
	private static final Logger logger = Logger.getLogger(EnrollmentParticipantsPage.class);

	private ParticipantDataProvider data = new ParticipantDataProvider();

	public String save() {
		logger.debug("Enrollment member page - saved");
		return Constants.SUCCESS;
	}

	public void changedMember(ValueChangeEvent event) throws LectureException {
		logger.debug("changed enrollment members");
	}

	private class ParticipantDataProvider extends AbstractPagedTable<EnrollmentMemberInfo> {

		private static final long serialVersionUID = -1918372320518667092L;
		
		private DataPage<EnrollmentMemberInfo> page;

		@Override
		public DataPage<EnrollmentMemberInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<EnrollmentMemberInfo> participants = enrollmentService.getParticipants(enrollment);
				page = new DataPage<EnrollmentMemberInfo>(participants.size(), 0, participants);
			}
			return page;
		}
	}

	public String showProfile() {
		EnrollmentMemberInfo participant = data.getRowData();
		User user = User.Factory.newInstance();
		user.setId(participant.getUserId());
		setSessionBean(Constants.SHOW_USER_PROFILE, user);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}

	public String delete() {
		logger.error("enrollment participant deleted");
		EnrollmentMemberInfo participant = data.getRowData();
		enrollmentService.removeMember(participant.getId());
		addMessage(i18n("message_enrollment_removed_participant"),participant.getUsername());
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