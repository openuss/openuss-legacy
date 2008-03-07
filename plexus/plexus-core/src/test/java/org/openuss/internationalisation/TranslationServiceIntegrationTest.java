// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.internationalisation;

import java.util.List;

import org.openuss.TestUtility;
import org.openuss.calendar.AppointmentTypeInfo;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.foundation.DomainObject;
import org.openuss.security.SecurityService;

/**
 * JUnit Test for Spring Hibernate TranslationService class.
 * 
 * @see org.openuss.internationalisation.TranslationService
 */
public class TranslationServiceIntegrationTest extends
		TranslationServiceIntegrationTestBase {
	
	SecurityService securityService;

	public void testLanguageAdministration() {
		try {
			translationService.addLanguage("deDE");
			List<String> languages = translationService.getLanguages();
			assertNotNull(languages);
			assertEquals(1, languages.size());
			assertEquals("deDE", languages.get(0).toString());
			translationService.removeLanguage("deDE");
			List<String> languages2 = translationService.getLanguages();
			assertEquals(0, languages2.size());
		} catch (TranslationApplicationException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	public void testTranslationAdministration() {
		TranslationTextInfo transInfo = new TranslationTextInfo();
		transInfo.setDomainIdentifier(1L);
		transInfo.setSubKey("street");
		transInfo.setText("Stra�e");
		TranslationTextInfo transInfo2 = new TranslationTextInfo();
		transInfo2.setDomainIdentifier(1L);
		transInfo2.setSubKey("description");
		transInfo2.setText("Bezeichnung");
		try {
			translationService.addLanguage("deDE");
			translationService.addTranslationText(transInfo, "deDE");
			translationService.addTranslationText(transInfo2, "deDE");
			String result = translationService.getTranslation(1L, "street", "deDE");
			assertEquals("Stra�e", result);
			String result2 = translationService.getTranslation(1L, "description", "deDE");
			assertEquals("Bezeichnung", result2);
			try {
				translationService.getTranslation(2L, "notFound", "deDE");
				fail();
			} catch (NoTranslationFoundException e) {
				e.printStackTrace();
			}
			// TODO Implement Update of Translation
			List<TranslationTextInfo> newTranslationInfos = translationService.getTranslationTexts("deDE", 1L);
			TranslationTextInfo newTranslationInfo = newTranslationInfos.get(1);
			newTranslationInfo.setText("Beschreibung");
			translationService.updateTranslationText(newTranslationInfo);
			String result3 = translationService.getTranslation(1L, "description", "deDE");
			assertNotNull(result3);
			assertEquals("Beschreibung", result3);
		} catch (TranslationApplicationException e) {
			e.printStackTrace();
			fail();
		}
	}

}