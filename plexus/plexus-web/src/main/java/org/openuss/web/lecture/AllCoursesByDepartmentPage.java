package org.openuss.web.lecture;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.lang.StringUtils;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseService;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.InstituteService;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.PeriodInfo;
import org.openuss.lecture.UniversityInfo;
import org.openuss.web.Constants;
import org.springframework.beans.support.PropertyComparator;

/**
 * 
 * Backing Bean for the view allcoursesbydepartment.xhtml and allcoursesbydepartmenttableoverview.xhtml.
 * 
 * @author Matthias Krieft
 * @author Christian Peters
 * @author Adrian Schmidt
 * 
 */
@Bean(name = "views$public$department$allcoursesbydepartment", scope = Scope.REQUEST)
@View
public class AllCoursesByDepartmentPage extends AbstractDepartmentPage{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AllCoursesByDepartmentPage.class);
	
	/**
	 * This is the paged table with all relevant courses.
	 * 
	 * @see AllCoursesTable (subclass)
	 */
	private AllCoursesTable allCoursesTable = new AllCoursesTable();
	
	@Property(value = "#{universityInfo}")
	protected UniversityInfo universityInfo;
	
	@Property(value = "#{instituteInfo}")
	protected InstituteInfo instituteInfo;
		
	@Property(value = "#{instituteService}")
	private InstituteService instituteService;
	
	@Property(value = "#{courseService}")
	private CourseService courseService;
	
	@Property(value = "#{periodInfo}")
	protected PeriodInfo periodInfo;
	
	@Prerender
	@SuppressWarnings( { "unchecked" })
	public void prerender() throws LectureException {
		
		super.prerender();
		final Long universityId = departmentInfo.getUniversityId();
		final List<PeriodInfo> periodInfos = universityService.findPeriodsByUniversity(universityId);
		
		if (periodInfo != null && !periodInfos.contains(periodInfo)) {
			if (periodInfo.getId() != null) {
				// if id of period is ALL_ACTIVE_PERIODS or ALL_PERIODS do nothing. Remain selected!
				if(!periodInfo.getId().equals(Constants.COURSES_ALL_PERIODS) && !periodInfo.getId().equals(Constants.COURSES_ALL_ACTIVE_PERIODS)) {
					periodInfo.setId(Constants.COURSES_ALL_ACTIVE_PERIODS);
					periodInfo.setName(i18n("all_active_periods"));
				}
			}
		
			// Suppose the case you initially starting the application no periods are selected --> no periodInfo VO was initiated.
			// Set default selection to ALL_ACTIVE_PERIODS
			if ((periodInfo.getId() == null) && (periodInfos.size() > 0)) {
				periodInfo.setId(Constants.COURSES_ALL_ACTIVE_PERIODS);
				periodInfo.setName(i18n("all_active_periods"));
			}
			
			if (periodInfos.size() < 1) {
				// normally this should never happen due to the fact that each university has a standard period!
				periodInfo = new PeriodInfo();	
			}
		}
		
		setSessionBean(Constants.PERIOD_INFO, periodInfo);		
		addBreadCrumbs();
	}
	
	private void addBreadCrumbs() {
		breadcrumbs.loadDepartmentCrumbs(departmentInfo);
	}

	/**
	 * Delivers content for the period select box.
	 * 
	 * @return universityPeriodItems - ArrayList<SelectItem>
	 */
	@SuppressWarnings( { "unchecked" })
	public List<SelectItem> getBelongingUniversityPeriods() {
		
		final List<SelectItem> universityPeriodItems = new ArrayList<SelectItem>();
		
		departmentInfo = (DepartmentInfo) getSessionBean(Constants.DEPARTMENT_INFO);
		final Long universityId = departmentInfo.getUniversityId();
		universityInfo = universityService.findUniversity(universityId);
		//Only periods with courses are found, not the active ones without courses
		final List<PeriodInfo> universityPeriods = universityService.findPeriodsByUniversityWithCourses(universityId);
		
		//create item in combobox displaying all active periods
		final SelectItem itemAllActivePeriods = new SelectItem(Constants.COURSES_ALL_ACTIVE_PERIODS, i18n("all_active_periods"));
		universityPeriodItems.add(itemAllActivePeriods);
		//create item in combobox displaying all periods
		final SelectItem itemAllPeriods = new SelectItem(Constants.COURSES_ALL_PERIODS, i18n("all_periods"));
		universityPeriodItems.add(itemAllPeriods);
		
		for(PeriodInfo period : universityPeriods) {
			SelectItem item = new SelectItem(period.getId(),period.getName());
			universityPeriodItems.add(item);
		}
		
		return universityPeriodItems;
	}
	
	/**
	 * Value Change Listener to change name of data table.
	 * 
	 * @param event
	 */
	public void processPeriodSelectChanged(ValueChangeEvent event) {
		final Long periodId = (Long) event.getNewValue();
		if (periodId.equals(Constants.COURSES_ALL_PERIODS)) {
			periodInfo.setName(i18n("all_periods"));
		} else if (periodId.equals(Constants.COURSES_ALL_ACTIVE_PERIODS)) {
			periodInfo.setName(i18n("all_active_periods"));
		} else {
			periodInfo = universityService.findPeriod(periodId);
			setSessionBean(Constants.PERIOD_INFO, periodInfo);
		}
	}
	
    //// bookmark - functions ///////////////////////////////////////
	public Boolean getBookmarkedCourse() {
		try {
			CourseInfo currentCourse = allCoursesTable.getRowData();
			return desktopService2.isCourseBookmarked(currentCourse.getId(), user.getId());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return false;
	}
	
	/**
	 * Bookmarks the selected course on the MyUni Page.
	 * @return Outcome
	 */
	public String bookmarkCourse() {
		try {
			CourseInfo currentCourse = allCoursesTable.getRowData();
			desktopService2.linkCourse(desktopInfo.getId(), currentCourse.getId());
			addMessage(i18n("desktop_command_add_course_succeed"));
			return Constants.SUCCESS;
		} catch (DesktopException e) {
			addError(i18n(e.getMessage()));
			return Constants.FAILURE;
		}
	}
	
	public String removeCourseBookmark()
	{
		try {
			CourseInfo currentCourse = allCoursesTable.getRowData();
			desktopService2.unlinkCourse(desktopInfo.getId(), currentCourse.getId());
		} catch (Exception e) {
			addError(i18n("course_error_remove_shortcut"), e.getMessage());
			return Constants.FAILURE;
		}
		
		addMessage(i18n("institute_success_remove_shortcut"));
		return Constants.SUCCESS;
	}
	
	//// getter/setter methods ////////////////////////////////////////////////	
	public InstituteService getInstituteService() {
		return instituteService;
	}

	public void setInstituteService(InstituteService instituteService) {
		this.instituteService = instituteService;
	}

	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}
	
	public AllCoursesTable getAllCoursesTable() {
		return allCoursesTable;
	}

	public void setAllCoursesTable(AllCoursesTable allCoursesTable) {
		this.allCoursesTable = allCoursesTable;
	}

	public UniversityInfo getUniversityInfo() {
		return universityInfo;
	}

	public void setUniversityInfo(UniversityInfo universityInfo) {
		this.universityInfo = universityInfo;
	}

	public InstituteInfo getInstituteInfo() {
		return instituteInfo;
	}

	public void setInstituteInfo(InstituteInfo instituteInfo) {
		this.instituteInfo = instituteInfo;
	}

	public PeriodInfo getPeriodInfo() {
		return periodInfo;
	}

	public void setPeriodInfo(PeriodInfo periodInfo) {
		this.periodInfo = periodInfo;
	}
	
	
	/////// Inner classes ////////////////////////////////////////////////////
	private class AllCoursesTable extends AbstractPagedTable<CourseInfo> {
		
		private DataPage<CourseInfo> dataPage;
		private List<CourseInfo> courseList = new ArrayList<CourseInfo>();

		
		@SuppressWarnings("unchecked")
		@Override
		public DataPage<CourseInfo> getDataPage(int startRow, int pageSize) {
			
			if (dataPage == null) {
				DepartmentInfo departmentInfo = (DepartmentInfo) getSessionBean(Constants.DEPARTMENT_INFO);
				
				if (periodInfo != null) {
					if(periodInfo.getId() == null){
						periodInfo = universityService.findPeriod(Constants.COURSES_ALL_ACTIVE_PERIODS);
						periodInfo.setName(i18n("all_active_periods"));
					}
					if (periodInfo.getId().longValue() == Constants.COURSES_ALL_PERIODS) {
						courseList = getCourseService().findAllCoursesByDepartment(departmentInfo.getId(), false, true);
					} else if (periodInfo.getId().longValue() == Constants.COURSES_ALL_ACTIVE_PERIODS){
						courseList = getCourseService().findAllCoursesByDepartment(departmentInfo.getId(), true, true);
					} else {
						courseList = getCourseService().findAllCoursesByDepartmentAndPeriod(departmentInfo.getId(), periodInfo.getId(), true);
					}			
				}
				sort(courseList);
				dataPage = new DataPage<CourseInfo>(courseList.size(), 0, courseList);
			}
			
			return dataPage;
		}
		
		/**
		 * Default property sort method
		 * 
		 * @param periods
		 */
		@SuppressWarnings("unchecked")
		@Override
		protected void sort(List<CourseInfo> list) {
			ComparatorChain chain = new ComparatorChain();
								
			if (StringUtils.isNotBlank(getSortColumn())) {
				chain.addComparator(new PropertyComparator(getSortColumn(), true, isAscending()));
			} else {
				chain.addComparator(new PropertyComparator("name", true, isAscending()));
			}
			Collections.sort(list, chain);
		}
	}
}
