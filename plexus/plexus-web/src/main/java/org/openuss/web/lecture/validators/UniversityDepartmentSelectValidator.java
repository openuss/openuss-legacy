package org.openuss.web.lecture.validators;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.shale.tiger.register.FacesValidator;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.web.Constants;

/**
 * Checks whether or not the selected universities is valid. Not valid: Shortcuts for ALL ENABLED and DISABLED UNIVERSITIES
 * 
 * @author Kai Stettner
 */
@FacesValidator(value="universityDepartmentSelectValidator")
public class UniversityDepartmentSelectValidator extends BaseBean implements Validator {
	
	public static final String UNIVERSITY_DEPARTMENT_MESSAGE_ID = "error_choose_a_valid_university";

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		final Long universityId = (Long) value;
			if ((universityId.longValue() == Constants.UNIVERSITIES_DISABLED) || (universityId.longValue() == Constants.UNIVERSITIES_ENABLED)) {
				if (component instanceof UIInput) {
					((UIInput) component).setValid(false);
				}
				addError(i18n(UNIVERSITY_DEPARTMENT_MESSAGE_ID), null);
			}
	}
}
