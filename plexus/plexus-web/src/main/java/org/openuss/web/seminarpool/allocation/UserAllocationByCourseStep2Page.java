package org.openuss.web.seminarpool.allocation;

import java.util.ArrayList;
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
import org.openuss.seminarpool.CourseSeminarpoolAllocationInfo;
import org.openuss.seminarpool.SeminarPlaceAllocationInfo;
import org.openuss.web.Constants;
import org.openuss.web.seminarpool.AbstractSeminarpoolPage;

@Bean(name = "views$secured$seminarpool$allocation$userAllocationByCourseStep2", scope = Scope.REQUEST)
@View
public class UserAllocationByCourseStep2Page extends AbstractSeminarpoolPage {
	
	public static final Logger logger = Logger.getLogger(UserAllocationByCoursePage.class);
	
	private SeminarPlaceAllocationTable dataCourseTypes = new SeminarPlaceAllocationTable();
	
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
	
	public String removeAllocation(){
		
		return Constants.SEMINARPOOL_ALLOCATIONS_BY_COURSE_STEP2;
	}
	
	
	private class SeminarPlaceAllocationTable extends AbstractPagedTable<SeminarPlaceAllocationInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<SeminarPlaceAllocationInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" })
		public DataPage<SeminarPlaceAllocationInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<SeminarPlaceAllocationInfo> courseTypes = new ArrayList<SeminarPlaceAllocationInfo>(seminarpoolAdministrationService.getAllocationsByCourse(courseSeminarpoolAllocationInfo.getCourseId(), courseSeminarpoolAllocationInfo.getSeminarpoolId()));
				page = new DataPage<SeminarPlaceAllocationInfo>(courseTypes.size(), 0, courseTypes);
			}
			return page;
		}

	}


	public SeminarPlaceAllocationTable getDataCourseTypes() {
		return dataCourseTypes;
	}

	public void setDataCourseTypes(SeminarPlaceAllocationTable dataCourseTypes) {
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
