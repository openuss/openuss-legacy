/**
 JavaServer Faces in Action example code, Copyright (C) 2004 Kito D. Mann.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 **/

package org.openuss.framework.jsfcontrols.converter.taglib;

import java.util.Locale;
import java.util.TimeZone;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.webapp.ConverterTag;

import org.openuss.framework.jsfcontrols.converter.TimestampConverter;

public class TimestampConverterTag extends ConverterTag {

	private static final long serialVersionUID = -4493681241817550157L;

	private String dateStyle;
	private String timeStyle;
	private String timeZone;
	private String locale;
	private String type;

	public TimestampConverterTag() {
		super();
		setConverterId(TimestampConverter.CONVERTER_ID);
	}

	protected Converter createConverter() throws javax.servlet.jsp.JspException {
		TimestampConverter converter = (TimestampConverter) super.createConverter();
		FacesContext context = FacesContext.getCurrentInstance();
		Application app = context.getApplication();

		if (type != null) {
			if (isBindingExpression(type)) {
				converter.setType((String) app.createValueBinding(type).getValue(context));
			} else {
				converter.setType(type);
			}
		}

		if (dateStyle != null) {
			if (isBindingExpression(dateStyle)) {
				converter.setDateStyle((String) app.createValueBinding(dateStyle).getValue(context));
			} else {
				converter.setDateStyle(dateStyle);
			}
		}

		if (timeStyle != null) {
			if (isBindingExpression(timeStyle)) {
				converter.setTimeStyle((String) app.createValueBinding(timeStyle).getValue(context));
			} else {
				converter.setTimeStyle(timeStyle);
			}
		}

		if (locale != null) {
			if (isBindingExpression(locale)) {
				converter.setType((String) app.createValueBinding(locale).getValue(context));
			} else {
				Locale l = new Locale(locale);
				converter.setLocale(l);
			}
		}

		if (timeZone != null) {
			if (isBindingExpression(timeZone)) {
				converter.setType((String) app.createValueBinding(timeZone).getValue(context));
			} else {
				TimeZone tz = TimeZone.getTimeZone(timeZone);
				converter.setTimeZone(tz);
			}
		}

		return converter;
	}

	public void release() {
		super.release();
		type = null;
		locale = null;
		timeStyle = null;
		dateStyle = null;
		timeZone = null;
	}

	/**
	 * Checks whether or not the given object is a binding expression
	 * 
	 * @param value
	 * @return true if it is a binding expression
	 */
	public static boolean isBindingExpression(Object value) {
		return (value != null && value instanceof String && ((String) value).startsWith("#{") && ((String) value)
				.endsWith("}"));
	}

	public String getType() {
		return type;
	}

	public void setType(String style) {
		this.type = style;
	}

	public String getDateStyle() {
		return dateStyle;
	}

	public void setDateStyle(String dateStyle) {
		this.dateStyle = dateStyle;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getTimeStyle() {
		return timeStyle;
	}

	public void setTimeStyle(String timeStyle) {
		this.timeStyle = timeStyle;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
}
