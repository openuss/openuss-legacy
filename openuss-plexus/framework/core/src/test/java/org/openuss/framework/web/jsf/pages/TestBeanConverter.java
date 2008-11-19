package org.openuss.framework.web.jsf.pages;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * TestBean Converter
 * 
 * @author Ingo Dueppe
 */
public class TestBeanConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		TestBean testBean = new TestBean();
		testBean.setId(Long.parseLong(value));
		return testBean;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		return ((TestBean) value).getId().toString();
	}
	
}