package org.openuss.web.seminarpool.allocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.security.UserInfo;
import org.openuss.seminarpool.CourseGroupInfo;
import org.openuss.seminarpool.CourseSeminarpoolAllocationInfo;
import org.openuss.seminarpool.util.UserInfoFirstNameComparator;
import org.openuss.web.Constants;
import org.openuss.web.seminarpool.AbstractSeminarpoolPage;


@Bean(name = "views$secured$seminarpool$allocation$userAllocationByCourseStep4", scope = Scope.REQUEST)
@View
public class UserAllocationByCourseStep4Page extends AbstractSeminarpoolPage {

	public static final Logger logger = Logger.getLogger(UserAllocationByCourseStep4Page.class);
	
	private SeminarpoolCourseGroupsTable dataCourseTypes = new SeminarpoolCourseGroupsTable();
	
	@Property(value="#{"+Constants.SEMINARPOOL_COURSE_ALLOCATION_INFO+"}")
	private CourseSeminarpoolAllocationInfo courseSeminarpoolAllocationInfo;
	
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("seminarpool_allocation_breadcrumb_course_step4", 
				seminarpoolInfo.getName()) + " (" +
				seminarpoolInfo.getShortcut() + ")");
		crumb.setHint(i18n("seminarpool_allocation_breadcrumb_course_step4", 
				seminarpoolInfo.getName()) + " (" +
				seminarpoolInfo.getShortcut() + ")");
		breadcrumbs.addCrumb(crumb);
	}
	
	public String selectUser(){
		UserInfo userInfo = (UserInfo)getSessionBean(Constants.SEMINARPOOL_ALLOCATIONS_SELECTED_USER_INFO);
		if (seminarpoolAdministrationService.addUserToAllocation(userInfo.getId(), dataCourseTypes.getRowData().getId() ) ){
			addMessage(i18n(Constants.SEMINARPOOL_ALLOCATIONS_BY_COURSE_USER_ADDED));
		} else {
			addError(i18n(Constants.SEMINARPOOL_ALLOCATIONS_BY_COURSE_ERROR_USER_DOUPLE));
		}
		return Constants.SEMINARPOOL_ALLOCATIONS_BY_COURSE_STEP2;
	}
	
	private class SeminarpoolCourseGroupsTable extends AbstractPagedTable<CourseGroupInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<CourseGroupInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" })
		public DataPage<CourseGroupInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<CourseGroupInfo> courseTypes = new ArrayList<CourseGroupInfo>(seminarpoolAdministrationService.findCourseGroupsByCourseAllocationId(courseSeminarpoolAllocationInfo.getId()));
				page = new DataPage<CourseGroupInfo>(courseTypes.size(), 0, courseTypes);
			}
			return page;
		}
	}

	public SeminarpoolCourseGroupsTable getDataCourseTypes() {
		return dataCourseTypes;
	}

	public void setDataCourseTypes(SeminarpoolCourseGroupsTable dataCourseTypes) {
		this.dataCourseTypes = dataCourseTypes;
	}
	
	public CourseSeminarpoolAllocationInfo getCourseSeminarpoolAllocationInfo() {
		return courseSeminarpoolAllocationInfo;
	}

	public void setCourseSeminarpoolAllocationInfo(
			CourseSeminarpoolAllocationInfo courseSeminarpoolAllocationInfo) {
		this.courseSeminarpoolAllocationInfo = courseSeminarpoolAllocationInfo;
	}
}
