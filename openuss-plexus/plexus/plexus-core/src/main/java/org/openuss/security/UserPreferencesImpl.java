// OpenUSS - Open Source University Support System
//
// Attention: Generated code! Do not modify by hand!
// Generated by: HibernateEntityImpl.vsl in in openuss/tools/andromda/templates.
//
package org.openuss.security;

import java.util.Locale;
import java.util.TimeZone;

/**
 * @see org.openuss.security.UserPreferences
 * @author ingo dueppe
 * @version $id $
 */
public class UserPreferencesImpl extends UserPreferencesBase implements UserPreferences {

	private static final long serialVersionUID = 1357311931143018441L;
	
	public UserPreferencesImpl() {
		setTimezone(TimeZone.getDefault().getID());
		setLocale(Locale.getDefault().toString());
		setTheme("plexus");
	}

}