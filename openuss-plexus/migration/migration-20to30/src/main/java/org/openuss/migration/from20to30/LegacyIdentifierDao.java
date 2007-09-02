package org.openuss.migration.from20to30;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
 * Legacy Identifier Map
 * 
 * @author Ingo Dueppe
 */
public class LegacyIdentifierDao extends SimpleJdbcDaoSupport implements InitializingBean {

	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(LegacyIdentifierDao.class);

	/** <code>CREATE TABLE LEGACY_USERIDS (LEGACYID VARCHAR(40) NOT NULL, ID BIGINT NOT NULL)</code> */
	private static final String SQL_LEGACYIDS_CREATE_TABLE = "CREATE TABLE LEGACY_IDS (LEGACYID VARCHAR(30) NOT NULL, ID BIGINT NOT NULL)";
	
	/** <code>ALTER TABLE LEGACY_USERIDS ADD CONSTRAINT PK_LEGACY_USERIDS PRIMARY KEY (L)</code> */
	private static final String SQL_LEGACYIDS_CREATE_INDEX = "ALTER TABLE LEGACY_IDS ADD CONSTRAINT PK_LEGACY_IDS PRIMARY KEY (LEGACYID)";

	/** <code>CREATE TABLE LEGACY_USERIDS (LEGACYID VARCHAR(40) NOT NULL, ID BIGINT NOT NULL)</code> */
	private static final String SQL_USERIDS_CREATE_TABLE = "CREATE TABLE LEGACY_USERIDS (LEGACYID VARCHAR(40) NOT NULL, ID BIGINT NOT NULL)";

	/** <code>ALTER TABLE LEGACY_USERIDS ADD CONSTRAINT PK_LEGACY_USERIDS PRIMARY KEY (L)</code> */
	private static final String SQL_USERIDS_CREATE_INDEX = "ALTER TABLE LEGACY_USERIDS ADD CONSTRAINT PK_LEGACY_USERIDS PRIMARY KEY (LEGACYID)";

	/** <code>CREATE TABLE LEGACY_CONSOLIDATED (ID BIGINT NOT NULL, USERNAME  VARCHAR(250) NOT NULL)</code> */
	private static final String SQL_CONSOLIDATED_CREATE_TABLE = "CREATE TABLE LEGACY_CONSOLIDATED (ID BIGINT NOT NULL, USERNAME  VARCHAR(250) NOT NULL)";

	/** <code>ALTER TABLE LEGACY_CONSOLIDATED ADD CONSTRAINT PK_LEGACY_CONSOLIDATED PRIMARY KEY (ID, USERNAME)</code> */
	private static final String SQL_CONSOLIDATED_CREATE_INDEX = "ALTER TABLE LEGACY_CONSOLIDATED ADD CONSTRAINT PK_LEGACY_CONSOLIDATED PRIMARY KEY (ID, USERNAME)";

	/** <code>CREATE TABLE LEGACY_RENAMED (LEGACYID VARCHAR(40) NOT NULL, OLDNAME VARCHAR(250) NOT NULL)</code> */
	private static final String SQL_RENAMED_CREATE_TABLE = "CREATE TABLE LEGACY_RENAMED (LEGACYID VARCHAR(40) NOT NULL, OLDNAME VARCHAR(250) NOT NULL)";

	/** <code>ALTER TABLE LEGACY_RENAMED ADD CONSTRAINT PK_LEGACY_CONSOLIDATED PRIMARY KEY (ID)</code> */
	private static final String SQL_RENAMED_CREATE_INDEX = "ALTER TABLE LEGACY_RENAMED ADD CONSTRAINT PK_LEGACY_RENAMED PRIMARY KEY (LEGACYID, OLDNAME)";

	/** <code>INSERT INTO LEGACY_RENAMED (LEGACYID, OLDNAME) VALUES (?,?)</code> */
	private static final String SQL_RENAMED_INSERT = "INSERT INTO LEGACY_RENAMED (LEGACYID, OLDNAME) VALUES (?,?)";

	/** <code>SELECT COUNT(*) FROM LEGACY_RENAMED WHERE LEGACYID = ?</code> */
	private static final String SQL_IS_RENAMED = "SELECT COUNT(*) FROM LEGACY_RENAMED WHERE LEGACYID = ?";

	/** <code>INSERT INTO LEGACY_USERIDS (LEGACYID, ID) VALUES (?,?)</code> */
	private static final String SQL_USERIDS_INSERT_ID = "INSERT INTO LEGACY_USERIDS (LEGACYID, ID) VALUES (?,?)";

	/** <code>SELECT ID FROM LEGACY_USERIDS WHERE LEGACYID = ? </code> */
	private static final String SQL_USERIDS_SELECT_ID = "SELECT ID FROM LEGACY_USERIDS WHERE LEGACYID = ?";

	/** <code>SELECT DISCTINCT ID FROM LEGACY_USERIDS </code> */
	private static final String SQL_USERIDS_SELECT_ALL_IDS = "SELECT DISTINCT ID FROM LEGACY_USERIDS";

	/** <code>INSERT INTO LEGACY_USERIDS (LEGACYID, ID) VALUES (?,?)</code> */
	private static final String SQL_LEGACYIDS_INSERT_ID = "INSERT INTO LEGACY_IDS (LEGACYID, ID) VALUES (?,?)";
	
