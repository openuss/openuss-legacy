package org.openuss.framework.web.jsf.events;

import java.util.Stack;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * ViewStackPhaseListener main the view stack of the visit views of the user
 *  
 * @author Ingo Dueppe
 * @author Sebastian Roekens
 */
public class ViewStackPhaseListener implements PhaseListener {

	private static final Logger logger = Logger.getLogger(ViewStackPhaseListener.class);

	private static final long serialVersionUID = 1050736639917655781L;
	
	private static final String VIEW_STACK_KEY = "viewStack";
	
	public void afterPhase(PhaseEvent event) {
		if (logger.isDebugEnabled())
			logger.debug("After " + event.getPhaseId() + " Phase ");
		
		String viewId = null;
		
		if (event.getFacesContext().getViewRoot() != null) {
			viewId = event.getFacesContext().getViewRoot().getViewId();
			viewId = event.getFacesContext().getApplication().getViewHandler().getActionURL(event.getFacesContext(), viewId);
			//FIXME should not be dependent on string
			viewId = viewId.substring("/plexus-web".length());
		}
		
		if (StringUtils.isNotBlank(viewId)) {
			Stack<String> viewStack = getViewStack(event.getFacesContext());
			
			if (!viewStack.contains(viewId)) {
				viewStack.push(viewId);
			} else {
				while (!viewId.equals(viewStack.peek())) {
					viewStack.pop();
				}
			}
		}
		
	}

	public void beforePhase(PhaseEvent event) {}

	/**
	 * {@inheritDoc}
	 */
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

	/**
	 * Returns the view stack for the current user.
	 * @param facesContext
	 * @return A Stack representing the views that have been visited during the user sessions.
	 */
	private Stack<String> getViewStack(FacesContext facesContext) {
		Stack<String> viewStack = (Stack<String>) facesContext.getExternalContext().getSessionMap().get(VIEW_STACK_KEY);
		if (viewStack == null) {
			logger.debug("Init session view stack");
			viewStack = new Stack<String>();
			facesContext.getExternalContext().getSessionMap().put(VIEW_STACK_KEY, viewStack);
		}
		return viewStack;
	}


}
