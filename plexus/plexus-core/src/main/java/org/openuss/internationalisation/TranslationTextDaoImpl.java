// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.internationalisation;
/**
 * @see org.openuss.internationalisation.TranslationText
 */
public class TranslationTextDaoImpl
    extends org.openuss.internationalisation.TranslationTextDaoBase
{
    /**
     * @see org.openuss.internationalisation.TranslationTextDao#toTranslationTextInfo(org.openuss.internationalisation.TranslationText, org.openuss.internationalisation.TranslationTextInfo)
     */
    public void toTranslationTextInfo(
        org.openuss.internationalisation.TranslationText sourceEntity,
        org.openuss.internationalisation.TranslationTextInfo targetVO)
    {
        // @todo verify behavior of toTranslationTextInfo
        super.toTranslationTextInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.internationalisation.TranslationTextDao#toTranslationTextInfo(org.openuss.internationalisation.TranslationText)
     */
    public org.openuss.internationalisation.TranslationTextInfo toTranslationTextInfo(final org.openuss.internationalisation.TranslationText entity)
    {
        // @todo verify behavior of toTranslationTextInfo
        return super.toTranslationTextInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.internationalisation.TranslationText loadTranslationTextFromTranslationTextInfo(org.openuss.internationalisation.TranslationTextInfo translationTextInfo)
    {
        // @todo implement loadTranslationTextFromTranslationTextInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.internationalisation.loadTranslationTextFromTranslationTextInfo(org.openuss.internationalisation.TranslationTextInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.internationalisation.TranslationText translationText = this.load(translationTextInfo.getId());
        if (translationText == null)
        {
            translationText = org.openuss.internationalisation.TranslationText.Factory.newInstance();
        }
        return translationText;
        */
    }

    
    /**
     * @see org.openuss.internationalisation.TranslationTextDao#translationTextInfoToEntity(org.openuss.internationalisation.TranslationTextInfo)
     */
    public org.openuss.internationalisation.TranslationText translationTextInfoToEntity(org.openuss.internationalisation.TranslationTextInfo translationTextInfo)
    {
        // @todo verify behavior of translationTextInfoToEntity
        org.openuss.internationalisation.TranslationText entity = this.loadTranslationTextFromTranslationTextInfo(translationTextInfo);
        this.translationTextInfoToEntity(translationTextInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.internationalisation.TranslationTextDao#translationTextInfoToEntity(org.openuss.internationalisation.TranslationTextInfo, org.openuss.internationalisation.TranslationText)
     */
    public void translationTextInfoToEntity(
        org.openuss.internationalisation.TranslationTextInfo sourceVO,
        org.openuss.internationalisation.TranslationText targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of translationTextInfoToEntity
        super.translationTextInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}