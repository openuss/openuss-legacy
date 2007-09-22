package org.openuss.messaging;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.openuss.foundation.AbstractMockDao;

/**
 * 
 * @author Ingo Dueppe
 *
 */
public class MessageJobDaoMock extends AbstractMockDao<MessageJob> implements MessageJobDao {

	public MessageJob create(Date created, boolean sendAsSms, JobState state) {
		return null;
	}

	public Object create(int transform, Date created, boolean sendAsSms, JobState state) {
		return null;
	}

	public MessageJob create(Date created, Message message, boolean sendAsSms, JobState state) {
		return null;
	}

	public Object create(int transform, Date created, Message message, boolean sendAsSms, JobState state) {
		return null;
	}

	public List<MessageJob> findByState(JobState state) {
		return null;
	}

	public List<MessageJob> findByState(String queryString, JobState state) {
		return null;
	}

	public List<?> findByState(int transform, JobState state) {
		return null;
	}

	public List<?> findByState(int transform, String queryString, JobState state) {
		return null;
	}

	public void jobInfoToEntity(JobInfo sourceVO, MessageJob targetEntity, boolean copyIfNull) {
		
	}

	public MessageJob jobInfoToEntity(JobInfo jobInfo) {
		return null;
	}

	@SuppressWarnings("unchecked")
	public void jobInfoToEntityCollection(Collection instances) {
		
	}

	public void toJobInfo(MessageJob sourceEntity, JobInfo targetVO) {
		
	}

	public JobInfo toJobInfo(MessageJob entity) {
		return null;
	}

	@SuppressWarnings("unchecked")
	public void toJobInfoCollection(Collection entities) {
		
	}

}
