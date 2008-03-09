package org.openuss.web.system;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.apache.shale.tiger.managed.Bean;
import org.apache.shale.tiger.managed.Property;
import org.apache.shale.tiger.managed.Scope;
import org.apache.shale.tiger.view.Prerender;
import org.apache.shale.tiger.view.View;
import org.openuss.calendar.AppointmentTypeInfo;
import org.openuss.internationalisation.TranslationApplicationException;
import org.openuss.internationalisation.TranslationService;
import org.openuss.internationalisation.TranslationTextInfo;
import org.openuss.web.BasePage;
import org.openuss.web.Constants;

/** 
 * Backing Bean for the view secured/system/appointmenttypes.xhtml
 * 
 * @author Ralf Plattfaut
 * @author Gerrit Busse
 *
 */
@Bean(name = "views$secured$system$translateappointmenttypes", scope = Scope.REQUEST)
@View
public class TranslateAppointmentTypesPage extends BasePage{

	private static final Logger logger = Logger.getLogger(TranslateAppointmentTypesPage.class);
	
	@Property(value = "#{appointmentTypeInfo}")
	private AppointmentTypeInfo appointmentTypeInfo;
	
	@Property(value = "#{translationService}")
	TranslationService translationService;
	
	private String chosenLocale;
	
	private String translationString;
	
	@Prerender
	public void prerender() throws Exception {	
		super.prerender();
		try {
			if(chosenLocale == null || chosenLocale.length()==0)
		setChosenLocale("en");
		setTranslationString(translationService.getTranslation(appointmentTypeInfo.getId(), appointmentTypeInfo.getName(), getChosenLocale()));
		} catch (Exception e) {
			setTranslationString("");
		}
	}


	public AppointmentTypeInfo getAppointmentTypeInfo() {
		return appointmentTypeInfo;
	}
	
	public void languageSelected(ValueChangeEvent event){
		try {
			chosenLocale = (String)event.getNewValue();
			logger.info("valuechangeevent thrown");
			this.translationString = translationService.getTranslation(appointmentTypeInfo.getId(), appointmentTypeInfo.getName(), getChosenLocale());
		} catch (TranslationApplicationException e) {
			this.translationString = "";
		}
	}
	
	public String translate(){
		TranslationTextInfo translationTextInfo = new TranslationTextInfo();
		translationTextInfo.setDomainIdentifier(appointmentTypeInfo.getId());
		translationTextInfo.setSubKey(appointmentTypeInfo.getName());
		translationTextInfo.setText(translationString);
		logger.debug("New translation added");
		try {
			translationService.addTranslationText(translationTextInfo, chosenLocale);
		} catch (TranslationApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Constants.OPENUSS4US_CALENDAR_TRANSLATE;
	}

	public void setAppointmentTypeInfo(AppointmentTypeInfo appointmentTypeInfo) {
		this.appointmentTypeInfo = appointmentTypeInfo;
	}

	public TranslationService getTranslationService() {
		return translationService;
	}

	public void setTranslationService(TranslationService translationService) {
		this.translationService = translationService;
	}

	public List<String> getAllLocales(){
		List<String> locales = new ArrayList<String>();
		Application application = FacesContext.getCurrentInstance().getApplication();
		for (Iterator<Locale> iter = application.getSupportedLocales(); iter.hasNext();) {
			Locale locale = (Locale) iter.next();
			locales.add(locale.toString() +" (" + locale.getDisplayName()+")");
		}
		if (locales.size() == 0) {
			Locale defaultLocale = application.getDefaultLocale();
			locales.add(defaultLocale.toString() + " (default: " + defaultLocale.getDisplayName()+")");
		}
		return locales;
	}

	public String getChosenLocale() {
		return chosenLocale;
	}

	public void setChosenLocale(String chosenLocale) {
		logger.debug("setChosenLocale");
		this.chosenLocale = chosenLocale;
	}

	public String getTranslationString() {
		return translationString;
	}

	public void setTranslationString(String translationString) {
		this.translationString = translationString;
	}
}