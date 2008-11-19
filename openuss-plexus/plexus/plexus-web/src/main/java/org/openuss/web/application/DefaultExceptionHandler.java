package org.openuss.web.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.shale.view.ExceptionHandler;
import org.apache.shale.view.faces.FacesConstants;
import org.openuss.foundation.ApplicationException;
import org.openuss.framework.web.jsf.controller.BaseBean;

/**
 * OpenUSS-Plexus default exception handler
 * 
 * @author Ingo Dueppe
 */
public class DefaultExceptionHandler extends BaseBean implements ExceptionHandler {

	private static final Logger logger = Logger.getLogger(DefaultExceptionHandler.class);

	private static final long serialVersionUID = 630352128490874669L;

	/**
	 * <p>
	 * Log the specified exception, and record it in a request scoped
	 * <code>List</code> that can be used to report them all at a future point
	 * in time to report all of the accumulated exceptions.
	 * </p>
	 * 
	 * @param exception
	 *            Exception to be handled
	 */
	public void handleException(Exception exception) {
		boolean found = false;
		// search for application exceptions
		Throwable throwable = exception;
		while (throwable != throwable.getCause() && throwable.getCause() != null && !found) {
			throwable = throwable.getCause();
			found = throwable instanceof ApplicationException;
		}
		if (found) {
			handleApplicationException((Exception)throwable);
		} else {
			handleSystemException(exception);
		}

	}

	/**
	 * Application Exceptions just ends in an Error message within the same page.
	 * @param exception
	 */
	private void handleApplicationException(Exception exception) {
		if (logger.isDebugEnabled()) {
			logger.debug("Catched ApplicationException", exception);
		}
		addError(i18n(exception.getMessage()));
	}

	/**
	 * 
	 * @param exception
	 */
	@SuppressWarnings("unchecked")
	private void handleSystemException(Exception exception) {
		logger.error("SystemException", exception);
		FacesContext context = FacesContext.getCurrentInstance();

		// Are we within the context of a JavaServer Faces request?
		// If so, accumulate this exception to the list that can be
		// reported at the completion of the request.

		if (context != null && context.getExternalContext() != null) {
			Map<String, List<Throwable>> requestMap = context.getExternalContext().getRequestMap();
			List<Throwable> list = (List<Throwable>) requestMap.get(FacesConstants.EXCEPTIONS_LIST);
			if (list == null) {
				list = new ArrayList<Throwable>();
				requestMap.put(FacesConstants.EXCEPTIONS_LIST, list);
			}
			list.add(exception);
		}
		if (context != null) {
			context.responseComplete();
		}
	}
}
