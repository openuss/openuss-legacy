package org.openuss.framework.web.jsf.pages;

import javax.faces.el.ValueBinding;

import org.apache.shale.test.base.AbstractJsfTestCase;
import org.apache.shale.test.mock.MockValueBinding;

public class ParameterTest extends AbstractJsfTestCase {

	public ParameterTest() {
		super("ParameterTest");
	}

	/**
	 * Test Value From Request
	 */
	@SuppressWarnings("unchecked")
	public void testValueFromRequest() {
		// init parameter
		Parameter parameter = new Parameter("contactId");
		parameter.setConverterId("javax.faces.Long");
		// init mockups
		request.addParameter("contactId", "123456");
		// testing
		Object value = parameter.getValueFromRequest(facesContext, request.getParameterMap());
		assertTrue("Value is not of type long", value instanceof Long);
		long longId = ((Long)value).longValue();
		assertEquals(123456, longId);
	}
	
	/**
	 * Test Missing Request Parameter
	 */
	@SuppressWarnings("unchecked")
	public void testValueFromMissingRequest() {
		Parameter parameter = new Parameter("testId");
		parameter.setValueBinding(application.createValueBinding("#{test.id}"));
		parameter.setConverterId("javax.faces.Long");
		// no parameters
		Object value = parameter.getValueFromRequest(facesContext, request.getParameterMap());
		// testing
		assertNull("expected null because of no parameters", value);
	}
	
	/**
	 * Test Multi Parameter - IllegalArgumentException
	 */
	@SuppressWarnings("unchecked")
	public void testMultiParameter() {
		Parameter parameter = new Parameter("testId");
		request.addParameter("testId", "12345");
		request.addParameter("testId", "54321");
		// no parameters 
		try {
			parameter.getValueFromRequest(facesContext, request.getParameterMap());
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// succeed
		}
	}
	
	/**
	 * Test to fetch data from model
	 */
	public void testValueFromModel() {
		Parameter parameter = new Parameter("testId");
		
		ValueBinding vbTest = application.createValueBinding("#{test}");
		vbTest.setValue(facesContext, new TestBean());
		
		MockValueBinding vb = (MockValueBinding) application.createValueBinding("#{test.id}");
		parameter.setValueBinding(vb);
		
		Object value = parameter.getValueFromModel(facesContext);
		
		assertNotNull("Expected that value is not null", value);
		assertTrue("Expected that the value is a string", value instanceof String);
		assertEquals("Expected the values is of string and equals", value, "123456789");
	}
	
	/**
	 * Test converterValueBinding()
	 */
	public void testConverterValueBinding() {
		Parameter parameter = new Parameter("testId");
		
		application.createValueBinding("#{longConverter}").setValue(facesContext, new TestBeanConverter());
		application.createValueBinding("#{test}").setValue(facesContext, new TestBean());
		
		parameter.setConverterValueBinding(application.createValueBinding("#{longConverter}"));
		parameter.setValueBinding(application.createValueBinding("#{test}"));
		
		Object value = parameter.getValueFromModel(facesContext);
		
		assertEquals("Expected the value is type of String and equal to 123456789.", value, "123456789");
	}
}
