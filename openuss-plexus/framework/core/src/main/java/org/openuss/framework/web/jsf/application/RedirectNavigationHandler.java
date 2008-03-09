package org.openuss.framework.web.jsf.application;

import java.util.Stack;

import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openuss.framework.web.jsf.events.PostRedirectGetPhaseListener;
import org.openuss.framework.web.jsf.util.ConversationUtil;

/**
 * This NavigationHandler forces a redirect on each request if the viewId
 * change.
 * 
 * @author idueppe
 */
public class RedirectNavigationHandler extends NavigationHandler {

	private static final Logger logger = Logger.getLogger(RedirectNavigationHandler.class);

	private static final String VIEW_BACKWARD = "view:backward";

	private static final String VIEW_HOME = "home";

	private static final String VIEW_STACK_KEY = "viewStack";

	private final NavigationHandler originalNavigationHandler;

	public RedirectNavigationHandler(NavigationHandler original) {
		logger.debug(" initialized");
		this.originalNavigationHandler = original;
	}

	@Override
	public void handleNavigation(FacesContext facesContext, String fromAction, String outcome) {
		if (logger.isDebugEnabled()) {
			logger.debug("handleNavigation(context=" + facesContext + ", fromAction=" + fromAction + ", outcome="
					+ outcome + ") - start");
		}

		if (facesContext.getResponseComplete()) {
			return; // nothing to do
		}

		// check if the outcome is a viewId
		Stack<String> viewStack = getViewStack(facesContext);

		String currentViewId = facesContext.getViewRoot().getViewId();

		if (isBackward(outcome) && !viewStack.isEmpty()) {
			String viewId = viewStack.pop();
			if (StringUtils.equals(viewId, currentViewId) && !viewStack.isEmpty()) {
				viewId = viewStack.pop();
			}
			if (!StringUtils.equals(currentViewId, viewId)) {
				redirectToViewId(facesContext, viewId);
				return;
			} else {
				outcome = VIEW_HOME;
			}
		}

		if (isViewId(outcome)) {
			ConversationUtil.interpolateAndRedirect(facesContext, outcome);
			return;
		} else {
			originalNavigationHandler.handleNavigation(facesContext, fromAction, outcome);
		}
		checkForRedirect(facesContext, currentViewId);
	}

	private boolean isBackward(String outcome) {
		return outcome != null && outcome.startsWith(VIEW_BACKWARD);
	}

	/**
	 * If the outcome starts with a <tt>/</tt> it defines a view id.
	 * 
	 * @param outcome
	 * @return true, if the outcome starts with a <tt>/</tt>
	 */
	private boolean isViewId(String outcome) {
		return outcome != null && outcome.startsWith("/");
	}

	/**
	 * Checks if the viewId has changed and a redirect must be performed.
	 * 
	 * @param context
	 * @param currentViewId
	 */
	private void checkForRedirect(FacesContext context, String currentViewId) {
		/*
		 * Due to action processing based on GET-methods we must check for
		 * redirect if processing a GET-Request like it was previously done just
		 * for POST-methods.
		 * 
		 * Check if the viewId has changed. If it has changed then perform a
		 * redirect so that the browser will display the right url and the
		 * security filter can do its work.
		 */
		final String newViewId = context.getViewRoot().getViewId();
		if (!StringUtils.equals(newViewId, currentViewId)) {
			redirectToViewId(context, newViewId);
		}
	}

	/**
	 * Performs a PRG-Redirect to ViewId.
	 * 
	 * @param facesContext
	 * @param viewId
	 */
	private void redirectToViewId(FacesContext facesContext, final String viewId) {
		logger.debug("Perform redirect to view " + viewId);
		final ExternalContext externalContext = facesContext.getExternalContext();
		externalContext.getSessionMap().put(PostRedirectGetPhaseListener.POST_REDIRECT_GET_KEY, viewId);
		ConversationUtil.redirect(facesContext, viewId, null);
		facesContext.responseComplete();
	}

	/**
	 * Returns the view stack for the current user.
	 * 
	 * @param facesContext
	 * @return A Stack representing the views that have been visited during the
	 *         user sessions.
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
