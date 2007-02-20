package org.openuss.framework.web.jsf.events;

import org.apache.log4j.Logger;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.openuss.framework.web.jsf.pages.Pages;

/**
 * This PhaseListener will inject page request parameters into the model after
 * the restore view phase
 * 
 * @author Ingo Dueppe
 */
public class PagePhaseListener implements PhaseListener {
	private static final Logger logger = Logger.getLogger(PagePhaseListener.class);

	private static final long serialVersionUID = 6953211197121628585L;
	
	public void afterPhase(PhaseEvent event) {
		FacesContext facesContext = event.getFacesContext();
		if (facesContext.getViewRoot() != null) {
			logger.debug("apply request parameter values...");
			Pages.instance().applyRequestParameterValues(facesContext);
			logger.debug("perform security constraints check...");
			Pages.instance().performSecurityConstraints(facesContext);
		}
	}

	public void beforePhase(PhaseEvent event) {
		// do nothing
	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
