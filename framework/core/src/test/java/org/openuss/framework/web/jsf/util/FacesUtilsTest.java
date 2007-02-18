package org.openuss.framework.web.jsf.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.shale.test.base.AbstractJsfTestCase;

public class FacesUtilsTest extends AbstractJsfTestCase {

	public FacesUtilsTest() {
		super("Test of FacesUtils");
	}

	public void testEncodeParameters() {
		// without parameters
		String url = "http://localhost:8080/pages/test.faces";
		Map<String, Object> parameters = new HashMap<String, Object>();
		String paramUrl = FacesUtils.encodeParameters(url, parameters);
		assertEquals(paramUrl, url);
		// with one parameter
		parameters.put("test", "123456");
		paramUrl = FacesUtils.encodeParameters(url, parameters);
		assertEquals(paramUrl, url+"?test=123456");
		// with two parameters
		parameters.put("test2", "123456");
		paramUrl = FacesUtils.encodeParameters(url, parameters);
		assertEquals(paramUrl, url+"?test=123456&test2=123456");
		// with one predefined and two parameters
		url = "http://localhost:8080/pages/test.faces?id=123";
		paramUrl = FacesUtils.encodeParameters(url, parameters);
		assertEquals(paramUrl, url+"&test=123456&test2=123456");

	}
	

}
