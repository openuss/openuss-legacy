// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.internationalisation;


/**
 * JUnit Test for Spring Hibernate TranslationTextDao class.
 * @see org.openuss.internationalisation.TranslationTextDao
 */
public class TranslationTextDaoTest extends TranslationTextDaoTestBase {
	
	public void testTranslationTextDaoCreate() {
		TranslationText translationText = TranslationText.Factory.newInstance();
//		translationText.setDomainIdentifier(" ");
		translationText.setSubKey(" ");
		translationText.setText(" ");
		assertNull(translationText.getId());
		translationTextDao.create(translationText);
		assertNotNull(translationText.getId());
	}
}
