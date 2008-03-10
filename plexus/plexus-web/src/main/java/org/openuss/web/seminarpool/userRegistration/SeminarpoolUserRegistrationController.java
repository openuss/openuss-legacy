package org.openuss.web.seminarpool.userRegistration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.myfaces.component.html.ext.HtmlSelectOneListbox;
import org.apache.myfaces.taglib.html.ext.HtmlSelectOneListboxTag;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Preprocess;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.web.jsf.model.AbstractPagedTable;
import org.openuss.framework.web.jsf.model.DataPage;
import org.openuss.lecture.LectureException;
import org.openuss.lecture.UniversityInfo;
import org.openuss.seminarpool.CourseSeminarpoolAllocationInfo;
import org.openuss.seminarpool.SeminarConditionInfo;
import org.openuss.seminarpool.SeminarPrioritiesInfo;
import org.openuss.seminarpool.SeminarUserConditionValueInfo;
import org.openuss.seminarpool.SeminarUserRegistrationInfo;
import org.openuss.seminarpool.SeminarpoolAdministrationService;
import org.openuss.seminarpool.SeminarpoolInfo;
import org.openuss.seminarpool.SeminarpoolStatus;
import org.openuss.seminarpool.SeminarpoolUserRegistrationService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

import com.sun.corba.se.internal.Interceptors.PIORB;


@Bean(name = "seminarpoolUserRegistration", scope = Scope.REQUEST)
@View
public class SeminarpoolUserRegistrationController extends BasePage {
	/**
	 * starts the seminarpool registration process
	 * @return Outcome
	 */
	
	private static final Logger logger = Logger.getLogger(SeminarpoolUserRegistrationController.class);
	
	private List<SeminarConditionInfo> conditionList = new ArrayList<SeminarConditionInfo>();
	private List<SeminarUserConditionValueInfo> seminarUserConditionValueList = new ArrayList<SeminarUserConditionValueInfo>();
	
	@Property(value = "#{seminarpoolInfo}")
	protected SeminarpoolInfo seminarpoolInfo;
	@Property(value = "#{" + Constants.SEMINARPOOL_USER_REGISTRATION_INFO + "}")
	protected SeminarUserRegistrationInfo seminarUserRegistrationInfo;
	@Property(value = "#{seminarpoolAdministrationService}")
	protected SeminarpoolAdministrationService seminarpoolAdministrationService;
	@Property(value = "#{seminarpoolUserRegistrationService}")
	protected SeminarpoolUserRegistrationService seminarpoolUserRegistrationService;
	@Property(value="#{courseSeminarpoolAllocationInfo}")
	private CourseSeminarpoolAllocationInfo courseSeminarpoolAllocationInfo;
	
	int counter=0;
	
	private boolean status;
	private boolean status2;
	
	protected long priority;

	private List<SeminarPrioritiesInfo> seminarPriorityList;
	
	private List<String> priorities;

	public SeminarpoolUserRegistrationService getSeminarpoolUserRegistrationService() {
		return seminarpoolUserRegistrationService;
	}

	public void setSeminarpoolUserRegistrationService(
			SeminarpoolUserRegistrationService seminarpoolUserRegistrationService) {
		this.seminarpoolUserRegistrationService = seminarpoolUserRegistrationService;
	}

	public CourseSeminarpoolAllocationInfo getCourseSeminarpoolAllocationInfo() {
		return courseSeminarpoolAllocationInfo;
	}

	public void setCourseSeminarpoolAllocationInfo(
			CourseSeminarpoolAllocationInfo courseSeminarpoolAllocationInfo) {
		this.courseSeminarpoolAllocationInfo = courseSeminarpoolAllocationInfo;
	}
	
	public String removeUserRegistrationFinal(){
		this.getSeminarpoolUserRegistrationService().removeUserRegistration(this.seminarUserRegistrationInfo.getId());
		return "seminarpool_main";
	}

	public String start() {
		logger.debug("Start seminarpool registration process");

		// create new seminarpoolInfo object for session
		seminarUserRegistrationInfo = this.getSeminarpoolUserRegistrationService().findSeminarUserRegistrationByUserAndSeminarpool(user.getId(), seminarpoolInfo.getId());
		setSessionBean(Constants.SEMINARPOOL_USER_REGISTRATION_INFO, seminarUserRegistrationInfo);
		if ( seminarUserRegistrationInfo != null && seminarUserRegistrationInfo.getId() != null) {
			return Constants.SEMINARPOOL_USER_REGISTRATION_EDIT_STEP1_PAGE;
		} 
		return Constants.SEMINARPOOL_USER_REGISTRATION_STEP1_PAGE;
	}
	
