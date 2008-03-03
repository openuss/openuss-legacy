package org.openuss.web.seminarpool.add;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.seminarpool.CourseGroupInfo;
import org.openuss.seminarpool.CourseScheduleInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

@Bean(name = Constants.SEMINARPOOL_COURSE_ALLOCATION_STEP3, scope = Scope.REQUEST)
@View
public class SeminarRegistrationStep3Page extends BasePage {
	
	private DataPage<CourseScheduleInfo> dataPageCourseSchedule;

	private CourseScheduleOverview courseScheduleOverview = new CourseScheduleOverview();
	
	private List<CourseScheduleInfo> courseScheduleInfoList;
	
	private int capacity;
	
	private Date startTime;
	
	private Date endTime;
	
	
	private static final Logger logger = Logger.getLogger(SeminarRegistrationStep3Page.class);
	
	
	@Prerender
	public void prerender() throws Exception  {
		loadRequiredParams();
	}
	
	private void loadRequiredParams(){
		CourseGroupInfo aktCourseGroupInfo = (CourseGroupInfo) getSessionBean(Constants.SEMINARPOOL_COURSEGROUP_INFO);
		capacity = aktCourseGroupInfo.getCapacity();
		logger.info("Seminarpool CourseGroupInfo " + aktCourseGroupInfo == null);
		if ( aktCourseGroupInfo.getCourseSchedule() == null){
			courseScheduleInfoList = new ArrayList<CourseScheduleInfo>();
			aktCourseGroupInfo.setCourseSchedule(courseScheduleInfoList);
		}
		
	}

	
	// Start CourseScheduleInfo Overview 
	public DataPage<CourseScheduleInfo> fetchDataPageCourseSchedule(int startRow, int pageSize) {
		loadRequiredParams();
		if (dataPageCourseSchedule == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("fetch seminarpools data page at " + startRow + ", " + pageSize + " sorted by "
						+ courseScheduleOverview.getSortColumn());
			}
			dataPageCourseSchedule = new DataPage<CourseScheduleInfo>(courseScheduleInfoList.size(), 0, courseScheduleInfoList);
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

	public List<CourseScheduleInfo> getCourseScheduleInfoList() {
		return courseScheduleInfoList;
	}

	public void setCourseScheduleInfoList(
			List<CourseScheduleInfo> courseScheduleInfoList) {
		this.courseScheduleInfoList = courseScheduleInfoList;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


}
