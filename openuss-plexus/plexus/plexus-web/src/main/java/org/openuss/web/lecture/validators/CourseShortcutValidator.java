package org.openuss.web.lecture.validators;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.register.FacesValidator;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.lecture.CourseInfo;
import org.openuss.lecture.CourseService;
import org.openuss.lecture.CourseServiceException;
import org.openuss.web.Constants;

/**
 * Checks whether or not the entered shortcut is already in use or not.
 * 
 * @author Ingo Dueppe
 * @author Kai Stettner
 */
@FacesValidator(value="courseShortcutValidator")
public class CourseShortcutValidator extends BaseBean implements Validator {

	private static final Logger logger = Logger.getLogger(CourseShortcutValidator.class);

	public static final String SHORTCUT_MESSAGE_ID = "error_shortcut_already_exists";

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		final String shortcut = (String) value;
		CourseService courseService = (CourseService) getBean(Constants.COURSE_SERVICE);
		try {
			CourseInfo courseInfo = (CourseInfo) getSessionBean(Constants.COURSE_INFO);
			boolean unique = courseService.isNoneExistingCourseShortcut(courseInfo, shortcut);
			if (!unique) {
				((UIInput) component).setValid(false);
				addError(component.getClientId(context), i18n(SHORTCUT_MESSAGE_ID),null);
			}
		} catch (CourseServiceException e) {
			logger.error(e);
			addError(i18n(e.getMessage()));
			((UIInput) component).setValid(false);
		}
	}

}
