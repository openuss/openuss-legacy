package org.openuss.web.themes;

import javax.servlet.ServletContext;
import static org.easymock.EasyMock.*;

import junit.framework.TestCase;

public class ThemeBeanTest extends TestCase {
	
	private Theme theme;
	
	@Override
	public void setUp() {
		theme = new ThemeBean();
	}

	public void testIdProperty() {
		theme.setId("id");
		assertEquals("id", theme.getId());
	}

	public void testNameProperty() {
		theme.setName("Name");
		assertEquals("Name", theme.getName());
	}

	public void testImagesPathProperty() {
		theme.setImagesPath("imagepath");
		assertEquals("imagepath", theme.getImagesPath());
	}

	public void testLayoutProperty() {
		theme.setLayout("layout");
		assertEquals("layout", theme.getLayout());
	}

	public void testStylesheetProperty() {
		theme.setStylesheet("Stylesheet");
		assertEquals("Stylesheet", theme.getStylesheet());
	}
	
	public void testServletContextProperty() {
		ServletContext context = createMock(ServletContext.class);
		theme.setServletContext(context);
		assertEquals(context, theme.getServletContext());
	}
	
	public void testClassLoaderProperty() {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		theme.setClassLoader(cl);
		assertEquals(cl, theme.getClassLoader());
	}
	
	public void testEquals() {
		// init
		Theme themeA = new ThemeBean();
		Theme themeB = new ThemeBean();
		// both ids are null
		assertTrue(themeA.equals(themeB));
		assertFalse(themeA.equals(new Object()));
		themeA.setId("A");
		themeB.setId("B");
		assertFalse(themeA.equals(themeB));
		themeB.setId("A");
		assertTrue(themeA.equals(themeB));
		
	}
	
	public void testHashCode() {
		Theme themeA = new ThemeBean();
		Theme themeB = new ThemeBean();
		assertTrue(themeA.hashCode() != themeB.hashCode());
		themeA.setId("ID");
		themeB.setId("ID");
		assertEquals(themeA.hashCode(), themeB.hashCode());
	}

}
