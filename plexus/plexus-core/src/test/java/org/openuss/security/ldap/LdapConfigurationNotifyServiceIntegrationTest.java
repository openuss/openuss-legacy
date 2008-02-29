// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security.ldap;

import java.util.Collection;
import java.util.Iterator;

import org.openuss.commands.Command;
import org.openuss.commands.CommandDao;
import org.openuss.foundation.DefaultDomainObject;
import org.openuss.foundation.DomainObject;
import org.openuss.search.IndexerApplicationException;

/**
 * JUnit Test for Spring Hibernate LdapConfigurationNotifyService class.
 * @see org.openuss.security.ldap.LdapConfigurationNotifyService
 */
public class LdapConfigurationNotifyServiceIntegrationTest extends LdapConfigurationNotifyServiceIntegrationTestBase {
	
	private CommandDao commandDao;
	
	public void testCreateReconfigureCommand() throws IndexerApplicationException {
		ldapConfigurationNotifyService.reconfigure();
		check("ldapConfigDomainCommand", "RECONFIGURE", new DefaultDomainObject(1L));
//		setComplete();
//		endTransaction();
	}
	
	private void check(String command, String commandType, DomainObject domain) {
		Collection<Command> commands = commandDao.loadAll();
		assertFalse("command empty", commands.isEmpty());
		Command cmd = last(commands, domain.getId());
		assertEquals("domainIdentifier", domain.getId(), cmd.getDomainIdentifier());
		assertEquals("command", command, cmd.getCommand());
		assertEquals("commandType", commandType, cmd.getCommandType());
	}

	private Command last(Collection<Command> commands, Long domainId) {
		Command command = null;
		Iterator<Command> iter = commands.iterator();
		do {
			command = iter.next();
		} while (!command.getDomainIdentifier().equals(domainId) && iter.hasNext() );
		return command;
	}

	public CommandDao getCommandDao() {
		return commandDao;
	}

	public void setCommandDao(CommandDao commandDao) {
		this.commandDao = commandDao;
	}
	
}