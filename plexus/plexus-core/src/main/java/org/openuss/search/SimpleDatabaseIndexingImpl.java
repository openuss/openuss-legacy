package org.openuss.search;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.springframework.beans.factory.InitializingBean;
import org.springmodules.lucene.index.document.handler.database.SqlDocumentHandler;
import org.springmodules.lucene.index.document.handler.database.SqlRequest;
import org.springmodules.lucene.index.factory.IndexFactory;
import org.springmodules.lucene.index.object.database.DatabaseIndexer;
import org.springmodules.lucene.index.object.database.DatabaseIndexingListener;
import org.springmodules.lucene.index.object.database.DefaultDatabaseIndexer;

public class SimpleDatabaseIndexingImpl implements InitializingBean, SimpleDatabaseIndexing {

	private static final Logger logger = Logger.getLogger(SimpleDatabaseIndexingImpl.class);

	private DataSource dataSource;
	private IndexFactory indexFactory;
	private DatabaseIndexer indexer;

	public void afterPropertiesSet() throws Exception {
		Validate.notNull(indexFactory, "indexFactory is required");
		this.indexer = new DefaultDatabaseIndexer(indexFactory);
		prepareDatabaseHandlers();
	}
	

	public void prepareDatabaseHandlers() throws Exception {
		SqlDocumentHandler sqlDocumentHandler = new SqlDocumentHandler() {

			@Override
			public Document getDocument(SqlRequest request, ResultSet rs) throws SQLException {
				Document document = new Document();
				document.add(new Field("IDENTIFIER", rs.getString("ID"), Field.Store.YES, Field.Index.UN_TOKENIZED));
				document.add(new Field("TYPE", rs.getString("DOMAINTYPE"), Field.Store.YES, Field.Index.UN_TOKENIZED));
				document.add(new Field("NAME", rs.getString("DOMAINNAME"), Field.Store.YES, Field.Index.UN_TOKENIZED));

				int count = rs.getMetaData().getColumnCount();
				StringBuilder sb = new StringBuilder();
				for (int col = 1; col <= count; col++) {
					String content = rs.getString(col);
					if (StringUtils.isNotBlank(content)) {
						sb.append(content);
						sb.append(" ");
					}
				}
				document.add(new Field("CONTENT", sb.toString(), Field.Store.YES, Field.Index.TOKENIZED));
				return document;
			}
		};
		String sqlFaculty = "SELECT ID,'FACULTY' as DOMAINTYPE, NAME AS DOMAINNAME, SHORTCUT, OWNERNAME, POSTCODE, CITY, TELEPHONE, TELEFAX, DESCRIPTION, EMAIL FROM LECTURE_FACULTY";
		String sqlEnrollment = "SELECT e.id, 'ENROLLMENT' as DOMAINTYPE, e.shortcut, e.description, s.name AS DOMAINNAME, s.description, p.name, p.description, f.name, f.ownername FROM LECTURE_ENROLLMENT e, LECTURE_SUBJECT s, lecture_period p, LECTURE_FACULTY f WHERE e.subject_fk = s.id AND e.period_fk = p.id AND e.faculty_fk = f.id;";
		
		this.indexer.registerDocumentHandler(new SqlRequest(sqlFaculty), sqlDocumentHandler);
		this.indexer.registerDocumentHandler(new SqlRequest(sqlEnrollment), sqlDocumentHandler);
	}

	/**
	 * {@inheritDoc}
	 */
	public void indexDatabase() {
		indexer.index(dataSource, true);
	}

	public void prepareListener() {
		DatabaseIndexingListener listener = new DatabaseIndexingListener() {

			public void afterIndexingRequest(SqlRequest request) {
				logger.debug(" -> request indexed.");
			}

			public void beforeIndexingRequest(SqlRequest request) {
				logger.debug("Indexing the ruest : " + request.getSql() + " ...");
			}

			public void onErrorIndexingRequest(SqlRequest request, Exception ex) {
				logger.error(" -> Error during the indexing : " + ex.getMessage(), ex);
			}

		};
		indexer.addListener(listener);
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DatabaseIndexer getIndexer() {
		return indexer;
	}

	public void setIndexer(DatabaseIndexer indexer) {
		this.indexer = indexer;
	}

	public IndexFactory getIndexFactory() {
		return indexFactory;
	}

	public void setIndexFactory(IndexFactory indexFactory) {
		this.indexFactory = indexFactory;
	}

}
