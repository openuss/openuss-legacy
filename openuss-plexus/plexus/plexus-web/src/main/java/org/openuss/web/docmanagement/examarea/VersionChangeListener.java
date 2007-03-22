package org.openuss.web.docmanagement.examarea;

import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.apache.log4j.Logger;


/**
 * Actionlistener for Changing of Files
 */
public class VersionChangeListener implements ActionListener {	

	/** 
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VersionChangeListener.class);

	private Object getObjectFromContext(String expression) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
        ValueBinding valueBinding = facesContext.getApplication().createValueBinding(expression);
        return valueBinding.getValue(facesContext);
	}
	
	public void processAction(ActionEvent actionEvent) throws AbortProcessingException {
		HtmlCommandButton button = (HtmlCommandButton) actionEvent.getComponent();
		String clientId = button.getClientId(FacesContext.getCurrentInstance());
		logger.debug("ClientId of pressed button = "+clientId);		
		clientId = clientId.substring(0, clientId.lastIndexOf(":"));
		clientId = clientId.substring(clientId.lastIndexOf(":")+1);
		logger.debug("cleared ClientId of pressed button: " + clientId);
		VersionViewBacker vvb = (VersionViewBacker) getObjectFromContext("#{versionViewBacker}");
		vvb.setFileFacesPath(clientId);
	}

}
