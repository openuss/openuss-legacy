package org.openuss.framework.web.jsf.events;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openuss.framework.web.jsf.actions.PageActionManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 * ConversationPhaseListener will check if there are any page actions defined that 
 * should be performed on view requests.
 *  
 * @author Ingo Dueppe
 */
public class ConversationPhaseListener implements PhaseListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ConversationPhaseListener.class);

	private static final long serialVersionUID = 1050736639917655781L;
	
	private transient PageActionManager actionManager;
	

	public void afterPhase(PhaseEvent event) {
		if (logger.isDebugEnabled())
			logger.debug("After " + event.getPhaseId() + " Phase ");
		
		final FacesContext facesContext = event.getFacesContext();
		if (actionManager == null) {
			WebApplicationContext applicationContext = FacesContextUtils.getWebApplicationContext(facesContext);
			actionManager = (PageActionManager) applicationContext.getBean("actionManager");
		}
		final HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		if (StringUtils.equals("GET", request.getMethod())) { 
			actionManager.processActions(facesContext);
		}
	}

	public void beforePhase(PhaseEvent event) {}

	/**
	 * {@inheritDoc}
	 */
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
