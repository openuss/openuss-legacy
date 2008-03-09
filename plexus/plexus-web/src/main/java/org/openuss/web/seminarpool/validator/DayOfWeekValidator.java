package org.openuss.web.seminarpool.validator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.shale.tiger.register.FacesValidator;
import org.openuss.framework.web.jsf.controller.BaseBean;

@FacesValidator(value="dayOfWeekValidator")
public class DayOfWeekValidator extends BaseBean implements Validator{
	

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
				((UIInput) component).setValid(true);
	}
}