	public String startEditProcess() {
		logger.debug("Starts the edit process");
		return Constants.SEMINARPOOL_USER_REGISTRATION_EDIT_STEP2_PAGE;
	}
	
	
	public String step2(){
		seminarUserRegistrationInfo = (SeminarUserRegistrationInfo) this.getSessionBean("seminarUserRegistrationInfo");
		if(seminarPriorityList == null){
			seminarPriorityList = seminarUserRegistrationInfo.getSeminarPriorityList();
		}
		if ( seminarPriorityList == null || seminarPriorityList.size() == 0 ) {
			addError(i18n(Constants.SEMINARPOOL_USER_REGISTRATION_ERROR_NO_COURSE_SELECTED));
			return Constants.SEMINARPOOL_USER_REGISTRATION_STEP1_PAGE;			
		}
		if (seminarPriorityList != null && seminarPriorityList.size() > 0 && !checkDoubleElements(seminarPriorityList)){
			addError(i18n(Constants.SEMINARPOOL_USER_REGISTRATION_ERROR_DOUBLE_COURSES_SELECTED));
			return Constants.SEMINARPOOL_USER_REGISTRATION_STEP1_PAGE;
		}
		seminarUserRegistrationInfo.setSeminarPriorityList(seminarPriorityList);
		seminarUserRegistrationInfo.setSeminarpoolId(seminarpoolInfo.getId());
		seminarUserRegistrationInfo.setUserId(user.getId());
		this.setSessionBean("seminarPriorityList", seminarPriorityList);
		this.setSessionBean("seminarUserRegistrationInfo", seminarUserRegistrationInfo);
		return Constants.SEMINARPOOL_USER_REGISTRATION_STEP2_PAGE;
	}
	
	public String editStep2(){
		seminarUserRegistrationInfo = (SeminarUserRegistrationInfo) this.getSessionBean("seminarUserRegistrationInfo");
		if (seminarPriorityList != null && seminarPriorityList.size() > 0 && !checkDoubleElements(seminarPriorityList)){
			addError(i18n(Constants.SEMINARPOOL_USER_REGISTRATION_ERROR_DOUBLE_COURSES_SELECTED));
			return Constants.SEMINARPOOL_USER_REGISTRATION_EDIT_STEP2_PAGE;
		}
		if ( seminarPriorityList == null || seminarPriorityList.size() == 0 ) {
			addError(i18n(Constants.SEMINARPOOL_USER_REGISTRATION_ERROR_NO_COURSE_SELECTED));
			return Constants.SEMINARPOOL_USER_REGISTRATION_EDIT_STEP2_PAGE;			
		}
		seminarUserRegistrationInfo.setSeminarPriorityList(seminarPriorityList);
		seminarUserRegistrationInfo.setSeminarpoolId(seminarpoolInfo.getId());
		seminarUserRegistrationInfo.setUserId(user.getId());
		seminarpoolUserRegistrationService.editUserRegistration(seminarUserRegistrationInfo, null);
		return Constants.SEMINARPOOL_USER_REGISTRATION_EDIT_STEP1_PAGE;
	}

	
	private boolean checkDoubleElements(List<SeminarPrioritiesInfo> list){
		int size = list.size();
		for ( int outerIndex = 0; outerIndex < size; outerIndex++ ) {
			for ( int innerIndex = outerIndex + 1; innerIndex < size; innerIndex++ ) {
				if (list.get(outerIndex).getCourseId().equals(list.get(innerIndex).getCourseId())){
					return false;
				}
			}
		}		
		return true;
	}
	
	public String create(){
		if(seminarUserRegistrationInfo.getId() == null){
			this.getSeminarpoolUserRegistrationService().registerUserForSeminarpool(seminarUserRegistrationInfo, seminarUserConditionValueList);
		}
		else{
			this.getSeminarpoolUserRegistrationService().editUserRegistration(seminarUserRegistrationInfo, seminarUserConditionValueList);
		}
		
		return Constants.SEMINARPOOL_MAIN;
	}
	
	
	public List<SelectItem> getAllSeminars() {
		List<SelectItem> seminarItems = new ArrayList<SelectItem>();
		List<CourseSeminarpoolAllocationInfo> seminarlist = this.seminarpoolAdministrationService.findAcceptedCoursesInSeminarpool(seminarpoolInfo.getId());
		
		
		if (!seminarlist.isEmpty()) {
			seminarItems.add(new SelectItem(Constants.SEMINARLIST, i18n("seminarlist")));
			for (CourseSeminarpoolAllocationInfo seminar: seminarlist) {
				seminarItems.add(new SelectItem(seminar.getId(),seminar.getCourseName()));
			}
		}
		
		return seminarItems;
	}
	
