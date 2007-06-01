package org.openuss.web.news;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.shale.tiger.register.FacesConverter;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.news.NewsCategory;

/**
 * Converts a NewsCategory to string and vice versa
 * 
 * @author Ingo Dueppe
 * 
 */
@FacesConverter(value = "newsCategoryConverter")
public class NewsCategoryConverter extends BaseBean implements Converter {
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		int literal = Integer.parseInt(value);
		return NewsCategory.fromInteger(literal);
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		
		if (value instanceof NewsCategory){
			NewsCategory category = (NewsCategory) value;
			return category.getValue().toString();
		}  else if (value instanceof Integer) {
			return String.valueOf(value);
		} else if (value instanceof String) {
			return (String) value;
		}
		return null;
	}
	
}
