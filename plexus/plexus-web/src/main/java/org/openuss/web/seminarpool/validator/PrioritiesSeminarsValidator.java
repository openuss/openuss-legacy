/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
package org.openuss.web.seminarpool.validator;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.myfaces.validator.ValidatorBase;
import org.apache.shale.tiger.register.FacesValidator;
import org.openuss.framework.web.jsf.controller.BaseBean;


/**
 * @author mwessendorf (latest modification by $Author: paulsp $) modiefied by PS-Team Seminarplaceallocation
 * @version $Revision: 491777 $ $Date: 2007-01-02 12:27:11 +0100 (Di, 02 J�n 2007) $
 *
@FacesValidator(value = "prioritiesSeminarsValidator")
public class PrioritiesSeminarsValidator extends BaseBean implements Validator {

	/**
	 * <p>The standard converter id for this converter.</p>
	 *
	public static final String 	VALIDATOR_ID 	   = "org.apache.myfaces.validator.Equal";

	/**
	 * <p>The message identifier of the {@link FacesMessage} to be created if
	 * the equal_for check fails.</p>
	 *
	public static final String EQUAL_MESSAGE_ID = "org.apache.myfaces.Equal.INVALID";

	public PrioritiesSeminarsValidator(){
	}

	//the foreign component_id on which the validation is based.
	private String _for= "_id5:seminarpool_create_seminarpool:maxSeminarAllocations";

  // -------------------------------------------------------- ValidatorIF
	public void validate(
		FacesContext facesContext,
		UIComponent uiComponent,
		Object value)
		throws ValidatorException {

	    if (facesContext == null) throw new NullPointerException("facesContext");
        if (uiComponent == null) throw new NullPointerException("uiComponent");

		if (value == null)
		{
		    return;
		}
		
        UIComponent foreignComp = uiComponent.getParent().findComponent(_for);
        if(foreignComp==null)
            throw new FacesException("Unable to find component '" + _for + "' (calling findComponent on component '" + uiComponent.getId() + "')");
        if(false == foreignComp instanceof EditableValueHolder)
            throw new FacesException("Component '" + foreignComp.getId() + "' does not implement EditableValueHolder");
        EditableValueHolder foreignEditableValueHolder = (EditableValueHolder) foreignComp;

        if (foreignEditableValueHolder.isRequired() && foreignEditableValueHolder.getValue()== null ) {
            return;
        }

		Object[] args = {value.toString(),(foreignEditableValueHolder.getValue()==null) ? foreignComp.getId():foreignEditableValueHolder.getValue().toString()};

		if(foreignEditableValueHolder.getValue()==null || foreignEditableValueHolder.getValue().toString().compareTo(value.toString()) < 0  )
        {
            throw new ValidatorException(getFacesMessage(EQUAL_MESSAGE_ID, args));
        }

	}
	// -------------------------------------------------------- StateholderIF

	public Object saveState(FacesContext context) {
		Object[] state = new Object[2];
		state[0] = super.saveState(context);
        state[1] =_for;
		return state;
	}

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        _for = (String)values[1];
    }
	// -------------------------------------------------------- GETTER & SETTER

	/**
	 * @return the foreign component_id, on which a value should be validated
	 *
	public String getFor() {
		return _for;
	}

	/**
	 * @param string the foreign component_id, on which a value should be validated
	 *
	public void setFor(String string) {
		_for = string;
	}
} */