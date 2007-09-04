package org.openuss.migration.from20to30;

import org.hibernate.SessionFactory;
import org.openuss.migration.legacy.dao.LegacyDao;

/**
 * 
 * @author Ingo Dueppe
 */
public abstract class DefaultImport {

	/** LegacyDao */
	protected LegacyDao legacyDao;
	
	/** LegacyIdentityMap */
	protected LegacyIdentifierDao identifierDao;
	
	/** LegacySessionFactory */
	protected SessionFactory legacySessionFactory;

	public void setLegacyDao(LegacyDao legacyDao) {
		this.legacyDao = legacyDao;
	}

	public void setLegacySessionFactory(SessionFactory sessionFactory) {
		this.legacySessionFactory = sessionFactory;
	}

	public void setIdentifierDao(LegacyIdentifierDao identifierDao) {
		this.identifierDao = identifierDao;
	}

	protected void evict(Object object) {
		legacySessionFactory.getCurrentSession().evict(object);
	}

}
