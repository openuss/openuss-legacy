package org.openuss.collaboration;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * The default exception thrown for unexpected errors occurring within
 * {@link org.openuss.collaboration.WorkspaceService}.
 */
public class WorkspaceServiceException extends RuntimeException {

	private static final long serialVersionUID = 8238923317574554897L;

	/**
	 * The default constructor for <code>WorkspaceServiceException</code>.
	 */
	public WorkspaceServiceException() {
	}

	/**
	 * Constructs a new instance of <code>WorkspaceServiceException</code>.
	 * 
	 * @param throwable
	 *            the parent Throwable
	 */
	public WorkspaceServiceException(Throwable throwable) {
		super(findRootCause(throwable));
	}

	/**
	 * Constructs a new instance of <code>WorkspaceServiceException</code>.
	 * 
	 * @param message
	 *            the throwable message.
	 */
	public WorkspaceServiceException(String message) {
		super(message);
	}

	/**
	 * Constructs a new instance of <code>WorkspaceServiceException</code>.
	 * 
	 * @param message
	 *            the throwable message.
	 * @param throwable
	 *            the parent of this Throwable.
	 */
	public WorkspaceServiceException(String message, Throwable throwable) {
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