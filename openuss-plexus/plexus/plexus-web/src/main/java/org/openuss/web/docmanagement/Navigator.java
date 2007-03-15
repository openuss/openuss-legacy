package org.openuss.web.docmanagement;

import javax.faces.component.html.HtmlCommandLink;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import org.openuss.web.docmanagement.DistributionViewBacker;

import org.apache.log4j.Logger;

public class Navigator implements ActionListener {
	private static final Logger logger = Logger.getLogger(Navigator.class);

	public Navigator() {
		logger.info("Navigator <init>");
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
		DistributionViewBacker distributionViewBacker = (DistributionViewBacker) getObjectFromContext("#{distributionViewBacker}");
		distributionViewBacker.setFacesPath(clientId);	
	}
}
