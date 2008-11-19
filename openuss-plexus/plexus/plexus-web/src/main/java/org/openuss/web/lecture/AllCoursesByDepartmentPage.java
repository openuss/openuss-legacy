package org.openuss.web.lecture;

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
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.framework.web.jsf.util.AcegiUtils;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.lecture.CourseMemberType;
import org.openuss.lecture.CourseService;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.InstituteService;
import org.openuss.lecture.PeriodInfo;
import org.openuss.security.acl.LectureAclEntry;
import org.openuss.web.Constants;
import org.springframework.beans.support.PropertyComparator;

/**
 * 
 * Backing Bean for the view allcoursesbydepartment.xhtml and
 * allcoursesbydepartmenttableoverview.xhtml.
 * 
 * @author Matthias Krieft
 * @author Christian Peters
 * @author Adrian Schmidt
 * @author Ingo Düppe
 * 
 */
@Bean(name = "views$public$department$allcoursesbydepartment", scope = Scope.REQUEST)
@View
public class AllCoursesByDepartmentPage extends AbstractDepartmentPage {

	private static final String ALL_PERIODS = "all_periods";

	private static final String ALL_ACTIVE_PERIODS = "all_active_periods";

	private AllCoursesTable allCoursesTable = new AllCoursesTable();

	@Property(value = "#{instituteInfo}")
	protected InstituteInfo instituteInfo;

	@Property(value = "#{instituteService}")
	private InstituteService instituteService;

	@Property(value = "#{courseService}")
	private CourseService courseService;

	@Property(value = "#{periodInfo}")
	protected PeriodInfo periodInfo;

	@Property(value = "#{courseInfo}")
	protected CourseInfo courseInfo;
	
	@Prerender
	@SuppressWarnings( { "unchecked" })
	public void prerender() throws Exception {
		super.prerender();
		final Long universityId = departmentInfo.getUniversityId();
		final List<PeriodInfo> periodInfos = universityService.findPeriodsByUniversity(universityId);

		if (periodInfo != null && !periodInfos.contains(periodInfo)) {
			if (periodInfo.getId() != null) {
				// if id of period is ALL_ACTIVE_PERIODS or ALL_PERIODS do
				// nothing. Remain selected!
				if (periodInfo.getId() != Constants.COURSES_ALL_PERIODS
						&& periodInfo.getId() != Constants.COURSES_ALL_ACTIVE_PERIODS) {
					periodInfo.setId(Constants.COURSES_ALL_ACTIVE_PERIODS);
					periodInfo.setName(i18n(ALL_ACTIVE_PERIODS));
				}
			}

			// Suppose the case you initially starting the application no
			// periods are selected --> no periodInfo VO was initiated.
			// Set default selection to ALL_ACTIVE_PERIODS
			if ((periodInfo.getId() == null) && (!periodInfos.isEmpty())) {
				periodInfo.setId(Constants.COURSES_ALL_ACTIVE_PERIODS);
				periodInfo.setName(i18n(ALL_ACTIVE_PERIODS));
			}

			if (periodInfos.size() < 1) {
				// normally this should never happen due to the fact that each
				// university has a standard period!
				periodInfo = new PeriodInfo();
			}
		}

		setBean(Constants.PERIOD_INFO, periodInfo);
		addBreadCrumbs();
	}

	private void addBreadCrumbs() {
		breadcrumbs.loadDepartmentCrumbs(departmentInfo);

		BreadCrumb crumb = new BreadCrumb();
		crumb.setName(i18n("department_navigation_allcourses_link"));
		crumb.setHint(i18n("department_navigation_allcourses_link_hint"));

		breadcrumbs.addCrumb(crumb);
	}

	/**
	 * Delivers content for the period select box.
	 * 
	 * @return universityPeriodItems - ArrayList<SelectItem>
	 */
	@SuppressWarnings( { "unchecked" })
	public List<SelectItem> getBelongingUniversityPeriods() {
		final List<SelectItem> universityPeriodItems = new ArrayList<SelectItem>();

		final Long universityId = departmentInfo.getUniversityId();
		final List<PeriodInfo> universityPeriods = universityService.findPeriodsByUniversityWithCourses(universityId);

		// create item in combobox displaying all active periods
		universityPeriodItems.add(new SelectItem(Constants.COURSES_ALL_ACTIVE_PERIODS, i18n(ALL_ACTIVE_PERIODS)));
		// create item in combobox displaying all periods
		universityPeriodItems.add(new SelectItem(Constants.COURSES_ALL_PERIODS, i18n(ALL_PERIODS)));

		for (PeriodInfo period : universityPeriods) {
			universityPeriodItems.add(new SelectItem(period.getId(), period.getName()));
		}

		return universityPeriodItems;
	}

	/**
	 * Value Change Listener to change name of data table.
	 * 
	 * @param event
	 */
	public void processPeriodSelectChanged(final ValueChangeEvent event) {
		periodInfo.setId((Long) event.getNewValue());
		if (periodInfo.getId() == Constants.COURSES_ALL_PERIODS) {
			periodInfo.setName(i18n(AllCoursesByDepartmentPage.ALL_PERIODS));
		} else if (periodInfo.getId() == Constants.COURSES_ALL_ACTIVE_PERIODS) {
			periodInfo.setName(i18n(ALL_ACTIVE_PERIODS));
		} else {
			periodInfo = universityService.findPeriod(periodInfo.getId());
			setBean(Constants.PERIOD_INFO, periodInfo);
		}
	}

