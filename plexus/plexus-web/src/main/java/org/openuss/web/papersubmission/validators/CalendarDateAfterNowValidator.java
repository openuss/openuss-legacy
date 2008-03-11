package org.openuss.web.papersubmission.validators;

import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.shale.tiger.register.FacesValidator;
import org.openuss.framework.web.jsf.controller.BaseBean;

/**
 * Checks if the entered date is correct.
 * 
 * @author Team Collaboration (PS im WS 2007/08)
 */
@FacesValidator(value = "calendarDateAfterNowValidator")
public class CalendarDateAfterNowValidator extends BaseBean implements Validator {

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		Date date = (Date) value;
		
		Date currentDate = new Date();
		if (date.before(currentDate)) {
			addError(i18n("papersubmission_illegal_exam_message"));
			((UIInput) component).setValid(false);
			addError(component.getClientId(context), "Deadline", i18n("papersubmission_validate_error_deadline_before_today"));
		}
	}

}