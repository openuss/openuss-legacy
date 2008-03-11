package org.openuss.web.seminarpool.validator;

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
 * @author PS Seminarpool
 */
@FacesValidator(value = "endBeforeStartRegistrationDateValidator")
public class EndBeforeStartRegistrationDateValidator extends BaseBean implements Validator {

	private static final Logger logger = Logger
			.getLogger(EndBeforeStartRegistrationDateValidator.class);

	public static final String SHORTCUT_MESSAGE_ID = "error_shortcut_already_exists";

	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		try {
			// FIXME startdate should be configurable through validator tag
			// attribute
			UIComponent newuiop = component.getParent();
			newuiop = newuiop.findComponent("start_time");
			((UIOutput) newuiop).getValue();
			
			Date startDate = (Date) ((UIOutput) component.getParent()
					.findComponent("start_time")).getValue();
			Date endDate = (Date) value;

			if (startDate != null) {
				if (endDate == null) {
					((UIInput) component).setValid(false);
					addError(component.getClientId(context), "Enddate",
							i18n("validate_error_need_to_define_end_date"));
				}

				logger.debug(startDate.getClass().toString());
				logger.debug(endDate.getClass().toString());
				logger.debug(startDate);
				logger.debug(endDate);
				logger.debug(startDate.after(endDate));

				if (startDate.after(endDate)) {
					((UIInput) component).setValid(false);
					addError(component.getClientId(context), "Enddate",
							i18n("validate_error_end_date_after_start_date"));
				}
			}

		} catch (UniversityServiceException e) {
			logger.error(e);
			((UIInput) component).setValid(false);
			addError(i18n(e.getMessage()));
		}
	}

}