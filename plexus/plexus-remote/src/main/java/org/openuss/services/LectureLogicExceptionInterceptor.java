package org.openuss.services;

import org.apache.log4j.Logger;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Transfor any exception into LectureLogicExceptions
 * @author Ingo Dueppe
 *
 */
public class LectureLogicExceptionInterceptor implements MethodInterceptor{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LectureLogicExceptionInterceptor.class);

	@Override
	public Object invoke(final MethodInvocation invocation) throws LectureLogicException {
		try {
			return invocation.proceed();
		} catch (Throwable ex) { // NOPMD by idueppe on 08.03.08 23:59
			logger.error(ex);
			throw new LectureLogicException(ex.getMessage(), ex);
		}
	}
	
}
