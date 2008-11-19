// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.commands;

import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;

/**
 * @see org.openuss.commands.Command
 */
public class CommandDaoImpl extends CommandDaoBase {

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List findAllOnceCommands(final int transform, final String queryString) {
		try {
			Query queryObject = super.getSession(false).createQuery(queryString);
			queryObject.setLockMode("c", LockMode.UPGRADE_NOWAIT);
			List results = queryObject.list();
			transformEntities(transform, results);
			return results;
		} catch (HibernateException ex) {
			throw super.convertHibernateAccessException(ex);
		}
	}

	/**
	 * @see org.openuss.commands.CommandDao#load(int, java.lang.Long)
	 */
	@Override
	public Object load(final int transform, final Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Command.load - 'id' can not be null");
		}

		final Object entity = this.getHibernateTemplate().get(CommandImpl.class, id, LockMode.UPGRADE_NOWAIT);
		return transformEntity(transform, (org.openuss.commands.Command) entity);
	}

	/**
	 * @see org.openuss.commands.CommandDao#loadAll(int)
	 */
	@Override
	public Collection<?> loadAll(final int transform) {
		final Collection<?> results = this.getHibernateTemplate().loadAll(CommandImpl.class);
		this.transformEntities(transform, results);
		return results;
	}
}