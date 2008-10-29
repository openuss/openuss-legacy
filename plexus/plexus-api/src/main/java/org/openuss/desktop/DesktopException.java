package org.openuss.desktop;

import org.apache.commons.beanutils.PropertyUtils;
import org.openuss.foundation.ApplicationException;

/**
 * 
 * @author Ingo Dueppe
 * 
 */
public class DesktopException extends ApplicationException {

	private static final long serialVersionUID = -3228027055370888213L;

	/**
	 * The default constructor.
	 */
	public DesktopException() {
	}

	/**
	 * Constructs a new instance of DesktopException
	 * 
	 * @param throwable
	 *            the parent Throwable
	 */
	public DesktopException(Throwable throwable) {
		super(findRootCause(throwable));
	}

	/**
	 * Constructs a new instance of DesktopException
	 * 
	 * @param message
	 *            the throwable message.
	 */
	public DesktopException(String message) {
		super(message);
	}

	/**
	 * Constructs a new instance of DesktopException
	 * 
	 * @param message
	 *            the throwable message.
	 * @param throwable
	 *            the parent of this Throwable.
	 */
	public DesktopException(String message, Throwable throwable) {
		super(message, findRootCause(throwable));
	}

	/**
	 * Finds the root cause of the parent exception by traveling up the
	 * exception tree
	 */
	private static Throwable findRootCause(Throwable th) {
		if (th != null) {
			// Lets reflectively get any JMX or EJB exception causes.
			try {
				Throwable targetException = null;
				// reflect.InvocationTargetException
				// or javax.management.ReflectionException
				String exceptionProperty = "targetException";
				if (PropertyUtils.isReadable(th, exceptionProperty)) {
					targetException = (Throwable) PropertyUtils.getProperty(th, exceptionProperty);
				} else {
					exceptionProperty = "causedByException";
					// javax.ejb.EJBException
					if (PropertyUtils.isReadable(th, exceptionProperty)) {
						targetException = (Throwable) PropertyUtils.getProperty(th, exceptionProperty);
					}
				}
				if (targetException != null) {
					th = targetException;
				}
			} catch (Exception ex) {
				// just print the exception and continue
				ex.printStackTrace();
			}

			if (th.getCause() != null) {
				th = th.getCause();
				th = findRootCause(th);
			}
		}
		return th;
	}
}
