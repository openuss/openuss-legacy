package org.openuss.framework.web.jsf.pages;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;

import org.apache.log4j.Logger;

/**
 * Parameter of a Page
 * @author Ingo Dueppe
 */
public class Parameter {

	private static final Logger logger = Logger.getLogger(Parameter.class);

	private final String name;
	private ValueBinding valueBinding;
	private ValueBinding converterValueBinding;
	private String converterId;
	
	public Parameter(String name) {
		this.name = name;
	}
	
	/**
	 * @param facesContext
	 * @return Object
	 */
	public Object getValueFromModel(FacesContext facesContext) {
		Object value = getValueBinding().getValue(facesContext);
		if (value == null) {
			return null;
		} else {
			Converter converter = getParameterConverter(facesContext);
			if (converter == null) {
				return value;
			} else {
				return converter.getAsString(facesContext, facesContext.getViewRoot(), value);
			}
		}
	}
	
	/**
	 * Get Value from request parameters
	 * @param facesContext
	 * @param requestParameters
	 * @return value object
	 */
	public Object getValueFromRequest(FacesContext facesContext, Map<String, Object> requestParameters) {
		Object parameterValue = requestParameters.get(getName());
		if (parameterValue instanceof String[]) {
			String[] values = (String[]) parameterValue;
			if (values.length > 1) {
				logger.error("Multiple parameters are not supported!");
				throw new IllegalArgumentException("Multiple parameter values are not supported!");
			} else if (values.length == 1) {
				parameterValue = values[0];
			} 
		}
		if (parameterValue instanceof String) {
			String stringValue = (String) parameterValue;
			
			Converter converter = getParameterConverter(facesContext);
			if (converter == null) {
				return stringValue;
			} else {
				return converter.getAsObject(facesContext, facesContext.getViewRoot(), stringValue);
			}
		} else {
			return null;
		}
	}

	private Converter getParameterConverter(FacesContext facesContext) {
		try {
			return getConverter(facesContext);
		} catch (RuntimeException e) {
			logger.error(e);
			return null;
		}
	}
	
	/**
	 * Converter
	 * 
	 * @param facesContext
	 * @return Converter
	 */
	public Converter getConverter(FacesContext facesContext) {
		if (getConverterId() != null) {
			return facesContext.getApplication().createConverter(converterId);
		} else if (getConverterValueBinding() != null) {
			return (Converter) getConverterValueBinding().getValue(facesContext);
		} else if (getValueBinding() == null) {
			return null;
		} else {
			Class<?> type = getValueBinding().getType(facesContext);
			return facesContext.getApplication().createConverter(type);
		}
	}

	public String getConverterId() {
		return converterId;
	}

	public void setConverterId(String converterId) {
		this.converterId = converterId;
	}

	public ValueBinding getConverterValueBinding() {
		return converterValueBinding;
	}

	public void setConverterValueBinding(ValueBinding converterValueBinding) {
		this.converterValueBinding = converterValueBinding;
	}

	public String getName() {
		return name;
	}

	public ValueBinding getValueBinding() {
		return valueBinding;
	}

	public void setValueBinding(ValueBinding valueBinding) {
		this.valueBinding = valueBinding;
	}

}
