package org.openuss.newsletter;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * 
 */
public class NewsletterApplicationException extends org.openuss.foundation.ApplicationException {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 7713184771727230597L;

	/**
	 * The default constructor.
	 */
	public NewsletterApplicationException() {
	}

	/**
	 * Constructs a new instance of NewsletterApplicationException
	 * 
	 * @param throwable
	 *            the parent Throwable
	 */
	public NewsletterApplicationException(Throwable throwable) {
		super(findRootCause(throwable));
	}

	/**
	 * Constructs a new instance of NewsletterApplicationException
	 * 
	 * @param message
	 *            the throwable message.
	 */
	public NewsletterApplicationException(String message) {
		super(message);
	}

	/**
	 * Constructs a new instance of NewsletterApplicationException
	 * 
	 * @param message
	 *            the throwable message.
	 * @param throwable
	 *            the parent of this Throwable.
	 */
	public NewsletterApplicationException(String message, Throwable throwable) {
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
