package org.openuss.web.lecture;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.documents.FolderEntryInfo;
import org.openuss.framework.jsfcontrols.breadcrumbs.BreadCrumb;
import org.openuss.lecture.LectureException;
import org.openuss.web.Constants;
import org.openuss.web.course.AbstractCoursePage;

/**
 * Course Edit Page Controller
 * @author Ingo Dueppe
 *
 */
@Bean(name="views$secured$lecture$courseremove",scope=Scope.REQUEST)
@View
public class CourseRemovePage extends AbstractCoursePage {

	private static final Logger logger = Logger.getLogger(CourseRemovePage.class);

	private static final long serialVersionUID = 8821048605517398410L;
	
	@Property(value="#{sessionScope.document_selected_folderentries}")
	private List<FolderEntryInfo> entries;
	
	@Prerender
	public void prerender() throws Exception {
		super.prerender();
		courseInfo = courseService.getCourseInfo(courseInfo.getId());
		setSessionBean(Constants.COURSE, courseInfo );
		addPageCrumb();
	}
	
	private void addPageCrumb() {
		BreadCrumb crumb = new BreadCrumb();
		crumb.setLink("");
		crumb.setName(i18n("course_remove_header"));
		crumb.setHint(i18n("course_remove_header"));
		
		breadcrumbs.loadCourseCrumbs(courseInfo);
		breadcrumbs.addCrumb(crumb);
	}
	
	/**
	 * Remove the current course and all its data
	 * @return outcome
	 * @throws LectureException 
	 */
	public String removeCourse() throws LectureException {
		logger.trace("removing course");
		lectureService.removeCourse(courseInfo.getId());
		addMessage(i18n("institute_course_removed_succeed"));
		return Constants.INSTITUTE_PERIODS_PAGE;
	}
	
	/**
	 * Validator to check whether or not the removal is accepted
	 * @param context
	 * @param toValidate
	 * @param value
	 */
	public void validateRemoveConfirmation(FacesContext context, UIComponent toValidate, Object value) {
		boolean accept = (Boolean) value;
		if (!accept) {
			((UIInput)toValidate).setValid(false);
			addError(toValidate.getClientId(context), i18n("error_need_to_confirm_removement"), null);
		}
	}

	public List<FolderEntryInfo> getEntries() {
		return entries;
	}

	public void setEntries(List<FolderEntryInfo> entries) {
		this.entries = entries;
	}
}
