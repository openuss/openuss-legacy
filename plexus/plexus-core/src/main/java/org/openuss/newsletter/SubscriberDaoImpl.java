// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.newsletter;

import org.openuss.security.User;

/**
 * @see org.openuss.newsletter.Subscriber
 */
public class SubscriberDaoImpl extends SubscriberDaoBase {
	/**
	 * @see org.openuss.newsletter.SubscriberDao#toSubscriberInfo(org.openuss.newsletter.Subscriber,
	 *      org.openuss.newsletter.SubscriberInfo)
	 */
	public void toSubscriberInfo(Subscriber sourceEntity, SubscriberInfo targetVO) {
		super.toSubscriberInfo(sourceEntity, targetVO);
		targetVO.setDisplayName(sourceEntity.getSubscriberPk().getUser().getDisplayName());
		targetVO.setEmail(sourceEntity.getSubscriberPk().getUser().getEmail());
		targetVO.setUserId(sourceEntity.getSubscriberPk().getUser().getId());
		targetVO.setNewsletterId(sourceEntity.getSubscriberPk().getNewsletter().getId());
	}

	/**
	 * @see org.openuss.newsletter.SubscriberDao#toSubscriberInfo(org.openuss.newsletter.Subscriber)
	 */
	public SubscriberInfo toSubscriberInfo(final Subscriber entity) {
		SubscriberInfo si = new SubscriberInfo();
		toSubscriberInfo(entity, si);
		return si;
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private Subscriber loadSubscriberFromSubscriberInfo(SubscriberInfo subscriberInfo) {
		Subscriber subscriber;
		if (subscriberInfo.getUserId() == null || subscriberInfo.getNewsletterId() == null) {
			return Subscriber.Factory.newInstance();
		}
		SubscriberPK pk = new SubscriberPK();
		pk.setUser(User.Factory.newInstance(subscriberInfo.getUserId()));
		pk.setNewsletter(Newsletter.Factory.newInstance(subscriberInfo.getNewsletterId()));
		subscriber = this.load(pk);
		if (subscriber == null) {
			subscriber = Subscriber.Factory.newInstance();
		}
		return subscriber;
	}

	/**
	 * @see org.openuss.newsletter.SubscriberDao#subscriberInfoToEntity(org.openuss.newsletter.SubscriberInfo)
	 */
	public Subscriber subscriberInfoToEntity(SubscriberInfo subscriberInfo) {
		Subscriber entity = this.loadSubscriberFromSubscriberInfo(subscriberInfo);
		this.subscriberInfoToEntity(subscriberInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.newsletter.SubscriberDao#subscriberInfoToEntity(org.openuss.newsletter.SubscriberInfo,
	 *      org.openuss.newsletter.Subscriber)
	 */
	public void subscriberInfoToEntity(org.openuss.newsletter.SubscriberInfo sourceVO,
			org.openuss.newsletter.Subscriber targetEntity, boolean copyIfNull) {
		super.subscriberInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

    /**
     * @see org.openuss.newsletter.SubscriberDao#findByNewsletter(int, org.openuss.newsletter.Newsletter, boolean)
     */
	@Override
    public java.util.List findByNewsletter(final int transform, final org.openuss.newsletter.Newsletter newsletter, final boolean blocked)
    {
        return this.findByNewsletter(transform, "from org.openuss.newsletter.Subscriber as subscriber where subscriber.subscriberPk.newsletter = ? and subscriber.blocked = ?", newsletter, blocked);
    }
	
    /**
     * @see org.openuss.newsletter.SubscriberDao#findByUser(int, org.openuss.security.User)
     */
	@Override
    public java.util.List findByUser(final int transform, final org.openuss.security.User user)
    {
        return this.findByUser(transform, "from org.openuss.newsletter.Subscriber as subscriber where subscriber.subscriberPk.user = ?", user);
    }
    
    /**
     * @see org.openuss.newsletter.SubscriberDao#findByUserAndNewsletter(int, org.openuss.security.User, org.openuss.newsletter.Newsletter)
     */
	@Override
    public java.lang.Object findByUserAndNewsletter(final int transform, final org.openuss.security.User user, final org.openuss.newsletter.Newsletter newsletter)
    {
        return this.findByUserAndNewsletter(transform, "from org.openuss.newsletter.Subscriber as subscriber where subscriber.subscriberPk.user = ? and subscriber.subscriberPk.newsletter = ?", user, newsletter);
    }

    /**
     * @see org.openuss.newsletter.SubscriberDao#findByNewsletter(int, org.openuss.newsletter.Newsletter)
     */
	@Override
    public java.util.List findByNewsletter(final int transform, final org.openuss.newsletter.Newsletter newsletter)
    {
        return this.findByNewsletter(transform, "from org.openuss.newsletter.Subscriber as subscriber where subscriber.subscriberPk.newsletter = ?", newsletter);
    }
    
}