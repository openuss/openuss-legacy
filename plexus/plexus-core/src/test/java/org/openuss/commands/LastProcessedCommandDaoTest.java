// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.commands;

import org.openuss.TestUtility;
import java.util.Date;


/**
 * JUnit Test for Spring Hibernate LastProcessedCommandDao class.
 * @see org.openuss.commands.LastProcessedCommandDao
 */
public class LastProcessedCommandDaoTest extends LastProcessedCommandDaoTestBase {
	
	private TestUtility testUtitlity;
	
	public void testLastProcessedCommandDaoCreate() {
		Command command = Command.Factory.newInstance();
		
		command.setCommand("command");
		command.setCommandType("commandType");
		command.setCommandTime(new Date());
		command.setState(CommandState.ONCE);
		command.setDomainIdentifier(1234L);
		
		LastProcessedCommand lastProcessedCommand = new LastProcessedCommandImpl();
		lastProcessedCommand.setId(testUtitlity.unique());
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