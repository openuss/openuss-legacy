package org.openuss.migration.from20to30;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
 * Legacy Identifier Map
 * 
 * @author Ingo Dueppe
 *
 */
public class LegacyIdentifierDao extends SimpleJdbcDaoSupport implements InitializingBean {
	
	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(LegacyIdentifierDao.class);	
	
	/** SQL statement to create a lecagy identifier table */
	private final String SQL_CREATE_TABLE = "CREATE TABLE LEGACY_IDENTIFIER (LEGACYID VARCHAR(40) NOT NULL, ID BIGINT NOT NULL)";
	
	/** INSERT INTO LEGACY_IDENTIFIER (LEGACYID, ID) VALUES (?,?) */
	private final String SQL_INSERT_ID = "INSERT INTO LEGACY_IDENTIFIER (LEGACYID, ID) VALUES (?,?)";
	
	/** SELECT ID FROM LEGACY_IDENTIFIER WHERE LEGACYID = ? */
	private final String SQL_SELECT_ID = "SELECT ID FROM LEGACY_IDENTIFIER WHERE LEGACYID = ?";
	
	public void insert(String legacyId, Long id) {
		logger.debug("insert "+legacyId+" - "+id);
		try {
			getSimpleJdbcTemplate().update(SQL_INSERT_ID, legacyId, id);
		} catch (Throwable e) {
			logger.error(e);
		}
	}
	
	public Long getId(String legacyId) {
		logger.debug("looking for "+legacyId);
		try {
			return getSimpleJdbcTemplate().queryForLong(SQL_SELECT_ID, legacyId);
		} catch (Throwable e) {
			logger.debug("legacy id "+legacyId+" not found!");
			return null;
		}
	}
	
	@Override
	protected void initTemplateConfig() {
		super.initTemplateConfig();
		try {
			logger.debug("create identity map");
		 getJdbcTemplate().execute(SQL_CREATE_TABLE);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	

}
