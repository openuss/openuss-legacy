package org.openuss.web.seminarpool.allocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.seminarpool.CourseSeminarpoolAllocationInfo;
import org.openuss.seminarpool.SeminarpoolInfo;
import org.openuss.seminarpool.util.CourseSeminarpoolAllocationCourseNameComparator;
import org.openuss.seminarpool.util.SeminarpoolInfoNameComparator;
import org.openuss.seminarpool.util.SeminarpoolInfoShortcutComperator;
import org.openuss.web.Constants;
import org.openuss.web.seminarpool.AbstractSeminarpoolPage;

@Bean(name = "views$secured$seminarpool$allocation$userAllocationByCourse", scope = Scope.REQUEST)
@View
public class UserAllocationByCoursePage extends AbstractSeminarpoolPage {

	public static final Logger logger = Logger.getLogger(UserAllocationByCoursePage.class);
	
	private SeminarOverviewPage dataCourseTypes = new SeminarOverviewPage();
	
	/** course type info */
	@Property(value="#{"+Constants.SEMINARPOOL_COURSE_ALLOCATION_INFO+"}")
	private CourseSeminarpoolAllocationInfo courseSeminarpoolAllocationInfo;

	
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("seminarpool_allocation_breadcrumb_course_step1"));
		crumb.setHint(i18n("seminarpool_allocation_breadcrumb_course_step1"));
		breadcrumbs.addCrumb(crumb);
	}
	
	public String selectCurrentSeminar(){
		setSessionBean(Constants.SEMINARPOOL_COURSE_ALLOCATION_INFO, dataCourseTypes.getRowData());
		return Constants.SEMINARPOOL_ALLOCATIONS_BY_COURSE_STEP2;
	}
	
	
	private class SeminarOverviewPage extends AbstractPagedTable<CourseSeminarpoolAllocationInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<CourseSeminarpoolAllocationInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" })
		public DataPage<CourseSeminarpoolAllocationInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<CourseSeminarpoolAllocationInfo> courseTypes = new ArrayList<CourseSeminarpoolAllocationInfo>(seminarpoolAdministrationService.findCoursesInSeminarpool(seminarpoolInfo.getId()));
				sort(courseTypes);
				page = new DataPage<CourseSeminarpoolAllocationInfo>(courseTypes.size(), 0, courseTypes);
			}
			return page;
		}
		
		public void sort(List<CourseSeminarpoolAllocationInfo> courseSeminarpoolAllocation) {
				Collections.sort(courseSeminarpoolAllocation, new CourseSeminarpoolAllocationCourseNameComparator(dataCourseTypes.isAscending()));
		}

	}

	public SeminarOverviewPage getDataCourseTypes() {
		return dataCourseTypes;
	}

	public void setDataCourseTypes(SeminarOverviewPage dataCourseTypes) {
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
