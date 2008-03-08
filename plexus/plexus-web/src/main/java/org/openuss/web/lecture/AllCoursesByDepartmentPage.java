package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Preprocess;
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
import org.openuss.lecture.UniversityService;
import org.openuss.web.Constants;

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
	
	private List<PeriodInfo> periodInfos = null;
	private Long universityId;
	private Long departmentId;
	private List<SelectItem> universityPeriodItems;
	private List<PeriodInfo> universityPeriods;


	@Prerender
	@SuppressWarnings( { "unchecked" })
	public void prerender() throws LectureException {
		
		super.prerender();
		universityId = departmentInfo.getUniversityId();
		periodInfos = universityService.findPeriodsByUniversity(universityId);
		
		if (periodInfo != null && !periodInfos.contains(periodInfo)) {
			if (periodInfo.getId() != null) {
				// if id of period is ALL_ACTIVE_PERIODS or ALL_PERIODS do nothing. Remain selected!
				if((periodInfo.getId().longValue() == Constants.COURSES_ALL_PERIODS) || (periodInfo.getId().longValue() == Constants.COURSES_ALL_ACTIVE_PERIODS)) {
				// do nothing
				// Suppose the case you switch the institute and in the old institute page there was a period selected which is not available
				// in the new institute(this should be always!). --> The combobox value switches to ALL_ACTIVE_PERIODS
				} else {
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

	private class AllCoursesTable extends AbstractPagedTable<CourseInfo> {

		@Override
		public DataPage<CourseInfo> getDataPage(int startRow, int pageSize) {
			//logger.debug("Starting method getDataPage");
			return fetchDataPage(startRow, pageSize);
		}
	}

	private DataPage<CourseInfo> dataPage;
	
	/**
	 * Fetches all courses by department and selected period.
	 * 
	 * @param startRow
	 * @param pageSize
	 * @return dataPage - List of all courseInfos
	 */
	public DataPage<CourseInfo> fetchDataPage(int startRow, int pageSize) {
		
		if (dataPage == null) {
			/*if (logger.isDebugEnabled()) {
				logger.debug("fetch institutes data page at " + startRow + ", "+ pageSize+" sorted by "+institutes.getSortColumn());
			}*/		
		
			// getting the departmentInfo out of the user's session scope
			DepartmentInfo departmentInfo = (DepartmentInfo) getSessionBean(Constants.DEPARTMENT_INFO);
			
			List<CourseInfo> courseList = new ArrayList<CourseInfo>();
			if (periodInfo != null) {
				if(periodInfo.getId() == null){
					periodInfo = universityService.findPeriod(Constants.COURSES_ALL_ACTIVE_PERIODS);
					periodInfo.setName(i18n("all_active_periods"));
				}
				if (periodInfo.getId().longValue() == Constants.COURSES_ALL_PERIODS) {
					courseList = getCourseService().findAllCoursesByDepartment(departmentId, false, true);
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
	
	private void sort(List<CourseInfo> courseList) {
		if (StringUtils.equals("instituteName", allCoursesTable.getSortColumn())) {
			Collections.sort(courseList, new InstituteNameComparator());
		} else if (StringUtils.equals("periodName", allCoursesTable.getSortColumn())){
			Collections.sort(courseList, new PeriodComparator());
		} else {
			Collections.sort(courseList, new CourseNameComparator());
		}
	}
	
	/**
	 * Delivers content for the period select box.
	 * 
	 * @return universityPeriodItems - ArrayList<SelectItem>
	 */
	@SuppressWarnings( { "unchecked" })
	public List<SelectItem> getBelongingUniversityPeriods() {
		
		universityPeriodItems = new ArrayList<SelectItem>();
		
		departmentInfo = (DepartmentInfo) getSessionBean(Constants.DEPARTMENT_INFO);
		universityId = departmentInfo.getUniversityId();
		universityInfo = universityService.findUniversity(universityId);
		//Only periods with courses are found, not the active ones without courses
		universityPeriods = universityService.findPeriodsByUniversityWithCourses(universityId);

		Iterator<PeriodInfo> iter =  universityPeriods.iterator();
		PeriodInfo periodInfo;
		
		//create item in combobox displaying all active periods
		SelectItem itemAllActivePeriods = new SelectItem(Constants.COURSES_ALL_ACTIVE_PERIODS, i18n("all_active_periods"));
		universityPeriodItems.add(itemAllActivePeriods);
		//create item in combobox displaying all periods
		SelectItem itemAllPeriods = new SelectItem(Constants.COURSES_ALL_PERIODS, i18n("all_periods"));
		universityPeriodItems.add(itemAllPeriods);
		
		while (iter.hasNext()) {
			periodInfo = iter.next();
			SelectItem item = new SelectItem(periodInfo.getId(),periodInfo.getName());
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
		if (periodId.longValue() == Constants.COURSES_ALL_PERIODS) {
			periodInfo.setName(i18n("all_periods"));
		} else if (periodId.longValue() == Constants.COURSES_ALL_ACTIVE_PERIODS) {
			periodInfo.setName(i18n("all_active_periods"));
		} else {
			periodInfo = universityService.findPeriod(periodId);
			setSessionBean(Constants.PERIOD_INFO, periodInfo);
		}
	}
	
/* ----------- bookmark - functions -------------*/
	
	public Boolean getBookmarked() {
		try {
			return desktopService2.isInstituteBookmarked(instituteInfo.getId(), user.getId());
		} catch (Exception e) {
			//logger.error(e);
		}		
		return false;
	}
	
	public Boolean getBookmarked2() {
		try {
			CourseInfo currentCourse = currentCourse();
			return desktopService2.isCourseBookmarked(currentCourse.getId(), user.getId());
		} catch (Exception e) {
			//logger.error(e);
		}	
		return false;
	}
	
	private CourseInfo currentCourse() {
		CourseInfo course = allCoursesTable.getRowData();		
		return course;
	}
	
	/**
	 * Bookmarks the selected course on the MyUni Page.
	 * @return Outcome
	 */
	public String shortcutCourse() {
		//courseInfo = courseData.getRowData();
		try {
			CourseInfo currentCourse = currentCourse();
			desktopService2.linkCourse(desktopInfo.getId(), currentCourse.getId());
			addMessage(i18n("desktop_command_add_course_succeed"));
			return Constants.SUCCESS;
		} catch (DesktopException e) {
			addError(i18n(e.getMessage()));
			return Constants.FAILURE;
		}
	}
	
	public String removeCourseShortcut()
	{
		try {
			//courseInfo = courseData.getRowData();
			CourseInfo currentCourse = currentCourse();
			desktopService2.unlinkCourse(desktopInfo.getId(), currentCourse.getId());
		} catch (Exception e) {
			addError(i18n("institute_error_remove_shortcut"), e.getMessage());
			return Constants.FAILURE;
		}
		
		addMessage(i18n("institute_success_remove_shortcut"));
		return Constants.SUCCESS;
	}
	
	/**
	 * Adds a shortcut to the department
	 * @return
	 */
	public String addShortcut()
	{
		try {
			desktopService2.linkDepartment(desktopInfo.getId(), departmentInfo.getId());
		} catch (Exception e) {
			addError(i18n("department_error_shortcut"), e.getMessage());
			return Constants.FAILURE;
		}
		
		this.addMessage(i18n("department_success_shortcut"));
		return Constants.SUCCESS;
	}
	
	/**
	 * Removes the shortcut to the department
	 * @return
	 */
	public String removeShortcut()
	{
		try {
			desktopService2.unlinkDepartment(desktopInfo.getId(), departmentInfo.getId());
		} catch (Exception e) {
			addError(i18n("department_error_remove_shortcut"), e.getMessage());
			return Constants.FAILURE;
		}
		
		addMessage(i18n("department_success_remove_shortcut"));
		return Constants.SUCCESS;
	}
	
/* ----------- institute sorting comparators -------------*/
	
	private class CourseNameComparator implements Comparator<CourseInfo> {
		public int compare(CourseInfo f1, CourseInfo f2) {
			if (allCoursesTable.isAscending()) {
				return f1.getName().compareToIgnoreCase(f2.getName());
			} else {
				return f2.getName().compareToIgnoreCase(f1.getName());
			}
		}
	}

	private class InstituteNameComparator implements Comparator<CourseInfo> {
		public int compare(CourseInfo f1, CourseInfo f2) {
			if (allCoursesTable.isAscending()) {
				return f1.getInstituteName().compareToIgnoreCase(f2.getInstituteName());
			} else {
				return f2.getInstituteName().compareToIgnoreCase(f1.getInstituteName());
			}
		}
	}
	
	private class PeriodComparator implements Comparator<CourseInfo> {
		public int compare(CourseInfo f1, CourseInfo f2) {
			if (allCoursesTable.isAscending()) {
				return f1.getPeriodName().compareToIgnoreCase(f2.getPeriodName());
			} else {
				return f2.getPeriodName().compareToIgnoreCase(f1.getPeriodName());
			}
		}
	}
	
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

	public List<PeriodInfo> getPeriodInfos() {
		return periodInfos;
	}

	public void setPeriodInfos(List<PeriodInfo> periodInfos) {
		this.periodInfos = periodInfos;
	}

	public Long getUniversityId() {
		return universityId;
	}

	public void setUniversityId(Long universityId) {
		this.universityId = universityId;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public List<SelectItem> getUniversityPeriodItems() {
		return universityPeriodItems;
	}

	public void setUniversityPeriodItems(List<SelectItem> universityPeriodItems) {
		this.universityPeriodItems = universityPeriodItems;
	}

	public List<PeriodInfo> getUniversityPeriods() {
		return universityPeriods;
	}

	public void setUniversityPeriods(List<PeriodInfo> universityPeriods) {
		this.universityPeriods = universityPeriods;
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
}