	/** <code>SELECT ID FROM LEGACY_USERIDS WHERE LEGACYID = ? </code> */
	private static final String SQL_LEGACYIDS_SELECT_ID = "SELECT ID FROM LEGACY_IDS WHERE LEGACYID = ?";
	
	/** <code>INSERT INTO LEGACY_CONSOLIDATED (ID, USERNAME) VALUES (?,?) </code> */
	private static final String SQL_CONSOLIDATED_INSERT = "INSERT INTO LEGACY_CONSOLIDATED (ID, USERNAME) VALUES (?,?)";

	/** <code>SELECT USERNAME FROM LEGACY_CONSOLIDATED WHERE ID = ? </code> */
	private static final String SQL_CONSOLIDATED_SELECT = "SELECT USERNAME FROM LEGACY_CONSOLIDATED WHERE ID = ?";

	/**
	 * Retrieve all new imported user ids
	 */
	public List<Long> loadAllNewUserIds() {
		return getSimpleJdbcTemplate().query(SQL_USERIDS_SELECT_ALL_IDS, new ParameterizedRowMapper<Long>() {
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong(1);
			}
		});
	}

	/**
	 * Retrieve all consolidated username according to a user id
	 * 
	 * @param userId
	 */
	public List<String> allConsolidatedUserNames(Long userId) {
		return getSimpleJdbcTemplate().query(SQL_CONSOLIDATED_SELECT, new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		}, userId);
	}

	public boolean isRenamedName(String legacyId) {
		try {
			return 0 < getSimpleJdbcTemplate().queryForInt(SQL_IS_RENAMED, legacyId);
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
	}

	/**
	 * Map user id to consolidated user name
	 * 
	 * @param userId -
	 *            new user id
	 * @param userName -
	 *            consolidated username
	 */
	public void insertConsolidated(Long userId, String userName) {
		logger.debug("Add consolidation of username " + userName + " to user id" + userId);
		getSimpleJdbcTemplate().update(SQL_CONSOLIDATED_INSERT, userId, StringUtils.trimToEmpty(userName));
	}

	public void insertRenamed(String legacyId, String oldName) {
		logger.debug("Log renamed user " + oldName + " to legacy id " + legacyId);
		getSimpleJdbcTemplate().update(SQL_RENAMED_INSERT, legacyId, StringUtils.trimToEmpty(oldName));
	}

	/**
	 * Insert a mapping between a legacy id and a user id
	 * 
	 * @param legacyId
	 * @param user
	 */
	public void insertLegacyId(String legacyId, Long id) {
		logger.trace("insert " + legacyId + " - " + id);
		getSimpleJdbcTemplate().update(SQL_LEGACYIDS_INSERT_ID, legacyId, id);
	}

	/**
	 * Insert a mapping between a legacy id and a user id
	 * 
	 * @param legacyId
	 * @param user
	 */
	public void insertUserId(String legacyId, Long id) {
		logger.trace("insert " + legacyId + " - " + id);
		getSimpleJdbcTemplate().update(SQL_USERIDS_INSERT_ID, legacyId, id);
	}

	/**
	 * Retrieve according user id from legacy student or legacy id
	 * 
	 * @param legacyId
	 * @return user id
	 */
	public Long getUserId(String legacyId) {
		logger.trace("looking for " + legacyId);
		try {
			return getSimpleJdbcTemplate().queryForLong(SQL_USERIDS_SELECT_ID, legacyId);
		} catch (Throwable e) {
			logger.debug("legacy id " + legacyId + " not found!");
			return null;
		}
	}

	/**
	 * Retrieve new id of a legacy di
	 * 
	 * @param legacyId
	 * @return user id
	 */
	public Long getId(String legacyId) {
		logger.trace("looking for " + legacyId);
		try {
			return getSimpleJdbcTemplate().queryForLong(SQL_LEGACYIDS_SELECT_ID, legacyId);
		} catch (Throwable e) {
			logger.debug("legacy id " + legacyId + " not found!");
			return null;
		}
	}

	@Override
	protected void initTemplateConfig() {
		super.initTemplateConfig();
		try {
			logger.debug("create identity map");
			getJdbcTemplate().execute(SQL_LEGACYIDS_CREATE_TABLE);
			getJdbcTemplate().execute(SQL_LEGACYIDS_CREATE_INDEX);
		} catch (Throwable e) {
			logger.warn("Legacy tables already exists");
		}
		try {
			getJdbcTemplate().execute(SQL_USERIDS_CREATE_TABLE);
			getJdbcTemplate().execute(SQL_USERIDS_CREATE_INDEX);
		} catch (Throwable e) {
			logger.warn("Legacy tables already exists");
		}
		try {
			getJdbcTemplate().execute(SQL_CONSOLIDATED_CREATE_TABLE);
			getJdbcTemplate().execute(SQL_CONSOLIDATED_CREATE_INDEX);
		} catch (Throwable e) {
			logger.warn("Legacy tables already exists");
		}
		try {
			getJdbcTemplate().execute(SQL_RENAMED_CREATE_TABLE);
			getJdbcTemplate().execute(SQL_RENAMED_CREATE_INDEX);
		} catch (Throwable e) {
			logger.warn("Legacy tables already exists");
		}
	}

}
