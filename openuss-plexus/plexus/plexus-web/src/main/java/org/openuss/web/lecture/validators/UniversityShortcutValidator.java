package org.openuss.web.lecture.validators;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.register.FacesValidator;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.lecture.UniversityInfo;
import org.openuss.lecture.UniversityService;
import org.openuss.lecture.UniversityServiceException;
import org.openuss.web.Constants;

/**
 * Checks whether or not the entered shortcut is already in use or not.
 * 
 * @author Kai Stettner
 */
@FacesValidator(value="universityShortcutValidator")
public class UniversityShortcutValidator extends BaseBean implements Validator {
	
	private static final Logger logger = Logger.getLogger(UniversityShortcutValidator.class);

	public static final String SHORTCUT_MESSAGE_ID = "error_shortcut_already_exists";

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		final String shortcut = (String) value;
		UniversityService universityService = (UniversityService) getBean(Constants.UNIVERSITY_SERVICE);
		try {
			// TODO university should be defined by attribute 
			UniversityInfo universityInfo = (UniversityInfo) getSessionBean(Constants.UNIVERSITY_INFO);
			boolean unique = universityService.isNoneExistingUniversityShortcut(universityInfo, shortcut);
			if (!unique) {
				((UIInput) component).setValid(false);
				addError(component.getClientId(context), i18n(SHORTCUT_MESSAGE_ID),null);
			}
		} catch (UniversityServiceException e) {
			logger.error(e);
			((UIInput) component).setValid(false);
			addError(i18n(e.getMessage()));
		}
	}

}
