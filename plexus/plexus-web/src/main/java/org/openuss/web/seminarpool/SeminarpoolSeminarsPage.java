package org.openuss.web.seminarpool;

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
import org.openuss.lecture.LectureException;
import org.openuss.seminarpool.CourseGroupInfo;
import org.openuss.seminarpool.CourseSeminarpoolAllocationInfo;
import org.openuss.web.Constants;

@Bean(name = "views$secured$seminarpool$seminarpoolseminartable", scope = Scope.REQUEST)
@View
public class SeminarpoolSeminarsPage extends AbstractSeminarpoolPage {

	public static final Logger logger = Logger
			.getLogger(SeminarpoolSeminarsPage.class);

	private LocalDataModelCourseTypes dataCourseTypes = new LocalDataModelCourseTypes();

	private boolean enabled;

	/** course type info */
	@Property(value = "#{" + Constants.SEMINARPOOL_COURSE_ALLOCATION_INFO + "}")
	private CourseSeminarpoolAllocationInfo courseSeminarpoolAllocationInfo;

	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("coursetype_coursetypestable_header"));
		crumb.setHint(i18n("coursetype_coursetypestable_header"));
		breadcrumbs.addCrumb(crumb);
	}

	/**
	 * activates the seminar
	 * 
	 * @return outcome
	 * @throws LectureException
	 */
	public String activateSeminar() throws Exception {
		logger.debug("Starting method activateSeminar");
		CourseSeminarpoolAllocationInfo currentCourseType = currentCourseType();
		logger.debug("Returning to method activateSeminar");
		logger.debug(currentCourseType.getId());
		this.seminarpoolAdministrationService
				.activateSeminar(currentCourseType);
		return "";
	}

	public String loadCourseGroups() {
		logger.debug("Starting method loadCourseGroups");
		CourseSeminarpoolAllocationInfo courseAllocationInfo = dataCourseTypes
				.getRowData();
		List<CourseGroupInfo> courseGroupInfoList = getSeminarpoolAdministrationService()
				.findCourseGroupsByCourseAllocationId(
						courseAllocationInfo.getId());
		logger.debug("Returning to method loadCourseGroups");
		setSessionBean(
				Constants.SEMINARPOOL_COURSE_SEMINARPOOL_ALLOCATION_INFO,
				courseAllocationInfo);
		setSessionBean(Constants.SEMINARPOOL_COURSE_GROUPS_COLLECTION,
				courseGroupInfoList);
		return Constants.SEMINARPOOL_COURSE_GROUPS_EDIT;
	}

	/**
	 * deactivates the seminar
	 * 
	 * @return outcome
	 * @throws LectureException
	 */
	public String deactivateSeminar() throws Exception {
		logger.debug("Starting method activateSeminar");
		CourseSeminarpoolAllocationInfo currentCourseType = currentCourseType();
		logger.debug("Returning to method activateSeminar");
		logger.debug(currentCourseType.getId());
		this.seminarpoolAdministrationService
				.deactivateSeminar(currentCourseType);
		return "";
	}

	private CourseSeminarpoolAllocationInfo currentCourseType() {
		CourseSeminarpoolAllocationInfo courseType = dataCourseTypes
				.getRowData();
		return courseType;
	}

	public String selectSeminarAndConfirmRemove() {
		logger.debug("Starting method selectSeminarAndConfirmRemove");
		CourseSeminarpoolAllocationInfo currentCourseType = currentCourseType();
		logger.debug("Returning to method selectSeminarAndConfirmRemove");
		logger.debug(currentCourseType.getId());
		this.seminarpoolAdministrationService.removeSeminar(currentCourseType);
		return "";

	}

	public void setDataCourseTypes(LocalDataModelCourseTypes dataCourseTypes) {
		this.dataCourseTypes = dataCourseTypes;
	}

	public LocalDataModelCourseTypes getDataCourseTypes() {
		return dataCourseTypes;
	}

	private class LocalDataModelCourseTypes extends
			AbstractPagedTable<CourseSeminarpoolAllocationInfo> {
		private static final long serialVersionUID = -6289875618529435428L;

		private DataPage<CourseSeminarpoolAllocationInfo> page;

		@Override
		@SuppressWarnings( { "unchecked" })
		public DataPage<CourseSeminarpoolAllocationInfo> getDataPage(
				int startRow, int pageSize) {
			if (page == null) {
				List<CourseSeminarpoolAllocationInfo> courseTypes = new ArrayList<CourseSeminarpoolAllocationInfo>(
						seminarpoolAdministrationService.findCoursesInSeminarpool(seminarpoolInfo
										.getId()));
				sort(courseTypes);
				page = new DataPage<CourseSeminarpoolAllocationInfo>(
						courseTypes.size(), 0, courseTypes);
			}
			return page;
		}

	}

	public CourseSeminarpoolAllocationInfo getCourseSeminarpoolAllocationInfo() {
		return courseSeminarpoolAllocationInfo;
	}

	public void setCourseSeminarpoolAllocationInfo(
			CourseSeminarpoolAllocationInfo courseSeminarpoolAllocationInfo) {
		this.courseSeminarpoolAllocationInfo = courseSeminarpoolAllocationInfo;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
