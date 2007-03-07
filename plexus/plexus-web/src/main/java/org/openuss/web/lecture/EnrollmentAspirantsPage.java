package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Date;
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
import org.openuss.lecture.LectureException;
import org.openuss.security.User;
import org.openuss.security.UserInfo;
import org.openuss.web.Constants;

/**
 * Aspirant page to manage user application for membership 
 * 
 */
@Bean(name = "views$secured$lecture$enrollmentaspirants", scope = Scope.REQUEST)
@View
public class EnrollmentAspirantsPage extends AbstractEnrollmentPage {
	private static final Logger logger = Logger.getLogger(EnrollmentAspirantsPage.class);
	
	private AspirantDataProvider data = new AspirantDataProvider();
	
	private transient Set<UserInfo> acceptAspirants = new HashSet<UserInfo>();
	private transient Set<UserInfo> rejectAspirants = new HashSet<UserInfo>();
	
	public String save() {
		/*
		for (UserInfo userInfo : acceptAspirants) {
			try {
				lectureService.acceptFacultyAspirant(userInfo.getId(), faculty.getId());
				addMessage(i18n("faculty_add_member_to_faculty", userInfo.getUsername()));
			} catch (LectureException e) {
				logger.error(e);
				addError(i18n(e.getMessage()));
			}
		}
		
		for (UserInfo userInfo : rejectAspirants) {
			try {
				lectureService.rejectFacultyAspirant(userInfo.getId(), faculty.getId());
				addMessage(i18n("faculty_reject_aspirants",userInfo.getUsername()));
			} catch (LectureException e) {
				logger.error(e);
				addError(i18n(e.getMessage()));
			}
		}
		*/
		return Constants.SUCCESS;
	}
	
	public String showProfile() {
		UserInfo userInfo = data.getRowData();
		User user = User.Factory.newInstance();
		user.setId(userInfo.getId());
		setSessionBean("showuser", user);
		return Constants.USER_PROFILE_VIEW_PAGE;
	}
	
	public void changedAspirant(ValueChangeEvent event) throws LectureException {
		UserInfo aspirant = data.getRowData();
		if (logger.isDebugEnabled()) {
			logger.debug("changed " + aspirant.getUsername() + " from " + event.getOldValue() + " to " + event.getNewValue());
		}
		
		if ("accept".equals(event.getNewValue())) {
			acceptAspirants.add(aspirant);
		} else if ("reject".equals(event.getNewValue())) {
			rejectAspirants.add(aspirant);
		}
	}
	
	private class AspirantDataProvider extends AbstractPagedTable<UserInfo> {

		private DataPage<UserInfo> page; 
		
		@Override 
		public DataPage<UserInfo> getDataPage(int startRow, int pageSize) {
			/*
			if (page == null) {
				List aspirants = lectureService.getFacultyAspirants(faculty.getId());
				page = new DataPage<UserInfo>(aspirants.size(),0,aspirants);
				sort(aspirants);
			}*/
			ArrayList<UserInfo> al = new ArrayList<UserInfo>();			
			UserInfo ui1 = new UserInfo(new Long(1234), "cag", "Sebastian", "Roekens", "abc123", "plexus@openuss-plexus.com", true, false, false, new Date(System.currentTimeMillis()));
			UserInfo ui2 = new UserInfo(new Long(12345), "dueppe", "Ingo", "Düppe", "12345", "plexus@openuss-plexus.com", true, true, false, new Date(System.currentTimeMillis()));
			UserInfo ui3 = new UserInfo(new Long(1111), "bundy", "Al", "Bundy", "dumpfbacke", "plexus@openuss-plexus.com", true, false, true, new Date(System.currentTimeMillis()));
			al.add(ui1); al.add(ui2); al.add(ui3);
			page = new DataPage<UserInfo>(al.size(),0,al);
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
