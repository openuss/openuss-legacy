// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.mailinglist;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.openuss.security.User;
import org.openuss.viewtracking.ViewState;

/**
 * @see org.openuss.mailinglist.Mail
 */
public class MailDaoImpl extends MailDaoBase
{
    /**
     * @see org.openuss.mailinglist.MailDao#toMailInfo(org.openuss.mailinglist.Mail, org.openuss.mailinglist.MailInfo)
     */
    public void toMailInfo(Mail sourceEntity, MailInfo targetVO)
    {
        super.toMailInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.mailinglist.MailDao#toMailInfo(org.openuss.mailinglist.Mail)
     */
    public MailInfo toMailInfo(final Mail entity)
    {
        return super.toMailInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private Mail loadMailFromMailInfo(MailInfo mailInfo)
    {
    	Mail mail;
    	if (mailInfo.getId() == null)
    	{
    		mail = org.openuss.mailinglist.Mail.Factory.newInstance();
    		return mail;
    	}        
    	mail = this.load(mailInfo.getId());
    	if (mail == null)
    	{
    		mail = org.openuss.mailinglist.Mail.Factory.newInstance();
    	}        
    	return mail;
    }

    
    /**
     * @see org.openuss.mailinglist.MailDao#mailInfoToEntity(org.openuss.mailinglist.MailInfo)
     */
    public Mail mailInfoToEntity(MailInfo mailInfo)
    {
        Mail entity = this.loadMailFromMailInfo(mailInfo);
        this.mailInfoToEntity(mailInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.mailinglist.MailDao#mailInfoToEntity(org.openuss.mailinglist.MailInfo, org.openuss.mailinglist.Mail)
     */
    public void mailInfoToEntity(MailInfo sourceVO, Mail targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of mailInfoToEntity
        super.mailInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

    /**
     * @see org.openuss.mailinglist.MailDao#toMailDetail(org.openuss.mailinglist.Mail, org.openuss.mailinglist.MailDetail)
     */
    public void toMailDetail(Mail sourceEntity, MailDetail targetVO)
    {
        super.toMailDetail(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.mailinglist.MailDao#toMailDetail(org.openuss.mailinglist.Mail)
     */
    public MailDetail toMailDetail(final Mail entity)
    {
        return super.toMailDetail(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private Mail loadMailFromMailDetail(MailDetail mailDetail)
    {
    	Mail mail;
    	if (mailDetail.getId() == null)
    	{
    		mail = org.openuss.mailinglist.Mail.Factory.newInstance();
    		return mail;
    	}        
    	mail = this.load(mailDetail.getId());
    	if (mail == null)
    	{
    		mail = org.openuss.mailinglist.Mail.Factory.newInstance();
    	}        
    	return mail;
    }

    
    /**
     * @see org.openuss.mailinglist.MailDao#mailDetailToEntity(org.openuss.mailinglist.MailDetail)
     */
    public Mail mailDetailToEntity(MailDetail mailDetail)
    {
        Mail entity = this.loadMailFromMailDetail(mailDetail);
        this.mailDetailToEntity(mailDetail, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.mailinglist.MailDao#mailDetailToEntity(org.openuss.mailinglist.MailDetail, org.openuss.mailinglist.Mail)
     */
    public void mailDetailToEntity(MailDetail sourceVO,
        Mail targetEntity, boolean copyIfNull)
    {
        super.mailDetailToEntity(sourceVO, targetEntity, copyIfNull);
    }


	@Override
	protected List handleLoadAllMailInfos(final MailingList mailingList, final User user) throws Exception {
		// FIXME - Need to extend andromda generator to support association classes
		// Hibernate doesn't support left outer join on object that doesn't have a association.
		// Therefore ViewState should be an associaction class between mailingList and user, but
		// this isn't support by andromda 3.2 yet.
		
		// So the workaround are these two queries and the memory join.
		final String queryString = 
			" SELECT mail.ID,  v.VIEW_STATE " +
			" FROM MAILINGLIST_MAIL as mail LEFT OUTER JOIN TRACKING_VIEWSTATE as v " +
			" ON mail.id = v.DOMAIN_IDENTIFIER and v.USER_IDENTIFIER = :userId " +
			" WHERE mail.MAILING_LIST_FK = :mailingListId ";
		return (List) getHibernateTemplate().execute(new org.springframework.orm.hibernate3.HibernateCallback() {
			public Object doInHibernate(org.hibernate.Session session) throws HibernateException {
				Query queryObject = session.createSQLQuery(queryString);
				queryObject.setParameter("mailingListId", mailingList.getId());
				queryObject.setParameter("userId", user.getId());
				List<Object[]> results = queryObject.list();
				
				Map<Long, ViewState> viewstates = new HashMap<Long, ViewState>();
				for(Object[] obj : results) {
					Long mailId = ((BigInteger) obj[0]).longValue();
					ViewState state = ViewState.NEW;
					if (obj[1] != null) {
						state = ViewState.fromInteger((Integer)obj[1]);
					} 
					viewstates.put(mailId, state);
				}
				
				List<MailInfo> mails = findMailByMailingList(TRANSFORM_MAILINFO, mailingList);
				// inject view state
				for(MailInfo info: mails) {
					info.setViewState(viewstates.get(info.getId()));
				}
				return mails;
			}
		}, true);
	}	


	@Override
	protected List handleLoadSendMailInfos(final MailingList mailingList, final User user) throws Exception {
		// FIXME - Need to extend andromda generator to support association classes
		// Hibernate doesn't support left outer join on object that doesn't have a association.
		// Therefore ViewState should be an associaction class between topic and user, but
		// this isn't support by andromda 3.2 yet.
		
		// So the workaround are these two queries and the memory join.
		final String queryString = 
			" SELECT mail.ID,  v.VIEW_STATE " +
			" FROM MAILINGLIST_MAIL as mail LEFT OUTER JOIN TRACKING_VIEWSTATE as v " +
			" ON mail.id = v.DOMAIN_IDENTIFIER and v.USER_IDENTIFIER = :userId " +
			" WHERE mail.MAILING_LIST_FK = :mailingListId ";
		return (List) getHibernateTemplate().execute(new org.springframework.orm.hibernate3.HibernateCallback() {
			public Object doInHibernate(org.hibernate.Session session) throws HibernateException {
				Query queryObject = session.createSQLQuery(queryString);
				queryObject.setParameter("mailingListId", mailingList.getId());
				queryObject.setParameter("userId", user.getId());
				List<Object[]> results = queryObject.list();
				
				Map<Long, ViewState> viewstates = new HashMap<Long, ViewState>();
				for(Object[] obj : results) {
					Long mailId = ((BigInteger) obj[0]).longValue();
					ViewState state = ViewState.NEW;
					if (obj[1] != null) {
						state = ViewState.fromInteger((Integer)obj[1]);
					} 
					viewstates.put(mailId, state);
				}
				
				List<MailInfo> mails = findMailByMailingListAndStatus(TRANSFORM_MAILINFO, mailingList);
				// inject view state
				for(MailInfo info: mails) {
					info.setViewState(viewstates.get(info.getId()));
				}
				return mails;
			}
		}, true);
	}
	
    public java.util.List findNotDeletedByStatus(final int transform, final org.openuss.mailinglist.MailingList mailingList)
    {
        return this.findNotDeletedByStatus(transform, "from org.openuss.mailinglist.Mail as m where m.mailingList = :mailingList and m.status = org.openuss.mailinglist.MailingStatus.SEND and m.status <> org.openuss.mailinglist.MailingStatus.DELETED", mailingList);
    }

    public java.util.List findByMailingListWithoutDeleted(final int transform, final org.openuss.mailinglist.MailingList mailingList)
    {
        return this.findByMailingListWithoutDeleted(transform, "from org.openuss.mailinglist.Mail as m where m.mailingList = :mailingList and m.status <> org.openuss.mailinglist.MailingStatus.DELETED", mailingList);
    }


	@Override
	protected Mail handleFindMailByMessageId(Long messageId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}