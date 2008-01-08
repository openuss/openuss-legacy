package org.openuss.migration.from20to30;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.acegisecurity.providers.encoding.Md5PasswordEncoder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
 * Legacy Identifier Map
 * 
 * @author Ingo Dueppe
 */
public class UserPasswordEncoder extends SimpleJdbcDaoSupport implements InitializingBean {

	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(UserPasswordEncoder.class);

	/** <code>SELECT ID, PWD_HASH FROM SECURITY_USER WHERE ID > 0 ORDER BY ID</code> */
	private static final String SQL_SELECT_ALL_USERS = "SELECT ID, PWD_HASH FROM SECURITY_USER WHERE ID > 0 ORDER BY ID";

	/** <code>UPDATE SECURITY_USER SET PWD_HASH=? WHERE ID = ?</code> */
	private static final String SQL_UPDATE_PASSWORD = "UPDATE SECURITY_USER SET PWD_HASH=? WHERE ID = ?";

	public void performEncoding() {
		final List<UserRow> users = getSimpleJdbcTemplate().query(SQL_SELECT_ALL_USERS, new ParameterizedRowMapper<UserRow>() {
			public UserRow mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserRow info = new UserRow();
				info.id = rs.getLong(1);
				info.password = rs.getString(2);
				return info;
			}
		});

		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		encoder.setEncodeHashAsBase64(true);

		getJdbcTemplate().batchUpdate(SQL_UPDATE_PASSWORD, new BatchPreparedStatementSetter() {
			public int getBatchSize() {
				return users.size();
			}

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				if (i % 1000 == 0) {
					logger.debug("processed "+i);
				}
				UserRow user = users.get(i);
				ps.setString(1, encoder.encodePassword(user.password,user.id));
				ps.setLong(2, user.id );
			}
		});
	}

	public static class UserRow {
		public Long id;
		public String password;
	}

}
