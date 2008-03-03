package org.openuss.web.seminarpool.add;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Init;
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

	private CourseScheduleOverview courseScheduleOverview;
	
	private List<CourseScheduleInfo> courseScheduleInfoList;
	
	private List<CourseGroupInfo> courseGroupInfoList;
	
	private CourseGroupInfo aktCourseGroupInfo;
	

	
	
	private static final Logger logger = Logger.getLogger(SeminarRegistrationStep3Page.class);
	
	@Init
	public void init() throws Exception {
		loadRequiredParams();
		courseScheduleOverview= new CourseScheduleOverview();		
	}
	
	private void loadRequiredParams(){
		courseGroupInfoList = (List<CourseGroupInfo>)getSessionBean(Constants.SEMINARPOOL_COURSE_GROUPS_COLLECTION);
//		Map<?, ?> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//		Long paramCourseGroup;
//		try {
//			String stringCourseGroup = (String) params.get("courseGroup");
//			paramCourseGroup = Long.valueOf(stringCourseGroup);
//			Validate.notNull(paramCourseGroup, "seminarpoolId cannot be null");
//
//		} catch (Exception e) {
//			throw new IllegalArgumentException("seminarpoolId cannot be null");
//		}
//		aktCourseGroupInfo = courseGroupInfoList.get(paramCourseGroup.intValue());
		aktCourseGroupInfo = (CourseGroupInfo) getSessionBean(Constants.SEMINARPOOL_COURSEGROUP_INFO);
		logger.info("Seminarpool CourseGroupInfo " + aktCourseGroupInfo == null);
		if ( aktCourseGroupInfo.getCourseSchedule() == null){
			courseScheduleInfoList = new ArrayList<CourseScheduleInfo>();
			aktCourseGroupInfo.setCourseSchedule(courseScheduleInfoList);
		}
		
	}

	
	// Start CourseScheduleInfo Overview 
	public DataPage<CourseScheduleInfo> fetchDataPageCourseSchedule(int startRow, int pageSize) {

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

	public List<CourseGroupInfo> getCourseGroupInfoList() {
		return courseGroupInfoList;
	}

	public void setCourseGroupInfoList(List<CourseGroupInfo> courseGroupInfoList) {
		this.courseGroupInfoList = courseGroupInfoList;
	}

	public CourseGroupInfo getAktCourseGroupInfo() {
		return aktCourseGroupInfo;
	}

	public void setAktCourseGroupInfo(CourseGroupInfo aktCourseGroupInfo) {
		this.aktCourseGroupInfo = aktCourseGroupInfo;
	}

}
