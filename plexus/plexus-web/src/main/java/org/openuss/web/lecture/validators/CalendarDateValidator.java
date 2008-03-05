package org.openuss.web.lecture.validators;

import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.register.FacesValidator;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.lecture.UniversityServiceException;

/**
 * Checks if the entered date is correct.
 * 
 * @author Kai Stettner
 */
@FacesValidator(value = "calendarDateValidator")
public class CalendarDateValidator extends BaseBean implements Validator {

	private static final Logger logger = Logger.getLogger(CalendarDateValidator.class);

	public static final String SHORTCUT_MESSAGE_ID = "error_shortcut_already_exists";

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		try {
			// FIXME Startdate should be configurable through validator tag attribute 
			Date startDate = (Date) ((UIOutput) component.getParent().findComponent("startdate")).getValue();
			Date endDate = (Date) value;

			if (startDate != null) {
				if (endDate == null) {
					if (component instanceof UIInput) {
						((UIInput) component).setValid(false);
					}
					addError(component.getClientId(context), "Enddate", i18n("validate_error_need_to_define_end_date"));
				}

				logger.debug(startDate.getClass().toString());
				logger.debug(endDate.getClass().toString());
				logger.debug(startDate);
				logger.debug(endDate);
				logger.debug(startDate.after(endDate));

				if (startDate.after(endDate)) {
					if (component instanceof UIInput) {
						((UIInput) component).setValid(false);
					}
					addError(component.getClientId(context), "Enddate", i18n("validate_error_end_date_after_start_date"));
				}
			}

		} catch (UniversityServiceException e) {
			logger.error(e);
			if (component instanceof UIInput) {
				((UIInput) component).setValid(false);
			}
			addError(i18n(e.getMessage()));
		}
	}

}