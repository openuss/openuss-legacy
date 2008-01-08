package org.openuss.commands;

public class MockCommand extends AbstractDomainCommand {

	private int executionCount;

	private boolean throwException;
	
	private Exception mockException = new MockException();
	
	public static class MockException extends Exception{

		private static final long serialVersionUID = -1009697074693508307L;
		
		public MockException() {
			super("MOCKUP-COMMAND-TEST-EXCEPTION");
		}
		
	}
	
	public void execute() throws Exception {
		executionCount++;
		if (throwException) {
			throw mockException;
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
