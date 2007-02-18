package org.openuss.framework.web.jsf.pages;

import org.apache.commons.lang.StringUtils;
import org.apache.shale.test.base.AbstractJsfTestCase;

/**
 * Test case for Pages class.
 * this class uses /pages/test.page.xml and /WEB-INF/pages.xml
 *
 * @author Ingo Dueppe
 */
public class PagesTest extends AbstractJsfTestCase {

	private static final String EL_TEST_CONVERTER = "#{testConverter}";
	private static final String EL_TEST_BEAN2 = "#{testBean2}";
	private static final String EL_TEST_BEAN = "#{testBean}";
	private static final String EL_TEST = "#{test}";
	
	private static final String TEST_PAGE_VIEW_ID = "/pages/test.xhtml";

	public PagesTest() {
		super("Test of org.openuss.framework.web.jsf.pages.Pages");
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		registerManagedBeans();
	}

	public void testGetPage() {
		assertNull(Pages.instance().getPage(null));
		Page page = Pages.instance().getPage(TEST_PAGE_VIEW_ID);
		assertNotNull(page);
		assertEquals(page.getViewId(),TEST_PAGE_VIEW_ID);
	}
	
	public void testCreatePage() {
		Page page = Pages.instance().getPage(TEST_PAGE_VIEW_ID);
		assertEquals("expect page with 2 parameters ",page.getParameters().size(),2);
		for (Parameter parameter: page.getParameters()) {
			if (StringUtils.equals("testId", parameter.getName())) {
				assertEquals(parameter.getName(),"testId");
				assertEquals(parameter.getConverterId(),"javax.faces.Long");
				assertNotNull(parameter.getValueBinding());
				assertEquals(parameter.getValueBinding().getExpressionString(), "#{test.id}");
				assertNull("Expect that parameter does not have a converter value binding", parameter.getConverterValueBinding());
			} else if (StringUtils.equals("test",parameter.getName())) {
				assertEquals(parameter.getName(),"test");
				assertNull("Expect that parameter does not have a convertedId", parameter.getConverterId());
				assertNotNull(parameter.getValueBinding());
				assertEquals(parameter.getValueBinding().getExpressionString(), EL_TEST_BEAN);
				assertNotNull(parameter.getConverterValueBinding());
				assertEquals(parameter.getConverterValueBinding().getExpressionString(),EL_TEST_CONVERTER);
			} else {
				fail("unexpected parameter");
			}
		}
		assertNotNull(page);
	}
	
	public void testApplyRequestParameterValues() {
		// init test 
		facesContext.getViewRoot().setViewId("/pages/test.xhtml");
		
		request.addParameter("testId", "987654321");
		request.addParameter("test","123456789");
		request.addParameter("test2","123456789");
		externalContext.setRequestParameterMap(request.getParameterMap());
		// perfom test
		Pages.instance().applyRequestParameterValues(facesContext);
		// check
		assertEquals(getTestBean(EL_TEST).getId().longValue(), 987654321L);
		// check testBean parameter of /pages/test.page.xml
		assertEquals(getTestBean(EL_TEST_BEAN).getId().longValue(), 123456789L);
		// check testBeam parameter of /WEB-INF/pages.xml
		assertEquals(getTestBean(EL_TEST_BEAN2).getId().longValue(), 123456789L);
	}

	public void testEncodePageParameters() {
		String url = "http://localhost:8080/pages/test.faces";
		registerManagedBeans();
		
		url = Pages.instance().encodePageParameters(facesContext, url, "/pages/test.xhtml");
		assertEquals(url,"http://localhost:8080/pages/test.faces?testId=1&test=1&test2=2&test3=3");
	}

	private void registerBean(String expression, Object bean) {
		application.createValueBinding(expression).setValue(facesContext, bean);
	}
	
	private TestBean getTestBean(String expression) {
		return (TestBean) getBean(expression);
	}
	
	private Object getBean(String expression) {
		return application.createValueBinding(expression).getValue(facesContext);
	}

	private void registerManagedBeans() {
		registerBean(EL_TEST, new TestBean(1L));
		registerBean(EL_TEST_BEAN, new TestBean(1L));
		registerBean(EL_TEST_BEAN2, new TestBean(2L));
		registerBean("#{testBean3}", new TestBean(3L));
		registerBean(EL_TEST_CONVERTER, new TestBeanConverter());
	}
	
}
