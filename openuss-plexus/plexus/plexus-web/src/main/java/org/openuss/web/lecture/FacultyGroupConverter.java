package org.openuss.web.lecture;

import java.util.ArrayList;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.shale.tiger.register.FacesConverter;
import org.openuss.lecture.FacultyGroup;
import org.openuss.lecture.FacultyMember;

@FacesConverter(value = "facultyGroupConverter")
public class FacultyGroupConverter implements Converter  {

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		// parse infos
		int index = value.indexOf(':');
		
		// create faculty group
		FacultyGroup group = new FacultyGroup();
		group.setId(Long.parseLong(value.substring(0,index)));
		group.setName(value.substring(index+1));
		group.setMembers(new ArrayList<FacultyMember>());
		
		return group;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		if (value instanceof FacultyGroup) {
			final FacultyGroup group = (FacultyGroup) value;
			if (group != null) {
				return group.getId()+":"+group.getName();
			}
		} else if (value instanceof String) {
			return (String) value;
		} 
		return null;
	}
}
