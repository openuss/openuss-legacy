package org.openuss.web.docmanagement;

import javax.faces.component.html.HtmlCommandLink;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.apache.log4j.Logger;

public class SourceNavigator extends AbstractChangeActionListener implements ActionListener {
	private static final Logger logger = Logger.getLogger(SourceNavigator.class);

	public void processAction(ActionEvent actionEvent) {
		HtmlCommandLink link = (HtmlCommandLink)actionEvent.getComponent();
		String clientId = link.getClientId(FacesContext.getCurrentInstance());
		logger.debug("Selected compontent path: " + clientId);
		clientId = clientId.substring(clientId.indexOf("serverTree")+11, clientId.lastIndexOf(":"));		
		logger.debug("Cleaned compontent path: " + clientId);
		Folder2Folder folder2Folder = (Folder2Folder) getObjectFromContext("#{folder2Folder}");
		folder2Folder.setSourcePath(clientId);	
	}
}
