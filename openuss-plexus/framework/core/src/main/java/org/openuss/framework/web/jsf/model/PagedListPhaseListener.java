package org.openuss.framework.web.jsf.model;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.log4j.Logger;

/**
 * @author Ingo Dueppe
 */
public class PagedListPhaseListener implements PhaseListener {
	private static final long serialVersionUID = -2162329089047900625L;

	private static final Logger logger = Logger.getLogger(PagedListPhaseListener.class);

	
	private static ThreadLocal<PhaseId> phaseId = new ThreadLocal<PhaseId>();

	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

	/**
	 * Check if the request is the GET-Phase of the Post-Redirect-Get-Pattern.
	 * If so, abort the lifecycle and go directly to the render response phase
	 * {@inheritDoc}
	 */
	public void afterPhase(PhaseEvent event) {
		logger.debug("<<< End Phase "+event.getPhaseId() + " >>>");
	}

	public void beforePhase(PhaseEvent event) {
		logger.debug(">>> Start Phase "+event.getPhaseId() + " <<<");
		phaseId.set(event.getPhaseId());
	}
	
	public static PhaseId currentPhase() {
		return phaseId.get();
	}
}