package org.openuss.web.docmanagement.examarea;

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
public class ExamFileChangeActionListener extends AbstractChangeActionListener implements ActionListener {	

	/** 
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ExamFileChangeActionListener.class);
	
	public void processAction(ActionEvent actionEvent) throws AbortProcessingException {
		HtmlCommandButton button = (HtmlCommandButton) actionEvent.getComponent();
		String clientId = button.getClientId(FacesContext.getCurrentInstance());
		logger.debug("ClientId of pressed button = "+clientId);		
		clientId = clientId.substring(0, clientId.lastIndexOf(":"));
		clientId = clientId.substring(clientId.lastIndexOf(":")+1);
		logger.debug("cleared ClientId of pressed button: " + clientId);
		ExamAreaViewBacker eavb = (ExamAreaViewBacker) getObjectFromContext("#{examAreaViewBacker}");
		eavb.setFileFacesPath(clientId);
	}

}
