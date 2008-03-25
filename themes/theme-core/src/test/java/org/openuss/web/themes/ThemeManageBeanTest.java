package org.openuss.web.themes;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;

import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;

import org.apache.shale.test.mock.MockApplication;
import org.apache.shale.test.mock.MockExternalContext;
import org.apache.shale.test.mock.MockFacesContext;
import org.apache.shale.test.mock.MockHttpServletRequest;
import org.apache.shale.test.mock.MockHttpServletResponse;
import org.apache.shale.test.mock.MockHttpSession;
import org.apache.shale.test.mock.MockServletContext;

import junit.framework.TestCase;

public class ThemeManageBeanTest extends TestCase {

	private ThemeManagerBean manager;
	private ServletContext mockServletContext;
	private Theme theme;
	
	public void setUp() {
		manager = new ThemeManagerBean();
		
		MockFacesContext ctx = new MockFacesContext();
		ctx.setApplication(new MockApplication());
		ctx.setExternalContext(new MockExternalContext(
				new MockServletContext(),
				new MockHttpServletRequest(new MockHttpSession()), 
				new MockHttpServletResponse()));
		manager.setFacesContext(ctx);

		
		mockServletContext = createMock(ServletContext.class);
		expect(mockServletContext.getServletContextName()).andReturn("Test Servlet Context").anyTimes();
		theme = new ThemeBean();
		theme.setId("ThemeId");
		theme.setName("ThemeName");
		theme.setServletContext(mockServletContext);
		theme.setClassLoader(Thread.currentThread().getContextClassLoader());
	}
	
	public void testThemeRegistration() {
		replay(mockServletContext);
		manager.registerTheme(theme);
		manager.registerTheme(theme);
		assertEquals(1, manager.getThemes().size());
		manager.unregisterTheme(theme);
		assertEquals(0, manager.getThemes().size());
	}
	
	public void testDefaultThemeRegistration() {
		manager.setDefaultThemeId(theme.getId());
		mockServletContext.setAttribute(ThemeManager.THEME_SCOPEKEY, theme);
		
		replay(mockServletContext);
		try {
			manager.registerTheme(theme);
			fail("IllegalStateException expected!");
		} catch (IllegalStateException e) {
			manager.setServletContext(mockServletContext);
		}
		
		manager.registerTheme(theme);
		assertEquals(1, manager.getThemes().size());
		assertEquals(theme.getId(), manager.getDefaultThemeId());
		assertEquals(theme, manager.getDefaultTheme());
		assertEquals(theme.getId(),manager.getSelectedTheme());
		manager.unregisterTheme(theme);
		assertEquals(0, manager.getThemes().size());
	}
	
	public void testUnregisterThemeWithException() {
		theme = new ThemeBean();
		try {
			manager.unregisterTheme(theme);
			fail("Didn't throw IllegalArgumentException");
		} catch(IllegalArgumentException ex) {
			
		}
	}
	public void testRegisterThemeWithException() {
		theme = new ThemeBean();
		try {
			manager.registerTheme(theme);
			fail("Didn't throw IllegalArgumentException");
		} catch(IllegalArgumentException ex) {
			// success
		}
	}
	
	public void testSelectableTheme() {
		replay(mockServletContext);
		manager.registerTheme(theme);
		assertEquals(1, manager.getSelectableThemes().size());
		SelectItem item = (SelectItem) manager.getSelectableThemes().iterator().next();
		assertEquals(theme.getId(), item.getValue());
		
	}
}
