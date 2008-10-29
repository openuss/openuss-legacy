package org.openuss.registration;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * 
 */
public class RegistrationCodeNotFoundException extends org.openuss.registration.RegistrationException {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -1296496512919620400L;

	/**
	 * The default constructor.
	 */
	public RegistrationCodeNotFoundException() {
	}

	/**
	 * Constructs a new instance of RegistrationCodeNotFoundException
	 * 
	 * @param throwable
	 *            the parent Throwable
	 */
	public RegistrationCodeNotFoundException(Throwable throwable) {
		super(findRootCause(throwable));
	}

	/**
	 * Constructs a new instance of RegistrationCodeNotFoundException
	 * 
	 * @param message
	 *            the throwable message.
	 */
	public RegistrationCodeNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructs a new instance of RegistrationCodeNotFoundException
	 * 
	 * @param message
	 *            the throwable message.
	 * @param throwable
	 *            the parent of this Throwable.
	 */
	public RegistrationCodeNotFoundException(String message, Throwable throwable) {
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
