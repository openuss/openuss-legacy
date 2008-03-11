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
@FacesValidator(value = "dateBeforeNowValidator")
public class DateBeforeNowValidator extends BaseBean implements Validator {
	
	private Date now;

	private static final Logger logger = Logger.getLogger(DateBeforeNowValidator.class);

	public static final String SHORTCUT_MESSAGE_ID = "error_shortcut_already_exists";

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		
			Date startDate = (Date) value;
			now = new Date();
			
			if (startDate != null) {
				logger.debug(startDate.getClass().toString());
				logger.debug(startDate);

				if (startDate.before(now)) {
					((UIInput) component).setValid(false);
					addError(component.getClientId(context), "regStartTime", i18n("seminarpool_validate_error_start_date_before_now"));
				}
			}
	}

}