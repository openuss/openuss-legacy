// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.newsletter;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.openuss.security.User;
import org.openuss.viewtracking.ViewState;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * @see org.openuss.newsletter.Mail
 */
public class MailDaoImpl extends MailDaoBase {

	private static final String SQL_SELECT_MAIL_VIEW_STATE_BY_NEWSLETTER_ID_AND_USER_ID = " SELECT mail.ID,"
			+ " v.VIEW_STATE FROM NEWSLETTER_MAIL as mail LEFT OUTER JOIN TRACKING_VIEWSTATE as v "
			+ " ON mail.id = v.DOMAIN_IDENTIFIER and v.USER_IDENTIFIER = :userId "
			+ " WHERE mail.NEWSLETTER_FK = :newsletterId ";

	/**
	 * @see org.openuss.newsletter.MailDao#toMailInfo(org.openuss.newsletter.Mail,
	 *      org.openuss.newsletter.MailInfo)
	 */
	public void toMailInfo(Mail sourceEntity, MailInfo targetVO) {
		super.toMailInfo(sourceEntity, targetVO);
	}

	/**
	 * @see org.openuss.newsletter.MailDao#toMailInfo(org.openuss.newsletter.Mail)
	 */
	public MailInfo toMailInfo(final Mail entity) {
		return super.toMailInfo(entity);
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private Mail loadMailFromMailInfo(MailInfo mailInfo) {
		Mail mail;
		if (mailInfo.getId() == null) {
			mail = new org.openuss.newsletter.MailImpl();
			return mail;
		}
		mail = this.load(mailInfo.getId());
		if (mail == null) {
			mail = new org.openuss.newsletter.MailImpl();
		}
		return mail;
	}

	/**
	 * @see org.openuss.newsletter.MailDao#mailInfoToEntity(org.openuss.newsletter.MailInfo)
	 */
	public Mail mailInfoToEntity(MailInfo mailInfo) {
		Mail entity = this.loadMailFromMailInfo(mailInfo);
		this.mailInfoToEntity(mailInfo, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.newsletter.MailDao#mailInfoToEntity(org.openuss.newsletter.MailInfo,
	 *      org.openuss.newsletter.Mail)
	 */
	public void mailInfoToEntity(MailInfo sourceVO, Mail targetEntity, boolean copyIfNull) {
		// @todo verify behavior of mailInfoToEntity
		super.mailInfoToEntity(sourceVO, targetEntity, copyIfNull);
	}

	/**
	 * @see org.openuss.newsletter.MailDao#toMailDetail(org.openuss.newsletter.Mail,
	 *      org.openuss.newsletter.MailDetail)
	 */
	public void toMailDetail(Mail sourceEntity, MailDetail targetVO) {
		super.toMailDetail(sourceEntity, targetVO);
	}

	/**
	 * @see org.openuss.newsletter.MailDao#toMailDetail(org.openuss.newsletter.Mail)
	 */
	public MailDetail toMailDetail(final Mail entity) {
		return super.toMailDetail(entity);
	}

	/**
	 * Retrieves the entity object that is associated with the specified value
	 * object from the object store. If no such entity object exists in the
	 * object store, a new, blank entity is created
	 */
	private Mail loadMailFromMailDetail(MailDetail mailDetail) {
		Mail mail;
		if (mailDetail.getId() == null) {
			mail = new org.openuss.newsletter.MailImpl();
			return mail;
		}
		mail = this.load(mailDetail.getId());
		if (mail == null) {
			mail = new org.openuss.newsletter.MailImpl();
		}
		return mail;
	}

	/**
	 * @see org.openuss.newsletter.MailDao#mailDetailToEntity(org.openuss.newsletter.MailDetail)
	 */
	public Mail mailDetailToEntity(MailDetail mailDetail) {
		Mail entity = this.loadMailFromMailDetail(mailDetail);
		this.mailDetailToEntity(mailDetail, entity, true);
		return entity;
	}

	/**
	 * @see org.openuss.newsletter.MailDao#mailDetailToEntity(org.openuss.newsletter.MailDetail,
	 *      org.openuss.newsletter.Mail)
	 */
	public void mailDetailToEntity(MailDetail sourceVO, Mail targetEntity, boolean copyIfNull) {
		super.mailDetailToEntity(sourceVO, targetEntity, copyIfNull);
	}

	// Hibernate doesn't support left outer join on object that doesn't have
	// a association. Therefore ViewState should be an associaction class between
	// newsletter and user, but this isn't support by andromda 3.2 yet.
	@Override
	protected List handleLoadAllMailInfos(final Newsletter newsletter, final User user) throws Exception {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Map<Long, ViewState> viewstates = loadViewStates(newsletter, user, session);

				List<MailInfo> mails = findMailByNewsletter(TRANSFORM_MAILINFO, newsletter);
				injectViewState(viewstates, mails);
				return mails;
			}
		}, true);
	}

	@Override
	protected List handleLoadSendMailInfos(final Newsletter newsletter, final User user) throws Exception {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Map<Long, ViewState> viewstates = loadViewStates(newsletter, user, session);

				List<MailInfo> mails = findMailByNewsletterAndStatus(TRANSFORM_MAILINFO, newsletter);
				injectViewState(viewstates, mails);
				return mails;
			}
		}, true);
	}

	public java.util.List findNotDeletedByStatus(final int transform, final org.openuss.newsletter.Newsletter newsletter) {
		return this
				.findNotDeletedByStatus(
						transform,
						"from org.openuss.newsletter.Mail as m where m.newsletter = :newsletter and m.status <> org.openuss.newsletter.MailingStatus.DELETED",
						newsletter);
	}

	public java.util.List findByNewsletterWithoutDeleted(final int transform,
			final org.openuss.newsletter.Newsletter newsletter) {
		return this
				.findByNewsletterWithoutDeleted(
						transform,
						"from org.openuss.newsletter.Mail as m where m.newsletter = :newsletter and m.status = org.openuss.newsletter.MailingStatus.SEND",
						newsletter);
	}

	private Map<Long, ViewState> loadViewStates(final Newsletter newsletter, final User user, Session session) {
		Query queryObject = session.createSQLQuery(SQL_SELECT_MAIL_VIEW_STATE_BY_NEWSLETTER_ID_AND_USER_ID);
		queryObject.setParameter("newsletterId", newsletter.getId());
		queryObject.setParameter("userId", user.getId());
		List<Object[]> results = queryObject.list();

		Map<Long, ViewState> viewstates = new HashMap<Long, ViewState>();
		for (Object[] obj : results) {
			Long mailId = ((BigInteger) obj[0]).longValue();
			ViewState state = ViewState.NEW;
			if (obj[1] != null) {
				state = ViewState.fromInteger((Integer) obj[1]);
			}
			viewstates.put(mailId, state);
		}
		return viewstates;
	}

	private void injectViewState(Map<Long, ViewState> viewstates, List<MailInfo> mails) {
		for (MailInfo info : mails) {
			info.setViewState(viewstates.get(info.getId()));
		}
	}

}