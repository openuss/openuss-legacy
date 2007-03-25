package org.openuss.web.docmanagement.workingplace;

import javax.faces.component.html.HtmlCommandLink;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import org.openuss.web.docmanagement.AbstractChangeActionListener;
import org.apache.log4j.Logger;

public class WPTreeNavigator extends AbstractChangeActionListener implements ActionListener {
	private static final Logger logger = Logger.getLogger(WPTreeNavigator.class);

	public void processAction(ActionEvent actionEvent) {
		HtmlCommandLink link = (HtmlCommandLink)actionEvent.getComponent();
		String clientId = link.getClientId(FacesContext.getCurrentInstance());
		logger.debug("Selected compontent path: " + clientId);
		clientId = clientId.substring(clientId.indexOf("workTree")+9, clientId.lastIndexOf(":"));		
		logger.debug("Cleaned compontent path: " + clientId);
		WorkingPlaceViewBacker workingPlaceViewBacker = (WorkingPlaceViewBacker) getObjectFromContext("#{workingPlaceViewBacker }");
		workingPlaceViewBacker.setFolderPath(clientId);	
	}
}
