/**
 * <strong>OpenUSS - Plexus</strong>
 * The next generation e-learning plattform
 *
 * University of Muenster (c)
 * 
 * Developing Period 2005 - 2006
 */
package org.openuss.framework.utilities.ui;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * <strong>Description:</strong> 
 * <p>Display strings are handled through normal Java resource bundles. 
 * Almost all display strings are defined in JSP; there are a couple of classes, 
 * however that have display strings as well.</p>
 * <p> 
 *  
 * @author isarsu
 * @see http://sourceforge.net/users/isarsu/
 * 
 * Creation date: 20.06.2006
 *
 */
public abstract class Utils {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Utils.class.toString());
	
	/**
	 * <p>The code starts with a utility method, <code>getCurrentClassLoader</code>, 
	 * that returns either the class loader for the current thread or 
	 * the class loader of a specified default object. Why do we need this?</p> 
	 * <p>When you load a resource bundle, the ResourceBundle class searches the 
	 * classpath for files that are resource bundles. In this case, we want it 
	 * to search our web application’s classpath. Utility classes like Utils 
	 * are sometimes loaded from a web application’s main classpath with a different
	 * class loader, and consequently shared across many different web applications.</p>
	 * <p>If we didn’t account for this fact, we couldn’t guarantee that 
	 * <code>getDisplayString</code> would work properly unless the Utils class 
	 * was in the same <code>WEB-INF</code> directory as the rest of the web application.
	 *
	 * @param defaultObject
	 * @return
	 *
	 */
	protected static ClassLoader getCurrentClassLoader(Object defaultObject) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (loader == null) {
			loader = defaultObject.getClass().getClassLoader();
		}
		return loader;
	}
	
	/**
	 * <p>The <code>getDisplayString</code> method oads the bundle using 
	 * the static ResourceBundle. <code>getBundle</code> method, 
	 * passing in the ClassLoader instance retrieved from 
	 * <code>getCurrentClassLoader</code>. It then retrieves a string 
	 * from the bundle for the passedin identifier, using the 
	 * <code>ResourceBundle.getString</code> method. This method can throw
	 * a MissingResourceException if the specified identifier wasn’t found. 
	 * We’d prefer to return an error string instead in that case, 
	 * so we catch the exception. </p>
	 * <p>If an array of parameters were passed in, we insert those 
	 * into a string using the Message-Format class. We can use the 
	 * <code>getDisplayString</code> method in any code that needs 
	 * to retrieve a localized display string.</p>
	 * e.g. Let’s start with AuthorizationFilter. All we have to do 
	 * is change the hardcoded error message to use 
	 * the proper string from the resource bundle:
	 * <code>
	 * public void doFilter(
	 * 				ServletRequest request, 
	 * 				ServletResponse response,
	 * 				FilterChain chain)
	 * 				throws IOException, ServletException {
	 * 		...
	 * 		HttpServletRequest httpRequest = (HttpServletRequest)request;
	 * 		HttpServletResponse httpResponse = (HttpServletResponse)response;
	 * 		HttpSession session = httpRequest.getSession();
	 * 		String requestPath = httpRequest.getPathInfo();
	 * 		...
	 * 		if ((role.equals(RoleType.UPPER_MANAGER) && 
	 * 				requestPath.indexOf(Constants.PROTECTED_DIR) > 0) ||
	 * 				(!role.equals(RoleType.PROJECT_MANAGER) &&
	 * 				requestPath.indexOf(Constants.EDIT_DIR) > 0)) {
	 * 			<strong>String text</strong> = Utils.getDisplayString(Constants.BUNDLE_BASENAME, 
	 * 			"PathNotFound", new Object[] { requestPath }, request.getLocale());
	 * 			httpResponse.sendError(HttpServletResponse.SC_NOT_FOUND, <strong>text</strong>);
	 * 		}
	 * 		...
	 * }
	 * 
	 * @param bundleName the name of the resource bundle to find
	 * @param id the key to find within this resource bundle
	 * @param params the messages to be shown
	 * @param locale the specified locale
	 * @return
	 *
	 */
	public static String getDisplayString(
			String bundleName,
			String id,
			Object params[],
			Locale locale)	{
		String text = null;
		
		ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale, getCurrentClassLoader(params));
			try {
				text = bundle.getString(id);
			}
			catch (MissingResourceException e) {
				text = "!! key " + id + " not found !!";
				logger.log(Level.WARNING, "Could not find ressource bundle for key: " + id + "\n and bundle: " + bundle.toString());
			}
			if (params != null) {
				MessageFormat mf = new MessageFormat(text, locale);
				text = mf.format(params, new StringBuffer(), null).toString();
			}
			return text;
	}
}
