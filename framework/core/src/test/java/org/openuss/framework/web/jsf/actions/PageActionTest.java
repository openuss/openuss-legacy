package org.openuss.framework.web.jsf.actions;

import junit.framework.TestCase;

public class PageActionTest extends TestCase {
	
	private PageAction action;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		action = new PageAction();
	}

	public void testMatches() {
		action.setViewId("/views/test.xhtml");
		assertEquals("/views/test.xhtml",action.getViewId());
		assertTrue(action.matches("/views/test.xhtml"));
		assertFalse(action.matches("/views/test.xhtm"));

		action.setViewId("/views/test*");
		assertTrue(action.matches("/views/test.xhtml"));
		assertTrue(action.matches("/views/test/xyz.xhtml"));
		
		assertFalse(action.matches(""));
	}
	
	public void testGetterAndSetterAction() {
		action.setAction("#{el.expression}");
		assertEquals("#{el.expression}", action.getAction());
		assertEquals("el.expression", action.getFromAction());
		
		action.setAction("${el.expression}");
		assertEquals("el.expression", action.getFromAction());
		
		action.setAction("xyz");
		assertEquals("xyz", action.getFromAction());
	}
	
}
