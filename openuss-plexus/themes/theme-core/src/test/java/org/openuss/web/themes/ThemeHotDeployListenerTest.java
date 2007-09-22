package org.openuss.web.themes;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reportMatcher;
import static org.easymock.EasyMock.verify;
import static org.easymock.EasyMock.expectLastCall;

import java.io.InputStream;

import javax.servlet.ServletContext;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.easymock.IArgumentMatcher;
import org.openuss.framework.deploy.HotDeployEvent;
import org.openuss.framework.deploy.HotDeployException;

public class ThemeHotDeployListenerTest extends TestCase {

	private ThemeHotDeployListener listener;
	private ThemeManager themeManager;
	private ServletContext servletContext;
	private Theme theme;
	private ClassLoader classLoader;
	
	public void setUp() {
		listener = new ThemeHotDeployListener();
		themeManager = createMock(ThemeManager.class);

		listener.setThemeManager(themeManager);
		
		servletContext = createMock(ServletContext.class);
		classLoader = Thread.currentThread().getContextClassLoader();
		
		theme = createTheme();
	}
	
	public void testThemeDeployEvent() {
		themeManager.registerTheme(eqTheme(theme));

		expect(servletContext.getServletContextName()).andReturn("Mock Servlet Context");
		createClassLoaderAndStreamIntoServletContext();

		HotDeployEvent event = new HotDeployEvent(servletContext, classLoader);

		replay(themeManager);
		replay(servletContext);
		try {
			listener.invokeDeploy(event);
		} catch (HotDeployException e) {
			fail(e.getMessage());
		}
		verify(themeManager);
		verify(servletContext);
	}
	
	public void testThemeDeployEventWithException() {
		themeManager.registerTheme(eqTheme(theme));
		expectLastCall().andThrow(new RuntimeException("- Just testing!"));
		
		expect(servletContext.getServletContextName()).andReturn("Mock Servlet Context");
		createClassLoaderAndStreamIntoServletContext();
		
		HotDeployEvent event = new HotDeployEvent(servletContext, classLoader);
		
		replay(themeManager);
		replay(servletContext);
		try {
			listener.invokeDeploy(event);
			fail();
		} catch (HotDeployException e) {
			// success
		}
		verify(themeManager);
		verify(servletContext);
	}
	
	public void testUnknownDeployEvent() {
		expect(servletContext.getServletContextName()).andReturn("Mock Servlet Context");
		expect(servletContext.getResourceAsStream("/WEB-INF/openuss-theme.xml")).andReturn(null);
		
		HotDeployEvent event = new HotDeployEvent(servletContext, classLoader);

		replay(themeManager);
		replay(servletContext);
		try {
			listener.invokeDeploy(event);
		} catch (HotDeployException e) {
			fail(e.getMessage());
		}
		
		verify(servletContext);
		verify(themeManager);
	}
	
	public void testThemeUndeployEvent() {
		theme = new ThemeBean();
		theme.setServletContext(servletContext);
		theme.setClassLoader(classLoader);

		themeManager.unregisterTheme(eqTheme(theme));

		expect(servletContext.getServletContextName()).andReturn("Mock Servlet Context");

		createClassLoaderAndStreamIntoServletContext();

		HotDeployEvent event = new HotDeployEvent(servletContext, classLoader);

		replay(themeManager);
		replay(servletContext);
		try {
			listener.invokeUndeploy(event);
		} catch (HotDeployException e) {
			fail(e.getMessage());
		}
		verify(themeManager);
		verify(servletContext);
	}

	public void testThemeUndeployEventWithException() {
		createClassLoaderAndStreamIntoServletContext();
		
		HotDeployEvent event = new HotDeployEvent(servletContext, classLoader);
		
		theme = new ThemeBean();
		theme.setServletContext(servletContext);
		theme.setClassLoader(classLoader);
		
		themeManager.unregisterTheme(eqTheme(theme));
		expectLastCall().andThrow(new RuntimeException("- Just testing!"));

		replay(themeManager);
		replay(servletContext); 
		try {
			listener.invokeUndeploy(event);
			fail();
		} catch (HotDeployException e) {
			//success
		}
		verify(themeManager);
		verify(servletContext);
	}


	public void testUnknownUndeployEvent() {
		expect(servletContext.getServletContextName()).andReturn("Mock Servlet Context");
		expect(servletContext.getResourceAsStream("/WEB-INF/openuss-theme.xml")).andReturn(null);
		
		HotDeployEvent event = new HotDeployEvent(servletContext, classLoader);
		
		replay(themeManager);
		replay(servletContext);
		try {
			listener.invokeUndeploy(event);
		} catch (HotDeployException e) {
			fail(e.getMessage());
		}
		
		verify(servletContext);
		verify(themeManager);
	}

	public void testThemeManagerProperty() {
		assertEquals(themeManager, listener.getThemeManager());
		listener.setThemeManager(null);
		assertNull(listener.getThemeManager());
	}
	
	private void createClassLoaderAndStreamIntoServletContext() {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		InputStream stream = cl.getResourceAsStream("test-theme.xml");
		expect(servletContext.getServletContextName()).andReturn("Test Servlet Context").anyTimes();
		expect(servletContext.getResourceAsStream("/WEB-INF/openuss-theme.xml")).andReturn(stream);
	}

	private Theme createTheme() {
		Theme theme = new ThemeBean();
		theme.setId("themeid");
		theme.setName("themename");
		theme.setImagesPath("images-path");
		theme.setLayout("default-layout");
		theme.setStylesheet("stylesheet");
		return theme;
	}

	private static class ThemeMatcher implements IArgumentMatcher {
		private Theme theme;

		public ThemeMatcher(Theme theme) {
			this.theme = theme;
		}

		public void appendTo(StringBuffer buf) {
			buf.append("Theme object is not the same");
		}

		public boolean matches(Object obj) {
			if (!(obj instanceof Theme))
				return false;
			Theme in = (Theme) obj;
			return StringUtils.equals(theme.getId(), in.getId()) 
					&& StringUtils.equals(theme.getName(), in.getName())
					&& StringUtils.equals(theme.getImagesPath(), in.getImagesPath())
					&& StringUtils.equals(theme.getLayout(), in.getLayout())
					&& StringUtils.equals(theme.getStylesheet(), in.getStylesheet())
					&& in.getClassLoader() != null
					&& in.getServletContext() != null;
			
		}
	}
	
	private static Theme eqTheme(Theme theme) {
		reportMatcher(new ThemeMatcher(theme));
		return theme;
	}

}
