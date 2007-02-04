/**
 * <strong>OpenUSS - Plexus</strong>
 * The next generation e-learning plattform
 *
 * University of Muenster (c)
 * 
 * Developing Period 2005 - 2006
 */

package org.openuss.framework.jsfcontrols.converter;

import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import junit.framework.TestCase;

public class TimestampConverterTest extends TestCase {

	private static String SHORT = "short";
	private static String MEDIUM = "medium";
	private static String LONG = "long";
	private static String FULL = "full";

	private static String BOTH = "both";
	private static String TIME = "time";
	private static String DATE = "date";
	
	
	private FacesContext mockFacesContext;
	private UIComponent mockUIComponent;
	private UIViewRoot mockUIRoot;
	
	private String type = BOTH;
	private String dateStyle = FULL;
	private String timeStyle = LONG;
	private Locale locale = new Locale("en", "US");
	private TimeZone ts = TimeZone.getDefault();
	private Timestamp timestamp = new Timestamp((System.currentTimeMillis()/1000)*1000);
	
	public void testConverterSymmetryDateTimeFullStyleLocaleDe() {
		createMockEnvironment();
		
		TimestampConverter converter = setUpConverter(locale, null, type, dateStyle, timeStyle);
				
		setUpMockEnvironment();
		
		//get the reference string
		DateFormat df = DateFormat.getDateTimeInstance(getStyleInformation(dateStyle), getStyleInformation(timeStyle), locale);
		df.setTimeZone(ts);
		String expected = df.format(timestamp);
		
		Timestamp refTime = (Timestamp) converter.getAsObject(mockFacesContext, mockUIComponent, expected);
		
		String actual = converter.getAsString(mockFacesContext, mockUIComponent, refTime);
		
		assertEquals(expected, actual);
		
		verify(mockFacesContext);
	}
	
	public void testConverterForTimestampsWithDifferentTimeZones() {
		
		TimeZone ts_USA = TimeZone.getTimeZone("America/Los_Angeles");
		TimeZone ts_Pakistan = TimeZone.getTimeZone("Asia/Karachi");
		
		testConverterForTimestampWithDifferentTimezones(dateStyle, timeStyle, locale, ts_USA, ts_Pakistan);
		

		
		dateStyle = SHORT;
		timeStyle = SHORT;
		locale = new Locale("de", "DE");
		testConverterForTimestampWithDifferentTimezones(dateStyle, timeStyle, locale, ts_USA, ts_Pakistan);
		
		dateStyle = SHORT;
		timeStyle = FULL;
		testConverterForTimestampWithDifferentTimezones(dateStyle, timeStyle, locale, ts_USA, ts_Pakistan);

		dateStyle = LONG;
		timeStyle = MEDIUM;
		locale = new Locale("en", "US");
		testConverterForTimestampWithDifferentTimezones(dateStyle, timeStyle, locale, ts_USA, ts_Pakistan);
		
		verify(mockFacesContext);
	}

	
	public void testConverterForStringWithDifferentTimeZones() {
		
		TimeZone ts_USA = TimeZone.getTimeZone("America/Los_Angeles");
		
		testConverterForStringWithDifferentTimeZones(locale, dateStyle, timeStyle, ts_USA);
		
		dateStyle = SHORT;
		timeStyle = SHORT;
		locale = new Locale("de", "DE");
		testConverterForStringWithDifferentTimeZones(locale, dateStyle, timeStyle, ts_USA);
		
		dateStyle = SHORT;
		timeStyle = FULL;
		testConverterForStringWithDifferentTimeZones(locale, dateStyle, timeStyle, ts_USA);
		
		dateStyle = LONG;
		timeStyle = MEDIUM;
		locale = new Locale("en", "US");
		testConverterForStringWithDifferentTimeZones(locale, dateStyle, timeStyle, ts_USA);
		
		verify(mockFacesContext);
	}

	private void testConverterForStringWithDifferentTimeZones(Locale locale, String dateStyle, String timeStyle, TimeZone zone) {
		createMockEnvironment();
		
				
		TimestampConverter converter = setUpConverter(locale, zone, type, dateStyle, timeStyle);
		
		setUpMockEnvironment();
		
		
		DateFormat df = DateFormat.getDateTimeInstance(getStyleInformation(dateStyle), getStyleInformation(timeStyle), locale);
		df.setTimeZone(zone);
		String expected = df.format(timestamp);
		
		String actual = converter.getAsString(mockFacesContext, mockUIComponent, timestamp);
		
		assertEquals(expected, actual);
	}
	
	private void testConverterForTimestampWithDifferentTimezones(String dateStyle, String timeStyle, Locale locale, TimeZone zone1, TimeZone zone2) {
		
		createMockEnvironment();
		
		setUpMockEnvironment();
		
		TimestampConverter converter = setUpConverter(locale, null, type, dateStyle, timeStyle);
		
		//get the reference string
		DateFormat df = DateFormat.getDateTimeInstance(getStyleInformation(dateStyle), getStyleInformation(timeStyle), locale);
		
		df.setTimeZone(zone1);
		String timeString = df.format(timestamp);
		
		converter.setTimeZone(zone1);
		Timestamp expected = (Timestamp) converter.getAsObject(mockFacesContext, mockUIComponent, timeString); 
		
		df.setTimeZone(zone2);
		String timeString2 = df.format(timestamp);
		
		converter.setTimeZone(zone2);
		Timestamp actual = (Timestamp) converter.getAsObject(mockFacesContext, mockUIComponent, timeString2);
		
		assertEquals(expected, actual);
	}
	
	private TimestampConverter setUpConverter(Locale locale, TimeZone ts, String type, String dateStyle, String timeStyle) {
		TimestampConverter converter = new TimestampConverter();
		
		if (locale!=null) converter.setLocale(locale);
		if (ts!=null) converter.setTimeZone(ts);
		converter.setType(type);
		if (type.equalsIgnoreCase(TIME))
			converter.setTimeStyle(timeStyle);
		else if (type.equalsIgnoreCase(DATE))
			converter.setDateStyle(dateStyle);
		else {
			converter.setTimeStyle(timeStyle);
			converter.setDateStyle(dateStyle);
		}
		return converter;
	}
	
	private int getStyleInformation(String style) {
		if (style.equals("short")) {
			return DateFormat.SHORT;
		}
		if (style.equals("medium")) {
			return DateFormat.MEDIUM;
		}
		if (style.equals("long")) {
			return DateFormat.LONG;
		}
		if (style.equals("full")) {
			return DateFormat.FULL;
		}
		return 0;
	}
	
	private void createMockEnvironment() {
		mockFacesContext = createMock(FacesContext.class);
		mockUIRoot = createMock(UIViewRoot.class);
		mockUIComponent = createMock(UIComponent.class);
	}

	
	private void setUpMockEnvironment() {		
		replay(mockFacesContext);
		replay(mockUIRoot);
	}
}
