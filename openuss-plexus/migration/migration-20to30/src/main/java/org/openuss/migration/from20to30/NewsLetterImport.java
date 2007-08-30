package org.openuss.migration.from20to30;

import org.openuss.migration.legacy.dao.LegacyDao;


/**
 * Course NewsLetter Import
 * 
 * @author Ingo Dueppe
 */
public class NewsLetterImport {
	
	/** LegacyDao */
	private LegacyDao legacyDao;
	
	

	public LegacyDao getLegacyDao() {
		return legacyDao;
	}

	public void setLegacyDao(LegacyDao legacyDao) {
		this.legacyDao = legacyDao;
	}

}
