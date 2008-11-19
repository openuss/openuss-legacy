package org.openuss.framework.jsfcontrols.converter;

import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.Date;
import java.util.TimeZone;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;

/**
 * Converts TimeStamps to string and vise versa
 * 
 * @author Ingo Dueppe
 * @author Arne Sutor
 */
public class TimestampConverter extends DateTimeConverter {

	private static final Logger logger = Logger.getLogger(TimestampConverter.class);

	// API field
	public static final String CONVERTER_ID = "org.openuss.framework.converter.TimestampConverter";

	private boolean isTimeZoneDefined = false;

	/**
	 * This method uses the inherited method from the myFaces DateTimeConverter
	 * to recieve the <java.util.Date> object and casts it into a
	 * <code>java.sql.Timestamp</code> object. The method will return null, if
	 * the object could not be casted.
	 * 
	 * @param context
	 *            The Faces Context
	 * @param component
	 *            The UIComponent which uses the converter
	 * @param inputString
	 *            The inputString to be converted into a
	 *            <code>java.sql.Timestamp</code> object
	 * @return the converted timestamp object to be passed to the business
	 *         layer.
	 */
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String inputString) {
		logger.debug(inputString);
		// get date object from myFaces DateTimeConverter
		Date date = (Date) super.getAsObject(context, component, inputString);
		if (date != null) {
			return new Timestamp(date.getTime());
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see javax.faces.convert.DateTimeConverter#getTimeZone()
	 */
	@Override
	public TimeZone getTimeZone() {
		if (!isTimeZoneDefined) {
			super.setTimeZone(TimeZone.getDefault());
		}
		return super.getTimeZone();
	}

	/**
	 * {@inheritDoc}
	 * @see javax.faces.convert.DateTimeConverter#setTimeZone(java.util.TimeZone)
	 */
	@Override
	public void setTimeZone(TimeZone timeZone) {
		isTimeZoneDefined = timeZone != null;
		super.setTimeZone(timeZone);
	}
}
