package org.openuss.registration;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * 
 */
public class RegistrationParentDepartmentDisabledException extends org.openuss.registration.RegistrationException {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 8302851902098338977L;

	/**
	 * The default constructor.
	 */
	public RegistrationParentDepartmentDisabledException() {
	}

	/**
	 * Constructs a new instance of
	 * RegistrationParentDepartmentDisabledException
	 * 
	 * @param throwable
	 *            the parent Throwable
	 */
	public RegistrationParentDepartmentDisabledException(Throwable throwable) {
		super(findRootCause(throwable));
	}

	/**
	 * Constructs a new instance of
	 * RegistrationParentDepartmentDisabledException
	 * 
	 * @param message
	 *            the throwable message.
	 */
	public RegistrationParentDepartmentDisabledException(String message) {
		super(message);
	}

	/**
	 * Constructs a new instance of
	 * RegistrationParentDepartmentDisabledException
	 * 
	 * @param message
	 *            the throwable message.
	 * @param throwable
	 *            the parent of this Throwable.
	 */
	public RegistrationParentDepartmentDisabledException(String message, Throwable throwable) {
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
