package org.openuss.lecture;

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

/**
*
* @author Kai Stettner
* @author Malte Stockmann
*/
public class UniversityIndexer extends DomainIndexer {
	
	private static final String SPACE = " ";
	private static final String NEWLINE = "<br/>";
	
	private static final String DOMAINTYPE_VALUE = "university";

	private static final Logger logger = Logger.getLogger(UniversityIndexer.class);

	private UniversityDao universityDao;
	
	public void create() {
		final University university = getUniversity();
		if (university != null) {
			logger.debug("create index entry for university "+university.getName()+" ("+university.getId()+")");
			getLuceneIndexTemplate().addDocument(new DocumentCreator() {
				public Document createDocument() throws Exception {
					Document document = new Document();
					setFields(university, document);
					return document;
				}
			});
		}
	}

	public void update() {
		final University university = getUniversity();
		if (university != null) {
			logger.debug("update index entry for university "+university.getName()+" ("+university.getId()+")");
			delete();
			create();
		}
	}

	private University getUniversity() {
		Validate.notNull(getDomainObject(), "Parameter domainObject must not be null");
		Validate.notNull(getDomainObject().getId(), "Parameter domainObject.id must not be null");
		return universityDao.load(getDomainObject().getId());
	}

	private void setFields(final University university, Document document) {
		document.add(new Field(IDENTIFIER, String.valueOf(university.getId()), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(DOMAINTYPE, DOMAINTYPE_VALUE, Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(MODIFIED, 
				DateTools.dateToString(new Date(), Resolution.MINUTE), Field.Store.YES,
				Field.Index.UN_TOKENIZED));
		document.add(new Field(CONTENT, content(university), Field.Store.YES, Field.Index.TOKENIZED));
		document.add(new Field(DETAILS, details(university), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(NAME, name(university), Field.Store.YES, Field.Index.TOKENIZED));
		
		document.add(new Field(COURSE_TYPE_IDENTIFIER, "", Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(INSTITUTE_IDENTIFIER, "", Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(DEPARTMENT_IDENTIFIER, "", Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(UNIVERSITY_IDENTIFIER, String.valueOf(university.getId()), Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(PERIOD_IDENTIFIER, "", Field.Store.YES, Field.Index.UN_TOKENIZED));
		document.add(new Field(OFFICIAL_FLAG, String.valueOf(true), Field.Store.YES, Field.Index.UN_TOKENIZED));
	}
	
	private String name(final University university) {
		return university.getName()+" ("+university.getShortcut()+")";
	}
	
	private String details(final University university) {
		StringBuilder details = new StringBuilder();
		
		if(!StringUtils.isEmpty(university.getDescription()))
			details.append(NEWLINE + StringUtils.trimToEmpty(StringUtils.abbreviate(university.getDescription(), 200)));
		
		return details.toString();
	}
	
	private String content(final University university) {
		StringBuilder content = new StringBuilder();
		content.append(university.getId()+SPACE);
		content.append(StringUtils.trimToEmpty(university.getName())+SPACE);
		content.append(StringUtils.trimToEmpty(university.getShortcut())+SPACE);
		content.append(StringUtils.trimToEmpty(university.getDescription())+SPACE);

		return content.toString();
	}

	public UniversityDao getUniversityDao() {
		return universityDao;
	}

	public void setUniversityDao(UniversityDao universityDao) {
		this.universityDao = universityDao;
	}

}
