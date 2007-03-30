package org.openuss.web.enrollment;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.EnrollmentMemberInfo;
import org.openuss.lecture.EnrollmentServiceException;
import org.openuss.lecture.LectureException;
import org.openuss.security.User;
import org.openuss.web.Constants;

/**
 * Aspirant page to manage user application for membership 
 * 
 */
@Bean(name = "views$secured$enrollment$enrollmentaspirants", scope = Scope.REQUEST)
@View
public class EnrollmentAspirantsPage extends AbstractEnrollmentPage {
	private static final Logger logger = Logger.getLogger(EnrollmentAspirantsPage.class);
	
	private AspirantDataProvider data = new AspirantDataProvider();
	
	private transient Set<EnrollmentMemberInfo> acceptAspirants = new HashSet<EnrollmentMemberInfo>();
	private transient Set<EnrollmentMemberInfo> rejectAspirants = new HashSet<EnrollmentMemberInfo>();
	
	public String save() {
		acceptAspirants();
		rejectAspirants();
		
		return Constants.SUCCESS;
	}

	private void rejectAspirants() {
		for (EnrollmentMemberInfo aspirants: rejectAspirants) {
			try {
				enrollmentService.rejectAspirant(aspirants.getId());
				addMessage(i18n("enrollment_aspirant_reject", aspirants.getUsername()));
			} catch (EnrollmentServiceException e) {
				logger.error(e);
				addError(i18n(e.getMessage()));
			}
		}
	}

	private void acceptAspirants() {
		for (EnrollmentMemberInfo aspirants: acceptAspirants) {
			try {
				enrollmentService.acceptAspirant(aspirants.getId());
				addMessage(i18n("enrollment_aspirant_accepted", aspirants.getUsername()));
			} catch (EnrollmentServiceException e) {
				logger.error(e);
				addError(i18n(e.getMessage()));
			}
		}
	}
	
	public String showProfile() {
		EnrollmentMemberInfo aspirant = data.getRowData();
		User user = User.Factory.newInstance();
		user.setId(aspirant.getUserId());
		setSessionBean(Constants.SHOW_USER_PROFILE, user);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}
	
	public void changedAspirant(ValueChangeEvent event) throws LectureException {
		logger.debug("enrollment: changed aspirant");
		EnrollmentMemberInfo aspirant = data.getRowData();
		if (logger.isDebugEnabled()) {
			logger.debug("changed "+aspirant.getUsername()+ " from " + event.getOldValue() + " to " + event.getNewValue());
		}
		
		if ("accept".equals(event.getNewValue())) {
			acceptAspirants.add(aspirant);
		} else if ("reject".equals(event.getNewValue())) {
			rejectAspirants.add(aspirant);
		}
	}
	
	private class AspirantDataProvider extends AbstractPagedTable<EnrollmentMemberInfo> {

		private DataPage<EnrollmentMemberInfo> page; 
		
		@Override 
		public DataPage<EnrollmentMemberInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List aspirants = enrollmentService.getAspirants(enrollment);
				page = new DataPage<EnrollmentMemberInfo>(aspirants.size(),0,aspirants);
				sort(aspirants);
			}
			return page;
		}
	}
	
	/* --------------------- properties --------------------------*/
	public AspirantDataProvider getData() {
		return data;
	}
	
	public void setData(AspirantDataProvider data) {
		this.data = data;
	}
	

}