	// // bookmark - functions ///////////////////////////////////////
	public Boolean getBookmarkedCourse() {
		if (desktopInfo == null || desktopInfo.getId() == null) {
			refreshDesktop();
		}
		if (allCoursesTable == null || allCoursesTable.getRowIndex() == -1) {
			return false;
		}
		return desktopInfo.getCourseInfos().contains(allCoursesTable.getRowData());
	}

	/**
	 * Bookmarks the selected course on the MyUni Page.
	 * 
	 * @return Outcome
	 */
	public String bookmarkCourse() {
		try {
			CourseInfo currentCourse = allCoursesTable.getRowData();
			desktopService2.linkCourse(desktopInfo.getId(), currentCourse.getId());
			addMessage(i18n("desktop_command_add_course_succeed"));
			refreshDesktop();
			return Constants.SUCCESS;
		} catch (DesktopException e) {
			addError(i18n(e.getMessage()));
			return Constants.FAILURE;
		}
	}

	private boolean isAssistant(CourseInfo courseInfo) {
		return AcegiUtils.hasPermission(courseInfo, new Integer[] {LectureAclEntry.ASSIST});
	}	
	
	private boolean isParticipant(CourseInfo course){
		CourseMemberInfo courseMember = getCourseService().getMemberInfo(course, user);
		if (courseMember != null){
			return courseMember.getMemberType().equals(CourseMemberType.PARTICIPANT);
		}
		return false;
	}
	
	public String removeCourseBookmark() {
		try {
			CourseInfo currentCourse = allCoursesTable.getRowData();
			if (isAssistant(currentCourse) && isParticipant(currentCourse)){
				return removeMembership();
			}
			desktopService2.unlinkCourse(desktopInfo.getId(), currentCourse.getId());
			refreshDesktop();
		} catch (Exception e) {
			addError(i18n("course_error_remove_shortcut"), e.getMessage());
			return Constants.FAILURE;
		}

		addMessage(i18n("desktop_mesage_removed_course_succeed"));
		return Constants.SUCCESS;
	}

	public String removeMembership()
	{
		courseInfo = allCoursesTable.getRowData();
		setBean(Constants.COURSE_INFO, courseInfo);
		return Constants.COURSE_REMOVE_MEMBER;
	}

	// // getter/setter methods ////////////////////////////////////////////////
	public InstituteService getInstituteService() {
		return instituteService;
	}

	public void setInstituteService(final InstituteService instituteService) {
		this.instituteService = instituteService;
	}

	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(final CourseService courseService) {
		this.courseService = courseService;
	}

	public AllCoursesTable getAllCoursesTable() {
		return allCoursesTable;
	}

	public void setAllCoursesTable(final AllCoursesTable allCoursesTable) {
		this.allCoursesTable = allCoursesTable;
	}

	public InstituteInfo getInstituteInfo() {
		return instituteInfo;
	}

	public void setInstituteInfo(final InstituteInfo instituteInfo) {
		this.instituteInfo = instituteInfo;
	}

	public PeriodInfo getPeriodInfo() {
		return periodInfo;
	}

	public void setPeriodInfo(final PeriodInfo periodInfo) {
		this.periodInfo = periodInfo;
	}

	// ///// Inner classes ////////////////////////////////////////////////////
	private class AllCoursesTable extends AbstractPagedTable<CourseInfo> {

		private DataPage<CourseInfo> dataPage;
		private List<CourseInfo> courseList = new ArrayList<CourseInfo>();

		@SuppressWarnings("unchecked")
		@Override
		public DataPage<CourseInfo> getDataPage(int startRow, int pageSize) {
			if (dataPage == null) {
				if (periodInfo != null) {
					DepartmentInfo departmentInfo = (DepartmentInfo) getBean(Constants.DEPARTMENT_INFO);
					if (periodInfo.getId() == null) {
						periodInfo = universityService.findPeriod(Constants.COURSES_ALL_ACTIVE_PERIODS);
						periodInfo.setName(i18n(ALL_ACTIVE_PERIODS));
					}
					if (periodInfo.getId() == Constants.COURSES_ALL_PERIODS) {
						courseList = courseService.findAllCoursesByDepartment(departmentInfo.getId(), false, true);
					} else if (periodInfo.getId() == Constants.COURSES_ALL_ACTIVE_PERIODS) {
						courseList = courseService.findAllCoursesByDepartment(departmentInfo.getId(), true, true);
					} else {
						courseList = courseService.findAllCoursesByDepartmentAndPeriod(departmentInfo.getId(),
								periodInfo.getId(), true);
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


	public CourseInfo getCourseInfo() {
		return courseInfo;
	}

	public void setCourseInfo(CourseInfo courseInfo) {
		this.courseInfo = courseInfo;
	}
}
