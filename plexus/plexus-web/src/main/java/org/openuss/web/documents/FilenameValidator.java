package org.openuss.web.documents;

import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.shale.tiger.register.FacesValidator;
import org.openuss.framework.web.jsf.controller.BaseBean;

/**
 * Checks whether or not the entered filename is a valid windows xp filename.
 * 
 * @author Sebastian Roekens
 */
@FacesValidator(value="filenameValidator")
public class FilenameValidator extends BaseBean implements Validator {

	private boolean isValidFilename(String filename){
		Pattern regexpPattern = Pattern.compile("^[\\p{Graph}&&[^./:*?\"<>|.;\\\\]][\\p{Print}&&[^/:*?\"<>|;\\\\]]*$", Pattern.CASE_INSENSITIVE);
		return regexpPattern.matcher(filename).find();
	}
	
	public static final String NO_VALID_FILENAME = "error_filename_invalid";
	
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		final String filename = (String) value;
		if (!isValidFilename(filename)) {
			if (component instanceof UIInput) {
				((UIInput) component).setValid(false);
			}
			addError(component.getClientId(context), i18n(NO_VALID_FILENAME),null);
		}
		
	}

}

