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
    public void toSubscriberInfo(
        org.openuss.mailinglist.Subscriber sourceEntity,
        org.openuss.mailinglist.SubscriberInfo targetVO)
    {
        // @todo verify behavior of toSubscriberInfo
        super.toSubscriberInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.mailinglist.SubscriberDao#toSubscriberInfo(org.openuss.mailinglist.Subscriber)
     */
    public org.openuss.mailinglist.SubscriberInfo toSubscriberInfo(final org.openuss.mailinglist.Subscriber entity)
    {
        // @todo verify behavior of toSubscriberInfo
        return super.toSubscriberInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.mailinglist.Subscriber loadSubscriberFromSubscriberInfo(org.openuss.mailinglist.SubscriberInfo subscriberInfo)
    {
        // @todo implement loadSubscriberFromSubscriberInfo
        throw new java.lang.UnsupportedOperationException("org.openuss.mailinglist.loadSubscriberFromSubscriberInfo(org.openuss.mailinglist.SubscriberInfo) not yet implemented.");

        /* A typical implementation looks like this:
        org.openuss.mailinglist.Subscriber subscriber = this.load(subscriberInfo.getId());
        if (subscriber == null)
        {
            subscriber = org.openuss.mailinglist.Subscriber.Factory.newInstance();
        }
        return subscriber;
        */
    }

    
    /**
     * @see org.openuss.mailinglist.SubscriberDao#subscriberInfoToEntity(org.openuss.mailinglist.SubscriberInfo)
     */
    public org.openuss.mailinglist.Subscriber subscriberInfoToEntity(org.openuss.mailinglist.SubscriberInfo subscriberInfo)
    {
        // @todo verify behavior of subscriberInfoToEntity
        org.openuss.mailinglist.Subscriber entity = this.loadSubscriberFromSubscriberInfo(subscriberInfo);
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
        // @todo verify behavior of subscriberInfoToEntity
        super.subscriberInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}