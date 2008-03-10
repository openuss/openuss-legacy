// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.internationalisation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import net.sf.cglib.proxy.Factory;

/**
 * @see org.openuss.internationalisation.TranslationService
 */
public class TranslationServiceImpl extends
		org.openuss.internationalisation.TranslationServiceBase {

	/**
	 * @see org.openuss.internationalisation.TranslationService#addLanguage(org.openuss.internationalisation.LanguageInfo)
	 */
	protected void handleAddLanguage(String languageCode)
			throws java.lang.Exception {
		getLanguageDao().create(languageCode);
	}

	/**
	 * @see org.openuss.internationalisation.TranslationService#addTranslationtext(org.openuss.internationalisation.TranslationTextInfo)
	 */
	protected void handleAddTranslationText(
			org.openuss.internationalisation.TranslationTextInfo translationTextInfo,
			String languageCode) throws java.lang.Exception {

		// get translations to the given language
		Language lang = getLanguageDao().load(languageCode);
		Set<TranslationText> transTexts = lang.getTranslationTexts();
				
		 // are there existing translations for the same domain idfer and 		 subkey?
		 for (TranslationText textIt : transTexts) {
			 if ( (textIt.getDomainIdentifier() ==
			 translationTextInfo.getDomainIdentifier())
			 && (textIt.getSubKey() == translationTextInfo.getSubKey()) ) {
			 throw new TranslationApplicationException("translation is already existing");
			 }
		 }
		getTranslationTextDao().create(
				translationTextInfo.getDomainIdentifier(), lang,
				translationTextInfo.getSubKey(), translationTextInfo.getText());
	}

	/**
	 * @see org.openuss.internationalisation.TranslationService#removeTranslationtext(org.openuss.internationalisation.TranslationTextInfo)
	 */
	protected void handleRemoveTranslationText(
			org.openuss.internationalisation.TranslationTextInfo translationTextInfo)
			throws java.lang.Exception {
		// @todo implement deletion of translationtext
		throw new java.lang.UnsupportedOperationException(
				"org.openuss.internationalisation.TranslationService.handleRemoveTranslationtext(org.openuss.internationalisation.TranslationTextInfo translationTextInfo) Not implemented!");
	}

	/**
	 * @see org.openuss.internationalisation.TranslationService#removeLanguage(org.openuss.internationalisation.LanguageInfo)
	 */
	protected void handleRemoveLanguage(String languageCode)
			throws java.lang.Exception {
		Language language = getLanguageDao().load(languageCode);
		if (language != null) {
			getLanguageDao().remove(languageCode);
		} else {
			throw new TranslationApplicationException(
					"No language definition found with the given name.");
		}
	}

	/**
	 * @see org.openuss.internationalisation.TranslationService#getTranslation(java.lang.Long,
	 *      java.lang.String)
	 */
	protected java.lang.String handleGetTranslation(
			java.lang.Long domainIdentifier, java.lang.String subKey, java.lang.String languageCode)
			throws java.lang.Exception {
		Language lang = getLanguageDao().load(languageCode);
		TranslationTextSearchCriteria criteria = new TranslationTextSearchCriteria();
		criteria.setDomainIdentifier(domainIdentifier);
		criteria.setSubKey(subKey);
		List<TranslationText> transTexts = getTranslationTextDao().findByCriteria(criteria);
		List<TranslationTextInfo> returnList = new ArrayList<TranslationTextInfo>();
		for (TranslationText textIt : transTexts) {
			if (textIt.getLanguage() == lang) returnList.add(getTranslationTextDao().toTranslationTextInfo(textIt));
		}
		if (languageCode == "en") System.out.println("Jetzt ist es " + lang.getLanguageCode());
		if (returnList.size() != 0) {
			return returnList.get(0).getText();
		} else {
			// if no translation is available for the languageCode try english
			if (languageCode != "en") {
				System.out.println("Ist nicht englisch! Es ist: " + lang.getLanguageCode());
				return this.getTranslation(domainIdentifier, subKey, "en");
			// if the languageCode is english, throw an exception
			} else {
				throw new NoTranslationFoundException("No Translation found");
			}
		}

	}

	/**
	 * @see org.openuss.internationalisation.TranslationService#getLanguages()
	 */
	protected java.util.List handleGetLanguages() throws java.lang.Exception {
		List<Language> languages = (List) getLanguageDao().loadAll();
		List<String> languageStrings = new ArrayList<String>();
		for (Language lang : languages) {
			languageStrings.add(lang.getLanguageCode());
		}
		return languageStrings;
	}

	/**
	 * @see org.openuss.internationalisation.TranslationService#updateTranslationtext(org.openuss.internationalisation.TranslationTextInfo)
	 */
	protected void handleUpdateTranslationText(
			org.openuss.internationalisation.TranslationTextInfo translationTextInfo)
			throws java.lang.Exception {
		TranslationText transText = getTranslationTextDao().load(
				translationTextInfo.getId());
		transText.setSubKey(translationTextInfo.getSubKey());
		transText.setText(translationTextInfo.getText());
		// TODO TranslationServiceImpl: handleUpdateTranslationText() :
		// implement update of domainidentifiert and language if necessary
		getTranslationTextDao().update(transText);
	}

	@Override
	protected List handleGetTranslationTexts(String languageCode,
			Long domainIdentifier) throws Exception {
		Language lang = getLanguageDao().load(languageCode);
		TranslationTextSearchCriteria criteria = new TranslationTextSearchCriteria(
				domainIdentifier, null);
		List<TranslationText> transTextsByCriteria = getTranslationTextDao()
				.findByCriteria(criteria);
		List<TranslationTextInfo> returnList = new ArrayList<TranslationTextInfo>();
		for (TranslationText textIt : transTextsByCriteria) {
			if (textIt.getLanguage() == lang) returnList.add(getTranslationTextDao().toTranslationTextInfo(textIt));
		}
		return returnList;
	}
	
	@Override
    protected void handleRemoveTranslationTexts(java.lang.Long domainIdentifier)
    throws java.lang.Exception {
    	TranslationTextSearchCriteria criteria = new TranslationTextSearchCriteria(domainIdentifier, null);
    	List<TranslationText> transTexts = getTranslationTextDao().findByCriteria(criteria);
    	if (transTexts.size() == 0) throw new TranslationApplicationException("Could not remove translations");
    	for (TranslationText textIt : transTexts) {
    		// remove association between 
    		textIt.getLanguage().getTranslationTexts().remove(textIt);
    	}
    	// delete the translations with the given domain identifier
    	getTranslationTextDao().remove(transTexts);
    }
}