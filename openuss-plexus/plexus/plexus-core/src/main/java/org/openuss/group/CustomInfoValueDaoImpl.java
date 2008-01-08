// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.group;
/**
 * @see org.openuss.group.CustomInfoValue
 */
public class CustomInfoValueDaoImpl
    extends org.openuss.group.CustomInfoValueDaoBase
{
    /**
     * @see org.openuss.group.CustomInfoValueDao#toCustomInfoValueInfo(org.openuss.group.CustomInfoValue, org.openuss.group.CustomInfoValueInfo)
     */
    public void toCustomInfoValueInfo(
        org.openuss.group.CustomInfoValue sourceEntity,
        org.openuss.group.CustomInfoValueInfo targetVO)
    {
        super.toCustomInfoValueInfo(sourceEntity, targetVO);
        targetVO.setCustomNameId(sourceEntity.getCustomInfo().getId());
    }


    /**
     * @see org.openuss.group.CustomInfoValueDao#toCustomInfoValueInfo(org.openuss.group.CustomInfoValue)
     */
    public org.openuss.group.CustomInfoValueInfo toCustomInfoValueInfo(final org.openuss.group.CustomInfoValue entity)
    {
        CustomInfoValueInfo ci = new CustomInfoValueInfo();
        toCustomInfoValueInfo(entity, ci);
    	return ci;
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.group.CustomInfoValue loadCustomInfoValueFromCustomInfoValueInfo(org.openuss.group.CustomInfoValueInfo customInfoValueInfo)
    {
        if (customInfoValueInfo==null||customInfoValueInfo.getId()==null){
        	return CustomInfoValue.Factory.newInstance();        	
        }
    	return this.load(customInfoValueInfo.getId());
    }

    
    /**
     * @see org.openuss.group.CustomInfoValueDao#customInfoValueInfoToEntity(org.openuss.group.CustomInfoValueInfo)
     */
    public org.openuss.group.CustomInfoValue customInfoValueInfoToEntity(org.openuss.group.CustomInfoValueInfo customInfoValueInfo)
    {
        
    	CustomInfoValue entity = this.loadCustomInfoValueFromCustomInfoValueInfo(customInfoValueInfo);
    	if (entity==null){
    		entity=CustomInfoValue.Factory.newInstance();
    	}
        this.customInfoValueInfoToEntity(customInfoValueInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.group.CustomInfoValueDao#customInfoValueInfoToEntity(org.openuss.group.CustomInfoValueInfo, org.openuss.group.CustomInfoValue)
     */
    public void customInfoValueInfoToEntity(
        org.openuss.group.CustomInfoValueInfo sourceVO,
        org.openuss.group.CustomInfoValue targetEntity,
        boolean copyIfNull)
    {
        super.customInfoValueInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}