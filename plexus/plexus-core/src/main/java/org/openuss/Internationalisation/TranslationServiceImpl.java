// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.Internationalisation;

import java.util.List;

/**
 * @see org.openuss.Internationalisation.TranslationService
 */
public class TranslationServiceImpl
    extends org.openuss.Internationalisation.TranslationServiceBase
{

    /**
     * @see org.openuss.Internationalisation.TranslationService#addLanguage(java.lang.String)
     */
    protected void handleAddLanguage(java.lang.String languageCode)
        throws java.lang.Exception
    {
        // @todo implement protected void handleAddLanguage(java.lang.String languageCode)
        throw new java.lang.UnsupportedOperationException("org.openuss.Internationalisation.TranslationService.handleAddLanguage(java.lang.String languageCode) Not implemented!");
    }

    /**
     * @see org.openuss.Internationalisation.TranslationService#addTranslationtext(java.lang.String, java.lang.String, java.lang.Long)
     */
    protected void handleAddTranslationtext(java.lang.String subKey, java.lang.String translatedText, java.lang.Long domainIdentifier)
        throws java.lang.Exception
    {
        // @todo implement protected void handleAddTranslationtext(java.lang.String subKey, java.lang.String translatedText, java.lang.Long domainIdentifier)
        throw new java.lang.UnsupportedOperationException("org.openuss.Internationalisation.TranslationService.handleAddTranslationtext(java.lang.String subKey, java.lang.String translatedText, java.lang.Long domainIdentifier) Not implemented!");
    }

    /**
     * @see org.openuss.Internationalisation.TranslationService#removeTranslationtext(java.lang.Long, java.lang.String)
     */
    protected void handleRemoveTranslationtext(java.lang.Long domainIdentifier, java.lang.String subKey)
        throws java.lang.Exception
    {
        // @todo implement protected void handleRemoveTranslationtext(java.lang.Long domainIdentifier, java.lang.String subKey)
        throw new java.lang.UnsupportedOperationException("org.openuss.Internationalisation.TranslationService.handleRemoveTranslationtext(java.lang.Long domainIdentifier, java.lang.String subKey) Not implemented!");
    }

    /**
     * @see org.openuss.Internationalisation.TranslationService#removeLanguage(java.lang.Long)
     */
    protected void handleRemoveLanguage(java.lang.Long languageCode)
        throws java.lang.Exception
    {
        // @todo implement protected void handleRemoveLanguage(java.lang.Long languageCode)
        throw new java.lang.UnsupportedOperationException("org.openuss.Internationalisation.TranslationService.handleRemoveLanguage(java.lang.Long languageCode) Not implemented!");
    }

    /**
     * @see org.openuss.Internationalisation.TranslationService#getTranslation(java.lang.Long, java.lang.String)
     */
    protected java.lang.String handleGetTranslation(java.lang.Long domainIdentifier, java.lang.String subKey)
        throws java.lang.Exception
    {
        // @todo implement protected java.lang.String handleGetTranslation(java.lang.Long domainIdentifier, java.lang.String subKey)
        return null;
    }

	@Override
	protected List handleFindBySubKey(String subKey) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List handleGetLanguages() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void handleUpdateTranslationtext(Long id, String newSubKey,
			String newText) throws Exception {
		// TODO Auto-generated method stub
		
	}

}