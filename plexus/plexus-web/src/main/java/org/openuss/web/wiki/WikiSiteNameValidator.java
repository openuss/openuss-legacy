package org.openuss.web.wiki;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.shale.tiger.register.FacesValidator;
import org.openuss.framework.web.jsf.controller.BaseBean;

/**
 * Checks whether or not an a Wiki Site name is valid.
 * @author Projektseminar WS 07/08, Team Collaboration
 * @author Ingo Dueppe
 *
 */
@FacesValidator(value="wikiSiteNameValidator")
public class WikiSiteNameValidator extends BaseBean implements Validator {
	
	private static final String ALLOWED_CHARACTERS = "[a-zA-Z0-9äöüßÄÜÖ ]+";
	
	private static final String ILLEGAL_SITE_NAME_MESSAGE = "wiki_error_illegal_site_name";

	public static final Pattern ALLOWED_CHARACTERS_PATTERN = Pattern.compile(ALLOWED_CHARACTERS);

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		final String wikiSiteName = ((String) value).trim();
		final Matcher matcher = ALLOWED_CHARACTERS_PATTERN.matcher(wikiSiteName);
		if (!matcher.matches()) {
			if (component instanceof UIInput) {
				((UIInput) component).setValid(false);
			}
			addError(component.getClientId(context), i18n(ILLEGAL_SITE_NAME_MESSAGE), null);
		}
	}
	
}
