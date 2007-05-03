// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.mailinglist;
/**
 * @see org.openuss.mailinglist.Subscriber
 */
public class SubscriberDaoImpl
    extends org.openuss.mailinglist.SubscriberDaoBase
{
    /**
     * @see org.openuss.mailinglist.SubscriberDao#toSubscriberInfo(org.openuss.mailinglist.Subscriber, org.openuss.mailinglist.SubscriberInfo)
     */
    public void toSubscriberInfo(Subscriber sourceEntity,
        SubscriberInfo targetVO)
    {
        super.toSubscriberInfo(sourceEntity, targetVO);
        targetVO.setDisplayName(sourceEntity.getUser().getDisplayName());
        targetVO.setEmail(sourceEntity.getUser().getEmail());
    }


    /**
     * @see org.openuss.mailinglist.SubscriberDao#toSubscriberInfo(org.openuss.mailinglist.Subscriber)
     */
    public SubscriberInfo toSubscriberInfo(final Subscriber entity)
    {
        return toSubscriberInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private Subscriber loadSubscriberFromSubscriberInfo(SubscriberInfo subscriberInfo)
    {
    	Subscriber subscriber;
    	if (subscriberInfo.getId() == null)
    	{
    		return subscriber = Subscriber.Factory.newInstance();
    	}
        subscriber = this.load(subscriberInfo.getId());
    	if (subscriber == null)
    	{
    		subscriber = Subscriber.Factory.newInstance();
    	}
        return subscriber;
    }

    
    /**
     * @see org.openuss.mailinglist.SubscriberDao#subscriberInfoToEntity(org.openuss.mailinglist.SubscriberInfo)
     */
    public Subscriber subscriberInfoToEntity(SubscriberInfo subscriberInfo)
    {
        Subscriber entity = this.loadSubscriberFromSubscriberInfo(subscriberInfo);
        this.subscriberInfoToEntity(subscriberInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.mailinglist.SubscriberDao#subscriberInfoToEntity(org.openuss.mailinglist.SubscriberInfo, org.openuss.mailinglist.Subscriber)
     */
    public void subscriberInfoToEntity(
        org.openuss.mailinglist.SubscriberInfo sourceVO,
        org.openuss.mailinglist.Subscriber targetEntity,
        boolean copyIfNull)
    {
        super.subscriberInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}