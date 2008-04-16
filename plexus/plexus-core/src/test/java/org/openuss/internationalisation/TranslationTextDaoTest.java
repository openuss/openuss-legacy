// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.internationalisation;

import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate TranslationTextDao class.
 * @see org.openuss.internationalisation.TranslationTextDao
 */
public class TranslationTextDaoTest extends TranslationTextDaoTestBase {
	
	private long di = new TestUtility().unique();
	private long id = new TestUtility().unique();
	protected LanguageDao languageDao;
	
	public LanguageDao getLanguageDao() {
		return languageDao;
	}

	public void setLanguageDao(LanguageDao languageDao) {
		this.languageDao = languageDao;
	}	
	public void testTranslationTextDaoCreate() {
		TranslationText translationText = TranslationText.Factory.newInstance();
		translationText.setDomainIdentifier(di);
		translationText.setSubKey("subKey" + di);
		translationText.setText("text" + di);
		Language language = Language.Factory.newInstance();
		language.setId(id);
		language.setLanguageCode("languageCode"+id);
		languageDao.create(language);
		translationText.setLanguage(language);
		assertNull(translationText.getId());
		translationTextDao.create(translationText);
		assertNotNull(translationText.getId());
	}
}
