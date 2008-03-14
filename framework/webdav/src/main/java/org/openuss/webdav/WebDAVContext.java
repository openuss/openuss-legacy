package org.openuss.webdav;

import java.util.Locale;

import org.springframework.web.context.WebApplicationContext;

/**
 * Encapsulates WebDAV context information.
 * For use in WebDAVResources.
 */
public interface WebDAVContext {
	/**
	 * Special value of maxFileSize that allows every file size.
	 */
	public static final long NO_MAX_FILESIZE = -1;

	/**
	 * @return The current (Spring) Web application context. 
	 */
	public WebApplicationContext getWAC();
	/**
	 * @return The maximal allowed file size of uploads.
	 */
	public long getMaxFileSize();
	
	/**
	 * @return The locale used by the current user.
	 */
	public Locale getCurLocale();

	/**
	 * @return Whether umlauts should be converted to surrogate characters if possible
	 */
	public boolean shouldEvadeUmlauts();
	
	/**
	 * @param in The yet-unescaped String
	 * @return A possibly cleared string that uses surrogate character sequences for umlauts.
	 * 			in if !shouldEvadeUmlauts().
	 */
	public String evadeUmlauts(String in);
	
	/**
	 * Checks whether an upload of a specified size is allowed.
	 * 
	 * @param size The file size to check. -1 for undefined (=> result true).
	 * @return true iff uploading a file of size size is allowed.
	 */
	public boolean checkMaxFileSize(long size);
	
	/**
	 * Simple internationalization function
	 * 
	 * @param id The internationalization id to resolve
	 * @return The translated message
	 */
	public String i18n(String id);
	
	/**
	 * Fault-tolerant i18n. This method must not throw any RuntimeExceptions.
	 * 
	 * @param id id The internationalization id to resolve
	 * @param fallback The backup result.
	 * @return The translated message or fallback if it failed.
	 */
	public String i18n(String id, String fallback);
	
	/**
	 * Internationalization function.
	 * 
	 * @param id The internationalization id to resolve
	 * @param params Parameters for the internationalization message.
	 * 			They replace {1},{2} etc. in the translated context.
	 * @return The translated message
	 */
	public String i18n(String id, Object[] params);
	/**
	 * Internationalization function.
	 * 
	 * @param s The internationalization id to resolve
	 * @param params Parameters for the internationalization message.
	 * 			They replace {1},{2} etc. in the translated context.
	 * @param locakle
	 * @return The translated message
	 */
	public String i18n(String s, Object[] params, Locale locale);
}
