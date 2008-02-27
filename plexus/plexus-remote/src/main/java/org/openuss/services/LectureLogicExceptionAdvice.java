package org.openuss.services;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Transfor any exception into LectureLogicExceptions
 * @author Ingo Dueppe
 *
 */
public class LectureLogicExceptionAdvice implements MethodInterceptor{

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		try {
			return invocation.proceed();
		} catch (Throwable ex) {
			throw new LectureLogicException(ex.getMessage(), ex);
		}
	}
	
}
