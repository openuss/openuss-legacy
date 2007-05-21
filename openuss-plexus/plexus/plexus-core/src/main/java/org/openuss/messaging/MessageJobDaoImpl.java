// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.messaging;

/**
 * @see org.openuss.messaging.MessageJob
 * @author ingo dueppe
 */
public class MessageJobDaoImpl extends MessageJobDaoBase {
	/**
	 * @see org.openuss.messaging.MessageJobDao#toJobState(org.openuss.messaging.MessageJob,
	 *      org.openuss.messaging.JobInfo)
	 */
	public void toJobInfo(MessageJob sourceEntity, JobInfo targetVO) {
		super.toJobInfo(sourceEntity, targetVO);
		targetVO.setTosend(sourceEntity.getToSend());
		targetVO.setSend(sourceEntity.getSend());
		targetVO.setError(sourceEntity.getError());
		targetVO.setTotal(sourceEntity.getRecipients().size());		
	}

	/**
	 * @see org.openuss.messaging.MessageJobDao#toJobState(org.openuss.messaging.MessageJob)
	 */
	public JobInfo toJobInfo(final MessageJob entity) {
		return super.toJobInfo(entity);
	}

	/**
	 * @see org.openuss.messaging.MessageJobDao#jobStateToEntity(org.openuss.messaging.JobInfo)
	 */
	public MessageJob jobInfoToEntity(JobInfo jobState) {
		throw new java.lang.UnsupportedOperationException("Converting JobState to MessageJob is not supported.");
	}

	/**
	 * @see org.openuss.messaging.MessageJobDao#jobStateToEntity(org.openuss.messaging.JobInfo,
	 *      org.openuss.messaging.MessageJob)
	 */
	public void jobInfoToEntity(JobInfo sourceVO, MessageJob targetEntity, boolean copyIfNull) {
		throw new java.lang.UnsupportedOperationException("Converting JobState to MessageJob is not supported.");
	}

}