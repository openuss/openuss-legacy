package org.openuss.framework.web.hibernate;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Implements the hibernate-session-per-servlet-session pattern. See <a
 * href="http://www.hibernate.org/43.html">Hibernate Documenation</a>
 * 
 * @author Ingo Dueppe
 */
public class HibernateSessionConversationFilter extends OncePerRequestFilter {

	private static final Logger logger = Logger.getLogger(HibernateSessionConversationFilter.class);

	public static final String DEFAULT_SESSION_FACTORY_BEAN_NAME = "sessionFactory";
	public static final String HIBERNATE_SESSION_KEY = "hibernateSession";
	public static final String END_OF_CONVERSATION_FLAG = "endOfConversation";

	private String sessionFactoryBeanName = DEFAULT_SESSION_FACTORY_BEAN_NAME;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		logger.trace("filter started");

		final HttpSession httpSession = request.getSession();
		final SessionFactory sessionFactory = lookupSessionFactory();

		org.hibernate.classic.Session currentSession = null;
		Session disconnectedSession = (Session) httpSession.getAttribute(HIBERNATE_SESSION_KEY);

		try {
			// Start a new conversation or resume one
			if (disconnectedSession == null) {
				logger.trace(">>> new conversation");
				currentSession = (org.hibernate.classic.Session) SessionFactoryUtils.getSession(sessionFactory, true);
				currentSession.setFlushMode(FlushMode.MANUAL);
			} else {
				logger.trace("<<< continuing conversation");
				currentSession = (org.hibernate.classic.Session) disconnectedSession;
			}

			logger.trace("Binding the current Session");
			TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(currentSession));

			logger.trace("Starting a database transaction");
			currentSession.beginTransaction();

			logger.trace("Processing the event");
			filterChain.doFilter(request, response);

			if (TransactionSynchronizationManager.hasResource(sessionFactory)) {
				logger.debug("Unbinding Session after processing");
				TransactionSynchronizationManager.unbindResource(sessionFactory);
			}

			// End or continue the long-running conversation?
			if (currentSession != null) {
				if ((request.getAttribute(END_OF_CONVERSATION_FLAG) != null)
						|| (request.getParameter(END_OF_CONVERSATION_FLAG) != null)) {
					logger.trace("Flushing Session");
					currentSession.flush();

					logger.trace("Committing the database transaction");
					currentSession.getTransaction().commit();

					logger.trace("Closing the Session");
					currentSession.close();

					if (request.isRequestedSessionIdValid()) {
						logger.trace("Cleaning session from HttpSession");
						httpSession.removeAttribute(HIBERNATE_SESSION_KEY);
					}

					logger.trace("<<< End of conversation");
				} else {
					logger.trace("Committing database transaction");
					if (currentSession.getTransaction().isActive())
						currentSession.getTransaction().commit();

					if (request.isRequestedSessionIdValid()) {
						logger.trace("Storing Session in the HttpSession");
						httpSession.setAttribute(HIBERNATE_SESSION_KEY, currentSession);
					}

					logger.trace("> Returning to user in conversation");
				}
			}

		} catch (StaleObjectStateException ex) {
			logger.error("This interceptor does not implement optimistic concurrency control!");
			logger.error("Your application will not work until you add compensation actions!");
			// Rollback, close everything, possibly compensate for any permanent
			// changes during the conversation, and finally restart business
			// conversation. Maybe give the user of the application a chance 
			// to merge some of his work with fresh data... 
			// what you do here depends on your applications design.
			throw ex;
		} catch (Throwable ex) {
			// Rollback only
			try {
				if (sessionFactory.getCurrentSession().getTransaction().isActive()) {
					logger.debug("Trying to rollback database transaction after exception");
					sessionFactory.getCurrentSession().getTransaction().rollback();
				}
			} catch (Throwable rbEx) {
				logger.error("Could not rollback transaction after exception!", rbEx);
			} finally {
				logger.error("Cleanup after exception!");

				if (TransactionSynchronizationManager.hasResource(sessionFactory)) {
					logger.debug("Unbinding Session after exception");
					TransactionSynchronizationManager.unbindResource(sessionFactory);
				}

				if (currentSession != null) {
					logger.debug("Closing Session after exception");
					currentSession.close();
				}

				if (request.isRequestedSessionIdValid()) {
					logger.debug("Removing Session from HttpSession after exception");
					httpSession.removeAttribute(HIBERNATE_SESSION_KEY);
				}
			}
			// Let others handle it... maybe another interceptor for exceptions
			throw new ServletException(ex);
		}

	}

	/**
	 * Set the bean name of the SessionFactory to fetch from Spring's root
	 * application context. Default is "sessionFactory".
	 * 
	 * @see #DEFAULT_SESSION_FACTORY_BEAN_NAME
	 */
	public void setSessionFactoryBeanName(String sessionFactoryBeanName) {
		this.sessionFactoryBeanName = sessionFactoryBeanName;
	}

	/**
	 * Return the bean name of the SessionFactory to fetch from Spring's root
	 * application context.
	 */
	protected String getSessionFactoryBeanName() {
		return sessionFactoryBeanName;
	}

	/**
	 * Look up the SessionFactory that this filter should use.
	 * <p>
	 * Default implementation looks for a bean with the specified name in
	 * Spring's root application context.
	 * 
	 * @return the SessionFactory to use
	 * @see #getSessionFactoryBeanName
	 */
	protected SessionFactory lookupSessionFactory() {
		if (logger.isDebugEnabled()) {
			logger.debug("Using SessionFactory '" + getSessionFactoryBeanName()
					+ "' for HibernateSessionConversationFilter");
		}
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());

		return (SessionFactory) wac.getBean(getSessionFactoryBeanName(), SessionFactory.class);
	}

	/*
	 * public void init(FilterConfig filterConfig) throws ServletException {
	 * log.debug("Initializing filter..."); log.debug("Obtaining SessionFactory
	 * from static HibernateUtil singleton"); sf =
	 * HibernateUtil.getSessionFactory(); }
	 */

}
