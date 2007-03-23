package org.openuss.web.docmanagement.workingplace;

import javax.faces.component.html.HtmlCommandLink;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.apache.log4j.Logger;

public class WPTreeNavigator implements ActionListener {
	private static final Logger logger = Logger.getLogger(WPTreeNavigator.class);

	private Object getObjectFromContext(String expression) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
        ValueBinding valueBinding = facesContext.getApplication().createValueBinding(expression);
        return valueBinding.getValue(facesContext);
	}

	public void processAction(ActionEvent actionEvent) {
		HtmlCommandLink link = (HtmlCommandLink)actionEvent.getComponent();
		String clientId = link.getClientId(FacesContext.getCurrentInstance());
		logger.debug("Selected compontent path: " + clientId);
		//FIXME change
		clientId = clientId.substring(clientId.indexOf("serverTree")+11, clientId.lastIndexOf(":"));		
		logger.debug("Cleaned compontent path: " + clientId);
		WorkingPlaceViewBacker workingPlaceViewBacker = (WorkingPlaceViewBacker) getObjectFromContext("#{workingPlaceViewBacker }");
		workingPlaceViewBacker.setFolderPath(clientId);	
	}
}
