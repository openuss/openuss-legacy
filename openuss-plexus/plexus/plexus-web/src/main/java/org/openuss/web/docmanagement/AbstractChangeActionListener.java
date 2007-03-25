package org.openuss.web.docmanagement;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

public abstract class AbstractChangeActionListener{
	
	public Object getObjectFromContext(String expression) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
        ValueBinding valueBinding = facesContext.getApplication().createValueBinding(expression);
        return valueBinding.getValue(facesContext);
	}	
}