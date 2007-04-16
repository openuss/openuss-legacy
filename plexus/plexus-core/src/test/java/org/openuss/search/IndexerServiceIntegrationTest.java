// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.search;

import java.util.Collection;

import org.openuss.commands.Command;
import org.openuss.commands.CommandDao;
import org.openuss.foundation.DomainObject;
import org.openuss.lecture.Faculty;

/**
 * JUnit Test for Spring Hibernate IndexerService class.
 * @see org.openuss.search.IndexerService
 */
public class IndexerServiceIntegrationTest extends IndexerServiceIntegrationTestBase {
	
	private CommandDao commandDao;
	
	public void testCreateIndex() throws IndexerApplicationException {
		commandDao.remove(commandDao.loadAll());
		DomainObject domain = createDomainObject();
		indexerService.createIndex(domain);
		check("facultyIndexer", "CREATE", domain);
	}

	private DomainObject createDomainObject() {
		Faculty faculty = Faculty.Factory.newInstance();
		faculty.setId(testUtility.unique());
		return faculty;
	}

	public void testUpdateIndex() throws IndexerApplicationException {
		commandDao.remove(commandDao.loadAll());
		DomainObject domain = createDomainObject();
		indexerService.updateIndex(domain);
		check("facultyIndexer", "UPDATE", domain);
	}

	public void testDeleteIndex() throws IndexerApplicationException {
		commandDao.remove(commandDao.loadAll());
		DomainObject domain = createDomainObject();
		indexerService.deleteIndex(domain);
		check("facultyIndexer", "DELETE", domain);
	}

	private void check(String command, String commandType, DomainObject domain) {
		Collection<Command> commands = commandDao.loadAll();
		assertEquals(1, commands.size());
		Command event = commands.iterator().next();
		assertEquals(domain.getId(), event.getDomainIdentifier());
		assertEquals(command, event.getCommand());
		assertEquals(commandType, event.getCommandType());
	}


	public CommandDao getCommandDao() {
		return commandDao;
	}

	public void setCommandDao(CommandDao commandDao) {
		this.commandDao = commandDao;
	}
	
	protected String[] getConfigLocations() {
		return new String[] { 
				"classpath*:applicationContext.xml",
				"classpath*:applicationContext-localDataSource.xml",
				"classpath*:applicationContext-beans.xml", 
				"classpath*:applicationContext-tests.xml",
				"classpath*:applicationContext-lucene.xml",
				"classpath*:applicationContext-cache.xml",
				"classpath*:beanRefFactory",
				"classpath*:testSecurity.xml",
				"classpath*:testDataSource.xml"};
	}
}