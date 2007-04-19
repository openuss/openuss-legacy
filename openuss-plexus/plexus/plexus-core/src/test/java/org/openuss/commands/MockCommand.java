package org.openuss.commands;

public class MockCommand extends AbstractDomainCommand {

	private int executionCount;

	private boolean throwException;
	
	public void execute() throws Exception {
		executionCount++;
		if (throwException) {
			throw new Exception("Error during execution of mock command");
		}
	}

	public int getExecutionCount() {
		return executionCount;
	}

	public void reset() {
		executionCount = 0;
		throwException = false;
	}

	public boolean isThrowException() {
		return throwException;
	}

	public void setThrowException(boolean throwException) {
		this.throwException = throwException;
	}
	
}
