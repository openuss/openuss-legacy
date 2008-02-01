package org.openuss.web.documents;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.openuss.documents.FolderInfo;


public class FolderInfoConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		FolderInfo info = new FolderInfo();
		info.setId(Long.valueOf(value));
		return info;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		if (value instanceof FolderInfo) {
			return String.valueOf(((FolderInfo)value).getId());
		}
		return null;
	}

}
