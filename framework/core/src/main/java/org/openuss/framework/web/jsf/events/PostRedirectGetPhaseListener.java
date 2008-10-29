package org.openuss.framework.web.jsf.events;

import java.io.ObjectInputStream;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openuss.framework.web.jsf.util.ConversationUtil;

/**
 * This PostRedirectGet-PhaseListener will ensure in conjunction with the RedirectNavigationHandler
 * that the client browser will always display the current url. Also it ensures that the security
 * filter will also be called after a viewId changed even if using facelets that do not generate a
 * servlet forward during the render response phase.
 * <p>
 * The problem with a redirect before the render response phase that the request parameters get lost or
 * if the redirect url contains the request parameter some operations will be executed twice.
 * </p><p>
 * To solve this problem the post-redirect-get pattern need to be transparently to the jsf components. 
 * Even if the components stores their state within the request parameters. So, some how the request parameters
 * need to be recovered. Therefore the redirect will be perform with all request parameters.
 * <p/>
 * <p>
 * To prevent that some actionMethods and events are executed twice we must stop the lifecycle before the 
 * process validation phase. But the components need to get the chance to fetch their values from the request.
 * So, after the apply values phase we must proceed with the render response phase, during the get-phase of the prg-pattern. 
 * </p>
 * <p>
 * Attention: In some situations jsf jumps to the render response phase after restore view phase, so the token that mark the 
 * get-phase of prg need to be deleted within in phase. 
 * </p>
 * 
 * 
 * @see org.openuss.framework.application.RedirectNavigationHandler  
 * @author Ingo Dueppe
 */
public class PostRedirectGetPhaseListener implements PhaseListener {
	private static final Logger logger = Logger.getLogger(PostRedirectGetPhaseListener.class);

	private static final long serialVersionUID = -5124683930590968277L;

	public static final String POST_REDIRECT_GET_KEY = "org.openuss.framework.web.jsf.events.PostRedirectGetPhaseListener";
	public static final String POST_REDIRECT_GET_VALUE = "skip";

	private transient ThreadLocal<Boolean> prgGetPhase = new ThreadLocal<Boolean>();

	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

	/**
	 * Check if the request is the GET-Phase of the Post-Redirect-Get-Pattern.
	 * If so, abort the lifecycle and go directly to the render response phase
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public void afterPhase(PhaseEvent event) {
		final FacesContext facesContext = event.getFacesContext();
		final ExternalContext externalContext = event.getFacesContext().getExternalContext();

		if (event.getPhaseId() == PhaseId.RESTORE_VIEW) {
			if (logger.isDebugEnabled()) {
				logger.debug("After RESTORE_VIEW Phase");
			}

			ConversationUtil.restoreMessagesFromSession(facesContext);

			// setting default
			prgGetPhase.set(false);

			if (logger.isDebugEnabled()) {
				final HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
				logger.debug("Request Method = " + request.getMethod());
			}
			
			String prgViewId = (String) externalContext.getSessionMap().get(POST_REDIRECT_GET_KEY);
			String ctxViewId = "";
			if (facesContext.getViewRoot() != null) { 
				ctxViewId = facesContext.getViewRoot().getViewId();
			}
			
			if (prgViewId != null && StringUtils.equals(prgViewId, ctxViewId)) {
				logger.debug("Post-Redirect-Get-Request - Found PRG-Token");
				externalContext.getSessionMap().remove(POST_REDIRECT_GET_KEY);
				prgGetPhase.set(true);
			} else if (prgViewId != null) {
				logger.warn("prgViewId = "+prgViewId + ", ctxViewId = "+ctxViewId);
			}
		}

		if (event.getPhaseId() == PhaseId.APPLY_REQUEST_VALUES) {
			if (logger.isDebugEnabled()) {
				logger.debug("After " + event.getPhaseId() + " Phase");
			}
			if (prgGetPhase.get()) {
				logger.debug("Post-Redirect-Get-Request --> Proceeding with Render Response Phase");
				prgGetPhase.set(false);
				/*
				 * JSF normally clears input component values in the UpdateModel
				 * phase. However, this phase does not run for a GET request, so
				 * we must do it ourselves. Otherwise, the view will retain
				 * values from the first time it was loaded.
				 */
				ConversationUtil.resetComponentValues(facesContext.getViewRoot().getChildren());
				facesContext.renderResponse();
			}
		}
	}

	public void beforePhase(PhaseEvent event) {
		// if (event.getPhaseId() != PhaseId.RENDER_RESPONSE) {
		// 	return;
		// }
		//
		// final UIViewRoot viewRoot = facesContext.getViewRoot();
		//        
		// final ExternalContext externalContext =
		// facesContext.getExternalContext();
		// final HttpServletRequest request = (HttpServletRequest)
		// externalContext.getRequest();
		//        
		//
		// // Implement POST-REDIRECT-GET pattern
		// if ("POST".equals(request.getMethod())) {
		// logger.debug("POST-REDIRECT-GET Post-Phase");
		// externalContext.getSessionMap().put(POST_REDIRECT_GET_KEY,
		// POST_REDIRECT_GET_VALUE);
		//        	
		// String viewId = viewRoot.getViewId();
		//            
		// // set POST-REDIRECT-GET token
		//            
		// ConversationUtil.redirect(facesContext, viewId,
		// externalContext.getRequestParameterMap());
		// facesContext.responseComplete();
		// } else {
		// // Move saved messages from session back to request queue
		// ConversationUtil.restoreMessagesFromSession(facesContext);
		// /*
		// * JSF normally clears input component values in the UpdateModel
		// * phase. However, this phase does not run for a GET request, so we
		// * must do it ourselves. Otherwise, the view will retain values from
		// * the first time it was loaded.
		// */
		// ConversationUtil.resetComponentValues(viewRoot.getChildren());
		// }
	}
	
	private void readObject(ObjectInputStream in) {
		prgGetPhase = new ThreadLocal<Boolean>();
	}
}