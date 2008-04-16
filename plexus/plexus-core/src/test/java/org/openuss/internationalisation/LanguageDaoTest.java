// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.internationalisation;

import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate LanguageDao class.
 * @see org.openuss.internationalisation.LanguageDao
 */
public class LanguageDaoTest extends LanguageDaoTestBase {
	
	private long id = new TestUtility().unique();
	
	public void testLanguageDaoCreate() {
		Language language = Language.Factory.newInstance();
		language.setId(id);
		assertNull(language.getLanguageCode());
		language.setLanguageCode("languageCode"+id);
		languageDao.create(language);
		assertNotNull(language.getLanguageCode());
	}
}
