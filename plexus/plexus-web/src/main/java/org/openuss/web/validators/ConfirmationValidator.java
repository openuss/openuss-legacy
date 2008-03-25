package org.openuss.web.validators;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.shale.tiger.register.FacesValidator;
import org.openuss.framework.web.jsf.controller.BaseBean;

/**
 * Confirmation Validator checks if the confirmation checkbox is checked.
 * Validator to check whether the user has accepted the user agreement or not.
 * 
 * @author Ingo Dueppe
 */
@FacesValidator(value = "confirmationValidator")
public class ConfirmationValidator extends BaseBean implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		try {
			boolean accept = (Boolean) value;
			if (!accept) {
				if (component instanceof UIInput) {
					((UIInput) component).setValid(false);
				}
				addError(component.getClientId(context), i18n("error_need_to_confirm_removement"), null);
			}
		} catch (ClassCastException cce) {
			throw new RuntimeException("ConfirmationValidator can only be used with a checkbox",cce);
		}
	}
}
