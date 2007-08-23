package org.openuss.web.lecture.validators;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.register.FacesValidator;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.lecture.InstituteInfo;
import org.openuss.lecture.InstituteService;
import org.openuss.lecture.InstituteServiceException;
import org.openuss.web.Constants;

/**
 * Checks whether or not the entered shortcut is already in use or not.
 * 
 * @author Ingo Dueppe
 * @author Kai Stettner
 */
@FacesValidator(value="instituteShortcutValidator")
public class InstituteShortcutValidator extends BaseBean implements Validator {
	
	private static final Logger logger = Logger.getLogger(InstituteShortcutValidator.class);

	public static final String SHORTCUT_MESSAGE_ID = "error_shortcut_already_exists";

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		final String shortcut = (String) value;
		InstituteService instituteService = (InstituteService) getBean(Constants.INSTITUTE_SERVICE);
		try {
			// TODO institute should be defined by attribute 
			InstituteInfo instituteInfo = (InstituteInfo) getSessionBean(Constants.INSTITUTE_INFO);
			boolean unique = instituteService.isNoneExistingInstituteShortcut(instituteInfo, shortcut);
			if (!unique) {
				((UIInput) component).setValid(false);
				addError(component.getClientId(context), i18n(SHORTCUT_MESSAGE_ID),null);
			}
		} catch (InstituteServiceException e) {
			logger.error(e);
			((UIInput) component).setValid(false);
			addError(i18n(e.getMessage()));
		}
	}

}
