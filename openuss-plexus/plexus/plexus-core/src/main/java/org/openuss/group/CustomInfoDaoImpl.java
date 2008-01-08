// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.group;
/**
 * @see org.openuss.group.CustomInfo
 */
public class CustomInfoDaoImpl
    extends org.openuss.group.CustomInfoDaoBase
{
    /**
     * @see org.openuss.group.CustomInfoDao#toCustomInfoInfo(org.openuss.group.CustomInfo, org.openuss.group.CustomInfoInfo)
     */
    public void toCustomInfoInfo(
        org.openuss.group.CustomInfo sourceEntity,
        org.openuss.group.CustomInfoInfo targetVO)
    {
        super.toCustomInfoInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.group.CustomInfoDao#toCustomInfoInfo(org.openuss.group.CustomInfo)
     */
    public org.openuss.group.CustomInfoInfo toCustomInfoInfo(final org.openuss.group.CustomInfo entity)
    {
    	CustomInfoInfo ci = new CustomInfoInfo();
    	toCustomInfoInfo(entity, ci);
    	return ci;
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.group.CustomInfo loadCustomInfoFromCustomInfoInfo(org.openuss.group.CustomInfoInfo customInfoInfo)
    {
        if (customInfoInfo==null||customInfoInfo.getId()==null){
        	return CustomInfo.Factory.newInstance();
        }
    	return this.load(customInfoInfo.getId());
    }

    
    /**
     * @see org.openuss.group.CustomInfoDao#customInfoInfoToEntity(org.openuss.group.CustomInfoInfo)
     */
    public org.openuss.group.CustomInfo customInfoInfoToEntity(org.openuss.group.CustomInfoInfo customInfoInfo)
    {
        CustomInfo entity = this.loadCustomInfoFromCustomInfoInfo(customInfoInfo);
        if (entity == null){
        	entity=CustomInfo.Factory.newInstance();
        }
        this.customInfoInfoToEntity(customInfoInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.group.CustomInfoDao#customInfoInfoToEntity(org.openuss.group.CustomInfoInfo, org.openuss.group.CustomInfo)
     */
    public void customInfoInfoToEntity(
        org.openuss.group.CustomInfoInfo sourceVO,
        org.openuss.group.CustomInfo targetEntity,
        boolean copyIfNull)
    {
        super.customInfoInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}