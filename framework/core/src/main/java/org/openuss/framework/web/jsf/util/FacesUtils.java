package org.openuss.framework.web.jsf.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Utility class for JavaServer Faces. Found in JavaWorld article:
 * http://www.javaworld.com/javaworld/jw-07-2004/jw-0719-jsf.html
 * 
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 * @author idueppe
 */
public class FacesUtils {
	/**
	 * Get servlet context.
	 * 
	 * @return the servlet context
	 */
	public static ServletContext getServletContext() {
		return (ServletContext) getExternalContext().getContext();
	}

	/**
	 * Add the parameters to a URL
	 */
	@SuppressWarnings("unchecked")
	public static String encodeParameters(String url, Map<String, Object> parameters) {
		if (parameters.isEmpty()) {
			return url;
		}

		StringBuilder builder = new StringBuilder(url);
		
		for (Map.Entry<String, Object> param : parameters.entrySet()) {
			Object parameterValue = param.getValue();
			String parameterName = param.getKey();
		
			if (parameterValue instanceof Iterable) {
				for (Object value : (Iterable<Object>) parameterValue) {
					builder.append('&').append(parameterName).append('=');
					if (value != null) {
						builder.append(encode(value));
					}
				}
			} else {
				builder.append('&').append(parameterName).append('=');
				if (parameterValue != null) {
					builder.append(encode(parameterValue));
				}
			}
		}
		if (url.indexOf('?') < 0) {
			builder.setCharAt(url.length(), '?');
		}
		return builder.toString();
	}

	private static String encode(Object value) {
		try {
			return URLEncoder.encode(String.valueOf(value), "UTF-8");
		} catch (UnsupportedEncodingException iee) {
			throw new RuntimeException(iee);
		}
	}

	/**
	 * Get managed bean based on the bean name.
	 * 
	 * @param beanName
	 *            the bean name
	 * @return the managed bean associated with the bean name
	 * @deprecated
	 */
	public static Object getManagedBean(String beanName) {
		Object o = createValueBinding(el(beanName)).getValue(getFacesContext());

		return o;
	}

	/**
	 * Remove the managed bean based on the bean name.
	 * 
	 * @param beanName
	 *            the bean name of the managed bean to be removed
	 */
	public static void resetManagedBean(String beanName) {
		createValueBinding(el(beanName)).setValue(getFacesContext(), null);
	}

	/**
	 * Store the managed bean inside the session scope.
	 * 
	 * @param beanName
	 *            the name of the managed bean to be stored
	 * @param managedBean
	 *            the managed bean to be stored
	 */
	@SuppressWarnings("unchecked")
	public static void setManagedBeanInSession(String beanName, Object managedBean) {
		getExternalContext().getSessionMap().put(beanName, managedBean);
	}

	/**
	 * Get parameter value from request scope.
	 * 
	 * @param name
	 *            the name of the parameter
	 * @return the parameter value
	 */
	public static String getRequestParameter(String name) {
		return (String) getExternalContext().getRequestParameterMap().get(name);
	}

	/**
	 * Add information message.
	 * 
	 * @param msg
	 *            the information message
	 */
	public static void addInfoMessage(String msg) {
		addInfoMessage(null, msg);
	}

	/**
	 * Add information message to a sepcific client.
	 * 
	 * @param clientId
	 *            the client id
	 * @param msg
	 *            the information message
	 */
	public static void addInfoMessage(String clientId, String msg) {
		getFacesContext().addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
	}

	/**
	 * Add error message.
	 * 
	 * @param msg
	 *            the error message
	 */
	public static void addErrorMessage(String msg) {
		addErrorMessage(null, msg);
	}

	/**
	 * Add error message to a sepcific client.
	 * 
	 * @param clientId
	 *            the client id
	 * @param msg
	 *            the error message
	 */
	public static void addErrorMessage(String clientId, String msg) {
		getFacesContext().addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
	}

	/**
	 * Evaluate the integer value of a JSF expression.
	 * 
	 * @param el
	 *            the JSF expression
	 * @return the integer value associated with the JSF expression
	 */
	public static Integer evalInt(String el) {
		if (el == null) {
			return null;
		}

		if (UIComponentTag.isValueReference(el)) {
			Object value = getElValue(el);

			if (value == null) {
				return null;
			} else if (value instanceof Integer) {
				return (Integer) value;
			} else {
				return new Integer(value.toString());
			}
		} else {
			return new Integer(el);
		}
	}

	private static Application getApplication() {
		ApplicationFactory appFactory = (ApplicationFactory) FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);

		return appFactory.getApplication();
	}

	public static ValueBinding createValueBinding(String expression) {
		return getApplication().createValueBinding(expression);
	}
	
	public static MethodBinding createMethodBinding(String expression) {
		return getApplication().createMethodBinding(expression, null);
	}
	
	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) getExternalContext().getRequest();
	}

	/**
	 * @return FacesContext
	 */
	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	/**
	 * @return HttpServletResponse
	 */
	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) getExternalContext().getResponse();
	}

	/**
	 * @return ExternalContext
	 */
	public static ExternalContext getExternalContext() {
		return getFacesContext().getExternalContext();
	}

	public static HttpSession getSession() {
		return (HttpSession) getExternalContext().getSession(false);
	}

	private static Object getElValue(String el) {
		return createValueBinding(el).getValue(getFacesContext());
	}

	/**
	 * Convience method to wrap a string with el control chars
	 */
	private static String el(String value) {
		return "#{" + value + "}";
	}

	/**
	 * Convience method to add or update an object into the session context
	 * 
	 * @param key
	 *            unique id within the session
	 * @param value
	 *            scoped object
	 */
	public static void addToSessionContext(String key, Object value) {
		FacesContext facesContext = getFacesContext();
		facesContext.getApplication().createValueBinding(el("sessionScope." + key)).setValue(facesContext, value);
	}

	/**
	 * Checks whether or not the given object is a binding expression
	 * 
	 * @param value
	 * @return true if it is a binding expression
	 */
	public static boolean isBindingExpression(Object value) {
		return (value != null && value instanceof String && ((String) value).startsWith("#{") && ((String) value)
				.endsWith("}"));
	}

	/**
	 * Call navigation handler to handle an navigation outcome. 
	 * @param action jsf action expression
	 * @param outcome outcome of an jsf action
	 */
	public static void handleNavigationOutcome(String action, final String outcome) {
		final FacesContext facesContext = getFacesContext();
		final Application application = facesContext.getApplication();
		NavigationHandler navigationHandler = application.getNavigationHandler();
		navigationHandler.handleNavigation(facesContext, action, outcome);
	}

	/**
	 * Performs the action within the given faces context 
	 * @param facesContext
	 * @return null or the outcome string of the binded action method
	 */
	public static String perform(MethodBinding methodBinding) {
		String outcome = null;
		Object result = methodBinding.invoke(getFacesContext(), null);
		if (result instanceof String) {
			outcome = (String) result;
		}
		return outcome;
	}

	/**
	 * Checks whether or not the string is a el expression.
	 * @param expression
	 * @return true if it is a el expression.
	 */
	public static boolean isExpressionStatement(String expression) {
		return expression.indexOf("#{") > -1 || expression.indexOf("${)")> -1;
	}

	/**
	 * Sends a error status code.
	 * @see javax.servlet.http.HttpServletResponse.sendError(int)
	 * @param statusCode
	 */
	public static void sendError(int statusCode) throws IOException {
		HttpServletResponse response =(HttpServletResponse) getExternalContext().getResponse();
		response.sendError(statusCode);
		getFacesContext().responseComplete();
	}

}
