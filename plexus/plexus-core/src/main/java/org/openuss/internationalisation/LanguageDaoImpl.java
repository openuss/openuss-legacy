// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.internationalisation;
/**
 * @see org.openuss.internationalisation.Language
 */
public class LanguageDaoImpl
    extends org.openuss.internationalisation.LanguageDaoBase
{
    /**
     * @see org.openuss.internationalisation.LanguageDao#toLanguageInfo(org.openuss.internationalisation.Language, org.openuss.internationalisation.LanguageInfo)
     */
    public void toLanguageInfo(
        org.openuss.internationalisation.Language sourceEntity,
        org.openuss.internationalisation.LanguageInfo targetVO)
    {
        // @todo verify behavior of toLanguageInfo
        super.toLanguageInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.internationalisation.LanguageDao#toLanguageInfo(org.openuss.internationalisation.Language)
     */
    public org.openuss.internationalisation.LanguageInfo toLanguageInfo(final org.openuss.internationalisation.Language entity)
    {
        // @todo verify behavior of toLanguageInfo
        return super.toLanguageInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.internationalisation.Language loadLanguageFromLanguageInfo(org.openuss.internationalisation.LanguageInfo languageInfo)
    {
        // @todo implement loadLanguageFromLanguageInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.internationalisation.loadLanguageFromLanguageInfo(org.openuss.internationalisation.LanguageInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.internationalisation.Language language = this.load(languageInfo.getId());
        if (language == null)
        {
            language = org.openuss.internationalisation.Language.Factory.newInstance();
        }
        return language;
        */
    }

    
    /**
     * @see org.openuss.internationalisation.LanguageDao#languageInfoToEntity(org.openuss.internationalisation.LanguageInfo)
     */
    public org.openuss.internationalisation.Language languageInfoToEntity(org.openuss.internationalisation.LanguageInfo languageInfo)
    {
        // @todo verify behavior of languageInfoToEntity
        org.openuss.internationalisation.Language entity = this.loadLanguageFromLanguageInfo(languageInfo);
        this.languageInfoToEntity(languageInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.internationalisation.LanguageDao#languageInfoToEntity(org.openuss.internationalisation.LanguageInfo, org.openuss.internationalisation.Language)
     */
    public void languageInfoToEntity(
        org.openuss.internationalisation.LanguageInfo sourceVO,
        org.openuss.internationalisation.Language targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of languageInfoToEntity
        super.languageInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}