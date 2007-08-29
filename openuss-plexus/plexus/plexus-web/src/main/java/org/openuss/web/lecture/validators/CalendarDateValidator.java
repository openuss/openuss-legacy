package org.openuss.web.lecture.validators;

import java.util.Date;

import javax.faces.application.FacesMessage;
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
@FacesValidator(value="calendarDateValidator")
public class CalendarDateValidator extends BaseBean implements Validator {
	
	private static final Logger logger = Logger.getLogger(UniversityShortcutValidator.class);

	public static final String SHORTCUT_MESSAGE_ID = "error_shortcut_already_exists";

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		try {
			
			Date startDate = (Date)((UIOutput) component.findComponent("startdate")).getValue();
			Date endDate = (Date)value;
			
			if(startDate != null)
			{
				if(endDate == null)
				{
					((UIInput) component).setValid(false);
					context.addMessage(null, new FacesMessage("Das Enddatum darf nicht null sein.") );
				}
					
				logger.debug(startDate.getClass().toString());
				logger.debug(endDate.getClass().toString());
				logger.debug(startDate);
				logger.debug(endDate);
				logger.debug(startDate.after(endDate));
				
				if (startDate.after(endDate)) {
					((UIInput) component).setValid(false);
					context.addMessage(null, new FacesMessage("Das Enddatum muss nach dem Startdatum liegen.") );
				}
			}
			
		} catch (UniversityServiceException e) {
			logger.error(e);
			((UIInput) component).setValid(false);
			addError(i18n(e.getMessage()));
		}
	}

}