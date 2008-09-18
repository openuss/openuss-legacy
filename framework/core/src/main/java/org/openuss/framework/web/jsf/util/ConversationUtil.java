package org.openuss.framework.web.jsf.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

/**
 * 
 * @author Ingo Dueppe
 * 
 */
public class ConversationUtil {

	private static final Logger logger = Logger.getLogger(ConversationUtil.class);
	
	private static ConverterHelper converterHelper = new ConverterHelper();

	public static final String FACES_MESSAGES_SESSION_KEY = "org.openuss.framework.web.jsf.events.ConversationUtil.SaveGlobalFacesMessages";

	/**
	 * Redirects to the given URL 
	 * @param facesContext
	 * @param url
	 */
	public static void interpolateAndRedirect(FacesContext facesContext, String url) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		int loc = url.indexOf('?');
		if (loc > 0) {
			StringTokenizer tokens = new StringTokenizer(url.substring(loc + 1), "+=&");
			while (tokens.hasMoreTokens()) {
				String name = tokens.nextToken();
				String value = null;
				// be more robust against misspelling URLs
				if (tokens.hasMoreTokens())
					value = ConversationUtil.interpolate(tokens.nextToken());
				else
					value = "";
				parameters.put(name, value);
			}
			url = url.substring(0, loc);
		}
		ConversationUtil.redirect(facesContext, url, parameters);
	}

	/**
	 * Generates an redirect to a viewId
	 * 
	 * @param viewId
	 * @param parameters
	 */
	public static void redirect(FacesContext facesContext, String viewId, Map<String, Object> parameters) {
		logger.debug("Starting method redirect");
		final ExternalContext externalContext = facesContext.getExternalContext();

		String url = facesContext.getApplication().getViewHandler().getActionURL(facesContext, viewId);
		if (parameters != null) {
			logger.debug("Try to encodee "+url);
			url = ConversationUtil.encodeParameters(url, parameters);
			logger.debug(url);
			if (url.length() > 1024) {
				logger.error("url "+url+"is longer than 1024 bytes");
			}
		}
		try {
			logger.debug("Try to redirect to "+url);
			externalContext.redirect(externalContext.encodeActionURL(url));
		} catch (IOException ioe) {
			throw new RuntimeException("could not redirect to: " + url, ioe);
		}
		saveMessagesIntoSession(facesContext);
	}

	/**
	 * Replace all EL expressions in the form #{...} with their evaluated
	 * values.
	 * 
	 * @param string
	 *            a template
	 * @return the interpolated string
	 */
	public static String interpolate(String string, Object... params) {
		if (string.indexOf('#') < 0)
			return string;
		if (params.length > 10) {
			throw new IllegalArgumentException("more than 10 parameters");
		}

		FacesContext context = FacesContext.getCurrentInstance();

		StringTokenizer tokens = new StringTokenizer(string, "#{}", true);
		StringBuilder builder = new StringBuilder(string.length());
		while (tokens.hasMoreTokens()) {
			String token = tokens.nextToken();
			if ("#".equals(token)) {
				String nextToken = tokens.nextToken();
				if ("{".equals(nextToken)) {
					String expression = "#{" + tokens.nextToken() + "}";
					try {
						Object value = context.getApplication().createValueBinding(expression).getValue(context);
						if (value != null) {
							builder.append(converterHelper.asString(context, value.getClass(), value));
						}
					} catch (RuntimeException e) {
						logger.warn("exception interpolating string: " + string, e);
					}
					tokens.nextToken();
				} else {
					int index = Integer.parseInt(nextToken.substring(0, 1));
					if (index >= params.length) {
						throw new IllegalArgumentException("parameter index out of bounds: " + index + " in: " + string);
					}
					builder.append(params[index]);
					builder.append(nextToken.substring(1));
				}
			} else {
				builder.append(token);
			}
		}
		return builder.toString();
	}
	
	/**
	 * Appends to the given url the parameter key value pairs
	 * 
	 * @param url
	 * @param parameters
	 * @return url+parameters
	 */
	public static String encodeParameters(String url, Map<String, Object> parameters) {
		if (parameters.isEmpty())
			return url;

		StringBuilder builder = new StringBuilder(url);
		for (Map.Entry<String, Object> param : parameters.entrySet()) {
			builder.append('&').append(param.getKey()).append('=').append(param.getValue());
		}
		// replace first & with ?
		builder.setCharAt(url.length(), '?');
		return builder.toString();
	}


	/**
	 * Store global faces messages into session before redirect.
	 * 
	 * Remove the messages that are not associated with any particular component
	 * from the faces context and store them to the user’s session.
	 * @param facesContext
	 */
	public static void saveMessagesIntoSession(FacesContext facesContext) {
		// check if a valid session exist.
		if (facesContext.getExternalContext().getSession(false) == null) {
			logger.debug("session is invalid. No messages will be stored.");
			return;
		}
		logger.trace("save global messages");
		
		Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();
		
		if (!sessionMap.containsKey(FACES_MESSAGES_SESSION_KEY)) {
			sessionMap.put(FACES_MESSAGES_SESSION_KEY, new HashMap<String,List<FacesMessage>>());
		}
		
		Map<String, List<FacesMessage>> allMessages = (Map<String, List<FacesMessage>>) sessionMap.get(FACES_MESSAGES_SESSION_KEY);
		
		for (Iterator<String> ids = facesContext.getClientIdsWithMessages(); ids.hasNext();) {
			String clientId = ids.next();
			List<FacesMessage> messages = new ArrayList<FacesMessage>();
			// for each component (client id) retrieve the messages
			for (Iterator<FacesMessage> msgs = facesContext.getMessages(clientId); msgs.hasNext();) {
				messages.add(msgs.next());
				msgs.remove();
			}
			List<FacesMessage> clientMessages = allMessages.get(clientId);
			if (clientMessages != null) {
				clientMessages.addAll(messages);
			} else {
				allMessages.put(clientId, messages);
			}
		}
	}

	
	/**
	 * Restore global faces messages from session after redirect.
	 * 
	 * @param facesContext
	 */
	public static void restoreMessagesFromSession(FacesContext facesContext) {
		final Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();
		
		Map<String, List<FacesMessage>> allMessages = (Map<String, List<FacesMessage>>) sessionMap.remove(FACES_MESSAGES_SESSION_KEY);
		if (allMessages == null) {
			logger.trace("no messages to restore");
			return;
		}
		logger.trace("restore messages from session");
		
		for (Entry<String, List<FacesMessage>> entry : allMessages.entrySet()) {
			String clientId = (String) entry.getKey();
			List<FacesMessage> clientMessages = entry.getValue();
			for (FacesMessage message : clientMessages) {
				facesContext.addMessage(clientId, message);
			}
		}
	}
	
    /**
     * Resets UIInput component values From 
     * http://forum.java.sun.com/thread.jspa?threadID=495087&messageID=3704164
     */
    public static void resetComponentValues(List<UIComponent> childList) {
        for (int i = 0; i < childList.size(); i++) {
            UIComponent component = childList.get(i);
            if (component instanceof UIInput) {
            	UIInput input = (UIInput) component;
            	if (logger.isTraceEnabled()) {
            		logger.debug("Reset "+input.getClientId(FacesContext.getCurrentInstance())+" from "+input.getSubmittedValue()+" to null");
            	}
                input.setSubmittedValue(null);
            }
            resetComponentValues(component.getChildren());
        }
    }

}
