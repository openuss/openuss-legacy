// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.buddylist;
/**
 * @see org.openuss.buddylist.BuddyList
 */
public class BuddyListDaoImpl
    extends org.openuss.buddylist.BuddyListDaoBase
{
    /**
     * @see org.openuss.buddylist.BuddyListDao#toBuddyListInfo(org.openuss.buddylist.BuddyList, org.openuss.buddylist.BuddyListInfo)
     */
    public void toBuddyListInfo(
        org.openuss.buddylist.BuddyList sourceEntity,
        org.openuss.buddylist.BuddyListInfo targetVO)
    {
        super.toBuddyListInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.buddylist.BuddyListDao#toBuddyListInfo(org.openuss.buddylist.BuddyList)
     */
    public org.openuss.buddylist.BuddyListInfo toBuddyListInfo(final org.openuss.buddylist.BuddyList entity)
    {
        // @todo verify behavior of toBuddyListInfo
        return super.toBuddyListInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.buddylist.BuddyList loadBuddyListFromBuddyListInfo(org.openuss.buddylist.BuddyListInfo buddyListInfo)
    {
        org.openuss.buddylist.BuddyList buddyList = this.load(buddyListInfo.getId());
        if (buddyList == null)
        {
            buddyList = org.openuss.buddylist.BuddyList.Factory.newInstance();
        }
        return buddyList;
    }

    
    /**
     * @see org.openuss.buddylist.BuddyListDao#buddyListInfoToEntity(org.openuss.buddylist.BuddyListInfo)
     */
    public org.openuss.buddylist.BuddyList buddyListInfoToEntity(org.openuss.buddylist.BuddyListInfo buddyListInfo)
    {
        // @todo verify behavior of buddyListInfoToEntity
        org.openuss.buddylist.BuddyList entity = this.loadBuddyListFromBuddyListInfo(buddyListInfo);
        this.buddyListInfoToEntity(buddyListInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.buddylist.BuddyListDao#buddyListInfoToEntity(org.openuss.buddylist.BuddyListInfo, org.openuss.buddylist.BuddyList)
     */
    public void buddyListInfoToEntity(
        org.openuss.buddylist.BuddyListInfo sourceVO,
        org.openuss.buddylist.BuddyList targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of buddyListInfoToEntity
        super.buddyListInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}