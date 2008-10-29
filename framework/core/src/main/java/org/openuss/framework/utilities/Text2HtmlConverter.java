package org.openuss.framework.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * This is a Helper-Class for converting Text to HMTL
 * 
 * @author bodum
 * @author Ingo Düppe
 */
public class Text2HtmlConverter {

	/**
	 * Convert Text to HTML. > and < will be convertet to &lt; and &gt; \n will
	 * be &gt;br&lt;
	 * 
	 * If createLinks is true, it will try to find Links and create a
	 * href-Elements
	 * 
	 * @param text
	 *            Text-Files
	 * @return Result
	 */
	public static String toHtml(String text) {
//		// Disarm html entities
//		text = StringUtils.replace(text.trim(), "<", "&lt;");
//		text = StringUtils.replace(text.trim(), ">", "&gt;");

		// Translate line breaks to html breaks
		text = StringUtils.replace(text.trim(), "\n", "<br/>");

		// Create links for URLs
		Matcher matcher = Pattern.compile("(http://|www.)[^\\s<]*").matcher(text);
		StringBuilder result = new StringBuilder(200); 

		int end = 0;

		while (matcher.find()) {
			result.append(text.substring(end, matcher.start()));
			result.append("<a href=\"");
			String linkText = text.substring(matcher.start(), matcher.end());

			if (!linkText.startsWith("http://")) {
				result.append("http://");
			}

			result.append(linkText);
			result.append("\">");
			result.append(linkText);
			result.append("</a>");
			end = matcher.end();
		}

		result.append(text.substring(end));
		text = result.toString();

		return text;
	}

}