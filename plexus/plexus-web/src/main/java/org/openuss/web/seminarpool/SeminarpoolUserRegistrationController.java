package org.openuss.web.seminarpool;

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
import org.openuss.seminarpool.SeminarPrioritiesInfo;
import org.openuss.seminarpool.SeminarUserRegistrationInfo;
import org.openuss.seminarpool.SeminarpoolAdministrationService;
import org.openuss.seminarpool.SeminarpoolInfo;
import org.openuss.seminarpool.SeminarpoolStatus;
import org.openuss.seminarpool.SeminarpoolUserRegistrationService;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;


@Bean(name = "seminarpoolUserRegistration", scope = Scope.REQUEST)
@View
public class SeminarpoolUserRegistrationController extends BasePage {
	/**
	 * starts the seminarpool registration process
	 * @return Outcome
	 */
	
	private static final Logger logger = Logger.getLogger(SeminarpoolUserRegistrationController.class);
	
	@Property(value = "#{seminarpoolInfo}")
	protected SeminarpoolInfo seminarpoolInfo;
	@Property(value = "#{seminarUserRegistrationInfo}")
	protected SeminarUserRegistrationInfo seminarUserRegistrationInfo;
	@Property(value = "#{seminarpoolAdministrationService}")
	protected SeminarpoolAdministrationService seminarpoolAdministrationService;
	@Property(value = "#{seminarpoolUserRegistrationService}")
	protected SeminarpoolUserRegistrationService seminarpoolUserRegistrationService;
	@Property(value="#{courseSeminarpoolAllocationInfo}")
	private CourseSeminarpoolAllocationInfo courseSeminarpoolAllocationInfo;
	

	
	
	
	protected long priority;

	private List<SeminarPrioritiesInfo> seminarPriorityList;
	
	

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

	public String start() {
		logger.debug("Start seminarpool registration process");

		// create new seminarpoolInfo object for session
		seminarUserRegistrationInfo = this.getSeminarpoolUserRegistrationService().findSeminarUserRegistrationByUserAndSeminarpool(user.getId(), seminarpoolInfo.getId());
		return Constants.SEMINARPOOL_USER_REGISTRATION_STEP1_PAGE;
	}
	
	public String step2(){
		seminarUserRegistrationInfo.setSeminarPriorityList(seminarPriorityList);
		seminarUserRegistrationInfo.setSeminarpoolId(seminarpoolInfo.getId());
		seminarUserRegistrationInfo.setUserId(user.getId());
		this.setSessionBean("seminarPriorityList", seminarPriorityList);
		this.setSessionBean("seminarUserRegistrationInfo", seminarUserRegistrationInfo);
		return Constants.SEMINARPOOL_USER_REGISTRATION_STEP2_PAGE;
	}
	
	public String create(){
		if(seminarUserRegistrationInfo.getId() == null){
			this.getSeminarpoolUserRegistrationService().registerUserForSeminarpool(seminarUserRegistrationInfo, null);
		}
		else{
			this.getSeminarpoolUserRegistrationService().editUserRegistration(seminarUserRegistrationInfo, null);
		}
		
		return Constants.SEMINARPOOL_MAIN;
	}
	
	
	public List<SelectItem> getAllSeminars() {
		List<SelectItem> seminarItems = new ArrayList<SelectItem>();
		List<CourseSeminarpoolAllocationInfo> seminarlist = this.seminarpoolAdministrationService.findCoursesInSeminarpool(seminarpoolInfo.getId());
		
		
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
	
	
	
	public void processSeminarSelectChanged(ValueChangeEvent event) {
		if (event.getNewValue() instanceof Long) {
			SeminarPrioritiesInfo spi = new SeminarPrioritiesInfo();
			spi.setCourseId((Long) event.getNewValue());
			String identifier = event.getComponent().getId().toString();
			spi.setPriority(Integer.parseInt(identifier.substring(12)));
			if(seminarPriorityList == null){
			seminarPriorityList = new ArrayList<SeminarPrioritiesInfo>();
			}
			seminarPriorityList.add(spi);
			logger.info("ValueChangeEvent: Changing course id for new SeminarPriority to " + spi.getCourseId());
		}
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
	
}
