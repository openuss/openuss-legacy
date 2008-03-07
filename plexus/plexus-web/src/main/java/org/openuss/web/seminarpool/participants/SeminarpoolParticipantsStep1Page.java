package org.openuss.web.seminarpool.participants;

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
import org.openuss.web.Constants;
import org.openuss.web.seminarpool.AbstractSeminarpoolPage;
import org.openuss.web.seminarpool.SeminarpoolCourseTypesPage;


@Bean(name = "views$secured$seminarpool$participants$seminarpoolParticipantsStep1", scope = Scope.REQUEST)
@View
public class SeminarpoolParticipantsStep1Page extends AbstractSeminarpoolPage {

	public static final Logger logger = Logger.getLogger(SeminarpoolCourseTypesPage.class);

	private SeminarOverviewPage dataCourseTypes = new SeminarOverviewPage();
	
	/** course type info */
	@Property(value="#{"+Constants.SEMINARPOOL_COURSE_ALLOCATION_INFO+"}")
	private CourseSeminarpoolAllocationInfo courseSeminarpoolAllocationInfo;
	
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("seminarpool_participants_breadcrump_step1"));
		crumb.setHint(i18n("seminarpool_participants_breadcrump_step1"));
		breadcrumbs.addCrumb(crumb);
	}


	public String selectCurrentSeminar(){
		CourseSeminarpoolAllocationInfo allocationInfo = dataCourseTypes.getRowData();
		setSessionBean(Constants.SEMINARPOOL_COURSE_ALLOCATION_INFO, allocationInfo);
		return Constants.SEMINARPOOL_PARTICIPANTS_STEP2_PAGE;
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
