package org.openuss.web.wiki;

import junit.framework.TestCase;

public class TestWikiSiteNameValidator extends TestCase {

	public void testValidate() {
		assertTrue(WikiSiteNameValidator.ALLOWED_CHARACTERS_PATTERN.matcher("Positionsbestimmung des E-Learnings").matches());
	}

}
