package org.openuss.web.seminarpool.add;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.lecture.CourseInfo;
import org.openuss.seminarpool.CourseGroupInfo;
import org.openuss.seminarpool.CourseScheduleInfo;
import org.openuss.seminarpool.CourseSeminarpoolAllocationInfo;
import org.openuss.seminarpool.SeminarpoolAdministrationService;
import org.openuss.seminarpool.SeminarpoolInfo;
import org.openuss.web.Constants;


@Bean(name = "courseAllocationController", scope = Scope.REQUEST)
@View
public class SeminarRegistrationController extends BaseBean {
	
	@Property(value = "#{" + Constants.COURSE_INFO + "}")
	private CourseInfo courseInfo;
	
	@Property(value = "#{" + Constants.SEMINARPOOL_COURSE_SEMINARPOOL_ALLOCATION_INFO + "}")
	private CourseSeminarpoolAllocationInfo courseSeminarpoolAllocationInfo; 
	
	@Property(value = "#{" + Constants.SEMINARPOOL_COURSE_GROUPS_COLLECTION+ "}")
	private List<CourseGroupInfo> courseGroupInfoList;
	
	@Property(value = "#{seminarpoolAdministrationService}")
	protected SeminarpoolAdministrationService seminarpoolAdministrationService; 
	
	@Property(value = "#{" + Constants.COURSE_GROUP_INDEX + "}")
	private Integer groupIndex;
	
	public Date startTime;
	
	public Date endTime;
	
	private int courseGroupCapacity;

	
	public String start(){
		if (courseSeminarpoolAllocationInfo == null){
			courseSeminarpoolAllocationInfo = new CourseSeminarpoolAllocationInfo();
			setSessionBean(Constants.SEMINARPOOL_COURSE_SEMINARPOOL_ALLOCATION_INFO, courseSeminarpoolAllocationInfo);
		}
		courseSeminarpoolAllocationInfo.setCourseId(courseInfo.getId());	
		return Constants.SEMINARPOOL_COURSE_ALLOCATION_STEP1;
	}
	
	public String cancel(){
		if (getSessionBean(Constants.SEMINARPOOL_COURSE_SEMINARPOOL_ALLOCATION_INFO) != null){
			removeSessionBean(Constants.SEMINARPOOL_COURSE_SEMINARPOOL_ALLOCATION_INFO);			
		}
		if (getSessionBean(Constants.SEMINARPOOL_COURSE_GROUPS_COLLECTION) != null){
			removeSessionBean(Constants.SEMINARPOOL_COURSE_GROUPS_COLLECTION);	
		}
		return Constants.SEMINARPOOL_COURSE_ALLOCATION_START;
		
	}

	public String addGroup(){
		if (courseGroupCapacity != 0){
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
	
	public String addCourseSchedule(){
		if(endTime == null || startTime == null){
			addError(i18n(Constants.SEMINARPOOL_COURSE_ALLOCATION_MESSAGE_ERROR_NO_START_OR_END_TIME));
			return Constants.SEMINARPOOL_COURSE_ALLOCATION_STEP3;
		}	
		if(endTime.before(startTime)){
			addError(i18n(Constants.SEMINARPOOL_COURSE_ALLOCATION_MESSAGE_ERROR_END_BEFORE_START));
			return Constants.SEMINARPOOL_COURSE_ALLOCATION_STEP3;
		}	
		CourseScheduleInfo courseScheduleInfo = new CourseScheduleInfo();
		courseScheduleInfo.setStartTime(startTime);
		courseScheduleInfo.setEndTime(endTime);
		courseGroupInfoList.get(groupIndex).getCourseSchedule().add(courseScheduleInfo);
		setSessionBean("SEMINARPOOL_COURSE_GROUPS_COLLECTION", courseGroupInfoList);
		return Constants.SEMINARPOOL_COURSE_ALLOCATION_STEP3;
	}

	public String saveGroup(){
//		aktCourseGroupInfo.setCourseSchedule(courseScheduleList);
		return Constants.SEMINARPOOL_COURSE_ALLOCATION_STEP2;
	}
	
	/**
	 * Adds the current course to the selected seminarpool
	 * @return
	 */
	public String addSeminar(){
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
	
	private void clearCourseGroupInfoId(Collection<CourseGroupInfo> collection){
		for(CourseGroupInfo courseGroupInfo: collection){
			courseGroupInfo.setId(null);
		}
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

	public SeminarpoolAdministrationService getSeminarpoolAdministrationService() {
		return seminarpoolAdministrationService;
	}

	public void setSeminarpoolAdministrationService(
			SeminarpoolAdministrationService seminarpoolAdministrationService) {
		this.seminarpoolAdministrationService = seminarpoolAdministrationService;
	}

	public int getCourseGroupCapacity() {
		return courseGroupCapacity;
	}

	public void setCourseGroupCapacity(int courseGroupCapacity) {
		this.courseGroupCapacity = courseGroupCapacity;
	}

	public CourseInfo getCourseInfo() {
		return courseInfo;
	}

	public void setCourseInfo(CourseInfo courseInfo) {
		this.courseInfo = courseInfo;
	}

//	public CourseGroupInfo getAktCourseGroupInfo() {
//		return aktCourseGroupInfo;
//	}
//
//	public void setAktCourseGroupInfo(CourseGroupInfo aktCourseGroupInfo) {
//		this.aktCourseGroupInfo = aktCourseGroupInfo;
//	}

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

//	public List<CourseScheduleInfo> getCourseScheduleList() {
//		return courseScheduleList;
//	}
//
//	public void setCourseScheduleList(List<CourseScheduleInfo> courseScheduleList) {
//		this.courseScheduleList = courseScheduleList;
//	}

	public Integer getGroupIndex() {
		return groupIndex;
	}

	public void setGroupIndex(Integer groupIndex) {
		this.groupIndex = groupIndex;
	}
}
