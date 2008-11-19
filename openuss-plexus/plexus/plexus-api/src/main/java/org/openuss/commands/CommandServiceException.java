package org.openuss.commands;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * The default exception thrown for unexpected errors occurring within
 * {@link org.openuss.commands.CommandService}.
 * 
 * @author Ingo Dueppe
 */
public class CommandServiceException extends RuntimeException {
	private static final long serialVersionUID = 7904159467068820536L;

	/**
	 * The default constructor for <code>CommandServiceException</code>.
	 */
	public CommandServiceException() {
	}

	/**
	 * Constructs a new instance of <code>CommandServiceException</code>.
	 * 
	 * @param throwable
	 *            the parent Throwable
	 */
	public CommandServiceException(Throwable throwable) {
		super(findRootCause(throwable));
	}

	/**
	 * Constructs a new instance of <code>CommandServiceException</code>.
	 * 
	 * @param message
	 *            the throwable message.
	 */
	public CommandServiceException(String message) {
		super(message);
	}

	/**
	 * Constructs a new instance of <code>CommandServiceException</code>.
	 * 
	 * @param message
	 *            the throwable message.
	 * @param throwable
	 *            the parent of this Throwable.
	 */
	public CommandServiceException(String message, Throwable throwable) {
		super(message, findRootCause(throwable));
	}

	/**
	 * Finds the root cause of the parent exception by traveling up the
	 * exception tree
	 */
	private static Throwable findRootCause(Throwable th) {
		if (th != null) {
			// Reflectively get any exception causes.
			try {
				Throwable targetException = null;

				// reflect.InvocationTargetException
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