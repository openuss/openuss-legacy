// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.group;
/**
 * @see org.openuss.group.GroupWish
 */
public class GroupWishDaoImpl
    extends org.openuss.group.GroupWishDaoBase
{
    /**
     * @see org.openuss.group.GroupWishDao#toGroupWishInfo(org.openuss.group.GroupWish, org.openuss.group.GroupWishInfo)
     */
    public void toGroupWishInfo(
        org.openuss.group.GroupWish sourceEntity,
        org.openuss.group.GroupWishInfo targetVO)
    {
        // @todo verify behavior of toGroupWishInfo
        super.toGroupWishInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.group.GroupWishDao#toGroupWishInfo(org.openuss.group.GroupWish)
     */
    public org.openuss.group.GroupWishInfo toGroupWishInfo(final org.openuss.group.GroupWish entity)
    {
        // @todo verify behavior of toGroupWishInfo
        return super.toGroupWishInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.group.GroupWish loadGroupWishFromGroupWishInfo(org.openuss.group.GroupWishInfo groupWishInfo)
    {
        // @todo implement loadGroupWishFromGroupWishInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.group.loadGroupWishFromGroupWishInfo(org.openuss.group.GroupWishInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.group.GroupWish groupWish = this.load(groupWishInfo.getId());
        if (groupWish == null)
        {
            groupWish = org.openuss.group.GroupWish.Factory.newInstance();
        }
        return groupWish;
        */
    }

    
    /**
     * @see org.openuss.group.GroupWishDao#groupWishInfoToEntity(org.openuss.group.GroupWishInfo)
     */
    public org.openuss.group.GroupWish groupWishInfoToEntity(org.openuss.group.GroupWishInfo groupWishInfo)
    {
        // @todo verify behavior of groupWishInfoToEntity
        org.openuss.group.GroupWish entity = this.loadGroupWishFromGroupWishInfo(groupWishInfo);
        this.groupWishInfoToEntity(groupWishInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.group.GroupWishDao#groupWishInfoToEntity(org.openuss.group.GroupWishInfo, org.openuss.group.GroupWish)
     */
    public void groupWishInfoToEntity(
        org.openuss.group.GroupWishInfo sourceVO,
        org.openuss.group.GroupWish targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of groupWishInfoToEntity
        super.groupWishInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}