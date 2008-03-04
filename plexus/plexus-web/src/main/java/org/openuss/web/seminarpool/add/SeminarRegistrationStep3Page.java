package org.openuss.web.seminarpool.add;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.seminarpool.CourseGroupInfo;
import org.openuss.seminarpool.CourseSchedule;
import org.openuss.seminarpool.CourseScheduleInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

@Bean(name = "views$secured$seminarpool$add$courseAllocationStep3", scope = Scope.REQUEST)
@View
public class SeminarRegistrationStep3Page extends BasePage {
	
	@Property(value = "#{" + Constants.SEMINARPOOL_COURSE_GROUPS_COLLECTION + "}")
	private List<CourseGroupInfo> courseGroupInfoList;

	@Property(value = "#{" + Constants.COURSE_GROUP_INDEX + "}")
	private Integer groupIndex;

	
	public DataPage<CourseScheduleInfo> dataPageCourseSchedule;

	public CourseScheduleOverview courseScheduleOverview = new CourseScheduleOverview();
	
	private static final Logger logger = Logger.getLogger(SeminarRegistrationStep3Page.class);
	
	
	@Prerender
	public void prerender() throws Exception  {
		breadcrumbs.init();	
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("seminarpool_add_course_allocation_breadcrumb_step3"));
		newCrumb.setHint(i18n("seminarpool_add_course_allocation_breadcrumb_step3"));
		breadcrumbs.addCrumb(newCrumb);	
	}
	
	public String removeCurrentCourseSchedule(){
		int rowIndex = courseScheduleOverview.getRowIndex();
		List<CourseScheduleInfo> list = (List<CourseScheduleInfo>)courseGroupInfoList.get(groupIndex).getCourseSchedule(); 
		list.remove(rowIndex);
		setSessionBean(Constants.SEMINARPOOL_COURSE_GROUPS_COLLECTION, courseGroupInfoList);
		return Constants.SEMINARPOOL_COURSE_ALLOCATION_STEP3; 
	}
	
	public DataPage<CourseScheduleInfo> fetchDataPageCourseSchedule(int startRow, int pageSize) {
		courseGroupInfoList = (List<CourseGroupInfo>)getSessionBean("SEMINARPOOL_COURSE_GROUPS_COLLECTION");
		if (dataPageCourseSchedule == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("fetch seminarpools data page at " + startRow + ", " + pageSize + " sorted by "
						+ courseScheduleOverview.getSortColumn());
			}
			if ( courseGroupInfoList.get(groupIndex).getCourseSchedule() == null){
				courseGroupInfoList.get(groupIndex).setCourseSchedule(new ArrayList<CourseScheduleInfo>());
				setSessionBean("SEMINARPOOL_COURSEGROUP_INFO", courseGroupInfoList.get(groupIndex));
			}
			dataPageCourseSchedule = new DataPage<CourseScheduleInfo>(courseGroupInfoList.get(groupIndex).getCourseSchedule().size(), 0, (List<CourseScheduleInfo>)courseGroupInfoList.get(groupIndex).getCourseSchedule());
		}
		return dataPageCourseSchedule;
	}

	private class CourseScheduleOverview extends AbstractPagedTable<CourseScheduleInfo> {

		private static final long serialVersionUID = 5069930000478432045L;
		
		@Override
		public DataPage<CourseScheduleInfo> getDataPage(int startRow, int pageSize) {
			logger.debug("Starting method getDataPage");
			return fetchDataPageCourseSchedule(startRow, pageSize);
		}
	}

	public DataPage<CourseScheduleInfo> getDataPageCourseSchedule() {
		return dataPageCourseSchedule;
	}

	public void setDataPageCourseSchedule(
			DataPage<CourseScheduleInfo> dataPageCourseSchedule) {
		this.dataPageCourseSchedule = dataPageCourseSchedule;
	}

	public CourseScheduleOverview getCourseScheduleOverview() {
		return courseScheduleOverview;
	}

	public void setCourseScheduleOverview(
			CourseScheduleOverview courseScheduleOverview) {
		this.courseScheduleOverview = courseScheduleOverview;
	}

	public List<CourseGroupInfo> getCourseGroupInfoList() {
		return courseGroupInfoList;
	}

	public void setCourseGroupInfoList(List<CourseGroupInfo> courseGroupInfoList) {
		this.courseGroupInfoList = courseGroupInfoList;
	}

	public Integer getGroupIndex() {
		return groupIndex;
	}

	public void setGroupIndex(Integer groupIndex) {
		this.groupIndex = groupIndex;
	}
}
