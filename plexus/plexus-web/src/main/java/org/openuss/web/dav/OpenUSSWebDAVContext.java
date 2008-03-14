package org.openuss.web.dav;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.openuss.security.SecurityService;
import org.openuss.security.User;
import org.openuss.web.Constants;
import org.openuss.webdav.WebDAVContext;
import org.springframework.web.context.WebApplicationContext;

/**
 * An OpenUSS-specific implementation of WebDAVContext
 */
public class OpenUSSWebDAVContext implements WebDAVContext {
	private static final Logger logger = Logger.getLogger(OpenUSSWebDAVContext.class);
	/**
	 * Special value of maxFileSize that allows every file size.
	 */
	public static final long NO_MAX_FILESIZE = -1;
	protected WebApplicationContext wac;
	protected SecurityService securityService;
	/**
	 * The maximum allowed file size for uploads.
	 */
	protected long maxFileSize;
	
	public OpenUSSWebDAVContext(WebApplicationContext wac, long maxFileSize) {
		this.wac = wac;
		this.maxFileSize = maxFileSize;
		
		securityService = (SecurityService) getWAC().getBean(Constants.SECURITY_SERVICE);
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVContext#getWAC()
	 */
	public WebApplicationContext getWAC() {
		return wac;
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVContext#getMaxFileSize()
	 */
	public long getMaxFileSize() {
		return maxFileSize;
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVContext#checkMaxFileSize(long)
	 */
	public boolean checkMaxFileSize(long size) {
		if (maxFileSize == NO_MAX_FILESIZE) {
			return true;
		}
		if (size == -1) {
			return true;
		}
		
		return size <= maxFileSize;
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVContext#i18n(java.lang.String)
	 */
	public String i18n(String id) {
		return i18n(id, new Object[0]);
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVContext#i18n(java.lang.String, java.lang.Object[])
	 */
	public String i18n(String id, Object[] params) {
		return i18n(id, params, getCurLocale());
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVContext#i18n(java.lang.String, java.lang.Object[], java.util.Locale)
	 */
	public String i18n(String s, Object[] params, Locale locale) {
		return wac.getMessage(s, params, locale);
	}

	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVContext#i18n(java.lang.String, java.lang.String)
	 */
	public String i18n(String id, String fallback) {
		String res;
		
		try {
			res = i18n(id);
		} catch (Throwable th) {
			logger.error("Could not find i18n string \"" + id + "\"", th);
			res = fallback;
		}
		
		return res;
	}
	
	/* (non-Javadoc)
	 * @see org.openuss.webdav.WebDAVContext#getCurLocale()
	 */
	public Locale getCurLocale() {
		User u = getCurrentUser();
		Locale locale = new Locale(u.getLocale());
		
		return locale;
	}

	/**
	 * @return The currently logged in user
	 */
	protected User getCurrentUser() {
		return securityService.getCurrentUser();
	}
}
