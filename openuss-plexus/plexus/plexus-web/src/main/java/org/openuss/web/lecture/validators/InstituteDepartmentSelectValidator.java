package org.openuss.web.lecture.validators;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.register.FacesValidator;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.web.Constants;

/**
 * Checks whether or not the selected department is valid. Not valid: Shortcuts for ALL ENABLED and DISABLED DEPARTMENTS
 * 
 * @author Kai Stettner
 */
@FacesValidator(value="instituteDepartmentSelectValidator")
public class InstituteDepartmentSelectValidator extends BaseBean implements Validator {
	
	private static final Logger logger = Logger.getLogger(InstituteDepartmentSelectValidator.class);

	public static final String INSTITUTE_DEPARTMENT_MESSAGE_ID = "error_choose_a_valid_department";

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		final Long departmentId = (Long) value;
			if ((departmentId.longValue() == Constants.DEPARTMENTS_DISABLED) || (departmentId.longValue() == Constants.DEPARTMENTS_ENABLED)) {
				((UIInput) component).setValid(false);
				addError(i18n(INSTITUTE_DEPARTMENT_MESSAGE_ID), null);
			}
	}
}
