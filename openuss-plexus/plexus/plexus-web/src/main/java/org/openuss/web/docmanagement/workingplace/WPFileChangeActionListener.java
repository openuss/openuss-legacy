package org.openuss.web.docmanagement.workingplace;

import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;
import org.openuss.web.docmanagement.AbstractChangeActionListener;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.apache.log4j.Logger;


/**
 * Actionlistener for Changing of Files
 */
public class WPFileChangeActionListener extends AbstractChangeActionListener implements ActionListener {	

	/** 
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WPFileChangeActionListener.class);
	
	public void processAction(ActionEvent actionEvent) throws AbortProcessingException {
		HtmlCommandButton button = (HtmlCommandButton) actionEvent.getComponent();
		String clientId = button.getClientId(FacesContext.getCurrentInstance());
		logger.debug("ClientId of pressed button = "+clientId);
		//clientId = _id5:treeView:filesDataTable:0:_id113
		clientId = clientId.substring(0, clientId.lastIndexOf(":"));
		clientId = clientId.substring(clientId.lastIndexOf(":")+1);
		logger.debug("cleared ClientId of pressed button: " + clientId);
		WorkingPlaceViewBacker fvb = (WorkingPlaceViewBacker) getObjectFromContext("#{workingPlaceViewBacker}");
		fvb.setFileFacesPath(clientId);
	}

}
