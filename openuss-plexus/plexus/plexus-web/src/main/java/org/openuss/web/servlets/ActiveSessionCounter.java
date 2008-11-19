package org.openuss.web.servlets;

import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Listener that keeps track of the number of sessions that the Web application
 * is currently using.
 * <P>
 * Taken from More Servlets and JavaServer Pages from Prentice Hall and Sun
 * Microsystems Press, http://www.moreservlets.com/. &copy; 2002 Marty Hall; may
 * be freely used or adapted.
 */
public class ActiveSessionCounter implements HttpSessionListener {
	private static int sessionCount = 0;
	private static int sessionLimit = 1000;
	private ServletContext context = null;

	/**
	 * Each time a session is created, increment the running count. If the count
	 * exceeds the limit, print a warning in the log file.
	 */
	public void sessionCreated(HttpSessionEvent event) {
		sessionCount++;
		if (context == null) {
			recordServletContext(event);
		}
		String warning = getSessionCountWarning();
		if (warning != null) {
			context.log(warning);
		}
	}

	/**
	 * Each time a session is destroyed, decrement the running count. A session
	 * can be destroyed when a servlet makes an explicit call to invalidate, but
	 * it is more commonly destroyed by the system when the time since the last
	 * client access exceeds a limit.
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		sessionCount--;
	}

	/** The number of sessions currently in memory. */
	public static int getSessionCount() {
		return (sessionCount);
	}

	/**
	 * The limit on the session count. If the number of sessions in memory
	 * exceeds this value, a warning should be issued.
	 */
	public static int getSessionLimit() {
		return (sessionLimit);
	}

	/**
	 * If the number of active sessions is over the limit, this returns a
	 * warning string. Otherwise, it returns null.
	 */
	public static String getSessionCountWarning() {
		String warning = null;
		if (sessionCount > sessionLimit) {
			warning = "WARNING: the number of sessions in memory " + "(" + sessionCount + ") exceeds the limit " + "("
					+ sessionLimit + "). Date/time: " + new Date();
		}
		return (warning);
	}

	private void recordServletContext(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		context = session.getServletContext();
	}
}