	public int getMaxPriority(){
		return seminarpoolInfo.getPriorities();
	}
	
	public int getCountConditions(){
		List<SeminarConditionInfo> seminarconditions = this.seminarpoolAdministrationService.findConditionBySeminarpool(seminarpoolInfo.getId());
		return seminarconditions.size();
	}
	
	public int getType(){
		List<SeminarConditionInfo> seminarconditions = this.seminarpoolAdministrationService.findConditionBySeminarpool(seminarpoolInfo.getId());
		int type = seminarconditions.get(counter).getFieldType().getValue();
		counter++;
		return type;
	}
	
	
	public void processSeminarSelectChanged(ValueChangeEvent event) {
		if (event.getNewValue() instanceof Long) {
			Long courseId = (Long) event.getNewValue();
			if (seminarPriorityList == null){
				seminarPriorityList = new ArrayList<SeminarPrioritiesInfo>();
			}
			SeminarPrioritiesInfo spi = new SeminarPrioritiesInfo();
			spi.setCourseId(courseId);
			String identifier = event.getComponent().getId().toString();
			spi.setPriority(Integer.parseInt(identifier.substring(12)));
			seminarPriorityList.add(spi);
			logger.info("ValueChangeEvent: Changing course id for new SeminarPriority to " + spi.getCourseId());
		}
	}
	
	public void processChanged(ValueChangeEvent event) {
		if (seminarUserConditionValueList == null){
			seminarUserConditionValueList = new ArrayList<SeminarUserConditionValueInfo>();
		}
		List<SeminarConditionInfo> seminarconditions = this.seminarpoolAdministrationService.findConditionBySeminarpool(seminarpoolInfo.getId());
		int index=seminarUserConditionValueList.size();
		SeminarUserConditionValueInfo suci = new SeminarUserConditionValueInfo();
		suci.setConditionValue(event.getNewValue().toString());
		suci.setSeminarConditionId(seminarconditions.get(index).getId());
		seminarUserConditionValueList.add(suci);
		
		
	}

	public SeminarpoolInfo getSeminarpoolInfo() {
		return seminarpoolInfo;
	}

	public void setSeminarpoolInfo(SeminarpoolInfo seminarpoolInfo) {
		this.seminarpoolInfo = seminarpoolInfo;
	}

	public SeminarUserRegistrationInfo getSeminarUserRegistrationInfo() {
		return seminarUserRegistrationInfo;
	}

	public void setSeminarUserRegistrationInfo(
			SeminarUserRegistrationInfo seminarUserRegistration) {
		this.seminarUserRegistrationInfo = seminarUserRegistration;
	}

	public SeminarpoolAdministrationService getSeminarpoolAdministrationService() {
		return seminarpoolAdministrationService;
	}

	public void setSeminarpoolAdministrationService(
			SeminarpoolAdministrationService seminarpoolAdministrationService) {
		this.seminarpoolAdministrationService = seminarpoolAdministrationService;
	}

	public long getPriority() {
		priority=-1;
		return priority;
	}

	public void setPriority(long priority) {
		this.priority = priority;
	}

	public List<SeminarPrioritiesInfo> getSeminarPriorityList() {
		return seminarPriorityList;
	}

	public void setSeminarPriorityList(
			List<SeminarPrioritiesInfo> seminarPriorityList) {
		this.seminarPriorityList = seminarPriorityList;
	}

	public List<String> getPriorities() {
		return priorities;
	}

	public void setPriorities(List<String> priorities) {
		this.priorities = priorities;
	}

	public boolean getStatus() {
		return (seminarpoolInfo.getSeminarpoolStatus().getValue()>0);
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public boolean getStatus2() {
		return (seminarpoolInfo.getSeminarpoolStatus().getValue() < SeminarpoolStatus.REGISTRATIONCOMPLETEPHASE.getValue());
	}

	public void setStatus2(boolean status) {
		this.status = status;
	}

	public List<SeminarConditionInfo> getConditionList() {
		conditionList = this.getSeminarpoolAdministrationService().findConditionBySeminarpool(seminarpoolInfo.getId());
		return conditionList;
	}

	public void setConditionList(List<SeminarConditionInfo> conditionList) {
		this.conditionList = conditionList;
	}
	
}
