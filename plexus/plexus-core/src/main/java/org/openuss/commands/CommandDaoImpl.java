// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.commands;

import org.hibernate.LockMode;
import org.springframework.dao.DataAccessException;

/**
 * @see org.openuss.commands.Command
 */
public class CommandDaoImpl extends CommandDaoBase {

	@Override
	protected boolean handleObtainLock(Command command) throws Exception {
		try {
			getHibernateTemplate().lock(command, LockMode.FORCE);
			return true;
		} catch (DataAccessException dae) {
			return false;
		}
	}

	@Override
	protected void handleReleaseLock(Command command) throws Exception {
		getHibernateTemplate().lock(command, LockMode.NONE);
	}

}