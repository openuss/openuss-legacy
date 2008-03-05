// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.internationalisation;


/**
 * JUnit Test for Spring Hibernate LanguageDao class.
 * @see org.openuss.internationalisation.LanguageDao
 */
public class LanguageDaoTest extends LanguageDaoTestBase {
	
	public void testLanguageDaoCreate() {
		Language language = Language.Factory.newInstance();
		assertNull(language.getLanguageCode());
		languageDao.create(language);
		assertNotNull(language.getLanguageCode());
	}
}
