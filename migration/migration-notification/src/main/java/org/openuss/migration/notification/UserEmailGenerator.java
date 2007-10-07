package org.openuss.migration.notification;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Legacy Identifier Map
 * 
 * @author Ingo Dueppe
 */
public class UserEmailGenerator extends SimpleJdbcDaoSupport implements InitializingBean {

	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(UserEmailGenerator.class);

	/** <code>SELECT u.id, u.username, u.email FROM SECURITY_USER u WHERE u.enabled = 0 AND u.id > 0</code> */
	private static final String SQL_SELECT_USER = "SELECT u.id, u.username, u.email FROM SECURITY_USER u WHERE u.enabled = 0 AND u.id > 0";

	/** <code>SELECT username FROM legacy_consolidated WHERE id = ?</code> */
	private static final String SQL_SELECT_CONSOLIDATED = "SELECT username FROM legacy_consolidated WHERE id = ?";

	/** <code>SELECT username FROM legacy_consolidated WHERE id = ?</code> */
	private static final String SQL_SELECT_RENAMED = "SELECT oldname FROM LEGACY_RENAMED r, LEGACY_USERIDS u WHERE r.legacyid = u.legacyid and id=?";

	/** <code>SELECT GEN_ID(mygenerator, 1) AS NEW_VALUE FROM RDB$DATABASE</code> */
	private static final String SQL_SELECT_ID = "SELECT GEN_ID(global_sequence, 1) AS NEW_VALUE FROM RDB$DATABASE";

	/** <code>INSERT INTO SECURITY_ACTIVATIONCODE (ID, CREATED_AT) VALUES  (?, ?)</code> */
	private static final String SQL_INSERT_ACTIVATIONCODE = "INSERT INTO SECURITY_ACTIVATIONCODE (ID, CREATED_AT) VALUES  (?, ?)";

	/** <code>INSERT INTO SECURITY_USERCODE (ID, CODE, USER_FK) VALUES (?, ?, ?)</code> */
	private static final String SQL_INSERT_USERCODE = "INSERT INTO SECURITY_USERCODE (ID, CODE, USER_FK) VALUES (?, ?, ?)";

	/** <code>INSERT INTO USER_NOTIFICATION (USERID, EMAIL, CODE, TEXT) VALUES (?, ?, ?, ?)</code> */
	private static final String SQL_INSERT_EMAIL = "INSERT INTO USER_NOTIFICATION (USERID, EMAIL, TEXT) VALUES (?, ?, ?)";

	
	private static final String TEMPLATE_USERNOFICATION = "templates/emails/user_notification.vsl";
	
	private VelocityEngine velocityEngine;
	
	public void initialize() {
		final String SQL_DROP_TABLE = "DROP TABLE USER_NOTIFICATION";
		final String SQL_CREATE_TABLE = "CREATE TABLE USER_NOTIFICATION (USERID  BIGINT NOT NULL, EMAIL VARCHAR(200), SENDAT  TIMESTAMP, TEXT BLOB SUB_TYPE 1 SEGMENT SIZE 80)";
		final String SQL_CREATE_INDEX = "ALTER TABLE USER_NOTIFICATION ADD CONSTRAINT PK_USER_NOTIFICATION PRIMARY KEY (USERID)";
		try {	
			getJdbcTemplate().execute(SQL_DROP_TABLE);
		} catch (Throwable e) {
			logger.info("cannot drop table "+e.getMessage());
		}
		try {
			getJdbcTemplate().execute(SQL_CREATE_TABLE);
		} catch (Throwable e) {
			logger.info("cannot create table "+e.getMessage());
		}
		try {
			getJdbcTemplate().execute(SQL_CREATE_INDEX);
		} catch (Throwable e) {
			logger.info("cannot create table "+e.getMessage());
		}
		
	}
	
	public void perform() {
		initialize();

		final List<UserRow> users = getSimpleJdbcTemplate().query(SQL_SELECT_USER, new UserRowMapper());
		int count = 0;
		
		for (UserRow row : users) {
			if (count++ % 1000 == 0) {
				logger.debug("processed "+count);
			}
			List<String> usernames = loadConsolidatedNames(row);
			
			Boolean consolidated = !usernames.isEmpty();
			String oldname = null;
			try {
				oldname = getSimpleJdbcTemplate().queryForObject(SQL_SELECT_RENAMED, String.class , row.id);
			} catch (EmptyResultDataAccessException e) {
				oldname = null;
			}
			Boolean renamed = StringUtils.isNotBlank(oldname);
			
			String code = generateActivationCode(row);
			
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("username",row.username);
			params.put("email",row.email);
			params.put("code",code);
			params.put("consolidated", consolidated);
			params.put("renamed", renamed);
			params.put("accounts",usernames);
			
			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, TEMPLATE_USERNOFICATION, params);

			getSimpleJdbcTemplate().update(SQL_INSERT_EMAIL, row.id, row.email, text);
		}
	}


	private String generateActivationCode(UserRow row) {
		Long codeId = getSimpleJdbcTemplate().queryForLong(SQL_SELECT_ID);
		Date expire = DateUtils.addWeeks(new Date(), 1);
		String code = md5("AC{"+row.id+"}"+System.currentTimeMillis())+row.id;
		getSimpleJdbcTemplate().update(SQL_INSERT_ACTIVATIONCODE, codeId, expire);
		getSimpleJdbcTemplate().update(SQL_INSERT_USERCODE, codeId, code, row.id);
		return code;
	}


	private List<String> loadConsolidatedNames(UserRow row) {
		List<String> usernames = getSimpleJdbcTemplate().query(SQL_SELECT_CONSOLIDATED, new ParameterizedRowMapper<String>(){
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}}, row.id);
		return usernames;
	}
	
	public static class UserRowMapper implements ParameterizedRowMapper<UserRow>{

		public UserRow mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserRow row = new UserRow();
			row.id = rs.getLong("id");
			row.username = rs.getString("username");
			row.email = rs.getString("email");
			return row;
		}
		
	}

	private String md5(String raw) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			byte[] byteHash = md.digest(raw.getBytes());
			
			return new String(Base64.encodeBase64(byteHash));
		} catch(NoSuchAlgorithmException e) {
			logger.error("Error while MD5 encoding: ", e);
			throw new RuntimeException(e);
		}
	}

	public static class UserRow {
		public Long id;
		public String username;
		public String email;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

}
