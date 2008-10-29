package org.openuss.framework.web.jsf.pages;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.log4j.Logger;


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
		if (event.getPhaseId() == PhaseId.RESTORE_VIEW) {
			FacesContext facesContext = event.getFacesContext();
			
			if (facesContext.getViewRoot() != null ) {
				logger.debug("Request: apply request parameter values...");
				Pages.instance().applyRequestParameterValues(facesContext);
    			logger.debug("perform security constraints check...");
				Pages.instance().performSecurityConstraints(facesContext);
			}
		}
	}

	public void beforePhase(PhaseEvent event) {
		if (event.getPhaseId() == PhaseId.RENDER_RESPONSE) {
			FacesContext facesContext = event.getFacesContext();
			logger.debug("perform security constraints check...");
			Pages.instance().performSecurityConstraints(facesContext);
		}
	}

	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
