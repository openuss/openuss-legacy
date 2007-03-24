package org.openuss.web.docmanagement;

import javax.faces.component.html.HtmlCommandLink;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import org.apache.log4j.Logger;

public class FacultyFolderNavigator implements ActionListener {
	private static final Logger logger = Logger.getLogger(FacultyFolderNavigator.class);

	public FacultyFolderNavigator() {
		logger.info("FolderNavigator <init>");
	}
	
	private Object getObjectFromContext(String expression) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
        ValueBinding valueBinding = facesContext.getApplication().createValueBinding(expression);
        return valueBinding.getValue(facesContext);
	}

	public void processAction(ActionEvent actionEvent) {
		logger.debug("Navigatoraction started!");		
		HtmlCommandLink link = (HtmlCommandLink)actionEvent.getComponent();
		String clientId = link.getClientId(FacesContext.getCurrentInstance());
		logger.debug("Selected compontent path: " + clientId);
		clientId = clientId.substring(clientId.indexOf("serverTree")+11, clientId.lastIndexOf(":"));		
		logger.debug("Cleaned compontent path: " + clientId);
		FacultyViewBacker facultyViewBacker = (FacultyViewBacker) getObjectFromContext("#{facultyViewBacker}");
		facultyViewBacker.setFolderFacesPath(clientId);	
	}
}
