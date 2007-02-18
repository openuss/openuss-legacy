package org.openuss.framework.web.jsf.events;

import org.apache.log4j.Logger;
import org.openuss.framework.web.jsf.pages.Pages;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 * This PhaseListener will inject page request parameters into the model after
 * the restore view phase
 * 
 * @author Ingo Dueppe
 */
public class PageParameterPhaseListener implements PhaseListener {
	private static final long serialVersionUID = 6953211197121628585L;
	
	public void afterPhase(PhaseEvent event) {
		FacesContext facesContext = event.getFacesContext();
		if (facesContext.getViewRoot() != null) {
			Pages.instance().applyRequestParameterValues(facesContext);
		}
	}

	public void beforePhase(PhaseEvent event) {
		// do nothing
	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
