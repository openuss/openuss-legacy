package org.openuss.framework.utilities;

import java.util.Locale;

public class TranslationContext {

	private final static TranslationContext context = new TranslationContext();
	
	private TranslationContext() {};
	
	private ThreadLocal<Locale> locale = new ThreadLocal<Locale>();
	
	public static TranslationContext getCurrentInstance() {
		return context;
	}
	
	public void setLocale(Locale locale) {
		this.locale.set(locale);
	}
 	
	public Locale getLocale() {
		return this.locale.get();
	}
	
	
}
