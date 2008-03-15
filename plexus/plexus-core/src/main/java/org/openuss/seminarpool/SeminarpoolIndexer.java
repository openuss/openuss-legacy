package org.openuss.seminarpool;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.DateTools.Resolution;
import org.openuss.search.DomainIndexer;
import org.springmodules.lucene.index.core.DocumentCreator;

public class SeminarpoolIndexer extends DomainIndexer {
	
	private static final String SPACE = " ";
	private static final String NEWLINE = "<br/>";

	private static final String DOMAINTYPE_VALUE = "seminarpool";

	private static final Logger logger = Logger.getLogger(SeminarpoolIndexer.class);

	private SeminarpoolDao seminarpoolDao;
	
	public void create() {
		final Seminarpool seminarpool = getSeminarpool();
		if (seminarpool != null) {
			logger.debug("create new index entry for seminarpool "+seminarpool.getName()+" ("+seminarpool.getId()+")");
			getLuceneIndexTemplate().addDocument(new DocumentCreator() {
				public Document createDocument() throws Exception {
					Document document = new Document();
					setFields(seminarpool, document);
					return document;
				}
			});
		}
	}

	public void update() {
		final Seminarpool seminarpool = getSeminarpool();
		if (seminarpool != null) {
			logger.debug("update index entry for department "+seminarpool.getName()+" ("+seminarpool.getId()+")");
			delete();
			create();
		}
	}

	private Seminarpool getSeminarpool() {
		Validate.notNull(getDomainObject(), "Parameter domainObject must not be null");
		Validate.notNull(getDomainObject().getId(), "Parameter domainObject.id must not be null");
		return seminarpoolDao.load(getDomainObject().getId());
	}

	private void setFields(final Seminarpool seminarpool, Document document) {
		document.add(new Field(IDENTIFIER, String.valueOf(seminarpool.getId()), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(DOMAINTYPE, DOMAINTYPE_VALUE, Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(MODIFIED, 
				DateTools.dateToString(new Date(), Resolution.MINUTE), Field.Store.YES,
				Field.Index.UN_TOKENIZED));
		document.add(new Field(CONTENT, content(seminarpool), Field.Store.YES, Field.Index.TOKENIZED));
		document.add(new Field(DETAILS, details(seminarpool), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(NAME, name(seminarpool), Field.Store.YES, Field.Index.TOKENIZED));
		
		document.add(new Field(COURSE_TYPE_IDENTIFIER, "", Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(INSTITUTE_IDENTIFIER, "", Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(DEPARTMENT_IDENTIFIER, String.valueOf(seminarpool.getId()), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(UNIVERSITY_IDENTIFIER, String.valueOf(seminarpool.getUniversity().getId()), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(PERIOD_IDENTIFIER, "", Field.Store.YES, Field.Index.UN_TOKENIZED));
	}
	
	private String name(final Seminarpool seminarpool) {
		return seminarpool.getName()+" ("+seminarpool.getShortcut()+")";
	}
	
	private String details(final Seminarpool seminarpool) {
		StringBuilder details = new StringBuilder();
		details.append(StringUtils.trimToEmpty(seminarpool.getUniversity().getName()+SPACE));
		details.append(NEWLINE);
		details.append(StringUtils.trimToEmpty(StringUtils.abbreviate(seminarpool.getDescription(), 200)));
		details.append(NEWLINE);
		details.append(NEWLINE);
		return details.toString();
	}
	
	private String content(final Seminarpool seminarpool) {
		StringBuilder content = new StringBuilder();
		content.append(seminarpool.getId()+SPACE);
		content.append(StringUtils.trimToEmpty(seminarpool.getName())+SPACE);
		content.append(StringUtils.trimToEmpty(seminarpool.getShortcut())+SPACE);
		content.append(StringUtils.trimToEmpty(seminarpool.getDescription())+SPACE);
		return content.toString();
	}

	public SeminarpoolDao getSeminarpoolDao() {
	    return seminarpoolDao;
	}

	public void setSeminarpoolDao(SeminarpoolDao seminarpoolDao) {
	    this.seminarpoolDao = seminarpoolDao;
	}

}
