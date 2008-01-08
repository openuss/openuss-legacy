package org.openuss.framework.web.jsf.pages;

import org.apache.shale.test.base.AbstractJsfTestCase;

public class PageTest extends AbstractJsfTestCase {

	public PageTest() {
		super("Page test case");
	}

	public void testPage() {
		Page page = new Page("viewId");
		assertEquals(page.getViewId(), "viewId");
	}

	public void testParameters() {
		Page page = new Page("viewId");
		Parameter parameter = new Parameter("parameter");
		page.addParameter(parameter);
		assertEquals(page.getParameters().size(), 1);
		assertEquals(page.getParameters().get(0), parameter);
	}
	
	public void testSecurityConstraint() {
		Page page = new Page("viewId");
		SecurityConstraint constraint = new SecurityConstraint();
		page.addSecurityConstraint(constraint);
		assertEquals(page.getSecurityConstraints().size(), 1);
		assertEquals(page.getSecurityConstraints().get(0), constraint);
	}

}
