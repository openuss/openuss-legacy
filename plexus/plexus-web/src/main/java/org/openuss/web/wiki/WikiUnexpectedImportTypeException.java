package org.openuss.web.wiki;

/**
 * Exception for unexpected import types
 * @author Projektseminar WS 07/08, Team Collaboration
 * @see org.openuss.web.Constants
 */
public class WikiUnexpectedImportTypeException extends RuntimeException {
	
	private static final long serialVersionUID = -3628604070931200375L;

	/**
	 * Creates a new Instances of WikiUnexpectedImportTypeException with default message.
	 */
	public WikiUnexpectedImportTypeException() {
		super("Unexpected Wiki Import Type.");
	}	
	
}
