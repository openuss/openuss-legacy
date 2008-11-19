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
			viewId = event.getFacesContext().getApplication().getViewHandler().getActionURL(event.getFacesContext(),
					viewId);
			viewId = viewId.substring(event.getFacesContext().getExternalContext().getRequestContextPath().length());
		}

		if (StringUtils.isNotBlank(viewId)) {
			Stack<String> viewStack = getViewStack(event.getFacesContext());
			// Do not know why this must be synchronized, but I got some EmptyStack-Exceptions!
			synchronized (viewStack) {
				if (viewStack.isEmpty()) {
					viewStack.push(viewId);
				} else {
					while (viewStack.contains(removeParameters(viewId))) {
						viewStack.pop();
					}
					viewStack.push(viewId);
				}
			}
		}

	}

	private boolean noParameter(String url) {
		return (url.lastIndexOf("?") == -1);
	}

	private String removeParameters(String url) {
		if (noParameter(url)) {
			return url;
		}
		return url.substring(0, url.lastIndexOf("?"));
	}

	public void beforePhase(PhaseEvent event) {
	}

	/**
	 * {@inheritDoc}
	 */
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

	/**
	 * Returns the view stack for the current user.
	 * 
	 * @param facesContext
	 * @return A Stack representing the views that have been visited during the
	 *         user sessions.
	 */
	@SuppressWarnings("unchecked")
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
