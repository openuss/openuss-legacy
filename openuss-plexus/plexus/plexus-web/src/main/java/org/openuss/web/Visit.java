package org.openuss.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.application.Application;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.View;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.openuss.security.User;
import org.openuss.web.security.AuthenticationController;

/**
 * Represents a visit of a anonymous user. 
 * @author Ingo Dueppe
 */
@Bean(name="visit",scope=Scope.SESSION)
@View
public class Visit extends BaseBean {

	private static final Logger logger = Logger.getLogger(Visit.class);
	
	private static final long serialVersionUID = -2730284460023650259L;
	
	private List<SelectItem> localeItems;
	private TimeZone timeZone;
	private String locale;

	private List timeZoneItems;
	
	private int enrollmentsPageIndex;
	private int enrollmentsPageCount;
	
	public int getEnrollmentsPageCount() {
		return enrollmentsPageCount;
	}

	public void setEnrollmentsPageCount(int enrollmentsPageCount) {
		this.enrollmentsPageCount = enrollmentsPageCount;
	}

	public int getEnrollmentsPageIndex() {
		return enrollmentsPageIndex;
	}

	public void setEnrollmentsPageIndex(int enrollmentsPageIndex) {
		this.enrollmentsPageIndex = enrollmentsPageIndex;
	}

	public Visit() {
		logger.trace("Visit object created");
	}

	public String getTimeZone() {
		User user = (User) getSessionBean(Constants.USER);
		if (user != null) {
			return user.getTimezone();
		} 
		if (timeZone == null)
			timeZone = TimeZone.getDefault();
		return timeZone.getID();
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = TimeZone.getTimeZone(timeZone);
	}
	
	private UIViewRoot getViewRoot() {
		return getFacesContext().getViewRoot();
	}

	public String getLocale() {
		User user = (User) getSessionBean(AuthenticationController.USER_SESSION_KEY);
		
		if (user != null) {
			locale = user.getLocale();
		}
		if (locale == null) {
			locale = getViewRoot().getLocale().toString();
		}
		
		if (logger.isTraceEnabled()) {
			logger.trace("get locale "+locale);
		}
		if (locale == null) {
			logger.error("locale is still null - set to default en");
			locale = "en";
		}
		return locale;
	}

	public void setLocale(String locale) {
		if (logger.isTraceEnabled())
			logger.trace("set locale to "+locale);
		this.locale = locale;
		
		getViewRoot().setLocale(new Locale(locale));
	}
	
	public List getSupportedLocaleItems() {
		if (localeItems == null) {
			localeItems = new ArrayList<SelectItem>();
			Application application = FacesContext.getCurrentInstance().getApplication();
			for (Iterator iter = application.getSupportedLocales(); iter.hasNext();) {
				Locale locale = (Locale) iter.next();
				SelectItem item = new SelectItem(locale.toString(), locale.getDisplayName());
				localeItems.add(item);
			}
			if (localeItems.size() == 0) {
				Locale defaultLocale = application.getDefaultLocale();
				localeItems.add(new SelectItem(defaultLocale.toString(), defaultLocale.getDisplayName()));
			}
		}
		return localeItems;
	}

	public List getSupportedTimeZoneItems() {
		if (timeZoneItems == null) {
			timeZoneItems = new ArrayList<SelectItem>();
			
			String[] zones = TimeZone.getAvailableIDs();
			for (String zoneId : zones) {
				SelectItem item = new SelectItem(zoneId, zoneId);
				timeZoneItems.add(item);
			}
		}
		return timeZoneItems;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}
}
