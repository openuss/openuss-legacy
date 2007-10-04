// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.newsletter;

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
		targetVO.setDisplayName(sourceEntity.getUser().getDisplayName());
		targetVO.setEmail(sourceEntity.getUser().getEmail());
		targetVO.setUserId(sourceEntity.getUser().getId());
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
		if (subscriberInfo.getId() == null) {
			return Subscriber.Factory.newInstance();
		}
		subscriber = this.load(subscriberInfo.getId());
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

}