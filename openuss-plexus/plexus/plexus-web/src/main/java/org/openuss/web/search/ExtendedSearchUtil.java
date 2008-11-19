package org.openuss.web.search;

import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.openuss.web.Constants;

/**
 * utility methods which are used in order to create the extended search views
 * @author Malte Stockmann
 *
 */
public class ExtendedSearchUtil {

	public static ResourceBundle getResourceBundle(){
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle rb = ResourceBundle.getBundle(
				context.getApplication().getMessageBundle(), 
				context.getViewRoot().getLocale());
		return rb;
	}
	
	public static String getResourceExtensionString(Long searchScopeId){
		String extension = "";
		switch(searchScopeId.intValue()){
			case Constants.EXTENDED_SEARCH_SCOPE_UNIVERSITIES:
				extension = "univ";
				break;
			case Constants.EXTENDED_SEARCH_SCOPE_COMPANIES:
				extension = "comp";
				break;
			case Constants.EXTENDED_SEARCH_SCOPE_OTHER:
				extension = "other";
				break;
			default:
				extension = "unknown";
				break;
		}
		return extension;
	}
	
}
