package org.openuss.web.lecture.validators;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.register.FacesValidator;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.lecture.DepartmentInfo;
import org.openuss.lecture.DepartmentService;
import org.openuss.lecture.DepartmentServiceException;
import org.openuss.web.Constants;

/**
 * Checks whether or not the entered shortcut is already in use or not.
 * 
 * @author Kai Stettner
 */
@FacesValidator(value="departmentShortcutValidator")
public class DepartmentShortcutValidator extends BaseBean implements Validator {
	
	private static final Logger logger = Logger.getLogger(DepartmentShortcutValidator.class);

	public static final String SHORTCUT_MESSAGE_ID = "error_shortcut_already_exists";

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		final String shortcut = (String) value;
		DepartmentService departmentService = (DepartmentService) getBean(Constants.DEPARTMENT_SERVICE);
		try {
			// TODO department should be defined by attribute 
			DepartmentInfo departmentInfo = (DepartmentInfo) getSessionBean(Constants.DEPARTMENT_INFO);
			boolean unique = departmentService.isNoneExistingDepartmentShortcut(departmentInfo, shortcut);
			if (!unique) {
				((UIInput) component).setValid(false);
				addError(component.getClientId(context), i18n(SHORTCUT_MESSAGE_ID),null);
			}
		} catch (DepartmentServiceException e) {
			logger.error(e);
			((UIInput) component).setValid(false);
			addError(i18n(e.getMessage()));
		}
	}

}
