// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.buddylist;

import java.util.*;

/**
 * @see org.openuss.buddylist.Buddy
 */
public class BuddyDaoImpl
    extends org.openuss.buddylist.BuddyDaoBase
{
    /**
     * @see org.openuss.buddylist.BuddyDao#toBuddyInfo(org.openuss.buddylist.Buddy, org.openuss.buddylist.BuddyInfo)
     */
    public void toBuddyInfo(
        org.openuss.buddylist.Buddy sourceEntity,
        org.openuss.buddylist.BuddyInfo targetVO)
    {
        targetVO.setId(sourceEntity.getId());
        LinkedList<String> tags = new LinkedList<String>();
        for(Tag tag : sourceEntity.getTags()){
        	tags.add(tag.getTag());
        }
        Collections.sort(tags);
        targetVO.setTags(tags);
        targetVO.setName(sourceEntity.getUser().getDisplayName());
        targetVO.setUserId(sourceEntity.getUser().getId());
        targetVO.setUserPicture(sourceEntity.getUser().getImageId());
        targetVO.setRequesterId(sourceEntity.getBuddyList().getDomainIdentifier());           
    }


    /**
     * @see org.openuss.buddylist.BuddyDao#toBuddyInfo(org.openuss.buddylist.Buddy)
     */
    public org.openuss.buddylist.BuddyInfo toBuddyInfo(final org.openuss.buddylist.Buddy entity)
    {
    	BuddyInfo buddyInfo = new BuddyInfo();
        this.toBuddyInfo(entity, buddyInfo);
        return buddyInfo;
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.buddylist.Buddy loadBuddyFromBuddyInfo(org.openuss.buddylist.BuddyInfo buddyInfo)
    {
        org.openuss.buddylist.Buddy buddy = this.load(buddyInfo.getId());
        if (buddy == null)
        {
            buddy = org.openuss.buddylist.Buddy.Factory.newInstance();
        }
        return buddy;
    }

    
    /**
     * @see org.openuss.buddylist.BuddyDao#buddyInfoToEntity(org.openuss.buddylist.BuddyInfo)
     */
    public org.openuss.buddylist.Buddy buddyInfoToEntity(org.openuss.buddylist.BuddyInfo buddyInfo)
    {
        // @todo verify behavior of buddyInfoToEntity
        org.openuss.buddylist.Buddy entity = this.loadBuddyFromBuddyInfo(buddyInfo);
        this.buddyInfoToEntity(buddyInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.buddylist.BuddyDao#buddyInfoToEntity(org.openuss.buddylist.BuddyInfo, org.openuss.buddylist.Buddy)
     */
    public void buddyInfoToEntity(
        org.openuss.buddylist.BuddyInfo sourceVO,
        org.openuss.buddylist.Buddy targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of buddyInfoToEntity
        super.buddyInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}