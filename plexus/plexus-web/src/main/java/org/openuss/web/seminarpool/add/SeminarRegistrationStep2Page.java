package org.openuss.web.seminarpool.add;




	import java.util.ArrayList;
	import java.util.Collections;
	import java.util.Comparator;
	import java.util.List;
	import java.util.Locale;
import java.util.Map;
	import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
	import javax.faces.el.ValueBinding;

	import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
	import org.apache.log4j.Logger;
	import org.apache.shale.tiger.managed.Bean;
	import org.apache.shale.tiger.managed.Property;
	import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Init;
	import org.apache.shale.tiger.view.Preprocess;
	import org.apache.shale.tiger.view.Prerender;
	import org.apache.shale.tiger.view.View;
	import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
	import org.openuss.framework.web.jsf.model.AbstractPagedTable;
	import org.openuss.framework.web.jsf.model.DataPage;
	import org.openuss.lecture.CourseInfo;
	import org.openuss.lecture.Department;
	import org.openuss.lecture.DepartmentInfo;
	import org.openuss.lecture.DepartmentService;
	import org.openuss.lecture.InstituteInfo;
	import org.openuss.lecture.InstituteService;
	import org.openuss.lecture.LectureException;
	import org.openuss.lecture.University;
	import org.openuss.lecture.UniversityInfo;
	import org.openuss.lecture.UniversityService;
	import org.openuss.seminarpool.CourseGroupInfo;
	import org.openuss.seminarpool.CourseSeminarpoolAllocationInfo;
	import org.openuss.seminarpool.SeminarpoolAdministrationService;
	import org.openuss.seminarpool.SeminarpoolInfo;
	import org.openuss.seminarpool.SeminarpoolStatus;
	import org.openuss.seminarpool.util.SeminarpoolInfoNameComparator;
	import org.openuss.seminarpool.util.SeminarpoolInfoShortcutComperator;
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
		
		private CourseGroupOverview courseGroupOverview;

		private DataPage<SeminarpoolInfo> dataPage;

		private DataPage<CourseGroupInfo> dataPageCourseGroups;

		private CourseSeminarpoolAllocationInfo courseSeminarpoolAllocationInfo; 
		
		private List<CourseGroupInfo> courseGroupInfoList;
		
		private String courseGroupName = "";
		
		private int courseGroupCapacity;
		
		private static final Logger logger = Logger.getLogger(SeminarRegistrationStep1Page.class);

		
		@Init
		public void init() throws Exception {
			courseGroupInfoList = (List<CourseGroupInfo>)getSessionBean(Constants.SEMINARPOOL_COURSE_GROUPS_COLLECTION);
			if (courseGroupInfoList == null){
				courseGroupInfoList = new ArrayList<CourseGroupInfo>();
				CourseGroupInfo courseGroupInfo = new CourseGroupInfo();
				courseGroupInfo.setCapacity(0);
				courseGroupInfo.setName("Gruppe 1");
				courseGroupInfo.setIsTimeSet(false);
				courseGroupInfoList.add(courseGroupInfo);				
				setSessionBean(Constants.SEMINARPOOL_COURSE_GROUPS_COLLECTION, courseGroupInfoList);
			}
			courseSeminarpoolAllocationInfo = (CourseSeminarpoolAllocationInfo)getSessionBean(Constants.SEMINARPOOL_COURSE_SEMINARPOOL_ALLOCATION_INFO);
			if (courseSeminarpoolAllocationInfo == null){
				courseSeminarpoolAllocationInfo = new CourseSeminarpoolAllocationInfo();
				Map<?, ?> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
				try {
					String stringParamSeminarpool = (String) params.get("seminarpool");
					Long paramSeminarpool = Long.valueOf(stringParamSeminarpool);
					Validate.notNull(paramSeminarpool, "seminarpoolId cannot be null");
					courseSeminarpoolAllocationInfo.setSeminarpoolId(paramSeminarpool);
				} catch (Exception e) {
					throw new IllegalArgumentException("seminarpoolId cannot be null");
				}
				setSessionBean(Constants.SEMINARPOOL_COURSE_SEMINARPOOL_ALLOCATION_INFO, courseSeminarpoolAllocationInfo);
			}
			courseGroupOverview = new CourseGroupOverview();		
		}
		
		public String addGroup(){
			courseGroupInfoList = (List<CourseGroupInfo>)getSessionBean(Constants.SEMINARPOOL_COURSE_GROUPS_COLLECTION);
			CourseGroupInfo courseGroupInfo = new CourseGroupInfo();
			courseGroupInfo.setCapacity(courseGroupCapacity);
			courseGroupInfo.setName(courseGroupName);
			courseGroupInfo.setIsTimeSet(false);
			courseGroupInfoList.add(courseGroupInfo);
			return Constants.SEMINARPOOL_COURSE_ALLOCATION_STEP2;
		}
		
		public String selectCourseGroup() {
			logger.debug("Starting method selectInstitute");
			CourseGroupInfo seminarpoolInfo = currentCourseGroup();
			logger.debug("Returning to method selectInstitute");
			logger.debug(seminarpoolInfo.getId());
			// setSessionBean(Constants.INSTITUTE, institute);
			return Constants.INSTITUTE_PAGE;
		}
		
		private CourseGroupInfo currentCourseGroup() {
			logger.debug("Starting method currentSeminarpool");
			CourseGroupInfo courseGroupDetails = courseGroupOverview.getRowData();
			CourseGroupInfo newCourseGroup = new CourseGroupInfo();
			newCourseGroup.setId(courseGroupDetails.getId());
			return newCourseGroup;
		}
		
		// Start CourseGroup Overview 
		public DataPage<CourseGroupInfo> fetchDataPageCourseGroups(int startRow, int pageSize) {

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
		
	}
