package org.openuss.migration.notification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;
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
public class EmailGeneratorPatch extends SimpleJdbcDaoSupport implements InitializingBean {

	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(EmailGenerator.class);

	private static final String SQL_SELECT_USERCODE = "SELECT id, code FROM SECURITY_USERCODE";
	private static final String SQL_UPDATE_USERCODE = "UPDATE SECURITY_USERCODE SET code = ? WHERE id = ?";

	private static final String SQL_SELECT_INSTITUTECODE = "SELECT id, code FROM SECURITY_INSTITUTECODE";
	private static final String SQL_UPDATE_INSTITUTECODE = "UPDATE SECURITY_INSTITUTECODE SET code = ? WHERE id = ?";

	public void patchCodes() {
		final List<Code> codes = getSimpleJdbcTemplate().query(SQL_SELECT_USERCODE, new ParameterizedRowMapper<Code>() {
			public Code mapRow(ResultSet rs, int rowNum) throws SQLException {
				Code code = new Code();
				code.id = rs.getLong(1);
				code.code = rs.getString(2);
				return code;
			}
		});

		final URLCodec urlCodec = new URLCodec();
			
		getJdbcTemplate().batchUpdate(SQL_UPDATE_USERCODE, new BatchPreparedStatementSetter() {
			public int getBatchSize() {
				return codes.size();
			}
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				try {
					ps.setString(1, urlCodec.decode(codes.get(i).code));
					ps.setLong(2, codes.get(i).id);
				} catch (DecoderException e) {
					logger.error(e);
				}
			}
		});

		final List<Code>instituteCodes = getSimpleJdbcTemplate().query(SQL_SELECT_INSTITUTECODE, new ParameterizedRowMapper<Code>() {
			public Code mapRow(ResultSet rs, int rowNum) throws SQLException {
				Code code = new Code();
				code.id = rs.getLong(1);
				code.code = rs.getString(2);
				return code;
			}
		});
		
		getJdbcTemplate().batchUpdate(SQL_UPDATE_INSTITUTECODE, new BatchPreparedStatementSetter() {
			public int getBatchSize() {
				return instituteCodes.size();
			}
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				try {
					ps.setString(1, urlCodec.decode(instituteCodes.get(i).code));
					ps.setLong(2, instituteCodes.get(i).id);
				} catch (DecoderException e) {
					logger.error(e);
				}
			}
		});
	}

	public static class Code {
		public long id;
		public String code;
	}

}
