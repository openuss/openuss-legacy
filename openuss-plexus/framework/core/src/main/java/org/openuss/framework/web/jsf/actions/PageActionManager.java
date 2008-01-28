package org.openuss.framework.web.jsf.actions;

import java.util.List;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openuss.framework.web.jsf.util.FacesUtils;

/**
 * The PageActionManager process all associated PageActions.
 * The processActions methods must not be called before the view is restored.
 * 
 * @author Ingo Dueppe
 */
public class PageActionManager {

	private static final Logger logger = Logger.getLogger(PageActionManager.class);

	private List<PageAction> actions;

	/**
	 * Process all actions if they matches the requested view
	 * 
	 * @param facesContext
	 */
	public void processActions(FacesContext facesContext) {
		if (facesContext == null) {
			throw new IllegalArgumentException("FacesContext cannot be null!");
		}

		final UIViewRoot view = facesContext.getViewRoot();

		if (view == null) {
//			throw new IllegalStateException("View is not defined. Call processActions after the restore view phase!");
			return;
		}
		
		logger.debug("start processing actions");
		
		final String viewId = view.getViewId();
		if (StringUtils.isNotBlank(viewId)) {
			for (PageAction action : actions) {
				if (action.matches(viewId)) {
					if (logger.isDebugEnabled()) {
						logger.debug("perform page action " + action.getAction());
					}
					
					final String outcome = action.perform(facesContext);

					if (outcome != null) {
						FacesUtils.handleNavigationOutcome(action.getFromAction(), outcome);
						break;
					}
				}
			}
		}
		logger.debug("finish processing actions");
	}

	/**
	 * Get a list of PageActions
	 * 
	 * @return list<PageAction>
	 */
	public List<PageAction> getActions() {
		return actions;
	}

	/**
	 * Set a list of PageActions
	 * 
	 * @param actions
	 *            list<PageAction>
	 */
	public void setActions(List<PageAction> actions) {
		this.actions = actions;
	}

}
