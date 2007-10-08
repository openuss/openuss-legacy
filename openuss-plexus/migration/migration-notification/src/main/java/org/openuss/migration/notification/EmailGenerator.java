package org.openuss.migration.notification;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class EmailGenerator extends SimpleJdbcDaoSupport implements InitializingBean {

	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(EmailGenerator.class);

	private static final String SQL_SELECT_USER = "SELECT u.id, u.username, u.email FROM SECURITY_USER u WHERE u.enabled = 0 AND u.id > 0";
	private static final String SQL_SELECT_CONSOLIDATED = "SELECT username FROM legacy_consolidated WHERE id = ?";
	private static final String SQL_SELECT_RENAMED = "SELECT oldname FROM LEGACY_RENAMED r, LEGACY_USERIDS u WHERE r.legacyid = u.legacyid and id=?";
	private static final String SQL_SELECT_ID = "SELECT GEN_ID(global_sequence, 1) AS NEW_VALUE FROM RDB$DATABASE";
	private static final String SQL_INSERT_ACTIVATIONCODE = "INSERT INTO SECURITY_ACTIVATIONCODE (ID, CREATED_AT) VALUES  (?, ?)";
	private static final String SQL_INSERT_USERCODE = "INSERT INTO SECURITY_USERCODE (ID, CODE, USER_FK) VALUES (?, ?, ?)";

	private static final String SQL_INSERT_EMAIL = "INSERT INTO USER_NOTIFICATION (ID, EMAIL, TEXT) VALUES (GEN_ID(global_sequence,1), ?, ?)";

	private static final String SQL_INSERT_INSTITUTECODE = "INSERT INTO SECURITY_INSTITUTECODE (ID, CODE, INSTITUTE_FK) VALUES (?, ?, ?)";
	private static final String SQL_SELECT_INSTITUTE_ADMINS = "SELECT o.id, o.name, o.owner_name, u.username, u.email FROM lecture_institute i"
			+ " join lecture_organisation o on i.id = o.id left join security_group g on o.membership_fk = g.membership_fk"
			+ " left join security_group2authority ga on g.id = ga.groups_fk left join security_user u on ga.members_fk = u.id WHERE g.group_type = 2";

	private static final String TEMPLATE_USER_NOFICATION = "templates/emails/user_notification.vsl";
	private static final String TEMPLATE_INSTITUTE_NOFICATION = "templates/emails/institute_notification.vsl";

	private VelocityEngine velocityEngine;

	public void initialize() {
		final String SQL_CREATE_TABLE = "CREATE TABLE USER_NOTIFICATION (ID  BIGINT NOT NULL, EMAIL VARCHAR(200), SENDAT  TIMESTAMP, TEXT BLOB SUB_TYPE 1 SEGMENT SIZE 80)";
		final String SQL_CREATE_INDEX = "ALTER TABLE USER_NOTIFICATION ADD CONSTRAINT PK_NOTIFICATION PRIMARY KEY (ID)";
		try {
			getJdbcTemplate().execute(SQL_CREATE_TABLE);
		} catch (Throwable e) {
			logger.info("cannot create table " + e.getMessage());
		}
		try {
			getJdbcTemplate().execute(SQL_CREATE_INDEX);
		} catch (Throwable e) {
			logger.info("cannot create table " + e.getMessage());
		}

	}

	public void generateUserNotificationEmails() {
		initialize();

		final List<UserRow> users = getSimpleJdbcTemplate().query(SQL_SELECT_USER, new UserRowMapper());
		int count = 0;

		for (UserRow row : users) {
			progress(count++);
			List<String> usernames = loadConsolidatedNames(row);

			Boolean consolidated = !usernames.isEmpty();
			String oldname = null;
			try {
				oldname = getSimpleJdbcTemplate().queryForObject(SQL_SELECT_RENAMED, String.class, row.id);
			} catch (EmptyResultDataAccessException e) {
				oldname = null;
			}
			Boolean renamed = StringUtils.isNotBlank(oldname);

			String code = generateUserActivationCode(row);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("username", row.username);
			params.put("email", row.email);
			params.put("code", code);
			params.put("consolidated", consolidated);
			params.put("renamed", renamed);
			params.put("accounts", usernames);

			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, TEMPLATE_USER_NOFICATION, params);

			getSimpleJdbcTemplate().update(SQL_INSERT_EMAIL, row.email, text);
		}
	}

	public void generateInstituteNotification() {
		initialize();
		final List<InstituteRow> institutes = getSimpleJdbcTemplate().query(SQL_SELECT_INSTITUTE_ADMINS, new InstituteRowMapper());

		int count = 0;
		for (InstituteRow row : institutes) {
			if (StringUtils.isNotBlank(row.email)) {
				progress(count++);

				String code = generateInstituteActivationCode(row);

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("username", row.username);
				params.put("email", row.email);
				params.put("institute_name", row.name);
				params.put("institute_id", row.id);
				params.put("institute_owner", row.ownerName);
				params.put("code", code);

				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,TEMPLATE_INSTITUTE_NOFICATION, params);

				getSimpleJdbcTemplate().update(SQL_INSERT_EMAIL, row.email, text);
			}
		}
	}

	private void progress(int count) {
		if (count % 100 == 0) {
			logger.debug("processed " + count);
		}
	}

	private String generateUserActivationCode(UserRow row) {
		Long codeId = getSimpleJdbcTemplate().queryForLong(SQL_SELECT_ID);
		Date expire = DateUtils.addWeeks(new Date(), 1);
		String code = md5("AC{" + row.id + "}" + System.currentTimeMillis()) + row.id;
		getSimpleJdbcTemplate().update(SQL_INSERT_ACTIVATIONCODE, codeId, expire);
		getSimpleJdbcTemplate().update(SQL_INSERT_USERCODE, codeId, code, row.id);
		return code;
	}

	private String generateInstituteActivationCode(InstituteRow row) {
		Long codeId = getSimpleJdbcTemplate().queryForLong(SQL_SELECT_ID);
		Date expire = DateUtils.addWeeks(new Date(), 1);
		String code = md5("FA{" + row.id + "}" + System.currentTimeMillis()) + row.id;
		getSimpleJdbcTemplate().update(SQL_INSERT_ACTIVATIONCODE, codeId, expire);
		getSimpleJdbcTemplate().update(SQL_INSERT_INSTITUTECODE, codeId, code, row.id);
		return code;
	}

	private List<String> loadConsolidatedNames(UserRow row) {
		List<String> usernames = getSimpleJdbcTemplate().query(SQL_SELECT_CONSOLIDATED,
				new ParameterizedRowMapper<String>() {
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString(1);
					}
				}, row.id);
		return usernames;
	}

	private String md5(String raw) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			byte[] byteHash = md.digest(raw.getBytes());

		     StringBuffer result = new StringBuffer();
		     for(int i = 0; i < byteHash.length; i++) {
		    	 result.append(Integer.toHexString(0xFF & byteHash[i]));
		     }
		     return result.toString();
		} catch (NoSuchAlgorithmException e) {
			logger.error("Error while MD5 encoding: ", e);
			throw new RuntimeException(e);
		}
	}

	public static class UserRow {
		public Long id;
		public String username;
		public String email;
	}

	public static class UserRowMapper implements ParameterizedRowMapper<UserRow> {
		public UserRow mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserRow row = new UserRow();
			row.id = rs.getLong("id");
			row.username = rs.getString("username");
			row.email = rs.getString("email");
			return row;
		}
	}

	public static class InstituteRow extends UserRow {
		public String name;
		public String ownerName;
	}

	public static class InstituteRowMapper implements ParameterizedRowMapper<InstituteRow> {
		public InstituteRow mapRow(ResultSet rs, int rowNum) throws SQLException {
			InstituteRow row = new InstituteRow();
			row.id = rs.getLong("id");
			row.username = rs.getString("username");
			row.email = rs.getString("email");
			row.ownerName = rs.getString("owner_name");
			row.name = rs.getString("name");
			return row;
		}
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

}
