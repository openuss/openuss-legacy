package org.openuss.framework.deploy;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import javax.servlet.ServletContext;

import junit.framework.TestCase;

public class HotDeployUtilTest extends TestCase {
	
	private ServletContext servletContext;
	private ClassLoader classLoader;
	
	/**
	 * Reset HotDeployUtil after each test.
	 * {@inheritDoc}
	 */
	public void setUp() {
		HotDeployUtil.reset();
		servletContext = createMock(ServletContext.class);
		classLoader = Thread.currentThread().getContextClassLoader();
		expect(servletContext.getServletContextName()).andReturn("TEST SERVLET CONTEXT");
		expectLastCall().anyTimes();
	}
	
	public void testWrongEventFiring() {
	}
	
	/**
	 * Test Event firing
	 */
	public void testEventFiring() {
		HotDeployEvent eventA = new HotDeployEvent(servletContext,classLoader);
		HotDeployEvent eventB = new HotDeployEvent(servletContext,classLoader);

		HotDeployListener listenerMock = createMock(HotDeployListener.class);
		invokeDeploy(listenerMock, eventA);
		expectLastCall().times(3);
		invokeDeploy(listenerMock, eventB);
		expectLastCall().times(3);
		invokeUndeploy(listenerMock, eventA);
		expectLastCall().times(3);
		invokeUndeploy(listenerMock, eventB);
		expectLastCall().times(3);
		
		HotDeployUtil.registerListener(listenerMock);
		HotDeployUtil.registerListener(listenerMock);
		HotDeployUtil.registerListener(listenerMock);
		HotDeployUtil.flushEvents();
		replay(servletContext);
		replay(listenerMock);
		HotDeployUtil.fireDeployEvent(eventA);
		HotDeployUtil.fireDeployEvent(eventB);
		HotDeployUtil.fireUndeployEvent(eventA);
		HotDeployUtil.fireUndeployEvent(eventB);
		verify(listenerMock);
	}

	/**
	 * Test wether the event flushing after initialisation works correctly. 
	 */
	public void testEventFlushing() {
		HotDeployEvent eventA = new HotDeployEvent(servletContext,classLoader);
		HotDeployEvent eventB = new HotDeployEvent(servletContext,classLoader);
		
		HotDeployListener listenerMock = createMock(HotDeployListener.class);
		invokeDeploy(listenerMock, eventA);
		invokeDeploy(listenerMock, eventB);
		invokeDeploy(listenerMock, eventA);
		invokeDeploy(listenerMock, eventB);
		replay(servletContext);
		replay(listenerMock);
		// Fire events before initialisation.
		HotDeployUtil.fireDeployEvent(eventA);
		HotDeployUtil.fireDeployEvent(eventB);
		// Initialize HotDeployUtil
		HotDeployUtil.registerListener(listenerMock);
		HotDeployUtil.flushEvents();
		// Second flush should fire all events again
		HotDeployUtil.flushEvents();
		verify(listenerMock);
	}
	

	/**
	 * Test if exception within the listener object are catched correctly.
	 */
	public void testHotDeployException() {
		HotDeployEvent event = new HotDeployEvent(servletContext,classLoader);
		HotDeployListener mock = createMock(HotDeployListener.class);
		invokeDeploy(mock, event);
		expectLastCall().andThrow(new HotDeployException("Just for testing!"));
		replay(mock);
		// Initialize HotDeployUtil
		HotDeployUtil.registerListener(mock);
		HotDeployUtil.flushEvents();
		HotDeployUtil.fireDeployEvent(event);
		verify(mock);
	}
	
	/**
	 * Test if exception within the listener object are catched correctly.
	 */
	public void testHotUndeployException() {
		HotDeployEvent event = new HotDeployEvent(servletContext,classLoader);
		HotDeployListener mock = createMock(HotDeployListener.class);
		invokeUndeploy(mock, event);
		expectLastCall().andThrow(new HotDeployException("Just for testing!"));
		replay(mock);
		// Initialize HotDeployUtil
		HotDeployUtil.registerListener(mock);
		HotDeployUtil.flushEvents();
		HotDeployUtil.fireUndeployEvent(event);
		verify(mock);
	}
	
	/**
	 * Test wether HotDeployEvent does store the ServletContext and ClassLoader correctly.
	 */
	public void testHotDeployEvent() {
		ServletContext servletContext = createMock(ServletContext.class);
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		HotDeployEvent event = new HotDeployEvent(servletContext, classLoader );
		
		assertEquals(event.getServletContext(), servletContext);
		assertEquals(event.getContextClassLoader(), classLoader);
	}
	
	
	/**
	 * Test wether or not the HotDeployException can be initialized correctly.
	 */
	public void testCreatingHotDeployException() {
		HotDeployException ex;
		Throwable cause = new Throwable();
		
		// default constructor
		ex = new HotDeployException();
		assertNull(ex.getMessage());

		// msg constructor
		ex = new HotDeployException("HotDeployException");
		assertEquals(ex.getMessage(),"HotDeployException");
		
		// cause constructor
		ex = new HotDeployException(cause);
		assertEquals(cause, ex.getCause());

		// msg, cause constructor
		ex = new HotDeployException("HotDeployException", cause);
		assertEquals(ex.getMessage(),"HotDeployException");
		assertEquals(cause, ex.getCause());
	}

	/**
	 * Convience method for listener invocation
	 * @param listenerMock
	 * @param event
	 */
	private void invokeDeploy(HotDeployListener listenerMock, HotDeployEvent event) {
		try {
			listenerMock.invokeDeploy(event);
		} catch (HotDeployException e) {
			fail("Listener mock object couldn't be initialized.");
		}
	}

	/**
	 * Convience method for listener invocation
	 * @param listenerMock
	 * @param event
	 */
	private void invokeUndeploy(HotDeployListener listenerMock, HotDeployEvent event) {
		try {
			listenerMock.invokeUndeploy(event);
		} catch (HotDeployException e) {
			fail("Listener mock object couldn't be initialized.");
		}
	}

}
