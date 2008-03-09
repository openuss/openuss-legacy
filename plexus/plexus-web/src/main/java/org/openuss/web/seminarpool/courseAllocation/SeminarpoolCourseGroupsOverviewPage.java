package org.openuss.web.seminarpool.courseAllocation;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.seminarpool.CourseGroupInfo;
import org.openuss.seminarpool.SeminarpoolAdministrationService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;



@Bean(name = "views$commons$seminarpool$seminarpoolCourseGroupsTable", scope = Scope.REQUEST)
@View
public class SeminarpoolCourseGroupsOverviewPage extends BasePage {
	private static final long serialVersionUID = 5069930000478432045L;
	
	@Property(value = "#{seminarpoolAdministrationService}")
	protected SeminarpoolAdministrationService seminarpoolAdministrationService; 
	
	@Property(value = "#{" + Constants.SEMINARPOOL_COURSE_GROUPS_COLLECTION + "}")
	private List<CourseGroupInfo> courseGroupInfoList;

	@Property(value = "#{" + Constants.COURSE_GROUP_INDEX + "}")
	private Integer groupIndex;

	private CourseGroupOverview courseGroupOverview = new CourseGroupOverview();

	private DataPage<CourseGroupInfo> dataPageCourseGroups;
	
	private static final Logger logger = Logger.getLogger(SeminarRegistrationStep2Page.class);


	
	public String removeCurrentCourseGroup(){
		CourseGroupInfo courseGroupInfo = courseGroupInfoList.remove(courseGroupOverview.getRowIndex());
		if( courseGroupInfo.getId() != null ) {
			seminarpoolAdministrationService.removeCourseGroup(courseGroupInfo);
		}
		setSessionBean(Constants.SEMINARPOOL_COURSE_GROUPS_COLLECTION, courseGroupInfoList);
		addMessage(i18n(Constants.SEMINARPOOL_COURSE_ALLOCATION_MESSAGE_REMOVE_GROUP));
		return Constants.SEMINARPOOL_COURSE_ALLOCATION_STEP2;
	}

	/**
	 * Store the selected institute into session scope and go to institute main
	 * page.
	 * 
	 * @return Outcome
	 */
	public String selectCurrentCourseGroup() {
		logger.info("Starting method selectCurrentCourseGroup");
		groupIndex = courseGroupOverview.getRowIndex();
		setSessionBean("COURSE_GROUP_INDEX", groupIndex);
		return Constants.SEMINARPOOL_COURSE_ALLOCATION_STEP3;
	}

	// Start CourseGroup Overview 
	public DataPage<CourseGroupInfo> fetchDataPageCourseGroups(int startRow, int pageSize) {
		if (courseGroupInfoList == null){
			courseGroupInfoList = new ArrayList<CourseGroupInfo>();
			setSessionBean(Constants.SEMINARPOOL_COURSE_GROUPS_COLLECTION, courseGroupInfoList);
		}
		if (dataPageCourseGroups == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("fetch seminarpools data page at " + startRow + ", " + pageSize + " sorted by "
						+ courseGroupOverview.getSortColumn());
			}
			dataPageCourseGroups = new DataPage<CourseGroupInfo>(courseGroupInfoList.size(), 0, courseGroupInfoList);
		}
		return dataPageCourseGroups;
	}
	
	private class CourseGroupOverview extends AbstractPagedTable<CourseGroupInfo> {

		private static final long serialVersionUID = 5069930000478432045L;
		
		@Override
		public DataPage<CourseGroupInfo> getDataPage(int startRow, int pageSize) {
			logger.debug("Starting method getDataPage");
			return fetchDataPageCourseGroups(startRow, pageSize);
		}
	}

	// End CourseGroup Overview 
	public CourseGroupOverview getCourseGroupOverview() {
		return courseGroupOverview;
	}


	public void setCourseGroupOverview(CourseGroupOverview courseGroupOverview) {
		this.courseGroupOverview = courseGroupOverview;
	}

	public List<CourseGroupInfo> getCourseGroupInfoList() {
		return courseGroupInfoList;
	}


	public void setCourseGroupInfoList(List<CourseGroupInfo> courseGroupInfoList) {
		this.courseGroupInfoList = courseGroupInfoList;
	}

	public SeminarpoolAdministrationService getSeminarpoolAdministrationService() {
		return seminarpoolAdministrationService;
	}

	public void setSeminarpoolAdministrationService(
			SeminarpoolAdministrationService seminarpoolAdministrationService) {
		this.seminarpoolAdministrationService = seminarpoolAdministrationService;
	}

	public Integer getGroupIndex() {
		return groupIndex;
	}

	public void setGroupIndex(Integer groupIndex) {
		this.groupIndex = groupIndex;
	}
	
}