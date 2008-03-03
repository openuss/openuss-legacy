package org.openuss.web.seminarpool.add;




	import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.seminarpool.CourseGroupInfo;
import org.openuss.seminarpool.CourseSeminarpoolAllocationInfo;
import org.openuss.seminarpool.SeminarpoolAdministrationService;
import org.openuss.seminarpool.SeminarpoolInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;




	/**
	 * Backing bean for the Course to Seminarpool registration. Is responsible starting the
	 * wizard, binding the values and registrating the Course.
	 * 
	 * @author PS-Seminarplatzvergabe
	 * 
	 */

	@Bean(name = Constants.SEMINARPOOL_COURSE_ALLOCATION_STEP2, scope = Scope.REQUEST)
	@View
	public class SeminarRegistrationStep2Page extends BasePage {		
		private static final long serialVersionUID = 5069930000478432045L;
		
		@Property(value = "#{seminarpoolAdministrationService}")
		protected SeminarpoolAdministrationService seminarpoolAdministrationService; 
		
		private CourseGroupOverview courseGroupOverview = new CourseGroupOverview();

		private DataPage<CourseGroupInfo> dataPageCourseGroups;

		private CourseSeminarpoolAllocationInfo courseSeminarpoolAllocationInfo; 
		
		private List<CourseGroupInfo> courseGroupInfoList;
		
		private String courseGroupName = "";
		
		private int courseGroupCapacity;
		
		private static final Logger logger = Logger.getLogger(SeminarRegistrationStep2Page.class);

		
		@Prerender
		public void prerender() throws Exception {
			loadCourseGroupInfoList();
			courseSeminarpoolAllocationInfo = (CourseSeminarpoolAllocationInfo)getSessionBean(Constants.SEMINARPOOL_COURSE_SEMINARPOOL_ALLOCATION_INFO);
			SeminarpoolInfo seminarpoolInfo = (SeminarpoolInfo)getSessionBean(Constants.SEMINARPOOL_INFO);
		}
		
		public String addGroup(){
			if (courseGroupCapacity != 0){
				courseGroupInfoList = (List<CourseGroupInfo>)getSessionBean(Constants.SEMINARPOOL_COURSE_GROUPS_COLLECTION);
				CourseGroupInfo courseGroupInfo = new CourseGroupInfo();
				courseGroupInfo.setCapacity(courseGroupCapacity);
				courseGroupInfo.setName(i18n(Constants.SEMINARPOOL_COURSE_ALLOCATION_GROUP) + " " + (courseGroupInfoList.size()+1));
				courseGroupInfo.setIsTimeSet(false);
				courseGroupInfo.setIsDefault(false);
				courseGroupInfo.setId(new Long(courseGroupInfoList.size()));
				courseGroupInfoList.add(courseGroupInfo);
			} else {
				addError(i18n(Constants.SEMINARPOOL_COURSE_ALLOCATION_MESSAGE_ERROR_NUMBER));
			}
			return Constants.SEMINARPOOL_COURSE_ALLOCATION_STEP2;
		}
		
		
		/**
		 * Adds the current course to the selected seminarpool
		 * @return
		 */
		public String addSeminar(){
			courseGroupInfoList = (List<CourseGroupInfo>)getSessionBean(Constants.SEMINARPOOL_COURSE_GROUPS_COLLECTION);
			courseSeminarpoolAllocationInfo = (CourseSeminarpoolAllocationInfo)getSessionBean(Constants.SEMINARPOOL_COURSE_SEMINARPOOL_ALLOCATION_INFO);
			SeminarpoolInfo seminarpoolInfo = (SeminarpoolInfo)getSessionBean(Constants.SEMINARPOOL_INFO);
			courseSeminarpoolAllocationInfo.setSeminarpoolId(seminarpoolInfo.getId());
			if (courseGroupInfoList.size() == 0){
				// Error checking. no groups?
				addError(i18n(Constants.SEMINARPOOL_COURSE_ALLOCATION_MESSAGE_ERROR_NO_GROUPS) );
				return Constants.SEMINARPOOL_COURSE_ALLOCATION_STEP2;
			} else {
				// add course to Seminarpool
				clearCourseGroupInfoId(courseGroupInfoList);
				getSeminarpoolAdministrationService().addSeminar(courseSeminarpoolAllocationInfo, courseGroupInfoList);
				removeSessionBean(Constants.SEMINARPOOL_COURSE_SEMINARPOOL_ALLOCATION_INFO);
				removeSessionBean(Constants.SEMINARPOOL_COURSE_GROUPS_COLLECTION);
				return Constants.SEMINARPOOL_COURSE_ALLOCATION_FINISH;
			}
			
		}
		
		/**
		 * Store the selected institute into session scope and go to institute main
		 * page.
		 * 
		 * @return Outcome
		 */
		public String selectCurrentCourseGroup() {
			logger.info("Starting method selectCurrentCourseGroup");
			CourseGroupInfo courseGroupInfo = courseGroupOverview.getRowData();
			logger.info("Returning to method selectInstitute");
			logger.info(courseGroupInfo.getId());
			setSessionBean(Constants.SEMINARPOOL_COURSEGROUP_INFO, courseGroupInfo);
			return Constants.SEMINARPOOL_COURSE_ALLOCATION_STEP3;
		}

		// Start CourseGroup Overview 
		public DataPage<CourseGroupInfo> fetchDataPageCourseGroups(int startRow, int pageSize) {
			loadCourseGroupInfoList();
			if (dataPageCourseGroups == null) {
				if (logger.isDebugEnabled()) {
					logger.debug("fetch seminarpools data page at " + startRow + ", " + pageSize + " sorted by "
							+ courseGroupOverview.getSortColumn());
				}
				dataPageCourseGroups = new DataPage<CourseGroupInfo>(courseGroupInfoList.size(), 0, courseGroupInfoList);
			}
			return dataPageCourseGroups;
		}
		
		private void loadCourseGroupInfoList(){
			courseGroupInfoList = (List<CourseGroupInfo>)getSessionBean(Constants.SEMINARPOOL_COURSE_GROUPS_COLLECTION);
			if (courseGroupInfoList == null){
				courseGroupInfoList = new ArrayList<CourseGroupInfo>();
				setSessionBean(Constants.SEMINARPOOL_COURSE_GROUPS_COLLECTION, courseGroupInfoList);
			}
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


		public CourseSeminarpoolAllocationInfo getCourseSeminarpoolAllocationInfo() {
			return courseSeminarpoolAllocationInfo;
		}


		public void setCourseSeminarpoolAllocationInfo(
				CourseSeminarpoolAllocationInfo courseSeminarpoolAllocationInfo) {
			this.courseSeminarpoolAllocationInfo = courseSeminarpoolAllocationInfo;
		}


		public List<CourseGroupInfo> getCourseGroupInfoList() {
			return courseGroupInfoList;
		}


		public void setCourseGroupInfoList(List<CourseGroupInfo> courseGroupInfoList) {
			this.courseGroupInfoList = courseGroupInfoList;
		}


		public String getCourseGroupName() {
			return courseGroupName;
		}


		public void setCourseGroupName(String courseGroupName) {
			this.courseGroupName = courseGroupName;
		}


		public int getCourseGroupCapacity() {
			return courseGroupCapacity;
		}


		public void setCourseGroupCapacity(int courseGroupCapacity) {
			this.courseGroupCapacity = courseGroupCapacity;
		}

		public SeminarpoolAdministrationService getSeminarpoolAdministrationService() {
			return seminarpoolAdministrationService;
		}

		public void setSeminarpoolAdministrationService(
				SeminarpoolAdministrationService seminarpoolAdministrationService) {
			this.seminarpoolAdministrationService = seminarpoolAdministrationService;
		}
		
		private void clearCourseGroupInfoId(Collection<CourseGroupInfo> collection){
			for(CourseGroupInfo courseGroupInfo: collection){
				courseGroupInfo.setId(null);
			}
		}
		
	}
