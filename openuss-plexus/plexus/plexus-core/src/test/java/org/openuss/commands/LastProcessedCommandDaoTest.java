// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.commands;

import java.util.Date;

import org.openuss.TestUtility;


/**
 * JUnit Test for Spring Hibernate LastProcessedCommandDao class.
 * @see org.openuss.commands.LastProcessedCommandDao
 */
public class LastProcessedCommandDaoTest extends LastProcessedCommandDaoTestBase {
	
	private TestUtility testUtitlity;
	
	public void testLastProcessedCommandDaoCreate() {
		Command command = new CommandImpl();
		
		command.setCommand("command");
		command.setCommandType("commandType");
		command.setStartTime(new Date());
		command.setState(CommandState.ONCE);
		command.setDomainIdentifier(1234L);
		
		LastProcessedCommand lastProcessedCommand = new LastProcessedCommandImpl();
		lastProcessedCommand.setId(TestUtility.unique());
		lastProcessedCommand.setLast(command);
		lastProcessedCommandDao.create(lastProcessedCommand);
		
		setComplete();
		endTransaction();
	}

	public TestUtility getTestUtitlity() {
		return testUtitlity;
	}

	public void setTestUtitlity(TestUtility testUtitlity) {
		this.testUtitlity = testUtitlity;
	}
}