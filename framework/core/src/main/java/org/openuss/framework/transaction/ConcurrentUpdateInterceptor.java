package org.openuss.framework.transaction;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.UncategorizedSQLException;

public class ConcurrentUpdateInterceptor implements MethodInterceptor {

	private final Exception UNKNOWN = new Exception("Unknown!");
	
	private SessionFactory sessionFactory;
	
	private int maxTries;
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		int tries = 0;
		Throwable exception = UNKNOWN;
		while (tries++ < maxTries) {
			try {
				return invocation.proceed();
			} catch (Throwable ex) {
				if (ex instanceof UncategorizedSQLException && tries < maxTries) {
					Session session = sessionFactory.getCurrentSession();
					session.setFlushMode(FlushMode.AUTO);
					exception = ex;
				} else {
					throw ex;
				}
			}
		}
		throw exception;
	}

	public int getMaxTries() {
		return maxTries;
	}

	public void setMaxTries(int maxTries) {
		this.maxTries = maxTries;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
