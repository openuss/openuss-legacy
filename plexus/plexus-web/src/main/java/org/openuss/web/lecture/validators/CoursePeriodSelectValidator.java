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
 * Checks whether or not the selected period is valid. Not valid: Shortcuts for ALL ACTIVE and PASSIVE PERIODS
 * 
 * @author Kai Stettner
 */
@FacesValidator(value="coursePeriodSelectValidator")
public class CoursePeriodSelectValidator extends BaseBean implements Validator {
	
	public static final String COURSE_PERIOD_MESSAGE_ID = "error_choose_a_valid_period";

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		final Long periodId = (Long) value;
			if ((periodId.longValue() == Constants.PERIODS_ACTIVE) || (periodId.longValue() == Constants.PERIODS_PASSIVE)) {
				if (component instanceof UIInput) {
					((UIInput) component).setValid(false);
				}
				addError(i18n(COURSE_PERIOD_MESSAGE_ID), null);
			}
	}
}
