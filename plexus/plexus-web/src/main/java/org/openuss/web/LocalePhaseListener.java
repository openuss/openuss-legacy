package org.openuss.web;

import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.log4j.Logger;
import org.openuss.framework.utilities.TranslationContext;

/**
 * @author Ralf Plattfaut
 * @author Gerrit Busse
 * @author Ingo Düppe
 */
public class LocalePhaseListener implements PhaseListener {

	private static final Logger logger = Logger.getLogger(LocalePhaseListener.class);

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
	
	ValueBinding bindLocale;
	
	public LocalePhaseListener(){
	}

	/**
	 * Assign locale to TranslationContext
	 */
	public void afterPhase(PhaseEvent event) {
		bindLocale = FacesContext.getCurrentInstance().getApplication().createValueBinding("#{visit}");
		Visit visit = (Visit) bindLocale.getValue(event.getFacesContext());
		Locale locale = new Locale(visit.getLocale());
		TranslationContext.getCurrentInstance().setLocale(locale);
	}

	public void beforePhase(PhaseEvent event) {
		// do nothing
	}
}