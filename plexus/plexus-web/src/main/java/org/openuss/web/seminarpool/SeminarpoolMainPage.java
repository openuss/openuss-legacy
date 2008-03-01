package org.openuss.web.seminarpool;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.desktop.DesktopException;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.CourseApplicationException;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseMemberInfo;
import org.openuss.lecture.CourseMemberType;
import org.openuss.lecture.InstituteService;
import org.openuss.seminarpool.SeminarpoolAdministrationService;
import org.openuss.seminarpool.SeminarpoolInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/**
 *
 * @author PS-Seminarplaceallocation
 * 
 */

@Bean(name = "views$secured$seminarpool$main", scope = Scope.REQUEST)
@View 
public class SeminarpoolMainPage extends BasePage {

	@Property(value = "#{seminarpoolInfo}")
	protected SeminarpoolInfo seminarpoolInfo;
	
	@Property(value = "#{seminarpoolAdministrationService}")
	protected SeminarpoolAdministrationService seminarpoolAdministrationService;
	
	private static final Logger logger = Logger.getLogger(SeminarpoolMainPage.class);

	private String password;

	private List<CourseMemberInfo> assistants = new ArrayList<CourseMemberInfo>();
	
	@Override
	@Prerender
	public void prerender() throws Exception {
//		if (seminarpoolInfo != null) {
//			assistants = seminarpoolService.getAdmins(seminarpoolInfo);
//		}
		
		if (seminarpoolInfo != null && seminarpoolInfo.getId() != null) {
			seminarpoolInfo = seminarpoolAdministrationService.findSeminarpool(seminarpoolInfo.getId());
		}
		if (seminarpoolInfo == null) {
			addError(i18n("message_error_seminarpool_page"));
			redirect(Constants.OUTCOME_BACKWARD);
			return;
		} else {
//remove?			courseTypeInfo = courseTypeService.findCourseType(courseInfo.getCourseTypeId());
//remove?			instituteInfo = instituteService.findInstitute(courseTypeInfo.getInstituteId());
//FIXME			breadcrumbs.loadSeminarpoolCrumbs(seminarpoolInfo);
			setSessionBean(Constants.SEMINARPOOL_INFO, seminarpoolInfo);
		}
/*
		BreadCrumb newCrumb = new BreadCrumb();
		newCrumb.setName(i18n("nametest1"));
		newCrumb.setHint(i18n("hinttest2"));
		breadcrumbs.addCrumb(newCrumb);
*/	}

	public void validatePassword(FacesContext context, UIComponent toValidate, Object value) {
		String password = (String) value;
		if (!StringUtils.equalsIgnoreCase(password, seminarpoolInfo.getPassword())) {
			((UIInput) toValidate).setValid(false);
			addError(toValidate.getClientId(context), i18n("message_error_password_is_not_correct"), null);
		}
	}

/*	public String applyWithPassword() throws CourseApplicationException {
		logger.debug("course entry with password applied");
		seminarpoolService.applyUserByPassword(password, seminarpoolInfo, user);
		addMessage(i18n("message_course_password_accepted"));
		return Constants.SUCCESS;
	}
*/
	
/*	public String apply() throws CourseApplicationException {
		logger.debug("course entry applied");
		courseService.applyUser(courseInfo, user);
		addMessage(i18n("message_course_send_application"));
		return Constants.SUCCESS;
	}
*/

	/**
	 * Returns true, if seminarpool is boookmarked, otherwise false
	 * @return bookmarked-flag
	 */
	public Boolean getBookmarked() {
		try {
			return desktopService2.isSeminarpoolBookmarked(desktopInfo.getId(), seminarpoolInfo.getId());
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}
	
	/**
	 * Bookmarks the selected seminarpool on the MyUni Page.
	 * @return Outcome
	 */
	public String shortcutSeminarpool() {
		try {
			desktopService2.linkSeminarpool(desktopInfo.getId(), seminarpoolInfo.getId());
			addMessage(i18n("desktop_command_add_seminarpool_succeed"));
			return Constants.SUCCESS;
		} catch (DesktopException e) {
			logger.error(e);
			addError(i18n(e.getMessage()));
			return Constants.FAILURE;
		}
	}
	
	/**
	 * removes the Bookmark for the selected seminarpool on the MyUni Page.
	 * @return Outcome
	 */
	public String removeSeminarpoolShortcut() {
		try {
			desktopService2.unlinkSeminarpool(desktopInfo.getId(), seminarpoolInfo.getId());
			addMessage(i18n("desktop_command_remove_seminarpool_succeed"));
			return Constants.SUCCESS;
		} catch (DesktopException e) {
			logger.error(e);
			addError(i18n(e.getMessage()));
			return Constants.FAILURE;
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<CourseMemberInfo> getAssistants() {
		return assistants;
	}

	public SeminarpoolInfo getSeminarpoolInfo() {
		return seminarpoolInfo;
	}

	public void setSeminarpoolInfo(SeminarpoolInfo seminarpoolInfo) {
		this.seminarpoolInfo = seminarpoolInfo;
	}

	public SeminarpoolAdministrationService getSeminarpoolAdministrationService() {
		return seminarpoolAdministrationService;
	}

	public void setSeminarpoolAdministrationService(
			SeminarpoolAdministrationService seminarpoolAdministrationService) {
		this.seminarpoolAdministrationService = seminarpoolAdministrationService;
	}

}