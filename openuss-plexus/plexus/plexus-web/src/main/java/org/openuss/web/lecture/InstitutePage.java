package org.openuss.web.lecture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.PeriodInfo;
import org.openuss.news.NewsItemInfo;
import org.openuss.news.NewsService;
import org.openuss.web.Constants;

/**
 * Backing bean for the institute page.
 * 
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 * @author Malte Stockmann
 * @author Kai Stettner
 */

@View
@Bean(name = "views$public$institute$institute", scope = Scope.REQUEST)
public class InstitutePage extends AbstractLecturePage {

	private static final long serialVersionUID = -1982354759705300093L;

	@Property(value = "#{newsService}")
	private NewsService newsService;

	@Property(value = "#{periodInfo}")
	private PeriodInfo periodInfo;
	
	private CourseDataModel courseData = new CourseDataModel();
	
	private List<PeriodInfo> periodInfos = null;
	private Long universityId = 0l;
	private Long departmentId = 0l;
	private List<SelectItem> institutePeriodItems;
	private List<PeriodInfo> institutePeriods;

	@Prerender
	public void prerender() throws LectureException {
		super.prerender();
		
		//	add bread crumbs to institute page
		addBreadCrumbs();
		
		if (instituteInfo != null) {
			departmentId = instituteInfo.getDepartmentId();
			departmentInfo = departmentService.findDepartment(departmentId);
			universityId = departmentInfo.getUniversityId();
			universityInfo = universityService.findUniversity(universityId);
			periodInfos = universityService.findPeriodsByInstituteWithCoursesOrActive(instituteInfo);
		} 
		
		if (periodInfo == null && instituteInfo != null || instituteInfo != null && !periodInfos.contains(periodInfo)) {
			if (periodInfos.size() > 0) {
				periodInfo = periodInfos.get(0);
			} else {
				// normally this should never happen due to the fact that each university has a standard period!
				periodInfo = new PeriodInfo();
			}
		} 
			
		setSessionBean(Constants.PERIOD_INFO, periodInfo);
	}
	
	
	public void addBreadCrumbs()
	{	
		breadcrumbs.loadInstituteCrumbs(instituteInfo);
	}
	
	/**
	 * Bookmarks the selected course on the MyUni Page.
	 * @return Outcome
	 */
	public String shortcutCourse() {
		courseInfo = courseData.getRowData();
		try {
			desktopService2.linkCourse(desktopInfo.getId(), courseInfo.getId());
			addMessage(i18n("desktop_command_add_course_succeed"));
			return Constants.SUCCESS;
		} catch (DesktopException e) {
			logger.error(e);
			addError(i18n(e.getMessage()));
			return Constants.FAILURE;
		}
	}
	
	/**
	 * Adds a shortcut to the institute
	 * @return
	 */
	public String addShortcut()
	{
		try {
			desktopService2.linkInstitute(desktopInfo.getId(), instituteInfo.getId());
		} catch (Exception e) {
			addError(i18n("institute_error_shortcut"), e.getMessage());
			return Constants.FAILURE;
		}
		
		this.addMessage(i18n("institute_success_shortcut"));
		return Constants.SUCCESS;
	}
	
	/**
	 * Removes the shortcut to the isntitute
	 * @return
	 */
	public String removeShortcut()
	{
		try {
			desktopService2.unlinkInstitute(desktopInfo.getId(), instituteInfo.getId());
		} catch (Exception e) {
			addError(i18n("institute_error_remove_shortcut"), e.getMessage());
			return Constants.FAILURE;
		}
		
		addMessage(i18n("institute_success_remove_shortcut"));
		return Constants.SUCCESS;
	}
	
	public List<SelectItem> getBelongingInstitutePeriods() {
		
		institutePeriodItems = new ArrayList<SelectItem>();
		
		if (instituteInfo != null) {
			Long departmentId = instituteInfo.getDepartmentId();
			departmentInfo = departmentService.findDepartment(departmentId);
			Long universityId = departmentInfo.getUniversityId();
			universityInfo = universityService.findUniversity(universityId);
			//get all Period which are active and/or assigned to a course of the institute
			institutePeriods = universityService.findPeriodsByInstituteWithCoursesOrActive(instituteInfo);

			Iterator<PeriodInfo> iter =  institutePeriods.iterator();
			PeriodInfo periodInfo;
			
			//create item in combobox displaying all periods
			SelectItem itemAllPeriods = new SelectItem(institutePeriods.get(0).getId()," -ALLE ZEITRÄUME- ");
			institutePeriodItems.add(itemAllPeriods);
			
			while (iter.hasNext()) {
				periodInfo = iter.next();
				SelectItem item = new SelectItem(periodInfo.getId(),periodInfo.getName());
				institutePeriodItems.add(item);
			}
			
		} 
		
		return institutePeriodItems;
	}
	
	/**
	 * Value Change Listener to change name of data table.
	 * 
	 * @param event
	 */
	public void processPeriodSelectChanged(ValueChangeEvent event) {
		final Long periodId = (Long) event.getNewValue();
		periodInfo = universityService.findPeriod(periodId);
		setSessionBean(Constants.PERIOD_INFO, periodInfo);
	}

	/*public String resendActivationMail(){
		getLectureService().sendActivationCode(institute);
		addMessage(i18n("institute_activationcode_send"));
		return Constants.SUCCESS;
	}*/
	
	private class CourseDataModel extends AbstractPagedTable<CourseInfo> {

		private static final long serialVersionUID = 3682383483634321520L;

		private DataPage<CourseInfo> page;

		@Override
		public DataPage<CourseInfo> getDataPage(int startRow, int pageSize) {
			if (page == null) {
				List<CourseInfo> courses = new ArrayList<CourseInfo>();
				if (periodInfo != null) {
					List<CourseInfo> coursesByPeriodAndInstitute = courseService.findCoursesByPeriodAndInstitute(periodInfo.getId(), instituteInfo.getId());
					if (coursesByPeriodAndInstitute != null) {
						courses.addAll(coursesByPeriodAndInstitute);
					}
				} else {
					logger.error("institute page - no period selected!");
				}
				sort(courses);
				page = new DataPage<CourseInfo>(courses.size(), 0, courses);
			}
			return page;
		}
	}
	
	/**
	 * Apply for Membership.
	 * @return outcome
	 * @throws LectureException 
	 */
	public String applyForMembership() throws LectureException {
		if (user != null && institute != null) {
			lectureService.addInstituteAspirant(user.getId(), institute.getId());
			addMessage(i18n("institute_message_application_of_membership_send"));
		}
		return Constants.SUCCESS;
	}

	/**
	 * Current news items of institute
	 * 
	 * @return
	 * @throws LectureException
	 */
	public Collection<NewsItemInfo> getCurrentNewsItems() {
		return newsService.getCurrentNewsItems(institute, 10);
	}

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	public String showApplication() {
		return Constants.DESKTOP;
	}

	public CourseDataModel getCourseData() {
		return courseData;
	}

	public void setCourseData(CourseDataModel courseData) {
		this.courseData = courseData;
	}

	public PeriodInfo getPeriodInfo() {
		return periodInfo;
	}

	public void setPeriodInfo(PeriodInfo periodInfo) {
		this.periodInfo = periodInfo;
	}
}
