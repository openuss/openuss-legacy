package org.openuss.web.lecture.validators;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.register.FacesValidator;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.lecture.CourseTypeInfo;
import org.openuss.lecture.CourseTypeService;
import org.openuss.lecture.CourseTypeServiceException;
import org.openuss.web.Constants;
import org.openuss.web.lecture.InstituteCoursesPage;

/**
 * Checks whether or not the entered shortcut is already in use or not.
 * 
 * @author Ingo Dueppe
 * @author Kai Stettner
 */

@FacesValidator(value="courseTypeShortcutValidator")
public class CourseTypeShortcutValidator extends BaseBean implements Validator {

	private static final Logger logger = Logger.getLogger(CourseTypeShortcutValidator.class);

	public static final String SHORTCUT_MESSAGE_ID = "error_shortcut_already_exists";
	
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		final String shortcut = (String) value;
		CourseTypeService courseTypeService = (CourseTypeService) getBean(Constants.COURSE_TYPE_SERVICE);
		try {
			CourseTypeInfo courseTypeInfo = (CourseTypeInfo) getSessionBean(Constants.COURSE_TYPE_INFO);
			boolean unique = courseTypeService.isNoneExistingCourseTypeShortcut(courseTypeInfo, shortcut);
			if (!unique) {
				((UIInput) component).setValid(false);
				addMessage(i18n(SHORTCUT_MESSAGE_ID),null);
				addError(component.getClientId(context), i18n(SHORTCUT_MESSAGE_ID),null);
			}
		} catch (CourseTypeServiceException e) {
			logger.error(e);
			// reset renderBoolean --> courseTypeEdit form is still visible
			InstituteCoursesPage.renderCourseTypeEditNew = true;
			((UIInput) component).setValid(false);
			addError(i18n(e.getMessage()));	
		}
		
	}

}
