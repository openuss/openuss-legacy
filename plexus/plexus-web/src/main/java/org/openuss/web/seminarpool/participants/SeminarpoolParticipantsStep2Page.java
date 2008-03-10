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
import org.openuss.seminarpool.SeminarPriorityDetailInfo;
import org.openuss.seminarpool.SeminarpoolStatus;
import org.openuss.web.Constants;
import org.openuss.web.seminarpool.AbstractSeminarpoolPage;

	@Bean(name = "views$secured$seminarpool$participants$seminarpoolParticipantsStep2", scope = Scope.REQUEST)
	@View
	public class SeminarpoolParticipantsStep2Page extends AbstractSeminarpoolPage {

		public static final Logger logger = Logger.getLogger(SeminarpoolParticipantsStep2Page.class);

		private SeminarCourseRegistrationsOverviewPage dataCourseTypes = new SeminarCourseRegistrationsOverviewPage();
		
		private boolean status;
		
		/** course type info */
		@Property(value="#{"+Constants.SEMINARPOOL_COURSE_ALLOCATION_INFO+"}")
		private CourseSeminarpoolAllocationInfo courseSeminarpoolAllocationInfo;
		
		@Prerender
		public void prerender() throws Exception {
			super.prerender();
			BreadCrumb crumb = new BreadCrumb();
			crumb.setLink("");
			crumb.setName(i18n("seminarpool_participants_breadcrump_step2"));
			crumb.setHint(i18n("seminarpool_participants_breadcrump_step2"));
			breadcrumbs.addCrumb(crumb);
		}
		
		public String removeParticipant(){
			SeminarPriorityDetailInfo detailInfo = dataCourseTypes.getRowData();
			getSeminarpoolAdministrationService().removeSeminarPriorityById(detailInfo.getId());
			return Constants.SEMINARPOOL_PARTICIPANTS_STEP2_PAGE;
		}


		private class SeminarCourseRegistrationsOverviewPage extends AbstractPagedTable<SeminarPriorityDetailInfo> {
			private static final long serialVersionUID = -6289875618529435428L;

			private DataPage<SeminarPriorityDetailInfo> page;

			@Override
			@SuppressWarnings( { "unchecked" })
			public DataPage<SeminarPriorityDetailInfo> getDataPage(int startRow, int pageSize) {
				if (page == null) {
					List<SeminarPriorityDetailInfo> courseTypes = new ArrayList<SeminarPriorityDetailInfo>(seminarpoolAdministrationService.getDetailCourseregistrationsById(courseSeminarpoolAllocationInfo.getId()));
					sort(courseTypes);
					page = new DataPage<SeminarPriorityDetailInfo>(courseTypes.size(), 0, courseTypes);
				}
				return page;
			}

		}

		public SeminarCourseRegistrationsOverviewPage getDataCourseTypes() {
			return dataCourseTypes;
		}


		public void setDataCourseTypes(SeminarCourseRegistrationsOverviewPage dataCourseTypes) {
			this.dataCourseTypes = dataCourseTypes;
		}


		public CourseSeminarpoolAllocationInfo getCourseSeminarpoolAllocationInfo() {
			return courseSeminarpoolAllocationInfo;
		}


		public void setCourseSeminarpoolAllocationInfo(
				CourseSeminarpoolAllocationInfo courseSeminarpoolAllocationInfo) {
			this.courseSeminarpoolAllocationInfo = courseSeminarpoolAllocationInfo;
		}
		
		public boolean getStatus() {
			return (seminarpoolInfo.getSeminarpoolStatus().getValue() <= SeminarpoolStatus.REGISTRATIONCOMPLETEPHASE.getValue());
		}
		
		public void setStatus(boolean status) {
			this.status = status;
		}
	}
