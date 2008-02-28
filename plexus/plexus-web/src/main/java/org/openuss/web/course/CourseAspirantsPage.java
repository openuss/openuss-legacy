package org.openuss.web.course;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.openuss.lecture.CourseServiceException;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;

/**
 * Aspirant page to manage user application for membership 
 * 
 */
@Bean(name = "views$secured$course$courseaspirants", scope = Scope.REQUEST)
@View
public class CourseAspirantsPage extends AbstractCoursePage {
	private static final Logger logger = Logger.getLogger(CourseAspirantsPage.class);
	
	private AspirantDataProvider data = new AspirantDataProvider();
	
	private transient Set<CourseMemberInfo> acceptAspirants = new HashSet<CourseMemberInfo>();
	private transient Set<CourseMemberInfo> rejectAspirants = new HashSet<CourseMemberInfo>();

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("course_command_options_aspirants"));
		crumb.setHint(i18n("course_command_options_aspirants"));
		breadcrumbs.addCrumb(crumb);
	}	
	
	public String save() {
		acceptAspirants();
		rejectAspirants();
		
		return Constants.SUCCESS;
	}

	private void rejectAspirants() {
		for (CourseMemberInfo aspirants: rejectAspirants) {
			try {
				courseService.rejectAspirant(aspirants.getId());
				addMessage(i18n("course_aspirant_reject", aspirants.getUsername()));
			} catch (CourseServiceException e) {
				logger.error(e);
				addError(i18n(e.getMessage()));
			}
		}
	}

	private void acceptAspirants() {
		for (CourseMemberInfo aspirants: acceptAspirants) {
			try {
				courseService.acceptAspirant(aspirants.getId());
				addMessage(i18n("course_aspirant_accepted", aspirants.getUsername()));
			} catch (CourseServiceException e) {
				logger.error(e);
				addError(i18n(e.getMessage()));
			}
		}
	}
	

	public void changedAspirant(ValueChangeEvent event) throws LectureException {
		logger.debug("course: changed aspirant");
		CourseMemberInfo aspirant = data.getRowData();
		if (logger.isDebugEnabled()) {
			logger.debug("changed "+aspirant.getUsername()+ " from " + event.getOldValue() + " to " + event.getNewValue());
		}
		
		if ("accept".equals(event.getNewValue())) {
			acceptAspirants.add(aspirant);
		} else if ("reject".equals(event.getNewValue())) {
			rejectAspirants.add(aspirant);
		}
	}
	
	private class AspirantDataProvider extends AbstractPagedTable<CourseMemberInfo> {

		private static final long serialVersionUID = 2219795204824844857L;
		
		private DataPage<CourseMemberInfo> page; 
		
		@Override 
		public DataPage<CourseMemberInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<CourseMemberInfo> aspirants = courseService.getAspirants(courseInfo);
				page = new DataPage<CourseMemberInfo>(aspirants.size(),0,aspirants);
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
