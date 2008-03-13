package org.openuss.web.seminarpool.courseAllocation;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.desktop.DesktopInfo;
import org.openuss.desktop.DesktopService2;
import org.openuss.lecture.CourseInfo;
import org.openuss.seminarpool.CourseGroupInfo;
import org.openuss.seminarpool.CourseScheduleInfo;
import org.openuss.seminarpool.CourseSeminarpoolAllocationInfo;
import org.openuss.seminarpool.DayOfWeek;
import org.openuss.seminarpool.SeminarpoolAdministrationService;
import org.openuss.seminarpool.SeminarpoolInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;


@Bean(name = "courseAllocationController", scope = Scope.REQUEST)
@View
public class SeminarRegistrationController extends BasePage {
	
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
	
	@Property(value="#{desktopService2}")
	protected DesktopService2 desktopService2;
	
	public Date startTime;
	
	public Date endTime;
	
	private int courseGroupCapacity;
	
	private Integer weekDay;
	
	
	public String start(){
		
		if (courseSeminarpoolAllocationInfo == null){
			courseSeminarpoolAllocationInfo = new CourseSeminarpoolAllocationInfo();
			setSessionBean(Constants.SEMINARPOOL_COURSE_SEMINARPOOL_ALLOCATION_INFO, courseSeminarpoolAllocationInfo);
		}
		if (getSessionBean(Constants.SEMINARPOOL_COURSE_GROUPS_COLLECTION) != null){
			removeSessionBean(Constants.SEMINARPOOL_COURSE_GROUPS_COLLECTION);	
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
			courseGroupInfoList.add(courseGroupInfo);
			addMessage(i18n(Constants.SEMINARPOOL_COURSE_ALLOCATION_MESSAGE_ADD_GROUP));
		} else {
			addError(i18n(Constants.SEMINARPOOL_COURSE_ALLOCATION_MESSAGE_ERROR_NUMBER));
		}
		return Constants.SEMINARPOOL_COURSE_ALLOCATION_STEP2;
	}
	
	public String storeGroup(){
		if (courseGroupCapacity != 0){
			CourseGroupInfo courseGroupInfo = new CourseGroupInfo();
			courseGroupInfo.setCapacity(courseGroupCapacity);
			courseGroupInfo.setName(i18n(Constants.SEMINARPOOL_COURSE_ALLOCATION_GROUP) + " " + (courseGroupInfoList.size()+1));
			courseGroupInfo.setIsTimeSet(false);
			courseGroupInfo.setIsDefault(false);
			courseGroupInfo.setCourseSeminarpoolAllocationId(courseSeminarpoolAllocationInfo.getId());
			courseGroupInfo.setId(seminarpoolAdministrationService.addCourseGroup(courseGroupInfo));
			courseGroupInfoList.add(courseGroupInfo);
			setSessionBean("SEMINARPOOL_COURSE_GROUPS_COLLECTION", courseGroupInfoList);
			addMessage(i18n(Constants.SEMINARPOOL_COURSE_ALLOCATION_MESSAGE_ADD_GROUP));
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
		courseScheduleInfo.setDayOfWeek(DayOfWeek.fromInteger(weekDay));
		courseGroupInfoList.get(groupIndex).getCourseSchedule().add(courseScheduleInfo);
		courseGroupInfoList.get(groupIndex).setIsTimeSet(true);
		setSessionBean("SEMINARPOOL_COURSE_GROUPS_COLLECTION", courseGroupInfoList);
		addMessage(i18n(Constants.SEMINARPOOL_COURSE_ALLOCATION_MESSAGE_ADD_GROUP_SCHEDULE));
		return Constants.SEMINARPOOL_COURSE_ALLOCATION_STEP3;
	}
	
	public String storeCourseSchedule(){
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
		courseScheduleInfo.setDayOfWeek(DayOfWeek.fromInteger(weekDay));
		CourseGroupInfo courseGroup = courseGroupInfoList.get(groupIndex);
		courseGroup.setIsTimeSet(true);
		courseScheduleInfo.setCourseGroupId(courseGroup.getId());
		courseScheduleInfo.setId(seminarpoolAdministrationService.addCourseSchedule(courseScheduleInfo));
		courseGroupInfoList.get(groupIndex).getCourseSchedule().add(courseScheduleInfo);
		setSessionBean("SEMINARPOOL_COURSE_GROUPS_COLLECTION", courseGroupInfoList);
		addMessage(i18n(Constants.SEMINARPOOL_COURSE_ALLOCATION_MESSAGE_ADD_GROUP_SCHEDULE));
		return Constants.SEMINARPOOL_COURSE_ALLOCATION_STEP3;
	}
	

	public String saveGroup(){
//		aktCourseGroupInfo.setCourseSchedule(courseScheduleList);
		return Constants.SEMINARPOOL_COURSE_ALLOCATION_STEP2;
	}
	
	public void processWeekdayListener(ValueChangeEvent event) {
//		weekDay = (DayOfWeek)event.getNewValue();
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
			try{
				DesktopInfo desktopInfo = desktopService2.findDesktopByUser(user.getId()); 
				if (!desktopService2.isSeminarpoolBookmarked(desktopInfo.getId(), seminarpoolInfo.getId())){
					desktopService2.linkSeminarpool(desktopInfo.getId(), seminarpoolInfo.getId());
				}
			} catch (DesktopException ex){
				return Constants.SEMINARPOOL_COURSE_ALLOCATION_FINISH;
			}
			removeSessionBean(Constants.SEMINARPOOL_COURSE_SEMINARPOOL_ALLOCATION_INFO);
			removeSessionBean(Constants.SEMINARPOOL_COURSE_GROUPS_COLLECTION);
			addMessage(i18n(Constants.SEMINARPOOL_COURSE_ALLOCATION_MESSAGE_ADD_SEMINAR));			
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

	public DesktopService2 getDesktopService2() {
		return desktopService2;
	}

	public void setDesktopService2(DesktopService2 desktopService2) {
		this.desktopService2 = desktopService2;
	}

	public Integer getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(Integer weekDay) {
		this.weekDay = weekDay;
	}
}
