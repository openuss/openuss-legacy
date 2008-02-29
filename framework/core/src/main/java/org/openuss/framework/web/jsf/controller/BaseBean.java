package org.openuss.framework.web.jsf.controller;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Abstract basic class to provide convenience methods to access external e
 * 
 * @author Ingo Dueppe
 */
public abstract class BaseBean {

	private static final Logger logger = Logger.getLogger(BaseBean.class);

	public static final String jstlBundleParam = "javax.servlet.jsp.jstl.fmt.localizationContext";

	transient private FacesContext facesContext;
	
	private boolean redirected;
	
	/**
	 * Convience method to set a session attribute
	 * @param key
	 * @param value
	 */
	public void setSessionAttribute(String key, Object value) {
		getExternalContext().getSessionMap().put(key, value);
	}
	
	/**
	 * Convience method to get a session attribute
	 * @param key
	 * @return value
	 */
	public Object getSessionAttribute(String key) {
		return getExternalContext().getSessionMap().get(key);
	}
	
	/**
	 * @param key
	 * @return true if a attribute with the key exists
	 */
	public boolean containsSessionKey(String key) {
		return getExternalContext().getSessionMap().containsKey(key);
	}

	/**
	 * Convience method for unit testing to inject a specific FacesContext into
	 * the controller.
	 * @param facesContext
	 */
	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}

	/**
	 * Convenience method to get the FacesContext. Supports unit testing through
	 * dependency injection by using setFacesContext.
	 * @return FacesContext
	 */
	public FacesContext getFacesContext() {
		if (facesContext != null)
			return facesContext; // for unit testing
		return FacesContext.getCurrentInstance();
	}

	/**
	 * Convenience method to get a parameter from the request.
	 * @param name of the parameter
	 * @return parameter value
	 */
	public String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	/**
	 * Convenience method to get the Application object of the faces context.
	 * @return Application
	 */
	public Application getApplication() {
		return getFacesContext().getApplication();
	}

	/**
	 * Convenience method to get the HttpServletRequest of the external context.
	 * @return HttpServletRequest
	 */
	protected HttpServletRequest getRequest() {
		if (getFacesContext() != null) {
			if (getFacesContext().getExternalContext() != null) {
				if (getFacesContext().getExternalContext().getRequest() != null) {
					return (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
				}
			}
		}
		return null;
	}

	/**
	 * Convenience method to get the HttpSession from the external context.
	 * @return HttpSession
	 */
	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * Convenience method to get the HttpServletResponse of the external
	 * context.
	 * 
	 * @return HttpServletResponse
	 */
	protected HttpServletResponse getResponse() {
		return (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
	}

	/**
	 * Convenience method to get the ServletContext of the external context.
	 * @return ServletContext
	 */
	protected ServletContext getServletContext() {
		return (ServletContext) getFacesContext().getExternalContext().getContext();
	}
	
	/**
	 * Convenience method to get the ExternalContext of the current faces context.
	 * @return ExternalContext
	 */
	protected ExternalContext getExternalContext() {
		return getFacesContext().getExternalContext();
	}

	/**
     * Store the managed bean inside the session scope.
     * @param name the name of the managed bean to be stored
     * @param bean the managed bean to be stored
     */
    @SuppressWarnings("unchecked")
	protected void setSessionBean(String name, Object bean) {
    	setBean("sessionScope."+name, bean);
    }
    
    
    /**
     * Retrieves a bean from session scope.
     * @param name of the bean in the session
     * @return bean instance
     */
    protected Object getSessionBean(String name) {
    	return getBean("sessionScope."+name);
    }
    
    public String contextPath() {
    	return getRequest().getContextPath();
    }

    /**
     * Remove bean from session.
     * @param name
     */
    protected void removeSessionBean(String name) {
    	if (name != null) {
    		setSessionBean(name, null);
    	}
    }
    
    /**
     * Store the bean instance inside the request scope.
     * @param name of the bean
     * @param bean instance of the bean
     */
    protected void setRequestBean(String name, Object bean) {
    	setBean("requestScope."+name, bean);
    }
    
    /**
     * Retrieve a bean from request scope 
     * @param name
     * @return bean instance or null
     */
    protected Object getRequestBean(String name) {
    	return getBean("requestScope."+name);
    }
    
    
    /**
     * @return BundleName
     */
	public String getBundleName() {
		// get name of resource bundle from JSTL settings.
		// JSF makes this too hard
		return getServletContext().getInitParameter(jstlBundleParam);
	}

	/**
	 * @return ResoureBundle
	 */
	public ResourceBundle getBundle() {
		if (getFacesContext().getViewRoot() == null) {
			return ResourceBundle.getBundle(getBundleName(), getRequest().getLocale());
		} else {
			return ResourceBundle.getBundle(getBundleName(), getFacesContext().getViewRoot().getLocale());
		}
	}

	/**
	 * Convenience method to get a string from the resources. 
	 * @param key of the resources string
	 * @return
	 */
	public String i18n(String key) {
		String message;

		try {
			message = getBundle().getString(key);
		} catch (java.util.MissingResourceException mre) {
			logger.warn("Missing key for '" + key + "'");

			return "[?" + key + "?]";
		} catch (NullPointerException ex) {
			return "[?" + key + "(bundle not found) ?]";
		}

		return message;
	}
	
	public String i18n(String key, String defaultLabel, Object arg) {
		String message;
		try {
			message = getBundle().getString(key);
		} catch (java.util.MissingResourceException mre) {
			return msgFormat(defaultLabel, arg);
		} catch (NullPointerException ex) {
			return msgFormat(defaultLabel, arg);
		}
		return msgFormat(message,arg);
	}

	/**
	 * Convenience method to get a String from the resources.
	 * @param key of the string within the resource file
	 * @param arg arguments
	 * @return string 
	 */
	public String i18n(String key, Object arg) {
		String text = i18n(key);
		return msgFormat(text, arg);
	}

	private String msgFormat(String text, Object arg) {
		MessageFormat form = new MessageFormat(text);

		if (arg instanceof String) {
			return form.format(new Object[] { arg });
		} else if (arg instanceof Object[]) {
			return form.format(arg);
		} else {
			logger.error("arg '" + arg + "' not String or Object[]");
			return text;
		}
	}

	/**
	 * Convenience method to add an message to faces messages. 
	 * @param severity
	 * @param summary
	 * @param details
	 * @param clientId
	 */
	protected void addMessage(String clientId, Severity severity, String summary, String details) {
		FacesMessage msg = new FacesMessage(severity, summary, details);
		getFacesContext().addMessage(clientId, msg);
	}

	/**
	 * Convenience method to add an message to faces messages. 
	 * @param severity
	 * @param summary
	 * @param details
	 * @param clientId
	 */
	protected void addMessage(Severity severity, String summary, String details) {
		addMessage(null, severity, summary, details);
	}

	/**
	 * Convenience method to add an message to faces messages. 
	 * @param severity
	 * @param summary
	 */
	protected void addMessage(Severity severity, String summary) {
		addMessage(severity, summary, null);
	}

	/**
	 * Convenience method to add an message to faces messages. 
	 * @param summary
	 * @param details
	 */
	protected void addMessage(String summary, String details) {
		addMessage(FacesMessage.SEVERITY_INFO, summary, details);
	}

	/**
	 * Convenience method to add an message to faces messages. 
	 * @param summary
	 */
	protected void addMessage(String summary) {
		addMessage(summary, null);
	}

	/**
	 * Convenience method to add an error message to faces messages.
	 * @param summary
	 */
	protected void addError(String summary) {
		addError(summary,null);
	}

	/**
	 * Convenience method to add an error message to faces messages.
	 * @param summary
	 * @param details
	 */
	protected void addError(String summary, String details) {
		addError(null, summary, details);
	}
	
	protected void addError(String clientId, String summary, String details) {
		addMessage(clientId, FacesMessage.SEVERITY_ERROR, summary, details);
	}

	/**
	 * Convenience method to add a warning message to faces messages.
	 * @param summary
	 */
	protected void addWarning(String summary) {
		addMessage(FacesMessage.SEVERITY_WARN, summary);
	}

	/**
	 * Convenience method to add a warning message to faces messages.
	 * @param summary
	 * @param details
	 */
	protected void addWarning(String summary, String details) {
		addMessage(FacesMessage.SEVERITY_WARN, summary, details);
	}
	
	
    /**
     * <p>Return the named bean from request, session, or application scope.
     * If this is a managed bean, it might also get created as a side effect.
     * Return <code>null</code> if no such bean can be found or created.</p>
     *
     * @param name Name of the desired bean
     */
    protected Object getBean(String name) {
    	return getValue("#{"+name+"}");
    }

    /**
     * <p>Replace the value of any attribute stored in request scope,
     * session scope, or application scope, under the specified name.
     * If there is no such value, store this value as a new request
     * scope attribute under the specified name.</p>
     *
     * @param name Name of the attribute to replace or create
     * @param value Value to be stored
     */
	protected void setBean(String name, Object value) {
		setValue("#{"+name+"}", value);
	}
	
    /**
     * <p>Evaluate the specified value binding expression and return
     * the value it points at.</p>
     *
     * @param expression Value binding expression to be evaluated
     */
    protected Object getValue(String expression) {
        ValueBinding vb = getApplication().createValueBinding(expression);
        return vb.getValue(getFacesContext());

    }

    /**
     * <p>Evaluate the specified value binding expression, and replace
     * the value it points at.</p>
     *
     * @param expression Value binding expression pointing at a writeable property
     * @param value New value to store there
     */
    protected void setValue(String expression, Object value) {
        ValueBinding vb = getApplication().createValueBinding(expression);
        vb.setValue(getFacesContext(), value);
    }

	protected void redirect(String outcome) {
		NavigationHandler navigation = getApplication().getNavigationHandler();
		navigation.handleNavigation(getFacesContext(), null , outcome);
		redirected = true;
	}

	public boolean isPostBack() {
		return "POST".equals(getRequest().getMethod());
	}

	public boolean isRedirected() {
		return redirected;
	}

	public void setRedirected(boolean redirected) {
		this.redirected = redirected;
	}
}
