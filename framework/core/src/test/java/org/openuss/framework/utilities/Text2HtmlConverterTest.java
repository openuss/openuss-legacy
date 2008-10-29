package org.openuss.framework.utilities;

import org.apache.log4j.Logger;

import junit.framework.TestCase;

/**
 * 
 * @author Ingo Dueppe
 *
 */
public class Text2HtmlConverterTest extends TestCase {
	
	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(Text2HtmlConverterTest.class);

	public void testText2Html() {
		String text = "Hello!\nNew OpenUSS Version under http://www.openuss.de\n and have fun\n\nYour OpenUSS Team";
		String html = Text2HtmlConverter.toHtml(text);
		assertNotNull(html);
		logger.info(html);
		html = Text2HtmlConverter.toHtml(html);
		logger.info(html);
	}
	
}
