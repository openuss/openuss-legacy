package org.openuss.web.lecture.validators;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.register.FacesValidator;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.lecture.Faculty;
import org.openuss.lecture.LectureService;
import org.openuss.lecture.LectureServiceException;
import org.openuss.web.Constants;

/**
 * Checks whether or not the entered shortcut is already in use or not.
 * 
 * @author Ingo Dueppe
 */
@FacesValidator(value="facultyShortcutValidator")
public class FacultyShortcutValidator extends BaseBean implements Validator {
	
	private static final Logger logger = Logger.getLogger(FacultyShortcutValidator.class);

	private static final long serialVersionUID = 3587957165927946017L;

	public static final String SHORTCUT_MESSAGE_ID = "error_shortcut_already_exists";

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		final String shortcut = (String) value;
		LectureService service = (LectureService) getBean(Constants.LECTURE_SERVICE);
		try {
			// TODO faculty should be defined by attribute 
			Faculty faculty = (Faculty) getSessionBean(Constants.FACULTY);
			boolean unique = service.isNoneExistingFacultyShortcut(faculty, shortcut);
			if (!unique) {
				((UIInput) component).setValid(false);
				addError(component.getClientId(context), i18n(SHORTCUT_MESSAGE_ID),null);
			}
		} catch (LectureServiceException e) {
			logger.error(e);
			((UIInput) component).setValid(false);
			addError(i18n(e.getMessage()));
		}
	}

}
